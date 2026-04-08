package com.example.movieticketapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movieticketapp.model.Ticket;
import com.example.movieticketapp.model.Showtime;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TicketRepo {
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;

    public TicketRepo() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> bookTicket(Ticket ticket, String showtimeId, int numSeats) {
        MutableLiveData<String> status = new MutableLiveData<>();
        
        DocumentReference showtimeRef = db.collection("showtimes").document(showtimeId);
        DocumentReference newTicketRef = db.collection("tickets").document();
        
        ticket.setId(newTicketRef.getId());
        ticket.setBookedAt(Timestamp.now().toDate());

        db.runTransaction(transaction -> {
            Showtime showtime = transaction.get(showtimeRef).toObject(Showtime.class);
            if (showtime == null || showtime.getAvailableSeats() < numSeats) {
                throw new RuntimeException("Ghế không khả dụng");
            }

            int newAvailable = showtime.getAvailableSeats() - numSeats;
            transaction.update(showtimeRef, "availableSeats", newAvailable);
            transaction.set(newTicketRef, ticket);

            return null;
        }).addOnSuccessListener(result -> status.setValue("SUCCESS"))
        .addOnFailureListener(e -> status.setValue("Lỗi giao dịch: " + e.getMessage()));

        return status;
    }

    public MutableLiveData<List<Ticket>> getMyTickets() {
        MutableLiveData<List<Ticket>> liveData = new MutableLiveData<>();
        if (mAuth.getCurrentUser() != null) {
            String uid = mAuth.getCurrentUser().getUid();
            db.collection("tickets")
                    .whereEqualTo("userId", uid)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            android.util.Log.e("TicketRepo", "Lỗi tải vé: " + error.getMessage());
                            liveData.setValue(null);
                            return;
                        }
                        if (value != null) {
                            List<Ticket> tickets = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : value) {
                                Ticket ticket = doc.toObject(Ticket.class);
                                ticket.setId(doc.getId());
                                tickets.add(ticket);
                            }
                            liveData.setValue(tickets);
                        }
                    });
        } else {
            liveData.setValue(null);
        }
        return liveData;
    }

    public MutableLiveData<List<Integer>> getBookedSeats(String showtimeId) {
        MutableLiveData<List<Integer>> liveData = new MutableLiveData<>();
        db.collection("tickets").whereEqualTo("showtimeId", showtimeId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Integer> bookedSeats = new ArrayList<>();
                    for (com.google.firebase.firestore.QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Ticket t = doc.toObject(Ticket.class);
                        if (t.getSeatNumbers() != null) {
                            for (String s : t.getSeatNumbers()) {
                                try {
                                    bookedSeats.add(Integer.parseInt(s));
                                } catch (NumberFormatException ignored) {}
                            }
                        }
                    }
                    liveData.setValue(bookedSeats);
                })
                .addOnFailureListener(e -> liveData.setValue(new ArrayList<>()));
        return liveData;
    }

    public MutableLiveData<Ticket> getTicketById(String ticketId) {
        MutableLiveData<Ticket> liveData = new MutableLiveData<>();
        db.collection("tickets").document(ticketId)
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null && value.exists()) {
                        Ticket t = value.toObject(Ticket.class);
                        if (t != null) {
                            t.setId(value.getId());
                            liveData.setValue(t);
                        }
                    }
                });
        return liveData;
    }
}
