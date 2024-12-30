package com.example.springps4.mapper;

import com.example.springps4.model.response.PublisherResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PublisherMapper implements RowMapper<PublisherResponse> {
    @Override
    public PublisherResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setPublisher_id(rs.getLong("publisher_id"));
        publisherResponse.setPublisher(rs.getString("publisher"));
        publisherResponse.setGame_id(rs.getInt("game_id"));
        return publisherResponse;
    }
}
