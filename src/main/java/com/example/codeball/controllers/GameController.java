package com.example.codeball.controllers;

import com.example.codeball.enums.EnrollmentStatus;
import com.example.codeball.models.*;
import com.example.codeball.repositories.EnrollmentRepository;
import com.example.codeball.repositories.GameRepository;
import com.example.codeball.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600L)
@RestController
public class GameController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/game")
    public Iterable<Game> getGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/api/game/upcoming")
    public ResponseEntity getUpcomingGame() {
        return StreamSupport.stream(gameRepository.findAll().spliterator(), false)
                .filter(game -> !game.isGameOver() && game.getStartTime().isAfter(LocalDateTime.now()))
                .findFirst().map(game -> new ResponseEntity(game, HttpStatus.OK))
                .orElse(new ResponseEntity(new ErrorMessage(true, "no upcoming games"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/game/{gameId}")
    public Game getGameById(@PathVariable int gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game doesn't exist"));
    }

    @PostMapping("/api/game")
    public ResponseEntity createGame(@RequestBody Game game) {
        return new ResponseEntity(gameRepository.save(game), HttpStatus.OK);
    }

    @PutMapping("/api/game/{gameId}")
    public ResponseEntity updategame(@RequestBody Game game, @PathVariable int gameId) {
        game.setId(gameId);
        return new ResponseEntity(gameRepository.save(game), HttpStatus.OK);
    }

    @DeleteMapping("/api/game/{gameId}")
    public ResponseEntity deleteGame(@PathVariable int gameId) {
        gameRepository.deleteById(gameId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/game/last")
    public Game getLastGame() {
        return StreamSupport.stream(gameRepository.findAllByOrderByStartTimeDesc().spliterator(), false)
                .filter(game -> game.isGameOver() && game.getStartTime().isBefore(LocalDateTime.now()))
                .findFirst().get();
    }

    @PutMapping("/api/game/{gameId}/end")
    public ResponseEntity endGame(@PathVariable int gameId) {
        Game game = getGameById(gameId);
        game.setGameOver(true);
        return new ResponseEntity(gameRepository.save(game), HttpStatus.OK);
    }

    @PutMapping("/api/game/{gameId}/enrollment")
    public ResponseEntity setEnrollmentStatus(@PathVariable int gameId, @RequestBody EnrollmentStatus enrollmentStatus) {
        Game game = getGameById(gameId);
        Optional<Enrollment> enrollmentByUserId = game.getEnrollments().stream()
                .filter(enrollment -> enrollment.getEnrollerId().equals(userSession.getCurrentUserId()))
                .findAny();

        if(!game.isEnrollmentOver()) {
            if (enrollmentByUserId.isPresent()) {
                enrollmentByUserId.get().setEnrollmentStatus(enrollmentStatus);
                enrollmentRepository.save(enrollmentByUserId.get());
            } else {
                Enrollment enrollment = new Enrollment(enrollmentStatus, userSession.getCurrentUser(), userSession.getCurrentUserId());
                game.getEnrollments().add(enrollment);
                enrollmentRepository.save(enrollment);
                gameRepository.save(game);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/game/{gameId}/enrollment/{userId}")
    public ResponseEntity setOtherPlayerEnrollmentStatus(@PathVariable int gameId, @PathVariable int userId, @RequestBody EnrollmentStatus enrollmentStatus) {
        Game game = getGameById(gameId);
        User userById = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user doesn't exist"));
        Optional<Enrollment> enrollmentByUser = game.getEnrollments().stream()
                .filter(enrollment -> enrollment.getUser().equals(userById))
                .findAny();

        if (!game.isEnrollmentOver()) {
            if (enrollmentByUser.isPresent()) {
                enrollmentByUser.get().setEnrollmentStatus(enrollmentStatus);
                enrollmentRepository.save(enrollmentByUser.get());
            } else {
                Enrollment enrollment = new Enrollment(enrollmentStatus, userById, userSession.getCurrentUserId());
                game.getEnrollments().add(enrollment);
                enrollmentRepository.save(enrollment);
                gameRepository.save(game);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(game, HttpStatus.OK);
    }

    @PutMapping("/api/game/{gameId}/finishEnrollment")
    public ResponseEntity finishEnrollment(@PathVariable int gameId) {
        Game game = getGameById(gameId);
        game.setEnrollmentOver(true);
        return new ResponseEntity(gameRepository.save(game), HttpStatus.OK);
    }

    @PutMapping("/api/game/{gameId}/score")
    public ResponseEntity setGameScore(@PathVariable int gameId, @RequestBody GameScoreRequest gameScoreRequest) {
        Game game = getGameById(gameId);
        if(game.isGameOver()) {
            game.setTeamAScore(gameScoreRequest.getTeamAScore());
            game.setTeamBScore(gameScoreRequest.getTeamBScore());
        }
        return new ResponseEntity(gameRepository.save(game), HttpStatus.OK);
    }

    @PutMapping("/api/game/{gameId}/team")
    public ResponseEntity drawTeams(@PathVariable int gameId) {
        Game game = getGameById(gameId);
        List<User> players = game.getEnrollments().stream()
                .filter(enrollment -> enrollment.getEnrollmentStatus().equals(EnrollmentStatus.YES))
                .map(enrollment -> enrollment.getUser())
                .collect(Collectors.toList());

        List<User> aPlayers = new ArrayList<>(players.subList(0, players.size()/2));
        List<User> bPlayers = new ArrayList<>(players.subList(players.size()/2, players.size()));
        game.setTeamA(aPlayers);
        game.setTeamB(bPlayers);

        return new ResponseEntity(gameRepository.save(game), HttpStatus.OK);
    }



}
