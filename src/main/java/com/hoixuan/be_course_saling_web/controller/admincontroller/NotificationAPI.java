package com.hoixuan.be_course_saling_web.controller.admincontroller;
import com.hoixuan.be_course_saling_web.model.Notification;
import com.hoixuan.be_course_saling_web.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class NotificationAPI {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification")
    public ResponseEntity<List<Notification>> getAll() {
        return new ResponseEntity<>(notificationService.getNewNotification(), HttpStatus.OK);
    }
    @PostMapping("/notification/done")
    public ResponseEntity<Notification> save(@RequestBody List<Notification> notifications) {
        for (Notification n:notifications) {
            n.setStatus(true);
            notificationService.save(n);
        }
        return new ResponseEntity<>(new Notification(), HttpStatus.OK);
    }
}