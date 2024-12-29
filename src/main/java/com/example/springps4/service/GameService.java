package com.example.springps4.service;

import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameDao gameDao;

    @Autowired
    public GameService(GameDao gameDao){
        this.gameDao = gameDao;
    }

    public GameResponse getGameDetailsByTitle(String title){
        return gameDao.getGameDetailsByTitle(title);
    }

    public List<String> getAllGameTitles(){return gameDao.getGameTitles();}

    public List<GameResponse> getAllGames(){return gameDao.getGames();}

    public List<String> getAllTitlesCopiesSold(){return gameDao.getGameTitlesCopiesSold();}

    public List<String> getTitlesSortByReleaseDate(){return gameDao.getTitlesSortByReleaseDate();}

    public List<String> getDistinctGenres(){return gameDao.getDistinctGenres();}
}
