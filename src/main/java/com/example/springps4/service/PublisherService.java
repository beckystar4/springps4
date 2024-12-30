package com.example.springps4.service;

import com.example.springps4.persistence.dao.PublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherDao publisherDao;

    @Autowired
    public PublisherService(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }
}
