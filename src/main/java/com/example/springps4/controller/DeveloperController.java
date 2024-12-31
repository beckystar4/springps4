package com.example.springps4.controller;

import com.example.springps4.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/developers")
public class DeveloperController {
    private final PublisherService publisherService;

    @Autowired
    public DeveloperController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
}
