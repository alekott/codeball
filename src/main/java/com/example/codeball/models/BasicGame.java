package com.example.codeball.models;

import java.time.LocalDateTime;

public class BasicGame {

    private Integer durationInMinutes;
    private Integer pitchId;
    private LocalDateTime startTimestamp;

    public BasicGame(Integer durationInMinutes, Integer pitchId, LocalDateTime startTimestamp) {
        this.durationInMinutes = durationInMinutes;
        this.pitchId = pitchId;
        this.startTimestamp = startTimestamp;
    }

    public BasicGame() {
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getPitchId() {
        return pitchId;
    }

    public void setPitchId(Integer pitchId) {
        this.pitchId = pitchId;
    }

    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(LocalDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
