package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class GameDao extends AbstractDatabaseDao {
    private final GameMapper gameMapper;

    @Autowired
    public GameDao(DataSource dataSource, GameMapper gameMapper) {
        super(dataSource);
        this.gameMapper = gameMapper;
    }

    private static final String SELECT_GAME_DETAILS = """
            SELECT * from game where title= :title;
            """;

    public GameResponse getGameDetailsByTitle(String title){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("title", title);
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_GAME_DETAILS, queryParams, gameMapper);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no game was found
            return null; // or a default response
        }
    }
}
