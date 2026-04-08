package com.example.movieticketapp.ui.movie;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.movieticketapp.adapter.SeatAdapter;
import com.example.movieticketapp.databinding.ActivitySeatSelectionBinding;
import com.example.movieticketapp.model.Movie;
import com.example.movieticketapp.model.Showtime;
import com.example.movieticketapp.model.Ticket;
import com.example.movieticketapp.service.NotificationWorker;
import com.example.movieticketapp.ui.home.HomeActivity;
import com.example.movieticketapp.viewmodel.TicketViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SeatSelectionActivity extends AppCompatActivity {
    private ActivitySeatSelectionBinding binding;
    private Movie movie;
    private Showtime showtime;
    private SeatAdapter seatAdapter;
    private TicketViewModel ticketViewModel;
    private double currentTotalPrice = 0;
    private List<Integer> currentSelectedSeats = new ArrayList<>();
    private String fcmToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movie = (Movie) getIntent().getSerializableExtra("movie");
        showtime = (Showtime) getIntent().getSerializableExtra("showtime");

        if (movie == null || showtime == null) {
            finish();
            return;
        }

        ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> fcmToken = token);

        binding.progressBar.setVisibility(View.VISIBLE);
        ticketViewModel.getBookedSeats(showtime.getId()).observe(this, unavailable -> {
            binding.progressBar.setVisibility(View.GONE);
            if (unavailable == null) unavailable = new ArrayList<>();
            setupGrid(unavailable);
        });

        binding.btnConfirm.setOnClickListener(v -> bookTicket());
    }

    private void setupGrid(List<Integer> unavailable) {

        seatAdapter = new SeatAdapter(showtime.getTotalSeats(), unavailable, selectedSeats -> {
            currentSelectedSeats = selectedSeats;
            currentTotalPrice = selectedSeats.size() * showtime.getPrice();
            binding.tvTotalPrice.setText(String.format(Locale.getDefault(), "%,.0f đ", currentTotalPrice));
            
            if (selectedSeats.isEmpty()) {
                binding.tvSelectedSeatsLabel.setText("Chưa chọn ghế");
                binding.btnConfirm.setEnabled(false);
            } else {
                List<String> stringSeats = new ArrayList<>();
                for(int s : selectedSeats) stringSeats.add(String.valueOf(s));
                binding.tvSelectedSeatsLabel.setText("Ghế: " + String.join(", ", stringSeats));
                binding.btnConfirm.setEnabled(true);
            }
        });

        binding.rvSeats.setLayoutManager(new GridLayoutManager(this, 5));
        binding.rvSeats.setAdapter(seatAdapter);
    }

    private void bookTicket() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;
        
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnConfirm.setEnabled(false);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        List<String> textSeats = new ArrayList<>();
        for (int i : currentSelectedSeats) textSeats.add(String.valueOf(i));

        Ticket ticket = new Ticket(
                null, uid, showtime.getId(), movie.getId(), movie.getTitle(),
                showtime.getTheaterId(), showtime.getStartTime(), textSeats,
                currentTotalPrice, "BOOKED", new java.util.Date(), fcmToken
        );

        ticketViewModel.bookTicket(ticket, showtime.getId(), currentSelectedSeats.size()).observe(this, statusMsg -> {
            binding.progressBar.setVisibility(View.GONE);
            if ("SUCCESS".equals(statusMsg)) {
                scheduleNotification();
                showSuccessDialog();
            } else {
                binding.btnConfirm.setEnabled(true);
                Toast.makeText(this, statusMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void scheduleNotification() {
        long showtimeMillis = showtime.getStartTime().getTime();
        long delay = showtimeMillis - System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1);
        
        if (delay > 0) {
            Data inputData = new Data.Builder()
                    .putString("movieTitle", movie.getTitle())
                    .putString("theaterName", showtime.getTheaterId())
                    .build();

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(inputData)
                    .build();

            WorkManager.getInstance(this).enqueue(workRequest);
        }
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thành công")
                .setMessage("Đặt vé thành công!")
                .setPositiveButton("Tuyệt vời", (dialog, which) -> {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}
