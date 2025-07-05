package com.drug.drug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "doctor/dashboard";
    }

    @GetMapping("/appointments")
    public String appointments() {
        return "doctor/appointments";
    }

    @GetMapping("/patients")
    public String patients() {
        return "doctor/patients";
    }

    @GetMapping("/assessments")
    public String assessments() {
        return "doctor/assessments";
    }

    @GetMapping("/resources")
    public String resources() {
        return "doctor/resources";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "doctor/notifications";
    }

    @GetMapping("/profile")
    public String profile() {
        return "doctor/profile";
    }
}
