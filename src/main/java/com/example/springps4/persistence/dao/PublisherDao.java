package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.model.response.PublisherResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class PublisherDao extends AbstractDatabaseDao {
    private static final String SELECT_PUBLISHER_DETAILS = """
             select * from publishers;
            """;

    private static final String SELECT_DISTINCT_PUBLISHERS = """
            SELECT DISTINCT publisher FROM publishers ORDER BY publisher ASC;
            """;

    private static final String NUMBER_OF_GAMES_BY_PUBLISHER = """
            select count(game_id) from publishers where publisher=:publisher;
            """;

    private static final String NUMBER_OF_GAMES_GROUP_BY_PUBLISHER = """
            SELECT publisher, COUNT(game_id) AS game_count
            FROM publishers
            GROUP BY publisher
            ORDER BY publisher ASC;
            """;

    // Most Recent game by publisher
    // Oldest game by publisher
    // Insert
        // Complex: Insert Game and Publisher
    // Update
    // Delete
    // Publisher with most games sold OR most copies sold for that publisher

    private final PublisherMapper publisherMapper;

    @Autowired
    public PublisherDao(DataSource dataSource, PublisherMapper publisherMapper) {
        super(dataSource);
        this.publisherMapper = publisherMapper;
    }

    public List<PublisherResponse> getPublisherDetails(){
        return jdbcTemplate.query(SELECT_PUBLISHER_DETAILS, publisherMapper);
    }

    public List<String> getDistinctPublishers(){
        return jdbcTemplate.query(SELECT_DISTINCT_PUBLISHERS, (rs, rowNum) -> rs.getString("publisher"));
    }

    public Integer getNumberOfGamesForSpecificPublisher(String publisher){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("publisher", publisher);

        Integer count =  namedParameterJdbcTemplate.queryForObject(NUMBER_OF_GAMES_BY_PUBLISHER, queryParams, Integer.class);
        System.out.println(count);
        return count != null ? count : 0;
    }

    public List<PublisherResponse> getNumberOfGamesGroupByPublisher() {
        return jdbcTemplate.query(
                NUMBER_OF_GAMES_GROUP_BY_PUBLISHER,
                (rs, rowNum) -> {
                    PublisherResponse publisherResponse = new PublisherResponse();
                    publisherResponse.setPublisher(rs.getString("publisher"));
                    publisherResponse.setGame_id(rs.getInt("game_count")); // Map game_count to game_id
                    return publisherResponse;
                }
        );
    }
}
