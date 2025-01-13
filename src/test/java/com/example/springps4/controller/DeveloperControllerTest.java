package com.example.springps4.controller;

import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.service.DeveloperService;
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
class DeveloperControllerTest {

    @Mock
    private DeveloperService developerService;

    @InjectMocks
    private DeveloperController developerController;

    private DeveloperResponse mockedDeveloperResponse;

    @BeforeEach
    void setup(){
        mockedDeveloperResponse = new DeveloperResponse();
        mockedDeveloperResponse.setDeveloper("Naughty Dog");
        mockedDeveloperResponse.setGame_id(80);
    }

    @Test
    void getDeveloperDetails() {
        List<DeveloperResponse> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(mockedDeveloperResponse);
        when(developerService.getDeveloperDetails()).thenReturn(listOfDevelopers);

        ResponseEntity<List<DeveloperResponse>> actualResponse = developerController.getDeveloperDetails();

        verify(developerService).getDeveloperDetails();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getDistinctDevelopers() {
        List<String> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(mockedDeveloperResponse.getDeveloper());
        when(developerService.getDistinctDevelopers()).thenReturn(listOfDevelopers);

        ResponseEntity<List<String>> actualResponse = developerController.getDistinctDevelopers();

        verify(developerService).getDistinctDevelopers();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getNumberOfGamesForSpecificDeveloper() {
        when(developerService.getNumberOfGamesForSpecificDevelopers(mockedDeveloperResponse.getDeveloper()))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = developerController.getNumberOfGamesForSpecificDeveloper(mockedDeveloperResponse.getDeveloper());
        verify(developerService).getNumberOfGamesForSpecificDevelopers(mockedDeveloperResponse.getDeveloper());
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getNumberOfGamesGroupByDeveloper() {
        List<DeveloperResponse> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(mockedDeveloperResponse);
        when(developerService.getNumberOfGamesGroupBDevelopers()).thenReturn(listOfDevelopers);

        ResponseEntity<List<String>> actualResponse = developerController.getNumberOfGamesGroupByDeveloper();

        verify(developerService).getNumberOfGamesGroupBDevelopers();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void deleteGameByDeveloperName() {
        when(developerService.deleteByDeveloperName(mockedDeveloperResponse.getDeveloper()))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = developerController.deleteGameByDeveloperName(mockedDeveloperResponse.getDeveloper());
        verify(developerService).deleteByDeveloperName(mockedDeveloperResponse.getDeveloper());
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void saveDeveloper() {
        DeveloperRequest developerRequest = new DeveloperRequest();
        developerRequest.setDeveloper("Naughty Dog");
        developerRequest.setGame_id(81); // game count for this publisher

        List<DeveloperRequest> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(developerRequest);

        when(developerService.insertDeveloper(developerRequest))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = developerController.saveDeveloper(listOfDevelopers);
        verify(developerService).insertDeveloper(developerRequest);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void updateDeveloperNameByGameId() {
        DeveloperRequest developerRequest = new DeveloperRequest();
        developerRequest.setDeveloper("Naughty Dog");
        developerRequest.setGame_id(81); // game count for this publisher

        List<DeveloperRequest> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(developerRequest);

        when(developerService.updateDeveloperNameByGameId(developerRequest))
                .thenReturn(1);
        ResponseEntity<String> actualResponse = developerController.updateDeveloperNameByGameId(listOfDevelopers);
        verify(developerService).updateDeveloperNameByGameId(developerRequest);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }
}