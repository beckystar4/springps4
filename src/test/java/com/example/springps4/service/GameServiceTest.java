package com.example.springps4.service;

import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.GameDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private GameService gameService;

    // Is initalized in the @BeforeEach to save space and re-use.
    private GameResponse mockedGameResponse;

    @BeforeEach
    void setup(){
        mockedGameResponse = new GameResponse();
        mockedGameResponse.setGame_id(24L);
        mockedGameResponse.setTitle("Knack");
        mockedGameResponse.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameResponse.setMillions_of_copies_sold(2);
        mockedGameResponse.setGenres("Platform beat em up");
    }

    @Test
    void getGameDetailsByTitle() {
        when(gameDao.getGameDetailsByTitle("Knack")).thenReturn(mockedGameResponse);

        GameResponse actualResponse = gameService.getGameDetailsByTitle("Knack");
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getTitle()).isEqualTo(mockedGameResponse.getTitle());
        assertThat(actualResponse.getGame_id()).isEqualTo(mockedGameResponse.getGame_id());
    }

    @Test
    void getGameDetailsById() {
        when(gameDao.getGameDetailsById(24L)).thenReturn(mockedGameResponse);

        GameResponse actualResponse = gameService.getGameDetailsById(24L);
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getTitle()).isEqualTo(mockedGameResponse.getTitle());
        assertThat(actualResponse.getGame_id()).isEqualTo(mockedGameResponse.getGame_id());
    }

    @Test
    void getAllGameTitles() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getTitle());
        listOfGames.add("Last of Us");
        when(gameDao.getGameTitles()).thenReturn(listOfGames);

        List<String> actualResponse = gameService.getAllGameTitles();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfGames.size());
        assertThat(actualResponse).isSameAs(listOfGames);
    }

    @Test
    void getAllGames() {
        List<GameResponse> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse);
        when(gameDao.getGames()).thenReturn(listOfGames);

        List<GameResponse> actualResponse = gameService.getAllGames();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfGames.size());
        assertThat(actualResponse).isSameAs(listOfGames);
    }

    @Test
    void getAllTitlesCopiesSold() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getTitle() + " : " + mockedGameResponse.getMillions_of_copies_sold() + " million copies sold");
        when(gameDao.getGameTitlesCopiesSold()).thenReturn(listOfGames);

        List<String> actualResponse = gameService.getAllTitlesCopiesSold();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfGames.size());
        assertThat(actualResponse).isSameAs(listOfGames);
    }

    @Test
    void getTitlesSortByReleaseDate() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getTitle() + " was released on " + mockedGameResponse.getRelease_date());
        when(gameDao.getTitlesSortByReleaseDate()).thenReturn(listOfGames);

        List<String> actualResponse = gameService.getTitlesSortByReleaseDate();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfGames.size());
        assertThat(actualResponse).isSameAs(listOfGames);
    }

    @Test
    void getDistinctGenres() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getGenres());
        listOfGames.add("Adventure");
        when(gameDao.getDistinctGenres()).thenReturn(listOfGames);

        List<String> actualResponse = gameService.getDistinctGenres();
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.size()).isEqualTo(listOfGames.size());
        assertThat(actualResponse).isSameAs(listOfGames);
    }

    @Test
    void insertGames() {
        GameRequest mockedGameRequest = new GameRequest();
        mockedGameRequest.setTitle("Luigi's Mansion");
        mockedGameRequest.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameRequest.setMillions_of_copies_sold(200);
        mockedGameRequest.setGenres("Platformer");

        when(gameDao.insertGame(mockedGameRequest)).thenReturn(1);

        Integer actualResponse = gameService.insertGames(mockedGameRequest);
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void updateGameDetails() {
        GameRequest mockedGameRequest = new GameRequest();
        mockedGameRequest.setTitle("Luigi's Mansion");
        mockedGameRequest.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameRequest.setMillions_of_copies_sold(200);
        mockedGameRequest.setGenres("Platformer");

        when(gameDao.updateGameDetails(1L, mockedGameRequest)).thenReturn(1);

        Integer actualResponse = gameService.updateGameDetails(1L, mockedGameRequest);
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }

    @Test
    void deleteGameById() {
        when(gameDao.deleteGameById(1L)).thenReturn(1);
        Integer actualResponse = gameService.deleteGameById(1L);
        assertThat(actualResponse).isNotEqualTo(0);  // Check that it's not 0 (success)
    }
}