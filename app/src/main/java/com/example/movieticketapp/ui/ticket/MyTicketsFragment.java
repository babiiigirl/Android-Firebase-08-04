package com.example.movieticketapp.ui.ticket;

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

import com.example.movieticketapp.adapter.TicketAdapter;
import com.example.movieticketapp.databinding.FragmentMyTicketsBinding;
import com.example.movieticketapp.viewmodel.TicketViewModel;

import java.util.ArrayList;

public class MyTicketsFragment extends Fragment {
    private FragmentMyTicketsBinding binding;
    private TicketViewModel ticketViewModel;
    private TicketAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyTicketsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        adapter = new TicketAdapter(new ArrayList<>(), ticket -> {
            Intent intent = new Intent(requireContext(), TicketDetailActivity.class);
            intent.putExtra("ticketId", ticket.getId());
            startActivity(intent);
        });

        binding.rvTickets.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTickets.setAdapter(adapter);

        binding.progressBar.setVisibility(View.VISIBLE);
        ticketViewModel.getMyTickets().observe(getViewLifecycleOwner(), tickets -> {
            binding.progressBar.setVisibility(View.GONE);
            if (tickets == null) {
                binding.tvEmpty.setText("Có lỗi khi tải danh sách vé (Check quyền Rules)!");
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.rvTickets.setVisibility(View.GONE);
                android.widget.Toast.makeText(getContext(), "Lỗi tải vé từ Firebase", android.widget.Toast.LENGTH_LONG).show();
            } else if (!tickets.isEmpty()) {
                adapter.setTickets(tickets);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.rvTickets.setVisibility(View.VISIBLE);
            } else {
                binding.tvEmpty.setText("Bạn chưa đặt vé nào");
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.rvTickets.setVisibility(View.GONE);
            }
        });
    }
}
