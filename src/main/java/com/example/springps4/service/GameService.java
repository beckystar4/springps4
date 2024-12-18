package com.example.springps4.service;

import com.example.springps4.mapper.GameMapper;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.persistence.dao.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameDao gameDao;
    private final GameMapper gameMapper;

    @Autowired
    public GameService(GameDao gameDao, GameMapper gameMapper){
        this.gameDao = gameDao;
        this.gameMapper = gameMapper;
    }

    public GameResponse getGameDetailsByTitle(String title){
        return gameDao.getGameDetailsByTitle(title);
    }
}
