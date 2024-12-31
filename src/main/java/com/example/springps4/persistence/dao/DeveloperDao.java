package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class DeveloperDao extends AbstractDatabaseDao {
    private static final String SELECT_DEVELOPER_DETAILS = """
             select * from developers ORDER BY developer_id ASC;
            """;

    private static final String SELECT_DISTINCT_DEVELOPERS = """
            SELECT DISTINCT developer FROM developers ORDER BY developer ASC;
            """;

    private static final String NUMBER_OF_GAMES_BY_DEVELOPER = """
            select count(game_id) from developers where developer=:developer;
            """;

    private static final String NUMBER_OF_GAMES_GROUP_BY_DEVELOPER = """
            SELECT developer, COUNT(game_id) AS game_count
            FROM developers
            GROUP BY developer
            ORDER BY developer ASC;
            """;

    private static final String DELETE_DEVELOPER_BY_NAME = """
            DELETE FROM developers WHERE developer=:developer;
            """;

    private static final String INSERT_DEVELOPER = """
            INSERT INTO developers (developer, game_id) VALUES (:developer, :game_id);
            """;

    private static final String UPDATE_DEVELOPER_NAME = """
            UPDATE developers SET developer=:developer where game_id=:game_id;
            """;

    private final DeveloperMapper developerMapper;

    @Autowired
    public DeveloperDao(DataSource dataSource, DeveloperMapper developerMapper) {
        super(dataSource);
        this.developerMapper = developerMapper;
    }

    public List<DeveloperResponse> getDeveloperDetails(){
        return jdbcTemplate.query(SELECT_DEVELOPER_DETAILS, developerMapper);
    }


    public List<String> getDistinctDevelopers(){
        return jdbcTemplate.query(SELECT_DISTINCT_DEVELOPERS, (rs, rowNum) -> rs.getString("developer"));
    }

    public Integer getNumberOfGamesForSpecificDeveloper(String developer){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("developer", developer);

        Integer count =  namedParameterJdbcTemplate.queryForObject(NUMBER_OF_GAMES_BY_DEVELOPER, queryParams, Integer.class);
        System.out.println(count);
        return count != null ? count : 0;
    }

    public List<DeveloperResponse> getNumberOfGamesGroupByDeveloper() {
        return jdbcTemplate.query(
                NUMBER_OF_GAMES_GROUP_BY_DEVELOPER,
                (rs, rowNum) -> {
                    DeveloperResponse developerResponse = new DeveloperResponse();
                    developerResponse.setDeveloper(rs.getString("developer"));
                    developerResponse.setGame_id(rs.getInt("game_count")); // Map game_count to game_id
                    return developerResponse;
                }
        );
    }

    public Integer deleteByDeveloperName(String developer){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("developer", developer);

        if (namedParameterJdbcTemplate.update(DELETE_DEVELOPER_BY_NAME,queryParams) > 0){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Integer insertDevelopers(DeveloperRequest developerRequest){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("developer", developerRequest.getDeveloper());
        queryParams.addValue("game_id", developerRequest.getGame_id());

        try {
            // Attempt to insert the game into the database
            return namedParameterJdbcTemplate.update(INSERT_DEVELOPER, queryParams);
        } catch (BadSqlGrammarException e) {
            System.out.println("Error");
            return 0; // Return 0 to indicate failure due to duplicate title
        }
    }

    public Integer updateDevelopersNameByGameId(DeveloperRequest developerRequest){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("developer", developerRequest.getDeveloper());
        queryParams.addValue("game_id", developerRequest.getGame_id());

        return namedParameterJdbcTemplate.update(UPDATE_DEVELOPER_NAME, queryParams);
    }
}
