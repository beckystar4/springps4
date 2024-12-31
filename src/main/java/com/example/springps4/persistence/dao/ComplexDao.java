package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.mapper.GameMapper;
import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import com.example.springps4.utility.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class ComplexDao extends AbstractDatabaseDao {
    private final PublisherMapper publisherMapper;
    private final GameMapper gameMapper;
    private final DeveloperMapper developerMapper;

    private static final String SELECT_GAME_TITLE_PUBLISHER = """
             select title, publisher from games inner join publishers USING(game_id);
            """;

    private static final String SELECT_GAME_TITLE_DEVELOPERS = """
             select title, developer from games inner join developers USING(game_id);
            """;

    private static final String SELECT_GAME_TITLE_PUBLISHERS_DEVELOPERS = """
            select title, developer, publisher 
            FROM games
            INNER JOIN developers ON games.game_id = developers.game_id
            INNER JOIN publishers ON games.game_id = publishers.game_id;
            """;

    private static final String SELECT_ALL_DETAILS = """
           SELECT title, genres, millions_of_copies_sold, release_date, publisher, developer
            FROM games
            INNER JOIN developers ON games.game_id = developers.game_id
            INNER JOIN publishers ON games.game_id = publishers.game_id
            """;

    private static final String SELECT_ALL_DETAILS_BY_TITLE = """
           SELECT title, genres, millions_of_copies_sold, release_date, publisher, developer
            FROM games
            INNER JOIN developers ON games.game_id = developers.game_id
            INNER JOIN publishers ON games.game_id = publishers.game_id
            WHERE title=:title;
            """;

    private static final String NUMBER_OF_GENRES_BY_PUBLISHER = """
             select count(distinct genres) from games inner join publishers USING(game_id) where publisher=:publisher;
            """;

    private static final String SELECT_GENRES_BY_PUBLISHER = """
             select distinct genres from games inner join publishers USING(game_id) where publisher=:publisher;
            """;

    //Batch insert
    private static final String INSERT_GAMES = """
            INSERT INTO games (title, genres, millions_of_copies_sold, release_date) 
            VALUES (:title, :genres, :millions_of_copies_sold, :release_date);
            """;
    private static final String GET_GAME_DETAILS = """
            SELECT * from games where title=:title;
            """;
    private static final String INSERT_PUBLISHER = """
            INSERT INTO publishers (publisher, game_id) 
            VALUES (:publisher, select game_id from games where game_id=:game_id));
            """;
    private static final String INSERT_DEVELOPERS = """
            INSERT INTO developers (developer, game_id) 
            VALUES (:developer, select game_id from games where game_id=:game_id));
            """;

    @Autowired
    public ComplexDao(DataSource dataSource, PublisherMapper publisherMapper, GameMapper gameMapper, DeveloperMapper developerMapper) {
        super(dataSource);  // Pass the dataSource to the superclass constructor
        this.publisherMapper = publisherMapper;
        this.gameMapper = gameMapper;
        this.developerMapper = developerMapper;
    }

    public List<Map<String, String>> getGameTitleAndPublishers(){
        return namedParameterJdbcTemplate.query(SELECT_GAME_TITLE_PUBLISHER,
                (rs, rowNum) -> {
                    Map<String, String> result = new java.util.HashMap<>();

                    String title = rs.getString("title");
                    String publisher = rs.getString("publisher");

                    // Put the values into the map
                    result.put("title", title);  // Store title in the map
                    result.put("publisher", publisher);  // Store publisher in the map
                    return result;
                });
    }

    public List<Map<String, String>> getGameTitleAndDevelopers(){
        return namedParameterJdbcTemplate.query(SELECT_GAME_TITLE_DEVELOPERS,
                (rs, rowNum) -> {
                    Map<String, String> result = new java.util.HashMap<>();

                    String title = rs.getString("title");
                    String developer = rs.getString("developer");

                    // Put the values into the map
                    result.put("title", title);  // Store title in the map
                    result.put("developer", developer);
                    return result;
                });
    }

    public List<Map<String, String>> getGameTitleDevelopersAndPublishers(){
        return namedParameterJdbcTemplate.query(SELECT_GAME_TITLE_PUBLISHERS_DEVELOPERS,
                (rs, rowNum) -> {
                    Map<String, String> result = new java.util.HashMap<>();

                    String title = rs.getString("title");
                    String developer = rs.getString("developer");
                    String publisher = rs.getString("publisher");

                    // Put the values into the map
                    result.put("title", title);  // Store title in the map
                    result.put("developer", developer);
                    result.put("publisher", publisher);
                    return result;
                });
    }

    public List<Map<String, String>> getDetails(){
        return namedParameterJdbcTemplate.query(SELECT_ALL_DETAILS,
                (rs, rowNum) -> {
                    Map<String, String> result = new java.util.HashMap<>();

                    String title = rs.getString("title");
                    String genres = rs.getString("genres");
                    Integer millions_of_copies_sold = rs.getInt("millions_of_copies_sold");
                    LocalDate release_date =  DateTimeUtil.sqlDateToLocalDate(rs.getDate("release_date"));
                    String developer = rs.getString("developer");
                    String publisher = rs.getString("publisher");

                    // Put the values into the map
                    result.put("title", title);  // Store title in the map
                    result.put("genres", genres);
                    result.put("millions_of_copies_sold", millions_of_copies_sold.toString());
                    result.put("release_date", release_date.toString());
                    result.put("developer", developer);
                    result.put("publisher", publisher);
                    return result;
                });
    }

    public List<Map<String, String>> getDetailsByTitle(String title){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("title", title);

        return namedParameterJdbcTemplate.query(SELECT_ALL_DETAILS_BY_TITLE, queryParams,
                (rs, rowNum) -> {
                    Map<String, String> result = new java.util.HashMap<>();

                    String titleResult = rs.getString("title");
                    String genres = rs.getString("genres");
                    Integer millions_of_copies_sold = rs.getInt("millions_of_copies_sold");
                    LocalDate release_date =  DateTimeUtil.sqlDateToLocalDate(rs.getDate("release_date"));
                    String developer = rs.getString("developer");
                    String publisher = rs.getString("publisher");

                    // Put the values into the map
                    result.put("title", titleResult);  // Store title in the map
                    result.put("genres", genres);
                    result.put("millions_of_copies_sold", millions_of_copies_sold.toString());
                    result.put("release_date", release_date.toString());
                    result.put("developer", developer);
                    result.put("publisher", publisher);
                    return result;
                });
    }

    public Integer batchInsert(GameRequest gameRequest, String publisher, String developer){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        String title = gameRequest.getTitle();
        queryParams.addValue("title", title);

        String genres = gameRequest.getGenres();
        Integer copies_sold = gameRequest.getMillions_of_copies_sold();
        LocalDate release_date = gameRequest.getRelease_date();
        queryParams.addValue("genres", genres);
        queryParams.addValue("millions_of_copies_sold", copies_sold);
        queryParams.addValue("release_date", release_date);
        try {
            // Attempt to insert the game into the database
            if(namedParameterJdbcTemplate.update(INSERT_GAMES, queryParams) > 0){
               GameResponse result = namedParameterJdbcTemplate.queryForObject(GET_GAME_DETAILS, queryParams,gameMapper);
               if (result != null){
                   MapSqlParameterSource publisherParams = new MapSqlParameterSource();
                   publisherParams.addValue("game_id", result.getGame_id());
                   publisherParams.addValue("publisher", publisher);
                   if(namedParameterJdbcTemplate.update(INSERT_PUBLISHER, publisherParams)>1) {
                       MapSqlParameterSource developerParams = new MapSqlParameterSource();
                       publisherParams.addValue("game_id", result.getGame_id());
                       publisherParams.addValue("developer", developer);
                       if (namedParameterJdbcTemplate.update(INSERT_DEVELOPERS, publisherParams) > 1) {
                           return 1;
                       }
                   }
               }
            }
        } catch (DuplicateKeyException e) {
            // Handle the case where the title already exists
            // Log the exception or handle it gracefully
            System.out.println("Error: Duplicate title, insertion failed.");
            return 0; // Return 0 to indicate failure due to duplicate title
        }
        return 0;
    }

}
