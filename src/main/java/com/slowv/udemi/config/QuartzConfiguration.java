package com.slowv.udemi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {

//    @Bean
//    public JobDetail quartzJobDetail() {
//        final var clazz = ScanAccountJob.class;
//        return JobBuilder.newJob(clazz)
//                .withIdentity(clazz.isAnnotationPresent(JobName.class) ? clazz.getAnnotation(JobName.class).value() : clazz.getSimpleName(), "Authentication_group")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger quartzTrigger() {
//        return TriggerBuilder.newTrigger()
//                .withIdentity("Trigger_%s".formatted(ScanAccountJob.class.getName()), "Authentication_group")
//                .forJob(quartzJobDetail())
//                .withSchedule(
//                        SimpleScheduleBuilder.simpleSchedule()
//                                .withIntervalInSeconds(10)
//                                .repeatForever()
//                )
//                .build();
//    }
}
