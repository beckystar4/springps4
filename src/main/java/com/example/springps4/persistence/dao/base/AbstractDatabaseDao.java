package com.example.springps4.persistence.dao.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public abstract class AbstractDatabaseDao {
    protected final DataSource dataSource;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public AbstractDatabaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
