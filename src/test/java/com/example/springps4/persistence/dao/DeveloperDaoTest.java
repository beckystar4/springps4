package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.response.DeveloperResponse;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeveloperDaoTest {
    @Mock
    private DeveloperDao underTest;

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private DeveloperMapper developerMapper;

    private DeveloperResponse mockedDeveloperResponse;

    private static final String SELECT_DISTINCT_DEVELOPERS = """
            SELECT DISTINCT developer FROM developers ORDER BY developer ASC;
            """;

    private static final String NUMBER_OF_GAMES_GROUP_BY_DEVELOPER = """
            SELECT developer, COUNT(game_id) AS game_count
            FROM developers
            GROUP BY developer
            ORDER BY developer ASC;
            """;

    @BeforeEach
    void setup(){
        underTest = new DeveloperDao(dataSource,developerMapper);
        ReflectionTestUtils.setField(underTest,"jdbcTemplate", jdbcTemplate);
        ReflectionTestUtils.setField(underTest,"namedParameterJdbcTemplate", namedParameterJdbcTemplate);
        mockedDeveloperResponse = new DeveloperResponse();
        mockedDeveloperResponse.setDeveloper("Naughty Dog");
        mockedDeveloperResponse.setGame_id(80);
    }

    @Test
    void getDeveloperDetails() {
        List<DeveloperResponse> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(mockedDeveloperResponse);

        when(jdbcTemplate.query(anyString(), eq(developerMapper)))
                .thenReturn(listOfDevelopers);

        List<DeveloperResponse> actualResponse = underTest.getDeveloperDetails();

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(1);
        assertThat(actualResponse.get(0).getDeveloper()).isEqualTo("Naughty Dog");
    }

    @Test
    void getDistinctDevelopers() {
        // Given: Prepare mock data
        List<String> mockedDevelopers = List.of("Naughty Dog", "Rockstar");

        // When: Simulate the query execution and return the mocked genres list
        when(jdbcTemplate.query(eq(SELECT_DISTINCT_DEVELOPERS), any(RowMapper.class)))
                .thenReturn(mockedDevelopers);
        // Call the method to test
        List<String> actualResult = underTest.getDistinctDevelopers();

        // Then: Assert the results
        AssertionsForClassTypes.assertThat(actualResult).isNotNull();
        AssertionsForClassTypes.assertThat(actualResult.size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(actualResult).isSameAs(mockedDevelopers);
    }

    @Test
    void getNumberOfGamesForSpecificDeveloper() {
        String developer = "Naughty Dog";

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(1);

        Integer count = underTest.getNumberOfGamesForSpecificDeveloper(developer);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void getNumberOfGamesGroupByDeveloper() {
        // Given: Prepare mock data
        List<DeveloperResponse> listOfDevelopers = new ArrayList<>();
        DeveloperResponse developerResponse1 = new DeveloperResponse();
        developerResponse1.setDeveloper("Rockstar");
        developerResponse1.setGame_id(81); // game count for this publisher
        listOfDevelopers.add(developerResponse1);
        listOfDevelopers.add(mockedDeveloperResponse);

        // When: Simulate the query execution and return the mocked genres list
        when(jdbcTemplate.query(eq(NUMBER_OF_GAMES_GROUP_BY_DEVELOPER), any(RowMapper.class)))
                .thenReturn(listOfDevelopers);
        // Call the method to test
        List<DeveloperResponse> actualResult = underTest.getNumberOfGamesGroupByDeveloper();

        // Then: Assert the results
        AssertionsForClassTypes.assertThat(actualResult).isNotNull();
    }

    @Test
    void deleteByDeveloperName() {
        String name = "Naughty Dog";
        //when
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);

        Integer actualResponse = underTest.deleteByDeveloperName(name);

        //then
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void insertDevelopers() {
        DeveloperRequest developerRequest = new DeveloperRequest();
        developerRequest.setDeveloper("Naughty Dog");
        developerRequest.setGame_id(81); // game count for this publisher

        // When: Mocking namedParameterJdbcTemplate.update() to return 1
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);  // 1 indicates that one row was affected

        // Call the method to test
        Integer actualResponse = underTest.insertDevelopers(developerRequest);

        // Then: Assert the result
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void updateDevelopersNameByGameId() {
        DeveloperRequest developerRequest = new DeveloperRequest();
        developerRequest.setDeveloper("Naughty Dog");
        developerRequest.setGame_id(81); // game count for this publisher

        if(underTest.insertDevelopers(developerRequest) > 0){
            // When: Mocking namedParameterJdbcTemplate.update() to return 1
            when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                    .thenReturn(1);  // 1 indicates that one row was affected

            // Call the method to test
            Integer actualResponse = underTest.updateDevelopersNameByGameId(developerRequest);

            // Then: Assert the result
            assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
        }
    }
}