package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ComplexDao extends AbstractDatabaseDao {
    private final PublisherMapper publisherMapper;
    private final GameMapper gameMapper;

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

    @Autowired
    public ComplexDao(DataSource dataSource, PublisherMapper publisherMapper, GameMapper gameMapper) {
        super(dataSource);  // Pass the dataSource to the superclass constructor
        this.publisherMapper = publisherMapper;
        this.gameMapper = gameMapper;
    }
}
