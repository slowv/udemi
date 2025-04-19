package com.slowv.udemi.service.impl;

import com.slowv.udemi.common.utils.JobUtils;
import com.slowv.udemi.service.JobService;
import com.slowv.udemi.service.dto.JobDetailDto;
import com.slowv.udemi.service.dto.request.CreateJobRequest;
import com.slowv.udemi.service.dto.request.JobConfigRequest;
import com.slowv.udemi.service.dto.request.StartJobRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final Scheduler scheduler;
    private final ApplicationContext context;

    @SuppressWarnings("unchecked")
    @Override
    @SneakyThrows
    public JobDetailDto create(final CreateJobRequest request) {
        final var jobClazz = (Class<? extends Job>) context.getBean(request.getJobId()).getClass();
        final var jobDetail = JobUtils.createJobScanAccount(
                jobClazz,
                request.getJobGroup(),
                request.getJobDescription(),
                request.getEmails()
        );
        scheduler.addJob(jobDetail, true);

        final var jobDetailDto = new JobDetailDto();
        jobDetailDto.setJobDescription(jobDetail.getDescription());
        jobDetailDto.setJobName(jobDetail.getKey().getName());
        jobDetailDto.setJobGroup(jobDetail.getKey().getGroup());
        return jobDetailDto;
    }

    @Override
    @SneakyThrows
    public String start(final StartJobRequest request) {
        final var jobDetail = JobUtils.getJobDetail(request.getJobId(), request.getJobGroup(), scheduler);
        final var trigger = JobUtils.createTriggerWithTriggerIntervalSeconds(
                jobDetail,
                request.getTriggerName(),
                request.getTriggerGroup(),
                request.getIntervalInSeconds()
        );
        scheduler.scheduleJob(trigger);
        return "Job %s started!".formatted(jobDetail.getKey());
    }

    @Override
    public String pause(final List<JobConfigRequest> jobsConfig) {
        final var statusJob = new ArrayList<String>();
        jobsConfig.forEach(config -> {
            try {
                final var jobKey = JobKey.jobKey(config.getJobName(), config.getJobGroup());
                if (scheduler.checkExists(jobKey)) {
                    scheduler.pauseJob(jobKey);
                    statusJob.add("Job %s - group %s paused!".formatted(config.getJobName(), config.getJobGroup()));
                }
            } catch (Exception ex) {
                statusJob.add("Job %s - group %s cannot be paused!".formatted(config.getJobName(), config.getJobGroup()));
            }
        });

        return String.join(" | ", statusJob);
    }

    @Override
    public String resume(final List<JobConfigRequest> jobConfig) {
        return jobConfig.stream()
                .map(job -> JobKey.jobKey(job.getJobName(), job.getJobGroup()))
                .map(key -> {
                    try {
                        if (scheduler.checkExists(key)) {
                            scheduler.resumeJob(key);
                            return "Job %s - group %s resume!".formatted(key.getName(), key.getGroup());
                        }
                        return "Job %s - group %s not found!".formatted(key.getName(), key.getGroup());
                    } catch (Exception ex) {
                        return "Job %s - group %s has error!".formatted(key.getName(), key.getGroup());
                    }
                })
                .collect(Collectors.joining(" | "));
    }

    @Override
    public String delete(final List<JobConfigRequest> config) {
        return config.stream()
                .map(job -> JobKey.jobKey(job.getJobName(), job.getJobGroup()))
                .map(key -> {
                    try {
                        final var isDeleted = scheduler.deleteJob(key);
                        return isDeleted ? "Job %s deleted!".formatted(key) : "Job %s cant deleted!";
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.joining(" | "));
    }

    @Override
    public List<JobDetailDto> getStatus(final List<JobConfigRequest> configs) {
        return configs.stream()
                .map(JobUtils::getJobKey)
                .flatMap(jobKey -> {      // FlatMap về Pair<JobKey, Trigger>
                    try {
                        return scheduler.getTriggersOfJob(jobKey)
                                .stream()
                                .map(trigger -> Pair.of(jobKey, trigger));
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(pair -> {
                    final var jobKey = pair.getFirst();
                    final var trigger = pair.getSecond();

                    Trigger.TriggerState state;
                    try {
                        state = scheduler.getTriggerState(trigger.getKey());
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }

                    final var dto = new JobDetailDto();
                    dto.setJobName(jobKey.getName());
                    dto.setJobGroup(jobKey.getGroup());
                    dto.setTriggerName(trigger.getKey().getName());
                    dto.setTriggerGroup(trigger.getKey().getGroup());
                    dto.setStatus(String.valueOf(state));
                    return dto;
                })
                .toList();
    }
}
