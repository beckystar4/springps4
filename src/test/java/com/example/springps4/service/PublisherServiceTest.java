package com.example.springps4.service;

import com.example.springps4.model.request.PublisherRequest;
import com.example.springps4.model.response.PublisherResponse;
import com.example.springps4.persistence.dao.PublisherDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {
    @Mock
    private PublisherDao publisherDao;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherResponse mockedPublisherResponse;

    @BeforeEach
    void setup(){
        mockedPublisherResponse = new PublisherResponse();
        mockedPublisherResponse.setPublisher("Rockstar");
        mockedPublisherResponse.setGame_id(80);
    }

    @Test
    void getPublisherDetails() {
        List<PublisherResponse> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(mockedPublisherResponse);
        when(publisherDao.getPublisherDetails()).thenReturn(listOfPublishers);

        List<PublisherResponse> actualResponse = publisherService.getPublisherDetails();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfPublishers.size());
        assertThat(actualResponse).isSameAs(listOfPublishers);
    }

    @Test
    void getDistinctPublishers() {
        List<String> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(mockedPublisherResponse.getPublisher());
        listOfPublishers.add("Naughty Dog");

        when(publisherDao.getDistinctPublishers()).thenReturn(listOfPublishers);

        List<String> actualResponse = publisherService.getDistinctPublishers();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfPublishers.size());
        assertThat(actualResponse).isSameAs(listOfPublishers);
    }

    @Test
    void getNumberOfGamesForSpecificPublisher() {
        when(publisherDao.getNumberOfGamesForSpecificPublisher(mockedPublisherResponse.getPublisher()))
                .thenReturn(1);
        Integer actualResponse = publisherService.getNumberOfGamesForSpecificPublisher(mockedPublisherResponse.getPublisher());
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void getNumberOfGamesGroupByPublisher() {
        List<PublisherResponse> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(mockedPublisherResponse);
        when(publisherDao.getNumberOfGamesGroupByPublisher()).thenReturn(listOfPublishers);

        List<PublisherResponse> actualResponse = publisherService.getNumberOfGamesGroupByPublisher();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfPublishers.size());
        assertThat(actualResponse).isSameAs(listOfPublishers);
    }

    @Test
    void deleteByPublisherName() {
        when(publisherDao.deleteByPublisherName(mockedPublisherResponse.getPublisher()))
                .thenReturn(1);
        Integer actualResponse = publisherService.deleteByPublisherName(mockedPublisherResponse.getPublisher());
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void insertPublisher() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setPublisher("Naughty Dog");
        publisherRequest.setGame_id(81); // game count for this publisher

        when(publisherDao.insertPublishers(publisherRequest))
                .thenReturn(1);
        Integer actualResponse = publisherService.insertPublisher(publisherRequest);
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void updatePublisherNameByGameId() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setPublisher("Naughty Dog");
        publisherRequest.setGame_id(81); // game count for this publisher

        when(publisherDao.updatePublisherNameByGameId(publisherRequest))
                .thenReturn(1);
        Integer actualResponse = publisherService.updatePublisherNameByGameId(publisherRequest);
        assertThat(actualResponse).isEqualTo(1);
    }
}