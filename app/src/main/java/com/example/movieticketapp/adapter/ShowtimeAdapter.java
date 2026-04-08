package com.example.movieticketapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.R;
import com.example.movieticketapp.model.Showtime;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private List<Showtime> showtimes;
    private OnShowtimeClickListener listener;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());

    public interface OnShowtimeClickListener {
        void onBookClick(Showtime showtime);
    }

    public ShowtimeAdapter(List<Showtime> showtimes, OnShowtimeClickListener listener) {
        this.showtimes = showtimes;
        this.listener = listener;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime st = showtimes.get(position);
        holder.tvTheater.setText("Rạp: " + st.getTheaterId()); // We would typically join theater name, but ID works for simple schema if theaterId carries name. 
        if (st.getStartTime() != null) {
            holder.tvTime.setText(sdf.format(st.getStartTime()));
        }
        holder.tvAvailableSeats.setText("Ghế trống: " + st.getAvailableSeats() + "/" + st.getTotalSeats());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%,.0f đ", st.getPrice()));

        if (st.getAvailableSeats() > 0) {
            holder.btnBook.setEnabled(true);
            holder.btnBook.setAlpha(1.0f);
            holder.btnBook.setOnClickListener(v -> {
                if (listener != null) listener.onBookClick(st);
            });
        } else {
            holder.btnBook.setEnabled(false);
            holder.btnBook.setAlpha(0.5f);
            holder.btnBook.setText("Hết vé");
        }
    }

    @Override
    public int getItemCount() {
        return showtimes != null ? showtimes.size() : 0;
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheater, tvTime, tvAvailableSeats, tvPrice;
        Button btnBook;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTheater = itemView.findViewById(R.id.tvTheater);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
