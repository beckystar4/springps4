package com.example.springps4.service;

import com.example.springps4.model.request.GameRequest;
import com.example.springps4.persistence.dao.ComplexDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComplexService {
    private final ComplexDao complexDao;

    @Autowired
    public ComplexService(ComplexDao complexDao) {
        this.complexDao = complexDao;
    }

    public List<Map<String, String>> getGameTitleAndPublishers(){return complexDao.getGameTitleAndPublishers();}
    public List<Map<String, String>> getGameTitleAndDevelopers(){return complexDao.getGameTitleAndDevelopers();}
    public List<Map<String, String>> getGameTitleDevelopersAndPublishers(){return complexDao.getGameTitleDevelopersAndPublishers();}
    public List<Map<String, String>> getDetails(){return complexDao.getDetails();}
    public List<Map<String, String>> getDetailsByTitle(String title){return complexDao.getDetailsByTitle(title);}
    public List<String> getDistinctGenresPublisher(String publisher){return complexDao.getDistinctGenresPublisher(publisher);}
    public List<String> getDistinctGenresDeveloper(String developer){return complexDao.getDistinctGenresDeveloper(developer);}
    public List<String> getNewestGame(){return complexDao.getSelectNewestGame();}
    public Integer batchInsert(GameRequest gameRequest, String publisher, String developer){return complexDao.batchInsert(gameRequest, publisher, developer);}
}
