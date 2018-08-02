package com.example.codeball.models;

import com.example.codeball.enums.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue
    private Integer id;
    private EnrollmentStatus enrollmentStatus;
    @JsonIgnore
    @ManyToOne
    private User user;
    private Integer enrollerId;

    public Enrollment(EnrollmentStatus enrollmentStatus, User user, Integer enrollerId) {
        this.enrollmentStatus = enrollmentStatus;
        this.user = user;
        this.enrollerId = enrollerId;
    }

    public Enrollment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnrollerId() {
        return enrollerId;
    }

    public void setEnrollerId(Integer enrollerId) {
        this.enrollerId = enrollerId;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("userId")
    public int getUserId() {
        return user.getId();
    }
}
