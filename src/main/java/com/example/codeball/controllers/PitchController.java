package com.example.codeball.controllers;

import com.example.codeball.models.Pitch;
import com.example.codeball.repositories.PitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600L)
@RestController
public class PitchController {

    @Autowired
    private PitchRepository pitchRepository;

    @GetMapping("/api/pitch")
    public Iterable<Pitch> listPitches() {
        return pitchRepository.findAll();
    }

    @PostMapping("/api/pitch")
    public ResponseEntity createPitch(@RequestBody Pitch pitch) {
        return new ResponseEntity(pitchRepository.save(pitch), HttpStatus.OK);
    }

    @DeleteMapping("/api/pitch/{id}")
    public ResponseEntity deletePitch(@PathVariable int id) {
        pitchRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/pitch/{id}")
    public Pitch getPitchById(@PathVariable int id) {
        return pitchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pitch doesn't exist"));
    }

    @PutMapping("/api/pitch/{id}")
    public ResponseEntity updatePitch(@RequestBody Pitch pitch, @PathVariable int id) {
        pitch.setId(id);
        pitchRepository.save(pitch);
        return new ResponseEntity(HttpStatus.OK);
    }
}
