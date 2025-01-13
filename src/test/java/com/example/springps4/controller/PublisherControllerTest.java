package com.example.springps4.controller;

import com.example.springps4.model.request.PublisherRequest;
import com.example.springps4.model.response.PublisherResponse;
import com.example.springps4.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherControllerTest {

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

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
        when(publisherService.getPublisherDetails()).thenReturn(listOfPublishers);

        ResponseEntity<List<PublisherResponse>> actualResponse = publisherController.getPublisherDetails();
        verify(publisherService).getPublisherDetails();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getDistinctPublishers() {
        List<String> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(mockedPublisherResponse.getPublisher());
        listOfPublishers.add("Naughty Dog");

        when(publisherService.getDistinctPublishers()).thenReturn(listOfPublishers);

        ResponseEntity<List<String>> actualResponse = publisherController.getDistinctPublishers();
        verify(publisherService).getDistinctPublishers();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getNumberOfGamesForSpecificPublisher() {
        when(publisherService.getNumberOfGamesForSpecificPublisher(mockedPublisherResponse.getPublisher()))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = publisherController.getNumberOfGamesForSpecificPublisher(mockedPublisherResponse.getPublisher());
        verify(publisherService).getNumberOfGamesForSpecificPublisher(mockedPublisherResponse.getPublisher());
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getNumberOfGamesGroupByPublisher() {
        List<PublisherResponse> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(mockedPublisherResponse);
        when(publisherService.getNumberOfGamesGroupByPublisher())
                .thenReturn(listOfPublishers);
        ResponseEntity<List<String>> actualResponse = publisherController.getNumberOfGamesGroupByPublisher();
        verify(publisherService).getNumberOfGamesGroupByPublisher();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void deleteGameByPublisherName() {
        when(publisherService.deleteByPublisherName(mockedPublisherResponse.getPublisher()))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = publisherController.deleteGameByPublisherName(mockedPublisherResponse.getPublisher());
        verify(publisherService).deleteByPublisherName(mockedPublisherResponse.getPublisher());
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void savePublisher() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setPublisher("Naughty Dog");
        publisherRequest.setGame_id(81); // game count for this publisher

        List<PublisherRequest> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(publisherRequest);

        when(publisherService.insertPublisher(publisherRequest))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = publisherController.savePublisher(listOfPublishers);
        verify(publisherService).insertPublisher(publisherRequest);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void updatePublisherNameByGameId() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setPublisher("Naughty Dog");
        publisherRequest.setGame_id(81); // game count for this publisher

        List<PublisherRequest> listOfPublishers = new ArrayList<>();
        listOfPublishers.add(publisherRequest);

        when(publisherService.updatePublisherNameByGameId(publisherRequest))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = publisherController.updatePublisherNameByGameId(listOfPublishers);
        verify(publisherService).updatePublisherNameByGameId(publisherRequest);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }
}