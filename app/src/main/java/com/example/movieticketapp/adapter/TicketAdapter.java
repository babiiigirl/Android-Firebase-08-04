package com.example.movieticketapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.R;
import com.example.movieticketapp.model.Ticket;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> tickets;
    private OnTicketClickListener listener;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    public TicketAdapter(List<Ticket> tickets, OnTicketClickListener listener) {
        this.tickets = tickets;
        this.listener = listener;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket t = tickets.get(position);
        holder.tvMovieTitle.setText(t.getMovieTitle());
        holder.tvTheater.setText(t.getTheaterName());
        
        if (t.getStartTime() != null) {
            holder.tvTime.setText(sdf.format(t.getStartTime()));
        }
        
        String seats = String.join(", ", t.getSeatNumbers());
        holder.tvSeats.setText("Ghế: " + seats);
        holder.tvStatus.setText(t.getStatus());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTicketClick(t);
        });
    }

    @Override
    public int getItemCount() {
        return tickets != null ? tickets.size() : 0;
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvTheater, tvTime, tvSeats, tvStatus;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvTheater = itemView.findViewById(R.id.tvTheater);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
