package com.example.codeball.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer durationInMinutes;
    @ManyToMany
    private List<Enrollment> enrollments;
    private boolean isEnrollmentOver;
    private boolean isGameOver;
    private Integer pitchId;
    @JsonIgnore
    private LocalDateTime startTime;
    @JsonIgnore
    @ManyToMany
    private List<User> teamA;
    private Integer teamAScore;
    @JsonIgnore
    @ManyToMany
    private List<User> teamB;
    private Integer teamBScore;

    public Game() {
        this.enrollments = new ArrayList<>();
        this.teamA = new ArrayList<>();
        this.teamB = new ArrayList<>();
    }

    public Game(Integer id, Integer durationInMinutes, List<Enrollment> enrollments, boolean isEnrollmentOver, boolean isGameOver, Integer pitchId, LocalDateTime startTime, List<User> teamA, Integer teamAScore, List<User> teamB, Integer teamBScore) {
        this.id = id;
        this.durationInMinutes = durationInMinutes;
        this.enrollments = enrollments;
        this.isEnrollmentOver = isEnrollmentOver;
        this.isGameOver = isGameOver;
        this.pitchId = pitchId;
        this.startTime = startTime;
        this.teamA = teamA;
        this.teamAScore = teamAScore;
        this.teamB = teamB;
        this.teamBScore = teamBScore;
    }



    @JsonIgnore
    public BasicGame getBasicGame() {
        return new BasicGame(this.durationInMinutes, this.pitchId, this.startTime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @JsonGetter("isEnrollmentOver")
    public boolean isEnrollmentOver() {
        return isEnrollmentOver;
    }

    public void setEnrollmentOver(boolean enrollmentOver) {
        isEnrollmentOver = enrollmentOver;
    }

    @JsonGetter("isGameOver")
    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public Integer getPitchId() {
        return pitchId;
    }

    public void setPitchId(Integer pitchId) {
        this.pitchId = pitchId;
    }

    @JsonIgnore
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @JsonIgnore
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @JsonGetter("startTimestamp")
    private long getStartTimestamp() {
        return startTime.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    @JsonSetter("startTimestamp")
    private void setStartTImestamp(long startTimestamp) {
        this.startTime = Instant.ofEpochSecond(startTimestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @JsonIgnore
    public List<User> getTeamA() {
        return teamA;
    }

    @JsonIgnore
    public void setTeamA(List<User> teamA) {
        this.teamA = teamA;
    }

    @JsonGetter("teamAIds")
    public List<Integer> getTeamAIds() {
        return getTeamA().stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(Integer teamAScore) {
        this.teamAScore = teamAScore;
    }

    @JsonIgnore
    public List<User> getTeamB() {
        return teamB;
    }

    @JsonIgnore
    public void setTeamB(List<User> teamB) {
        this.teamB = teamB;
    }

    @JsonGetter("teamBIds")
    public List<Integer> getTeamBIds() {
        return getTeamB().stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(Integer teamBScore) {
        this.teamBScore = teamBScore;
    }
}
