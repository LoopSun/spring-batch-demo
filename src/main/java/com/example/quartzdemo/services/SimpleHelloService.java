package com.example.quartzdemo.services;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SimpleHelloService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public String hello(String name) {
        return dateFormat.format(new Date()) + name;
    }
}
