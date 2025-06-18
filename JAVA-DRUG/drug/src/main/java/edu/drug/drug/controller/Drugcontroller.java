package edu.drug.drug.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class Drugcontroller {
    @RequestMapping("/")
    public String index(){
    return "index";
    }
}