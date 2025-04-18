package com.slowv.udemi.scheduler.job;

import com.slowv.udemi.entity.annotation.JobName;
import com.slowv.udemi.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component("ScanAccountJob")
@JobName("ScanAccountJob")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScanAccountJob implements Job, Serializable {

    AccountRepository accountRepository;

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        log.info("Scan Account Job");
        log.info("Job EXECUTE {}", context.getJobDetail().getKey());
        final var dataMap = context.getMergedJobDataMap();
        final var emails = List.of(((String) dataMap.get("emailScan")).split(","));
        accountRepository.findAllByEmailIn(emails).forEach(account -> {
            log.info("Account SCAN {}", account);
        });
        log.info("Job COMPLETED {}", context.getJobDetail().getKey());
    }
}
