package com.example.movieticketapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Ticket implements Serializable {
    private String id;
    private String userId;
    private String showtimeId;
    private String movieId;
    private String movieTitle;
    private String theaterName;
    private Date startTime;
    private List<String> seatNumbers;
    private double totalPrice;
    private String status; // "BOOKED", "CANCELLED", "USED"
    private Date bookedAt;
    private String fcmToken;

    public Ticket() {}

    public Ticket(String id, String userId, String showtimeId, String movieId, String movieTitle, String theaterName, Date startTime, List<String> seatNumbers, double totalPrice, String status, Date bookedAt, String fcmToken) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.theaterName = theaterName;
        this.startTime = startTime;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
        this.status = status;
        this.bookedAt = bookedAt;
        this.fcmToken = fcmToken;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
