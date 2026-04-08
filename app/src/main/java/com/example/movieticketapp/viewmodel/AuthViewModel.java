package com.example.movieticketapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieticketapp.model.User;
import com.example.movieticketapp.repository.AuthRepo;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {
    private AuthRepo authRepo;

    public AuthViewModel() {
        authRepo = new AuthRepo();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return authRepo.getCurrentUser();
    }

    public LiveData<Boolean> login(String email, String password) {
        return authRepo.login(email, password);
    }

    public LiveData<String> register(String fullName, String email, String password) {
        return authRepo.register(fullName, email, password);
    }

    public void logout() {
        authRepo.logout();
    }

    public LiveData<User> getUserProfile() {
        return authRepo.getUserProfile();
    }

    public LiveData<Boolean> updateAvatar(String avatarUrl) {
        return authRepo.updateAvatar(avatarUrl);
    }
}
