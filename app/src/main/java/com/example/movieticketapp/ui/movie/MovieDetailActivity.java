package com.example.movieticketapp.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.movieticketapp.adapter.ShowtimeAdapter;
import com.example.movieticketapp.databinding.ActivityMovieDetailBinding;
import com.example.movieticketapp.model.Movie;
import com.example.movieticketapp.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    private ActivityMovieDetailBinding binding;
    private MovieViewModel movieViewModel;
    private ShowtimeAdapter showtimeAdapter;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movie = (Movie) getIntent().getSerializableExtra("movie");
        if (movie == null) {
            finish();
            return;
        }

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        binding.tvTitle.setText(movie.getTitle());
        binding.tvInfo.setText(movie.getGenre() + " • " + movie.getDurationMinutes() + " phút • ★ " + movie.getRating());
        binding.tvDescription.setText(movie.getDescription());

        Glide.with(this).load(movie.getPosterUrl()).into(binding.ivPoster);

        showtimeAdapter = new ShowtimeAdapter(new ArrayList<>(), showtime -> {
            Intent intent = new Intent(MovieDetailActivity.this, SeatSelectionActivity.class);
            intent.putExtra("movie", movie);
            intent.putExtra("showtime", showtime);
            startActivity(intent);
        });

        binding.rvShowtimes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvShowtimes.setAdapter(showtimeAdapter);

        loadShowtimes();
    }

    private void loadShowtimes() {
        binding.progressBar.setVisibility(View.VISIBLE);
        movieViewModel.getShowtimesForMovie(movie.getId()).observe(this, showtimes -> {
            binding.progressBar.setVisibility(View.GONE);
            if (showtimes != null && !showtimes.isEmpty()) {
                showtimeAdapter.setShowtimes(showtimes);
                binding.rvShowtimes.setVisibility(View.VISIBLE);
            } else {
                binding.rvShowtimes.setVisibility(View.GONE);
            }
        });
    }
}
