package com.fontana.backend.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fontana/api/v1")
public class TestController {

    @GetMapping("/test")
    public String getMessage() {
        return "Hello";
    }
}
