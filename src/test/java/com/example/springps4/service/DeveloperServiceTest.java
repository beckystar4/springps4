package com.example.springps4.service;

import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.persistence.dao.DeveloperDao;
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
class DeveloperServiceTest {

    @Mock
    private DeveloperDao developerDao;

    @InjectMocks
    private DeveloperService developerService;

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
        when(developerDao.getDeveloperDetails()).thenReturn(listOfDevelopers);

        List<DeveloperResponse> actualResponse = developerService.getDeveloperDetails();

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfDevelopers.size());
        assertThat(actualResponse).isSameAs(listOfDevelopers);
    }

    @Test
    void getDistinctDevelopers() {
        List<String> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(mockedDeveloperResponse.getDeveloper());
        listOfDevelopers.add("Rockstar");
        when(developerDao.getDistinctDevelopers()).thenReturn(listOfDevelopers);

        List<String> actualResponse = developerService.getDistinctDevelopers();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfDevelopers.size());
        assertThat(actualResponse).isSameAs(listOfDevelopers);
    }

    @Test
    void getNumberOfGamesForSpecificDevelopers() {
        when(developerDao.getNumberOfGamesForSpecificDeveloper(mockedDeveloperResponse.getDeveloper()))
                .thenReturn(1);
        Integer actualResponse = developerService.getNumberOfGamesForSpecificDevelopers(mockedDeveloperResponse.getDeveloper());
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void getNumberOfGamesGroupBDevelopers() {
        List<DeveloperResponse> listOfDevelopers = new ArrayList<>();
        listOfDevelopers.add(mockedDeveloperResponse);
        when(developerDao.getNumberOfGamesGroupByDeveloper()).thenReturn(listOfDevelopers);

        List<DeveloperResponse> actualResponse = developerService.getNumberOfGamesGroupBDevelopers();

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfDevelopers.size());
        assertThat(actualResponse).isSameAs(listOfDevelopers);
    }

    @Test
    void deleteByDeveloperName() {
        when(developerDao.deleteByDeveloperName(mockedDeveloperResponse.getDeveloper()))
                .thenReturn(1);
        Integer actualResponse = developerService.deleteByDeveloperName(mockedDeveloperResponse.getDeveloper());
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void insertDeveloper() {
        DeveloperRequest developerRequest = new DeveloperRequest();
        developerRequest.setDeveloper("Naughty Dog");
        developerRequest.setGame_id(81); // game count for this publisher
        when(developerDao.insertDevelopers(developerRequest))
                .thenReturn(1);
        Integer actualResponse = developerService.insertDeveloper(developerRequest);
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void updateDeveloperNameByGameId() {
        DeveloperRequest developerRequest = new DeveloperRequest();
        developerRequest.setDeveloper("Naughty Dog");
        developerRequest.setGame_id(81); // game count for this publisher
        when(developerDao.updateDevelopersNameByGameId(developerRequest))
                .thenReturn(1);
        Integer actualResponse = developerService.updateDeveloperNameByGameId(developerRequest);
        assertThat(actualResponse).isEqualTo(1);
    }
}