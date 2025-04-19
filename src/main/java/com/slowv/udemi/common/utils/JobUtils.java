package com.slowv.udemi.common.utils;

import com.slowv.udemi.entity.annotation.JobName;
import com.slowv.udemi.service.dto.request.JobConfigRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.List;

@UtilityClass
public class JobUtils {

    public JobDetail createJobScanAccount(
            Class<? extends Job> jobClazz,
            String jobGroup,
            String jobDescription,
            List<String> emails
    ) {
        final var jobDataMap = new JobDataMap();
        jobDataMap.put("emailScan", String.join(",", emails));

        return JobBuilder
                .newJob(jobClazz)
                .withIdentity(getJobName(jobClazz), jobGroup)
                .withDescription(jobDescription)
                .usingJobData(jobDataMap)
                .storeDurably(true)
                .build();
    }

    @SneakyThrows
    public JobDetail getJobDetail(String jobName, String group, Scheduler scheduler) {
        return scheduler.getJobDetail(JobKey.jobKey(jobName, group));
    }

    @SneakyThrows
    public JobDetail getJobDetail(String key, Scheduler scheduler) {
        return scheduler.getJobDetail(JobKey.jobKey(key));
    }

    public Trigger createTriggerWithTriggerIntervalSeconds(
            final JobDetail jobDetail,
            final @NotBlank String triggerName,
            final @NotBlank String triggerGroup,
            final int intervalInSeconds) {

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerName, triggerGroup)
                .usingJobData(jobDetail.getJobDataMap())
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(intervalInSeconds)
                                .repeatForever()
                )
                .build();
    }

    private String getJobName(Class<? extends Job> clazz) {
        return clazz.isAnnotationPresent(JobName.class) ? clazz.getAnnotation(JobName.class).value() : clazz.getSimpleName();
    }

    public JobKey getJobKey(JobConfigRequest request) {
        return JobKey.jobKey(request.getJobName(), request.getJobGroup());
    }
}
