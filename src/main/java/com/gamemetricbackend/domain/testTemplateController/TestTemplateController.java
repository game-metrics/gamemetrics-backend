package com.gamemetricbackend.domain.testTemplateController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestTemplateController {
    @RequestMapping("/")
    public String index(){
        System.out.println("testing index");
        return "index.html";
    }
}
