package com.example.springps4.controller;

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
//
//    @GetMapping("/ids/{title}")
//    public ResponseEntity<Long> getGameIdByTitle(
//            @PathVariable String title
//    ){
//        return Optional.ofNullable(service.getGameIdByTitle(title))
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.noContent().build());
//    }
}
