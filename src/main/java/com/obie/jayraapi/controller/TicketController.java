package com.obie.jayraapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @GetMapping("/greet/{name}")
    public String greetMe(@PathVariable String name, @RequestParam Map<String, String> param) {

        return "Hello " + name + " " + param.get("lastname") + "!";
    }
}
