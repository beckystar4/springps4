package com.example.springps4.persistence.dao;

import com.example.springps4.mapper.PublisherMapper;
import com.example.springps4.model.request.PublisherRequest;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherDaoTest {
    @Mock
    private PublisherDao underTest;

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private PublisherMapper publisherMapper;

    private PublisherResponse mockedPublisherResponse;
    @BeforeEach
    void setup(){
        underTest = new PublisherDao(dataSource,publisherMapper);
        ReflectionTestUtils.setField(underTest,"jdbcTemplate", jdbcTemplate);
        ReflectionTestUtils.setField(underTest,"namedParameterJdbcTemplate", namedParameterJdbcTemplate);
        mockedPublisherResponse = new PublisherResponse();
        mockedPublisherResponse.setPublisher("Naughty Dog");
        mockedPublisherResponse.setGame_id(80);
    }

    @Test
    void getPublisherDetails() {
        List<PublisherResponse> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(mockedPublisherResponse);

        when(jdbcTemplate.query(anyString(), eq(publisherMapper)))
                .thenReturn(listOfPublishers);

        List<PublisherResponse> actualResponse = underTest.getPublisherDetails();

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(1);
        assertThat(actualResponse.get(0).getPublisher()).isEqualTo("Naughty Dog");
    }

    @Test
    void getDistinctPublishers() {
    }

    @Test
    void getNumberOfGamesForSpecificPublisher() {
        String publisher = "Naughty Dog";

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(1);

        Integer count = underTest.getNumberOfGamesForSpecificPublisher(publisher);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void getNumberOfGamesGroupByPublisher() {
//        List<PublisherResponse> listOfPublishers = new ArrayList<>();
//        PublisherResponse publisherResponse1 = new PublisherResponse();
//        publisherResponse1.setPublisher("Naughty Dog");
//        publisherResponse1.setGame_id(81); // game count for this publisher
//        listOfPublishers.add(publisherResponse1);
//        listOfPublishers.add(mockedPublisherResponse);
//
//        lenient().when(jdbcTemplate.query(
//                anyString(),  // this matches any SQL string
//                eq(publisherMapper)  // matches the RowMapper
//        )).thenReturn(listOfPublishers);
//
//        List<PublisherResponse> actualResponse = underTest.getNumberOfGamesGroupByPublisher();
//
//        System.out.println("Actual response size: " + actualResponse.size());
//        if (!actualResponse.isEmpty()) {
//            System.out.println("Publisher: " + actualResponse.get(0).getPublisher());
//            System.out.println("Game count: " + actualResponse.get(0).getGame_id());
//        }
//
//        assertThat(actualResponse).isNotNull();
//        assertThat(actualResponse.size()).isEqualTo(1);
//        assertThat(actualResponse.get(0).getPublisher()).isEqualTo("Naughty Dog");
//        assertThat(actualResponse.get(0).getGame_id()).isEqualTo(81);
    }

    @Test
    void deleteByPublisherName() {
        String name = "Naughty Dog";
        //when
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);

        Integer actualResponse = underTest.deleteByPublisherName(name);

        //then
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void insertPublishers() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setPublisher("Naughty Dog");
        publisherRequest.setGame_id(81); // game count for this publisher

        // When: Mocking namedParameterJdbcTemplate.update() to return 1
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(1);  // 1 indicates that one row was affected

        // Call the method to test
        Integer actualResponse = underTest.insertPublishers(publisherRequest);

        // Then: Assert the result
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void updatePublisherNameByGameId() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setPublisher("Naughty Dog");
        publisherRequest.setGame_id(81); // game count for this publisher

        if(underTest.insertPublishers(publisherRequest) > 0){
            // When: Mocking namedParameterJdbcTemplate.update() to return 1
            when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                    .thenReturn(1);  // 1 indicates that one row was affected

            // Call the method to test
            Integer actualResponse = underTest.updatePublisherNameByGameId(publisherRequest);

            // Then: Assert the result
            assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
        }
    }
}