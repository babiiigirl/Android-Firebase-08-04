package com.example.movieticketapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movieticketapp.model.Movie;
import com.example.movieticketapp.model.Showtime;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieRepo {
    private FirebaseFirestore db;

    public MovieRepo() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<Movie>> getNowShowingMovies() {
        MutableLiveData<List<Movie>> liveData = new MutableLiveData<>();
        db.collection("movies")
                .whereEqualTo("nowShowing", true)
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null) {
                        List<Movie> movies = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Movie movie = doc.toObject(Movie.class);
                            movie.setId(doc.getId());
                            movies.add(movie);
                        }
                        liveData.setValue(movies);
                    }
                });
        return liveData;
    }

    public MutableLiveData<Movie> getMovieDetail(String movieId) {
        MutableLiveData<Movie> liveData = new MutableLiveData<>();
        db.collection("movies").document(movieId)
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null && value.exists()) {
                        Movie movie = value.toObject(Movie.class);
                        if (movie != null) {
                            movie.setId(value.getId());
                            liveData.setValue(movie);
                        }
                    }
                });
        return liveData;
    }

    public MutableLiveData<List<Showtime>> getShowtimesForMovie(String movieId) {
        MutableLiveData<List<Showtime>> liveData = new MutableLiveData<>();
        db.collection("showtimes")
                .whereEqualTo("movieId", movieId)
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null) {
                        List<Showtime> showtimes = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Showtime st = doc.toObject(Showtime.class);
                            st.setId(doc.getId());
                            showtimes.add(st);
                        }
                        liveData.setValue(showtimes);
                    }
                });
        return liveData;
    }
}
