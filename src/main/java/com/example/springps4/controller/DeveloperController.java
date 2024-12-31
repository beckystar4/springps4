package com.example.springps4.controller;

import com.example.springps4.model.request.DeveloperRequest;
import com.example.springps4.model.response.DeveloperResponse;
import com.example.springps4.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/developers")
public class DeveloperController {
    private final DeveloperService service;

    @Autowired
    public DeveloperController(DeveloperService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DeveloperResponse>> getDeveloperDetails(){
        return Optional.ofNullable(service.getDeveloperDetails())
                .filter(developers -> !CollectionUtils.isEmpty(developers))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getDistinctDevelopers(){
        return Optional.ofNullable(service.getDistinctDevelopers())
                .filter(developers -> !CollectionUtils.isEmpty(developers))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Example: /number-of-games-specific?developer=Sony+Interactive+Entertainment
    @GetMapping("/number-of-games-specific")
    public ResponseEntity<String> getNumberOfGamesForSpecificDeveloper(
            @RequestParam String developer
    ){
        Integer gameCount = service.getNumberOfGamesForSpecificDevelopers(developer);

        if (gameCount != null) {
            // Return OK with the game count and a success message
            return ResponseEntity.ok(developer + " has " + gameCount + " number of games");
        } else {
            // Return no content with a failure message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No games found for publisher: " + developer);
        }
    }

    //Avoids having producerId
    @GetMapping("/number-of-games")
    public ResponseEntity<List<String>> getNumberOfGamesGroupByDeveloper(){
        List<DeveloperResponse> output = service.getNumberOfGamesGroupBDevelopers();
        if (output == null){
            return ResponseEntity.noContent().build();
        }
        ArrayList<String> counts = new ArrayList<>();
        for (DeveloperResponse developer : output) {
            if(developer.getGame_id() == 1){
                counts.add(developer.getDeveloper() + " has " + developer.getGame_id() + " game");
            } else{
                counts.add(developer.getDeveloper() + " has " + developer.getGame_id() + " games");
            }
        }
        return ResponseEntity.ok(counts);
    }

    //    http://localhost:8080/api/v1/publishers/?publisher=Sony+Interactive+Entertainment
    @DeleteMapping("/")
    public ResponseEntity<String> deleteGameByDeveloperName(
            @RequestParam String developer
    ){
        int rowsAffected = service.deleteByDeveloperName(developer);
        if (rowsAffected == 1){
            return ResponseEntity.ok(developer + " deleted successfully.");
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> saveDeveloper(
            @RequestBody List<DeveloperRequest> developerRequests
    ){
        if (developerRequests == null || developerRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No games provided. Please provide at least one game.");
        }
        for (DeveloperRequest developerRequest : developerRequests) {
            if (developerRequest.getDeveloper() == null|| developerRequest.getDeveloper().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Publisher must not be null.");
            }

            System.out.println("Trying to save...");
            if (service.insertDeveloper(developerRequest) <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to save publisher details. Please try again.");
            }
        }
        return ResponseEntity.ok("All publisher details were saved successfully.");
    }

    @PutMapping("/")
    public ResponseEntity<String> updateDeveloperNameByGameId(
            @RequestBody List<DeveloperRequest> developerRequests
    ){
        if (developerRequests == null || developerRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No publishers provided. Please provide at least one publisher.");
        }
        for (DeveloperRequest developerRequest : developerRequests) {
            if (developerRequest.getDeveloper() == null || developerRequest.getDeveloper().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Publisher must not be null.");
            }

            System.out.println("Trying to save...");
            if (service.updateDeveloperNameByGameId(developerRequest) <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to update publisher details. Please try again.");
            }
        }
        return ResponseEntity.ok("All publisher details were updated successfully.");
    }
}
