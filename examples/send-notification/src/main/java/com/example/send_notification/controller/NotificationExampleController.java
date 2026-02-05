package com.example.send_notification.controller;

import com.example.send_notification.model.dtos.EmailRequest;
import com.example.send_notification.service.NotificationExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
public class NotificationExampleController {

    @Autowired
    private NotificationExampleService notificationExampleService;


    @PostMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmailNotification(@RequestBody EmailRequest request){

        return ResponseEntity.ok(notificationExampleService.sendEmail(request));

    }


}
