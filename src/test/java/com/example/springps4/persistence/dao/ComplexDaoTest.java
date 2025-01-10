package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.DeveloperMapper;
import com.example.springps4.mapper.GameMapper;
import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.model.response.PublisherResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    }

    @Test
    void getGameTitleAndDevelopers() {
    }

    @Test
    void getGameTitleDevelopersAndPublishers() {
    }

    @Test
    void getDetails() {
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