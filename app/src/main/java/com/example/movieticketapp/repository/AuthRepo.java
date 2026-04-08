package com.example.movieticketapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.movieticketapp.model.User;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepo {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public AuthRepo() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<FirebaseUser> getCurrentUser() {
        MutableLiveData<FirebaseUser> liveData = new MutableLiveData<>();
        liveData.setValue(mAuth.getCurrentUser());
        return liveData;
    }

    public MutableLiveData<Boolean> login(String email, String password) {
        MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> isSuccess.setValue(true))
                .addOnFailureListener(e -> isSuccess.setValue(false));
        return isSuccess;
    }

    public MutableLiveData<String> register(String fullName, String email, String password) {
        MutableLiveData<String> status = new MutableLiveData<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {
                        String uid = authResult.getUser().getUid();
                        User newUser = new User(uid, fullName, email, "", Timestamp.now());
                        db.collection("users").document(uid).set(newUser)
                                .addOnSuccessListener(unused -> status.setValue("SUCCESS"))
                                .addOnFailureListener(e -> status.setValue("Lỗi Firestore: " + e.getMessage()));
                    }
                })
                .addOnFailureListener(e -> status.setValue("Lỗi Auth: " + e.getMessage()));
        return status;
    }

    public void logout() {
        mAuth.signOut();
    }

    public MutableLiveData<User> getUserProfile() {
        MutableLiveData<User> liveData = new MutableLiveData<>();
        FirebaseUser fUser = mAuth.getCurrentUser();
        if (fUser != null) {
            db.collection("users").document(fUser.getUid())
                    .addSnapshotListener((value, error) -> {
                        if (error == null && value != null && value.exists()) {
                            liveData.setValue(value.toObject(User.class));
                        }
                    });
        }
        return liveData;
    }

    public MutableLiveData<Boolean> updateAvatar(String avatarUrl) {
        MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
        FirebaseUser fUser = mAuth.getCurrentUser();
        if (fUser != null) {
            db.collection("users").document(fUser.getUid())
                    .update("avatarUrl", avatarUrl)
                    .addOnSuccessListener(aVoid -> isSuccess.setValue(true))
                    .addOnFailureListener(e -> isSuccess.setValue(false));
        }
        return isSuccess;
    }
}
