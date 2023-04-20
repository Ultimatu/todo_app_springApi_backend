package com.todo.backendrestcrud.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// This controller is used to serve the index.html file
@Controller
public class SpringHomeController {

    // This is the default route
    @GetMapping("/**")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        return "home";
    }

}