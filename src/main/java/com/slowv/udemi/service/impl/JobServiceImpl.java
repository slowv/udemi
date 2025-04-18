package com.slowv.udemi.service.impl;

import com.slowv.udemi.common.utils.JobUtils;
import com.slowv.udemi.service.JobService;
import com.slowv.udemi.service.dto.JobDetailDto;
import com.slowv.udemi.service.dto.request.CreateJobRequest;
import com.slowv.udemi.service.dto.request.StartJobRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
}
