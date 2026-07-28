package com.example.backend_lab;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/chat")
    public String Hello() {
        return "chat";
    }

}
