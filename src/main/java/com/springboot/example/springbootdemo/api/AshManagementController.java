package com.springboot.example.springbootdemo.api;

import com.springboot.example.springbootdemo.model.UserMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/management/api/v1/mysite")
public class AshManagementController {

    List<UserMessage> userMessages = List.of(
            new UserMessage("1", "ashwin", "ashwin@gmail.com", "9999999999", "Hi!!"),
            new UserMessage("2", "chandraa", "chandraa@gmail.com", "8888888888", "hello !!")
    );

    @GetMapping(path = "/messages")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<UserMessage> getAllUserMessages() {
        return userMessages;
    }

    @PostMapping(path = "/add/message")
    @PreAuthorize("hasAuthority('user:write')")
    public void newMessage(@RequestBody UserMessage message) {
        System.out.println(message);
    }

    @DeleteMapping(path = "/delete/{messageId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteMessage(@PathVariable("messageId") String messageId) {
        System.out.println(messageId);
    }

    @PutMapping(path = "/update/{messageId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void updateMessage(@PathVariable("messageId") String messageId, @RequestBody UserMessage userMessage) {
        System.out.println(String.format("%s %s", messageId, userMessage));
    }
}
