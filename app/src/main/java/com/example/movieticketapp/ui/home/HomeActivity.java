package com.example.movieticketapp.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.ActivityHomeBinding;
import com.example.movieticketapp.ui.profile.ProfileFragment;
import com.example.movieticketapp.ui.ticket.MyTicketsFragment;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Auto generate sample data if it's the first run
        com.example.movieticketapp.utils.DataSeeder.seedData(this);

        // Initial fragment
        if (savedInstanceState == null) {
            loadFragment(new MovieListFragment());
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(new MovieListFragment());
                return true;
            } else if (itemId == R.id.nav_tickets) {
                loadFragment(new MyTicketsFragment());
                return true;
            } else if (itemId == R.id.nav_profile) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
