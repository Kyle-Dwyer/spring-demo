package com.example.springdemo;

import com.example.springdemo.Entity.Result;
import com.example.springdemo.Entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/")
    public HttpEntity<String> testHello() {
        DemoController.LOGGER.info("hello");
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/")
    public ResponseEntity<Result> testHello(@RequestBody Student stu) {
        DemoController.LOGGER.info("hello," + stu.getName());
        return ResponseEntity.ok(new Result<>(1, "Hello," + stu.getName()));
    }
}
