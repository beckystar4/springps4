package com.example.springps4.controller;

import com.example.springps4.model.request.GameRequest;
import com.example.springps4.model.response.GameResponse;
import com.example.springps4.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/games")
public class GameController {
    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/{title}")
    public ResponseEntity<GameResponse> getGameDetailsByTitle(
            @PathVariable String title
    ){
        return Optional.ofNullable(service.getGameDetailsByTitle(title))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/id={game_id}")
    public ResponseEntity<GameResponse> getGameDetailsById(
            @PathVariable Long game_id
    ){
        return Optional.ofNullable(service.getGameDetailsById(game_id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


    //    @GetMapping("/ids/{title}")
//    public ResponseEntity<Long> getGameIdByTitle(
//            @PathVariable String title
//    ){
//        return Optional.ofNullable(service.getGameIdByTitle(title))
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.noContent().build());
//    }
    @GetMapping
    public ResponseEntity<List<GameResponse>> getAllGames(){
        return Optional.ofNullable(service.getAllGames())
                .filter(titles -> !CollectionUtils.isEmpty(titles))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/titles")
    public ResponseEntity<List<String>> getAllGameTitles(){
        return Optional.ofNullable(service.getAllGameTitles())
                .filter(titles -> !CollectionUtils.isEmpty(titles))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/titles-copies-sold")
    public ResponseEntity<List<String>> getAllTitlesCopiesSold(){
        return Optional.ofNullable(service.getAllTitlesCopiesSold())
                .filter(titles -> !CollectionUtils.isEmpty(titles))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/titles-release-date")
    public ResponseEntity<List<String>> getTitlesSortByReleaseDate(){
        return Optional.ofNullable(service.getTitlesSortByReleaseDate())
                .filter(titles -> !CollectionUtils.isEmpty(titles))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getDistinctGenres(){
        return Optional.ofNullable(service.getDistinctGenres())
                .filter(genres -> !CollectionUtils.isEmpty(genres))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    //Genres and copies_sold can be null, Title and Release_date cannot be. Additionally title must be unique
    @PostMapping("/add-games")
    public ResponseEntity<String> saveGameDetails(@RequestBody GameRequest gameRequest){
        if (gameRequest.getTitle() != null && gameRequest.getRelease_date() != null){
            System.out.println("Trying to save...");
            if (service.insertGames(gameRequest) > 0){
                return ResponseEntity.ok("Game details were saved successfully.");
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to save game details. Please try again.");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Title and Release Date must not be null.");
        }
    }

    /*
    Will update game with all the details provided by user in one transaction.
    Example: if the user only puts name and release date, then only those two things will be updated
     */
    @PutMapping("/{game_id}")
    public ResponseEntity<String> updateEverythingByGameId(
            @PathVariable Long game_id,
            @RequestBody GameRequest updateGameRequest
    ){
        int rowsAffected = service.updateGameDetails(game_id, updateGameRequest);
        if (rowsAffected > 0){
            return ResponseEntity.ok("Game details were updated successfully.");
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{game_id}")
    public ResponseEntity<String> deleteGameByGameId(
            @PathVariable Long game_id
    ){
        int rowsAffected = service.deleteGameById(game_id);
        if (rowsAffected == 1){
            return ResponseEntity.ok("Game with id: " + game_id + " deleted successfully.");
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }
}
