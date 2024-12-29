package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameDao extends AbstractDatabaseDao {

    private static final String SELECT_GAME_BY_TITLE = """
            SELECT * FROM games WHERE title=:title;
            """;

    private static final String SELECT_GAME_BY_ID = """
            SELECT * FROM games WHERE game_id=:game_id;
            """;

    private static final String SELECT_GAMES_DETAILS = """
            SELECT * FROM games ORDER BY game_id;
            """;

    private static final String SELECT_ALL_BY_RELEASE_DATE = """
            SELECT * FROM games ORDER BY release_date;
            """;

    private static final String SELECT_DISTINCT_GENRES = """
            SELECT DISTINCT genres FROM games ORDER BY genres ASC;
            """;

    private static final String INSERT_GAMES = """
            INSERT INTO games (title, genres, millions_of_copies_sold, release_date) 
            VALUES (':title', ':genres', :millions_of_copies_sold, 'release_date');
            """;

    private static final String UPDATE_GAME_NAME = """
            UPDATE games SET title=:title WHERE game_id=:game_id;
            """;

    private static final String UPDATE_GAME_GENRE = """
            UPDATE games SET genres=:genres WHERE game_id=:game_id;
            """;

    private static final String UPDATE_GAME_COPIES_SOLD = """
            UPDATE games SET millions_of_copies_sold=:millions_of_copies_sold WHERE game_id=:game_id;
            """;

    private static final String UPDATE_GAME_RELEASE_DATE = """
            UPDATE games SET release_date=:release_date WHERE game_id=:game_id;
            """;

    private static final String UPDATE_ALL_GAME_DETAILS = """
            UPDATE games SET title=:title, genres=:genres, millions_of_copies_sold=:millions_of_copies_sold, release_date=:release_date WHERE game_id=:game_id;
            """;

    private static final String DELETE_GAME_BY_ID = """
            DELETE FROM games WHERE game_id=:game_id;
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
        return namedParameterJdbcTemplate.queryForObject(SELECT_GAME_BY_TITLE,queryParams,gameMapper);
    }

    public GameResponse getGameDetailsById(Long id){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("game_id", id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_GAME_BY_ID,queryParams,gameMapper);
    }

    /*
    .map(GameResponse::getTitle) maps each GameResponse object to just its title.
    .collect(Collectors.toList()) collects the result into a new list of strings containing just the titles.
    jdbcTemplate is used here instead of namedParameterJdbcTemplate because there is no request parameters.
    */
    public List<String> getGameTitles(){
        List<GameResponse> titles = jdbcTemplate.query(SELECT_GAMES_DETAILS, gameMapper);

        return titles.stream()
                .map(GameResponse::getTitle)
                .collect(Collectors.toList());
    }

    public List<GameResponse> getGames(){
        return jdbcTemplate.query(SELECT_GAMES_DETAILS, gameMapper);
    }

    // IntStream.range to iterate through both lists simultaneously (since you have two separate lists, you need to align them by index
    public List<String> getGameTitlesCopiesSold(){
        List<GameResponse> games = jdbcTemplate.query(SELECT_GAMES_DETAILS, gameMapper);

        List<String> titles =  games.stream()
                .map(GameResponse::getTitle)
                .toList();

        List<Integer> copies_sold =  games.stream()
                .map(GameResponse::getMillions_of_copies_sold)
                .toList();

        return IntStream.range(0, titles.size())
                .mapToObj(i -> titles.get(i) + " : " + copies_sold.get(i) + " million copies sold")
                .collect(Collectors.toList());
    }

    public List<String> getTitlesSortByReleaseDate(){
        List<GameResponse> games = jdbcTemplate.query(SELECT_ALL_BY_RELEASE_DATE, gameMapper);

        List<String> titles =  games.stream()
                .map(GameResponse::getTitle)
                .toList();

        List<LocalDate> release_date =  games.stream()
                .map(GameResponse::getRelease_date)
                .toList();

        return IntStream.range(0, titles.size())
                .mapToObj(i -> titles.get(i) + " was released on " + release_date.get(i))
                .collect(Collectors.toList());
    }

    // Uses its own Mapper in order to select distinct genres and not cause mapping errors.
    public List<String> getDistinctGenres(){
        return jdbcTemplate.query(SELECT_DISTINCT_GENRES, (rs, rowNum) -> rs.getString("genres"));
    }

    public Integer updateGameDetails(Long game_id, GameRequest updatedGameRequest){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("game_id", game_id);

        int parametersToBeUpdated = 0;
        int rowsAffected = 0;

        String title = updatedGameRequest.getTitle();
        String genres = updatedGameRequest.getGenres();
        Integer copies_sold = updatedGameRequest.getMillions_of_copies_sold();
        LocalDate release_date = updatedGameRequest.getRelease_date();

        // FIx isEmpty
        if((title != null) && (genres != null)
        && (copies_sold != null) && ((release_date != null))){
            queryParams.addValue("title", title);
            queryParams.addValue("genres", genres);
            queryParams.addValue("millions_of_copies_sold", copies_sold);
            queryParams.addValue("release_date", release_date);
            namedParameterJdbcTemplate.update(UPDATE_ALL_GAME_DETAILS,queryParams);
            parametersToBeUpdated=4;
            rowsAffected=4;
        } else if (title != null) {
            queryParams.addValue("title", title);
            parametersToBeUpdated+=1;
            if (namedParameterJdbcTemplate.update(UPDATE_GAME_NAME,queryParams) > 0){
                rowsAffected+=1;
            }
        } else if (genres != null) {
            queryParams.addValue("genres", genres);
            parametersToBeUpdated+=1;
            if (namedParameterJdbcTemplate.update(UPDATE_GAME_GENRE,queryParams) > 0){
                rowsAffected+=1;
            }
        } else if (copies_sold != null) {
            queryParams.addValue("millions_of_copies_sold", copies_sold);
            parametersToBeUpdated+=1;
            if (namedParameterJdbcTemplate.update(UPDATE_GAME_GENRE,queryParams) > 0){
                rowsAffected+=1;
            }
        }else if (release_date != null) {
            queryParams.addValue("release_date", release_date);
            parametersToBeUpdated+=1;
            if (namedParameterJdbcTemplate.update(UPDATE_GAME_GENRE,queryParams) > 0){
                rowsAffected+=1;
            }
        }

        if (parametersToBeUpdated==rowsAffected){
            return rowsAffected;
        }
        else{
            return 0;
        }
    }

    public Integer deleteGameById(Long id){
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("game_id", id);

        if (namedParameterJdbcTemplate.update(DELETE_GAME_BY_ID,queryParams) > 0){
            return 1;
        }
        else{
            return 0;
        }
    }
}
