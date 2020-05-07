package com.springboot.example.springbootdemo.api;

import com.springboot.example.springbootdemo.model.UserMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/mysite")
public class AshController {

    @PostMapping(path = "")
    public ResponseEntity sendEmail(@RequestBody @Valid UserMessage userMessage) {

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(path = "/hello")
    public String getString() {
        return "hello";
    }

}
