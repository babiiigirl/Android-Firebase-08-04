package com.example.movieticketapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieticketapp.model.Showtime;
import com.example.movieticketapp.model.Ticket;
import com.example.movieticketapp.repository.TicketRepo;

import java.util.List;

public class TicketViewModel extends ViewModel {
    private TicketRepo ticketRepo;

    public TicketViewModel() {
        ticketRepo = new TicketRepo();
    }

    public LiveData<String> bookTicket(Ticket ticket, String showtimeId, int numSeats) {
        return ticketRepo.bookTicket(ticket, showtimeId, numSeats);
    }

    public LiveData<List<Ticket>> getMyTickets() {
        return ticketRepo.getMyTickets();
    }

    public LiveData<List<Integer>> getBookedSeats(String showtimeId) {
        return ticketRepo.getBookedSeats(showtimeId);
    }

    public LiveData<Ticket> getTicketById(String ticketId) {
        return ticketRepo.getTicketById(ticketId);
    }
}
