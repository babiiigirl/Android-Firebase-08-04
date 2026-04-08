package com.example.movieticketapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.R;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private int totalSeats;
    private List<Integer> unavailableSeats;
    private List<Integer> selectedSeats;
    private OnSeatSelectionListener listener;

    public interface OnSeatSelectionListener {
        void onSeatSelected(List<Integer> selected);
    }

    public SeatAdapter(int totalSeats, List<Integer> unavailableSeats, OnSeatSelectionListener listener) {
        this.totalSeats = totalSeats;
        this.unavailableSeats = unavailableSeats;
        this.selectedSeats = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        int seatNum = position + 1;
        holder.tvSeatNumber.setText(String.valueOf(seatNum));

        holder.itemView.setOnClickListener(null);

        if (unavailableSeats.contains(seatNum)) {
            holder.tvSeatNumber.setBackgroundColor(Color.parseColor("#757575")); // unavailable = grey
        } else if (selectedSeats.contains(seatNum)) {
            holder.tvSeatNumber.setBackgroundColor(Color.parseColor("#FF9800")); // selected = orange
            holder.itemView.setOnClickListener(v -> toggleSeat(seatNum));
        } else {
            holder.tvSeatNumber.setBackgroundColor(Color.parseColor("#4CAF50")); // available = green
            holder.itemView.setOnClickListener(v -> toggleSeat(seatNum));
        }
    }

    private void toggleSeat(int seatNum) {
        if (selectedSeats.contains(seatNum)) {
            selectedSeats.remove(Integer.valueOf(seatNum));
        } else {
            if (selectedSeats.size() >= 6) {
                return; // max 6 seats
            }
            selectedSeats.add(seatNum);
        }
        notifyDataSetChanged();
        if (listener != null) {
            listener.onSeatSelected(selectedSeats);
        }
    }

    @Override
    public int getItemCount() {
        return totalSeats;
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeatNumber;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeatNumber = itemView.findViewById(R.id.tvSeatNumber);
        }
    }
}
