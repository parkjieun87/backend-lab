package com.example.backend_lab;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/step1")
    public String step1() {
        return "step1_selector_data";
    }

    @GetMapping("/step2")
    public String step2() {
        return "step2_selector_data";
    }

}
