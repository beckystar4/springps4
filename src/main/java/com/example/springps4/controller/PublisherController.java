package com.example.springps4.controller;

import com.example.springps4.model.response.GameResponse;
import com.example.springps4.model.response.PublisherResponse;
import com.example.springps4.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path="api/v1/publishers")
public class PublisherController {
    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponse>> getPublisherDetails(){
        return Optional.ofNullable(service.getPublisherDetails())
                .filter(publishers -> !CollectionUtils.isEmpty(publishers))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getDistinctPublishers(){
        return Optional.ofNullable(service.getDistinctPublishers())
                .filter(publishers -> !CollectionUtils.isEmpty(publishers))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Example: /number-of-games?publisher=Sony+Interactive+Entertainment
    @GetMapping("/number-of-games-specific")
    public ResponseEntity<String> getNumberOfGamesForSpecificPublisher(
            @RequestParam String publisher
    ){
        Integer gameCount = service.getNumberOfGamesForSpecificPublisher(publisher);

        if (gameCount != null) {
            // Return OK with the game count and a success message
            return ResponseEntity.ok(publisher + "has " + gameCount + " number of games");
        } else {
            // Return no content with a failure message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No games found for publisher: " + publisher);
        }
    }

    //Avoids having producerId
    @GetMapping("/number-of-games")
    public ResponseEntity<List<String>> getNumberOfGamesGroupByPublisher(){
        List<PublisherResponse> output = service.getNumberOfGamesGroupByPublisher();
        if (output == null){
            return ResponseEntity.noContent().build();
        }
        ArrayList<String> counts = new ArrayList<>();
        for (PublisherResponse publisher : output) {
            if(publisher.getGame_id() == 1){
                counts.add(publisher.getPublisher() + " has " + publisher.getGame_id() + " game");
            } else{
                counts.add(publisher.getPublisher() + " has " + publisher.getGame_id() + " games");
            }
        }
        return ResponseEntity.ok(counts);
    }
}
