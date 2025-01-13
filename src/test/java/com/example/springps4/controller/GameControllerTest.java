package com.example.springps4.controller;

import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

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
        when(gameService.getGameDetailsByTitle("Knack")).thenReturn(mockedGameResponse);
        ResponseEntity<GameResponse> actualResponse = gameController.getGameDetailsByTitle("Knack");
        verify(gameService).getGameDetailsByTitle("Knack");
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getGameDetailsById() {
        when(gameService.getGameDetailsById(24L)).thenReturn(mockedGameResponse);
        ResponseEntity<GameResponse> actualResponse = gameController.getGameDetailsById(24L);
        verify(gameService).getGameDetailsById(24L);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getAllGames() {
        List<GameResponse> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse);
        when(gameService.getAllGames()).thenReturn(listOfGames);

        ResponseEntity<List<GameResponse>> actualResponse = gameController.getAllGames();
        verify(gameService).getAllGames();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getAllGameTitles() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getTitle());
        listOfGames.add("Last of Us");
        when(gameService.getAllGameTitles()).thenReturn(listOfGames);

        ResponseEntity<List<String>> actualResponse = gameController.getAllGameTitles();
        verify(gameService).getAllGameTitles();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getAllTitlesCopiesSold() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getTitle());
        listOfGames.add("Last of Us");
        when(gameService.getAllTitlesCopiesSold()).thenReturn(listOfGames);

        ResponseEntity<List<String>> actualResponse = gameController.getAllTitlesCopiesSold();
        verify(gameService).getAllTitlesCopiesSold();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getTitlesSortByReleaseDate() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getTitle());
        listOfGames.add("Last of Us");
        when(gameService.getTitlesSortByReleaseDate()).thenReturn(listOfGames);

        ResponseEntity<List<String>> actualResponse = gameController.getTitlesSortByReleaseDate();
        verify(gameService).getTitlesSortByReleaseDate();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void getDistinctGenres() {
        List<String> listOfGames = new ArrayList<>();
        listOfGames.add(mockedGameResponse.getGenres());
        listOfGames.add("Adventure");
        when(gameService.getDistinctGenres()).thenReturn(listOfGames);

        ResponseEntity<List<String>> actualResponse = gameController.getDistinctGenres();
        verify(gameService).getDistinctGenres();
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void saveGameDetails() {
        List<GameRequest> listOfGames = new ArrayList<>();
        GameRequest mockedGameRequest = new GameRequest();
        mockedGameRequest.setTitle("Luigi's Mansion");
        mockedGameRequest.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameRequest.setMillions_of_copies_sold(200);
        mockedGameRequest.setGenres("Platformer");

        listOfGames.add(mockedGameRequest);
        when(gameService.insertGames(mockedGameRequest)).thenReturn(1);

        ResponseEntity<String> actualResponse = gameController.saveGameDetails(listOfGames);
        verify(gameService).insertGames(mockedGameRequest);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void updateEverythingByGameId() {
        GameRequest mockedGameRequest = new GameRequest();
        mockedGameRequest.setTitle("Luigi's Mansion");
        mockedGameRequest.setRelease_date(LocalDate.of(2013, 11, 15));
        mockedGameRequest.setMillions_of_copies_sold(200);
        mockedGameRequest.setGenres("Platformer");

        when(gameService.updateGameDetails(2L, mockedGameRequest)).thenReturn(1);

        ResponseEntity<String> actualResponse = gameController.updateEverythingByGameId(2L, mockedGameRequest);
        verify(gameService).updateGameDetails(2L,mockedGameRequest);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }

    @Test
    void deleteGameByGameId() {
        when(gameService.deleteGameById(2L)).thenReturn(1);

        ResponseEntity<String> actualResponse = gameController.deleteGameByGameId(2L);
        verify(gameService).deleteGameById(2L);
        assertThat(HttpStatus.OK).isEqualTo(actualResponse.getStatusCode());
    }
}