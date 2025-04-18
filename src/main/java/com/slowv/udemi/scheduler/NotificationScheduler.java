package com.slowv.udemi.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final SimpMessageSendingOperations messagingTemplate;

//    @Scheduled(cron = "0 * * * * *")
//    public void execute() {
//        log.info("Running NotificationScheduler... {}", "8H ngày mai yêu cầu mọi người đóng tiền điện.");
//        messagingTemplate.convertAndSend("/notifications", "8H ngày mai yêu cầu mọi người đóng tiền điện.");
//    }
}
