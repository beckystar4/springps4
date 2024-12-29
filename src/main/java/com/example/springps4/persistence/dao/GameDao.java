package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.base.AbstractDatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameDao extends AbstractDatabaseDao {

    private static final String SELECT_GAME_DETAILS = """
            SELECT * FROM games WHERE title=:title;
            """;

    private static final String SELECT_ALL_TITLES = """
            SELECT * FROM games;
            """;

    private static final String SELECT_ALL_BY_RELEASE_DATE = """
            SELECT * FROM games ORDER BY release_date;
            """;

    private static final String SELECT_DISTINCT_GENRES = """
            SELECT DISTINCT genres FROM games ORDER BY genres ASC;
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
        return namedParameterJdbcTemplate.queryForObject(SELECT_GAME_DETAILS,queryParams,gameMapper);
    }

    /*
    .map(GameResponse::getTitle) maps each GameResponse object to just its title.
    .collect(Collectors.toList()) collects the result into a new list of strings containing just the titles.
    */
    public List<String> getGameTitles(){
        List<GameResponse> titles = jdbcTemplate.query(SELECT_ALL_TITLES, gameMapper);

        return titles.stream()
                .map(GameResponse::getTitle)
                .collect(Collectors.toList());
    }

    // IntStream.range to iterate through both lists simultaneously (since you have two separate lists, you need to align them by index
    public List<String> getGameTitlesCopiesSold(){
        List<GameResponse> games = jdbcTemplate.query(SELECT_ALL_TITLES, gameMapper);

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
}
