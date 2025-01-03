package com.example.springps4.controller;

import com.example.springps4.model.request.GameRequest;
import com.example.springps4.service.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/v1")
public class ComplexController {
    private final ComplexService service;

    @Autowired
    public ComplexController(ComplexService service) {
        this.service = service;
    }

    @GetMapping("/titles-and-publishers")
    public ResponseEntity<ArrayList<String>> getGameTitlesAndPublishers(){
        List<Map<String, String>> gamePublisherList = service.getGameTitleAndPublishers();
        ArrayList<String> result = new ArrayList<>();
        result.add("Game Title || Publisher ");
        for (Map<String, String> row : gamePublisherList) {
            String game = row.get("title");
            String publisher = row.get("publisher");
            result.add(game + " || " + publisher);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/titles-and-developers")
    public ResponseEntity<ArrayList<String>> getGameTitleAndDevelopers(){
        List<Map<String, String>> gameDeveloperList = service.getGameTitleAndDevelopers();
        ArrayList<String> result = new ArrayList<>();
        result.add("Game Title || Developer ");
        for (Map<String, String> row : gameDeveloperList) {
            String game = row.get("title");
            String developer = row.get("developer");
            result.add(game + " || " + developer);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/titles-developers-publishers")
    public ResponseEntity<ArrayList<String>> getGameTitleDevelopersAndPublishers(){
        List<Map<String, String>> gameDeveloperList = service.getGameTitleDevelopersAndPublishers();
        ArrayList<String> result = new ArrayList<>();
        result.add("Game Title || Developer || Publisher");
        for (Map<String, String> row : gameDeveloperList) {
            String game = row.get("title");
            String developer = row.get("developer");
            String publisher = row.get("publisher");
            result.add(game + " || " + developer + " || " + publisher);
        }
        return ResponseEntity.ok(result);
    }

    // If title is present, find details for that title, otherwise get all details.
        //http://localhost:8080/api/v1/?title= gets all details
        //http://localhost:8080/api/v1/ gets all details
        //http://localhost:8080/api/v1/?title=God+of+War gets details for specific title
    @GetMapping("/")
    public ResponseEntity<ArrayList<String>> getAllDetails(
            @RequestParam(name = "title", required=false) String title
    ){
        List<Map<String, String>> details;
        if (title == null || title.isEmpty()){
            details = service.getDetails();
        }
        else{
            details = service.getDetailsByTitle(title);
        }
        ArrayList<String> result = new ArrayList<>();
        result.add("Game Title || genres || millions_of_copies_sold || release_date || Developer || Publisher");
        for (Map<String, String> row : details) {
            String game = row.get("title");
            String genres = row.get("genres");
            String millions_of_copies_sold = row.get("millions_of_copies_sold");
            String release_date =  row.get("release_date");
            String developer = row.get("developer");
            String publisher = row.get("publisher");
            result.add(game + " || " + genres  + " || " + millions_of_copies_sold + " million copies sold || "
                    + release_date + " || " + developer + " || " + publisher);
        }
        return ResponseEntity.ok(result);
    }

    //http://localhost:8080/api/v1/genres-pub?publisher=Square+Enix
    @GetMapping("/genres-pub")
    public ResponseEntity<String> getDistinctGenresPublisher(
            @RequestParam String publisher
    ){
        List<String> details = service.getDistinctGenresPublisher(publisher);

        if (publisher == null || publisher.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please provide required information: Publisher Name");
        }

        StringBuilder result = new StringBuilder( publisher + ": ");
        for (String genre : details) {
            result.append(genre).append(", ");  // Append each genre to the result
        }

        return ResponseEntity.ok(result.toString());
    }

    //http://localhost:8080/api/v1/genres-dev?developer=EA+DICE
    @GetMapping("/genres-dev")
    public ResponseEntity<String> getDistinctGenresDeveloper(
            @RequestParam String developer
    ){
        List<String> details = service.getDistinctGenresDeveloper(developer);

        if (developer == null || developer.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please provide required information: Developer Name");
        }

        StringBuilder result = new StringBuilder( developer + ": ");
        for (String genre : details) {
            result.append(genre).append(", ");  // Append each genre to the result
        }

        return ResponseEntity.ok(result.toString());
    }

    @GetMapping("/newest-game")
    public ResponseEntity<String> getNewestGame(){
        List<String> details = service.getNewestGame();

        return ResponseEntity.ok(details.get(0));
    }

    @PostMapping("/")
    public ResponseEntity<String> batchInsert(
            @RequestBody GameRequest gameRequest,
            @RequestParam String publisher,
            @RequestParam String developer) {
        System.out.println(gameRequest.getTitle());
        System.out.println(publisher + " " + developer);
        if (gameRequest == null ||
                gameRequest.getTitle() == null || gameRequest.getTitle().isEmpty() ||
                gameRequest.getRelease_date() == null || gameRequest.getRelease_date().toString().isEmpty() ||
                publisher == null || publisher.isEmpty() ||
                developer == null || developer.isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please provide required information: Game Title, Game Release Date, Publisher Name, and Developer Name");
        }

        Integer batchInsert = service.batchInsert(gameRequest, publisher, developer);

        if (batchInsert == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to save Game details. Please try again.");
        }
        else if (batchInsert == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to save Publisher details. Please try again.");
        }
        else if (batchInsert == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to save Developer details. Please try again.");
        }else if (batchInsert == -100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed. Please try again.");
        }

        return ResponseEntity.ok("All details were saved successfully.");
    }

}
