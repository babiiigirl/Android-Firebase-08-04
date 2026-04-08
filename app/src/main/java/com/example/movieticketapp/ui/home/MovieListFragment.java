package com.example.movieticketapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieticketapp.adapter.MovieAdapter;
import com.example.movieticketapp.databinding.FragmentMovieListBinding;
import com.example.movieticketapp.ui.movie.MovieDetailActivity;
import com.example.movieticketapp.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class MovieListFragment extends Fragment {
    private FragmentMovieListBinding binding;
    private MovieViewModel movieViewModel;
    private MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        adapter = new MovieAdapter(new ArrayList<>(), movie -> {
            Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });

        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMovies.setAdapter(adapter);

        binding.progressBar.setVisibility(View.VISIBLE);
        movieViewModel.getNowShowingMovies().observe(getViewLifecycleOwner(), movies -> {
            binding.progressBar.setVisibility(View.GONE);
            if (movies != null && !movies.isEmpty()) {
                adapter.setMovies(movies);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.rvMovies.setVisibility(View.VISIBLE);
            } else {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.rvMovies.setVisibility(View.GONE);
            }
        });
    }
}
