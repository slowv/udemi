package com.slowv.udemi.web.ws;

import com.slowv.udemi.service.dto.MessageRecord;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/chat")
public class ChatActivity {

    @MessageMapping("/{id}")
    @SendTo("/chat/{id}/receive")
    public MessageRecord message(@DestinationVariable String id, MessageRecord message, @Header("X_DEVICE") String device) {
        return message;
    }
}
