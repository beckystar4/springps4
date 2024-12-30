package com.example.springps4.controller;

import com.example.springps4.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/publishers")
public class PublisherController {
    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }
}
