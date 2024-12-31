package com.example.springps4.mapper;

import com.example.springps4.model.response.DeveloperResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DeveloperMapper implements RowMapper<DeveloperResponse> {
    @Override
    public DeveloperResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        DeveloperResponse developerResponse = new DeveloperResponse();
        developerResponse.setDeveloper_id(rs.getLong("developer_id"));
        developerResponse.setDeveloper(rs.getString("developer"));
        developerResponse.setGame_id(rs.getInt("game_id"));
        return developerResponse;
    }
}
