package com.example.codeball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

    @JsonProperty
    private boolean isSilent;

    @JsonProperty
    private String message;

    public ErrorMessage(boolean isSilent, String message) {
        this.isSilent = isSilent;
        this.message = message;
    }
}
