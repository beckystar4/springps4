package com.example.springps4.service;

import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.persistence.dao.DeveloperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {
    private final DeveloperDao developerDao;

    @Autowired
    public DeveloperService(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    public List<DeveloperResponse> getDeveloperDetails(){return developerDao.getDeveloperDetails();}

    public List<String> getDistinctDevelopers(){return developerDao.getDistinctDevelopers();}

    public Integer getNumberOfGamesForSpecificDevelopers(String publisher){return developerDao.getNumberOfGamesForSpecificDeveloper(publisher);}

    public List<DeveloperResponse> getNumberOfGamesGroupBDevelopers(){return developerDao.getNumberOfGamesGroupByDeveloper();}

    public Integer deleteByDeveloperName(String developer){return developerDao.deleteByDeveloperName(developer);}

    public Integer insertDeveloper(DeveloperRequest developerRequest){return developerDao.insertDevelopers(developerRequest);}

    public Integer updateDeveloperNameByGameId(DeveloperRequest developerRequest){return developerDao.updateDevelopersNameByGameId(developerRequest);}

}
