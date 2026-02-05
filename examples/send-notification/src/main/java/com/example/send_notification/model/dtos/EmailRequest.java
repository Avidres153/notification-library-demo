package com.example.send_notification.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private List<String> to;
    private String from;
    private List<String> copyTo;
    private List<String> hiddenCopyTo;
    private String subject;
    private String body;
    private List<Object> attachments;
}
