package com.example.springps4.persistence.dao.base;

import com.example.springps4.constants.SearchConstants;
import com.example.springps4.model.request.base.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public abstract class AbstractDatabaseDao {
    protected final DataSource dataSource;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public AbstractDatabaseDao(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    protected void addSearchParams(MapSqlParameterSource queryParams, SearchRequest searchRequest){
        queryParams.addValue(SearchConstants.SIZE, searchRequest.getSize());
        queryParams.addValue(SearchConstants.INDEX, searchRequest.getIndex());
    }
}
