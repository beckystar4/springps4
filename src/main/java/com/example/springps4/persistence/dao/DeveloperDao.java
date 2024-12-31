package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DeveloperDao extends AbstractDatabaseDao {
    private final DeveloperMapper developerMapper;

    @Autowired
    public DeveloperDao(DataSource dataSource, DeveloperMapper developerMapper) {
        super(dataSource);
        this.developerMapper = developerMapper;
    }
}
