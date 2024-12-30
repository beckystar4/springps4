package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PublisherDao extends AbstractDatabaseDao {
    private static final String SELECT_PUBLISHER_DETAILS = """
             select * from publisher;
            """;

    private static final String SELECT_DISTINCT_PUBLISHERS = """
            SELECT DISTINCT publisher FROM publishers ORDER BY publisher ASC;
            """;

    private static final String NUMBER_OF_GAMES_BY_PUBLISHER = """
            select count(game_id) from publishers where publisher=:publisher;"
            """;

    private static final String SELECT_GAME_TITLE_PUBLISHER = """
             select title, publisher from games inner join publishers USING(game_id);
            """;

    private static final String SELECT_GAME_PUBLISHER_DETAILS = """
             select * from games inner join publishers USING(game_id);
            """;

    private static final String NUMBER_OF_GENRES_BY_PUBLISHER = """
             select count(distinct genres) from games inner join publishers USING(game_id) where publisher=:publisher;
            """;

    private static final String SELECT_GENRES_BY_PUBLISHER = """
             select distinct genres from games inner join publishers USING(game_id) where publisher=:publisher;
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
}
