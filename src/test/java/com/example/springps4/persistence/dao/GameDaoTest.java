package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameDaoTest {

    @InjectMocks
    private GameDao underTest;

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private GameMapper gameMapper;

    // Is initalized in the @BeforeEach to save space and re-use.
    private GameResponse mockedGameResponse;

    private static final String SELECT_DISTINCT_GENRES = """
            SELECT DISTINCT genres FROM games ORDER BY genres ASC;
            """;


    @BeforeEach
    void setup(){
        underTest = new GameDao(dataSource, gameMapper);
        ReflectionTestUtils.setField(underTest,"jdbcTemplate", jdbcTemplate);
        ReflectionTestUtils.setField(underTest,"namedParameterJdbcTemplate", namedParameterJdbcTemplate);
        mockedGameResponse = new GameResponse();
        mockedGameResponse.setGame_id(24L);
        mockedGameResponse.setTitle("Knack");
        mockedGameResponse.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameResponse.setMillions_of_copies_sold(2);
        mockedGameResponse.setGenres("Platform beat em up");
    }

    @Test
    void getGameDetailsByTitle() {
        //given
        String title = "Knack";

        //when
        //Mocks the behavior to return the mocked Game Response
        /*
        The when(jdbcTemplate.queryForObject(...)) statement is where you're telling Mockito to return a specific GameResponse when queryForObject() is called.
        anyString() is used to match any query string (like the SELECT SQL query).
        any(MapSqlParameterSource.class) is used to match any parameter source (e.g., the parameters passed in the query).
        eq(gameMapper) ensures that gameMapper is passed as the RowMapper, which is responsible for converting the result set into a GameResponse.
         */
        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(gameMapper)))
                .thenReturn(mockedGameResponse);

        GameResponse actualResponse = underTest.getGameDetailsByTitle(title);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getGame_id()).isEqualTo(24);
        assertThat(actualResponse.getTitle()).isEqualTo("Knack");
        assertThat(actualResponse.getRelease_date()).isEqualTo(LocalDate.of(2013, 11, 15));
    }

    @Test
    void getGameDetailsById() {
        //given
        Long gameId = 24L;

        //when
        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(gameMapper)))
                .thenReturn(mockedGameResponse);

        GameResponse actualResponse = underTest.getGameDetailsById(gameId);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getGame_id()).isEqualTo(24);
        assertThat(actualResponse.getTitle()).isEqualTo("Knack");
    }

    @Test
    void getGameTitles() {
        // Given
        List<GameResponse> listOfGames = new ArrayList<>();

        // Create a mocked GameResponse object and add it to the list
        listOfGames.add(mockedGameResponse);

        GameResponse game2 = new GameResponse();
        game2.setTitle("Uncharted");
        listOfGames.add(game2);

        //when
        when(jdbcTemplate.query(anyString(), eq(gameMapper)))
                .thenReturn(listOfGames);

        List<String> actualResponse = underTest.getGameTitles();

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(2);
        assertThat(actualResponse.get(0)).isEqualTo("Knack");
    }

    @Test
    void getGames() {
        // Given
        List<GameResponse> listOfGames = new ArrayList<>();

        // Create a mocked GameResponse object and add it to the list
        listOfGames.add(mockedGameResponse);

        GameResponse game2 = new GameResponse();
        game2.setTitle("Uncharted");
        listOfGames.add(game2);

        //when
        when(jdbcTemplate.query(anyString(), eq(gameMapper)))
                .thenReturn(listOfGames);

        List<GameResponse> actualResponse = underTest.getGames();

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(2);
        assertThat(actualResponse.get(0).getTitle()).isEqualTo("Knack");
    }

    @Test
    void getGameTitlesCopiesSold() {
        // Given
        List<GameResponse> listOfGames = new ArrayList<>();

        // Create a mocked GameResponse object and add it to the list
        listOfGames.add(mockedGameResponse);

        GameResponse game2 = new GameResponse();
        game2.setTitle("Uncharted");
        game2.setMillions_of_copies_sold(5);
        listOfGames.add(game2);

        //when
        when(jdbcTemplate.query(anyString(), eq(gameMapper)))
                .thenReturn(listOfGames);

        List<String> actualResponse = underTest.getGameTitlesCopiesSold();

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(2);
        assertThat(actualResponse.get(0)).isEqualTo("Knack : 2 million copies sold");
    }

    @Test
    void getTitlesSortByReleaseDate() {
        // Given
        List<GameResponse> listOfGames = new ArrayList<>();

        // Create a mocked GameResponse object and add it to the list
        listOfGames.add(mockedGameResponse);

        GameResponse game2 = new GameResponse();
        game2.setTitle("Uncharted");
        game2.setRelease_date(LocalDate.of(2012,02,01));
        listOfGames.add(game2);

        //when
        when(jdbcTemplate.query(anyString(), eq(gameMapper)))
                .thenReturn(listOfGames);

        List<String> actualResponse = underTest.getTitlesSortByReleaseDate();

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(2);
        assertThat(actualResponse.get(0)).isEqualTo("Knack was released on 2013-11-15");
    }

    @Test
    void getDistinctGenres() {
        // Given: Prepare mock data
        List<String> mockedGenres = List.of("Action", "Adventure", "Platform");


        // When: Simulate the query execution and return the mocked genres list
        when(jdbcTemplate.query(eq(SELECT_DISTINCT_GENRES), any(RowMapper.class)))
                .thenReturn(mockedGenres);
        // Call the method to test
        List<String> actualGenres = underTest.getDistinctGenres();

        // Then: Assert the results
        assertThat(actualGenres).isNotNull();
        assertThat(actualGenres.size()).isEqualTo(3);
        assertThat(actualGenres).isSameAs(mockedGenres);
    }

    @Test
    void updateGameDetails() {
        GameRequest mockedGameRequest = new GameRequest();
        mockedGameRequest.setTitle("Luigi's Mansion");
        mockedGameRequest.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameRequest.setMillions_of_copies_sold(200);
        mockedGameRequest.setGenres("Platformer");

        if(underTest.insertGame(mockedGameRequest) > 0){
            // When: Mocking namedParameterJdbcTemplate.update() to return 1
            when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                    .thenReturn(1);  // 1 indicates that one row was affected

            // Call the method to test
            Integer actualResponse = underTest.updateGameDetails(1L, mockedGameRequest);

            // Then: Assert the result
            assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
            assertThat(actualResponse).isNotEqualTo(-100);
        }
    }

    @Test
    void insertGame() {
        GameRequest mockedGameRequest = new GameRequest();
        mockedGameRequest.setTitle("Luigi's Mansion");
        mockedGameRequest.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameRequest.setMillions_of_copies_sold(200);
        mockedGameRequest.setGenres("Platformer");

        // When: Mocking namedParameterJdbcTemplate.update() to return 1
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);  // 1 indicates that one row was affected

        // Call the method to test
        Integer actualResponse = underTest.insertGame(mockedGameRequest);

        // Then: Assert the result
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void deleteGameById() {
        Long id = 24L;
        //when
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);

        Integer actualResponse = underTest.deleteGameById(id);

        //then
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }
}