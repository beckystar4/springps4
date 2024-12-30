package com.example.springps4.service;

import com.example.springps4.model.response.PublisherResponse;
import com.example.springps4.persistence.dao.PublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherDao publisherDao;

    @Autowired
    public PublisherService(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    public List<PublisherResponse> getPublisherDetails(){return publisherDao.getPublisherDetails();}

    public List<String> getDistinctPublishers(){return publisherDao.getDistinctPublishers();}

    public Integer getNumberOfGamesForSpecificPublisher(String publisher){return publisherDao.getNumberOfGamesForSpecificPublisher(publisher);}

    public List<PublisherResponse> getNumberOfGamesGroupByPublisher(){return publisherDao.getNumberOfGamesGroupByPublisher();}
}
