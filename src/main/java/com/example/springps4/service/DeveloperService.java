package com.example.springps4.service;

import com.example.springps4.persistence.dao.DeveloperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperService {
    private final DeveloperDao developerDao;

    @Autowired
    public DeveloperService(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }
}
