package com.example.springps4.service;

import com.example.springps4.persistence.dao.ComplexDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplexService {
    private final ComplexDao complexDao;

    @Autowired
    public ComplexService(ComplexDao complexDao) {
        this.complexDao = complexDao;
    }
}
