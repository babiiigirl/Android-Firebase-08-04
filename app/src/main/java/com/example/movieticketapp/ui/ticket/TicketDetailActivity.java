package com.example.movieticketapp.ui.ticket;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieticketapp.databinding.ActivityTicketDetailBinding;
import com.example.movieticketapp.utils.QRCodeUtils;
import com.example.movieticketapp.viewmodel.TicketViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TicketDetailActivity extends AppCompatActivity {
    private ActivityTicketDetailBinding binding;
    private TicketViewModel ticketViewModel;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String ticketId = getIntent().getStringExtra("ticketId");
        if (ticketId == null) {
            finish();
            return;
        }

        ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        ticketViewModel.getTicketById(ticketId).observe(this, ticket -> {
            if (ticket != null) {
                binding.tvMovieTitle.setText(ticket.getMovieTitle());
                binding.tvTheater.setText("Rạp: " + ticket.getTheaterName());
                if(ticket.getStartTime() != null){
                    binding.tvTime.setText(sdf.format(ticket.getStartTime()));
                }
                
                String seats = String.join(", ", ticket.getSeatNumbers());
                binding.tvSeats.setText("Ghế: " + seats);
                binding.tvStatus.setText(ticket.getStatus());

                try {
                    Bitmap qrCode = QRCodeUtils.generateQRCode(ticket.getId(), 200, 200);
                    binding.ivQrCode.setImageBitmap(qrCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
