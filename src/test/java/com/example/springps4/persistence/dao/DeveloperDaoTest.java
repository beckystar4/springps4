package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.request.PublisherRequest;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.model.response.PublisherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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