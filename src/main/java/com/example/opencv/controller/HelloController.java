package com.example.opencv.controller;

import com.example.opencv.bean.Cat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String Hello() {
        return "hello baby";
    }

    @RequestMapping("/getCat")
    public Cat getCat() {
        Cat cat = new Cat();
        cat.setId(1);
        cat.setName("张三");
        return cat;
    }
}
