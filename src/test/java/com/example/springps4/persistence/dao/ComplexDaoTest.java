package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.mapper.GameMapper;
import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.model.response.PublisherResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplexDaoTest {
    @Mock
    private ComplexDao underTest;

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private DeveloperMapper developerMapper;

    @Mock
    private GameMapper gameMapper;

    @Mock
    private PublisherMapper publisherMapper;

    private GameResponse mockedGameResponse;
    private DeveloperResponse mockedDeveloperResponse;
    private PublisherResponse mockedPublisherResponse;

    private static final String SELECT_ALL_DETAILS_BY_TITLE = """
           SELECT title, genres, millions_of_copies_sold, release_date, publisher, developer
            FROM games
            INNER JOIN developers ON games.game_id = developers.game_id
            INNER JOIN publishers ON games.game_id = publishers.game_id
            WHERE title=:title;
            """;

    private static final String SELECT_GENRES_BY_PUBLISHER = """
             select distinct genres from games inner join publishers USING(game_id) where publisher=:publisher;
            """;

    @BeforeEach
    void setup(){
        underTest = new ComplexDao(dataSource, publisherMapper, gameMapper, developerMapper);
        ReflectionTestUtils.setField(underTest,"jdbcTemplate", jdbcTemplate);
        ReflectionTestUtils.setField(underTest,"namedParameterJdbcTemplate", namedParameterJdbcTemplate);

        mockedGameResponse = new GameResponse();
        mockedGameResponse.setGame_id(24L);
        mockedGameResponse.setTitle("Knack");
        mockedGameResponse.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameResponse.setMillions_of_copies_sold(2);
        mockedGameResponse.setGenres("Platform beat em up");

        mockedPublisherResponse = new PublisherResponse();
        mockedPublisherResponse.setPublisher("Naughty Dog");
        mockedPublisherResponse.setGame_id(80);

        mockedDeveloperResponse = new DeveloperResponse();
        mockedDeveloperResponse.setDeveloper("Naughty Dog");
        mockedDeveloperResponse.setGame_id(80);
    }

    @Test
    void getGameTitleAndPublishers() {
        // Given: Prepare mock data
        List<Map<String, String>> mockedResult = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("title", mockedGameResponse.getTitle());
        map.put("publisher", mockedPublisherResponse.getPublisher());
        mockedResult.add(map);

        // When: Simulate the query execution and return the mocked result list
        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(mockedResult);

        // Call the method to test
        List<Map<String, String>> actualResult = underTest.getGameTitleAndPublishers();

        // Then: Assert the results
        assertThat(actualResult).isNotNull();  // Ensure the result is not null
        assertThat(actualResult).hasSize(1);   // Ensure the list has one element
        assertThat(actualResult.get(0)).containsEntry("title", "Knack");  // Ensure the title is correct
        assertThat(actualResult.get(0)).containsEntry("publisher", "Naughty Dog");  // Ensure the publisher is correct
    }

    @Test
    void getGameTitleAndDevelopers() {
        // Given: Prepare mock data
        List<Map<String, String>> mockedResult = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("title", mockedGameResponse.getTitle());
        map.put("developer", mockedDeveloperResponse.getDeveloper());
        mockedResult.add(map);

        // When: Simulate the query execution and return the mocked result list
        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(mockedResult);

        // Call the method to test
        List<Map<String, String>> actualResult = underTest.getGameTitleAndDevelopers();

        // Then: Assert the results
        assertThat(actualResult).isNotNull();  // Ensure the result is not null
        assertThat(actualResult).hasSize(1);   // Ensure the list has one element
        assertThat(actualResult.get(0)).containsEntry("title", "Knack");  // Ensure the title is correct
        assertThat(actualResult.get(0)).containsEntry("developer", "Naughty Dog");
    }

    @Test
    void getGameTitleDevelopersAndPublishers() {
        // Given: Prepare mock data
        List<Map<String, String>> mockedResult = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("title", mockedGameResponse.getTitle());
        map.put("developer", mockedDeveloperResponse.getDeveloper());
        map.put("publisher", mockedPublisherResponse.getPublisher());
        mockedResult.add(map);

        // When: Simulate the query execution and return the mocked result list
        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(mockedResult);

        // Call the method to test
        List<Map<String, String>> actualResult = underTest.getGameTitleAndDevelopers();

        // Then: Assert the results
        assertThat(actualResult).isNotNull();  // Ensure the result is not null
        assertThat(actualResult).hasSize(1);   // Ensure the list has one element
        assertThat(actualResult.get(0)).containsEntry("title", "Knack");  // Ensure the title is correct
        assertThat(actualResult.get(0)).containsEntry("developer", "Naughty Dog");
        assertThat(actualResult.get(0)).containsEntry("publisher", "Naughty Dog");
    }

    @Test
    void getDetails() {
        // Given: Prepare mock data
        List<Map<String, String>> mockedResult = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("title", mockedGameResponse.getTitle());
        map.put("genres", mockedGameResponse.getGenres());
        map.put("millions_of_copies_sold", mockedGameResponse.getMillions_of_copies_sold().toString());
        map.put("release_date", mockedGameResponse.getRelease_date().toString());
        map.put("game_id", mockedGameResponse.getGame_id().toString());
        map.put("developer", mockedDeveloperResponse.getDeveloper());
        map.put("publisher", mockedPublisherResponse.getPublisher());
        mockedResult.add(map);

        // When: Simulate the query execution and return the mocked result list
        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(mockedResult);

        // Call the method to test
        List<Map<String, String>> actualResult = underTest.getDetails();

        // Then: Assert the results
        assertThat(actualResult).isNotNull();  // Ensure the result is not null
        assertThat(actualResult).hasSize(1);   // Ensure the list has one element
        assertThat(actualResult.get(0)).containsEntry("title", "Knack");  // Ensure the title is correct
        assertThat(actualResult.get(0)).containsEntry("game_id", "24");
        assertThat(actualResult.get(0)).containsEntry("genres", "Platform beat em up");
        assertThat(actualResult.get(0)).containsEntry("millions_of_copies_sold", "2");
        assertThat(actualResult.get(0)).containsEntry("developer", "Naughty Dog");
        assertThat(actualResult.get(0)).containsEntry("publisher", "Naughty Dog");
    }

    @Test
    void getDetailsByTitle() {
    }

    @Test
    void getDistinctGenresPublisher() {
    }

    @Test
    void getDistinctGenresDeveloper() {
    }

    @Test
    void getSelectNewestGame() {
    }

    @Test
    void batchInsert() {
    }
}