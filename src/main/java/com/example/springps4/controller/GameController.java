package com.example.springps4.controller;

import com.example.springps4.model.response.GameResponse;
import com.example.springps4.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("game/{title}")
    public ResponseEntity<GameResponse> getGameDetailsByTitle(
            @PathVariable String title
    ){
        GameResponse game = service.getGameDetailsByTitle(title);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if not found
        }
        return ResponseEntity.ok(game);  // Return 200 OK if found
    }
}
