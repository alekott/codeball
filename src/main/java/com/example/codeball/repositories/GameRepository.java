package com.example.codeball.repositories;

import com.example.codeball.models.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

    List<Game> findAllByOrderByStartTimeDesc();
}
