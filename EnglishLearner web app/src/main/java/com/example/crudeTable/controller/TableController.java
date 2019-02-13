package com.example.crudeTable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TableController {
    @GetMapping("/")
    public String returnTable () {
        return "table";
    }

    @GetMapping("/userTest")
    public String returnUserTest() {
        return "userTest";
    }

    @GetMapping("/testResult")
    public String returnTestResult() { return "testResult"; }
}