package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class GameDao extends AbstractDatabaseDao {

    private static final String SELECT_GAME_DETAILS = """
            SELECT * from games where title=:title;
            """;

    private final GameMapper gameMapper;

    @Autowired
    public GameDao(DataSource dataSource, GameMapper gameMapper) {
        super(dataSource);
        this.gameMapper = gameMapper;
    }

    public GameResponse getGameDetailsByTitle(String title){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("title", title);
        return namedParameterJdbcTemplate.queryForObject(SELECT_GAME_DETAILS,queryParams,gameMapper);
    }
}
