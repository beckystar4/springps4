package com.example.springps4.mapper;

import com.example.springps4.model.response.GameResponse;
import com.example.springps4.utility.DateTimeUtil;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GameMapper implements RowMapper<GameResponse> {
    @Override
    public GameResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setTitle(rs.getString("title"));
        gameResponse.setGenres(rs.getString("genres"));
        gameResponse.setMillions_of_copies_sold(rs.getInt("millions_of_copies_sold"));
        gameResponse.setRelease_date(DateTimeUtil.sqlDateToLocalDate(rs.getDate("release_date")));
        return gameResponse;
    }
}
