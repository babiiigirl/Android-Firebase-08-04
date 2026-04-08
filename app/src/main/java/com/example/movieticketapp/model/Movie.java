package com.example.movieticketapp.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String description;
    private String genre;
    private int durationMinutes;
    private String posterUrl;
    private float rating;
    private boolean isNowShowing;

    public Movie() {}

    public Movie(String id, String title, String description, String genre, int durationMinutes, String posterUrl, float rating, boolean isNowShowing) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.isNowShowing = isNowShowing;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isNowShowing() {
        return isNowShowing;
    }

    public void setNowShowing(boolean nowShowing) {
        isNowShowing = nowShowing;
    }
}
