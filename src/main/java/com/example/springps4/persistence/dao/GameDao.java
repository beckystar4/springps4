package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<GameResponse> getAllGameDetails(GameRequest gameRequest){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("title", gameRequest.getGame_id());
        return namedParameterJdbcTemplate.query(SELECT_GAME_DETAILS, queryParams, gameMapper);
    }
}
