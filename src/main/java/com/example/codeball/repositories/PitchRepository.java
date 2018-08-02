package com.example.codeball.repositories;

import com.example.codeball.models.Pitch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitchRepository extends CrudRepository<Pitch, Integer> {
}
