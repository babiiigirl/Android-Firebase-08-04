package com.example.movieticketapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieticketapp.model.Movie;
import com.example.movieticketapp.model.Showtime;
import com.example.movieticketapp.repository.MovieRepo;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MovieRepo movieRepo;

    public MovieViewModel() {
        movieRepo = new MovieRepo();
    }

    public LiveData<List<Movie>> getNowShowingMovies() {
        return movieRepo.getNowShowingMovies();
    }

    public LiveData<Movie> getMovieDetail(String movieId) {
        return movieRepo.getMovieDetail(movieId);
    }

    public LiveData<List<Showtime>> getShowtimesForMovie(String movieId) {
        return movieRepo.getShowtimesForMovie(movieId);
    }
}
