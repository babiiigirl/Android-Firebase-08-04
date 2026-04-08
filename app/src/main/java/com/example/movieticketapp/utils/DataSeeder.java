package com.example.movieticketapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.movieticketapp.model.Movie;
import com.example.movieticketapp.model.Showtime;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DataSeeder {
    public static void seedData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("is_data_seeded_v2", false)) {
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        try {
            // Thêm rạp chiếu
            com.example.movieticketapp.model.Theater theater1 = new com.example.movieticketapp.model.Theater("CGV_Vincom", "CGV Vincom", "123 Bà Triệu", "Hà Nội");
            db.collection("theaters").document(theater1.getId()).set(theater1);

            // Thêm 2 bộ phim
            Movie m1 = new Movie("movie1", "Avenger: Endgame", "Sau những biến cố tàn khốc của Infinity War...", "Hành động, Viễn tưởng", 181, "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX300.jpg", 8.4f, true);
            Movie m2 = new Movie("movie2", "Dune: Part Two", "Paul Atreides đoàn kết với Chani...", "Hành động, Phiêu lưu, Drama", 166, "https://m.media-amazon.com/images/M/MV5BODdjMjM3NGQtZDA5OC00NGE4LWIyZDQtZjYwOGZlMTM5ZTQ1XkEyXkFqcGc@._V1_SX300.jpg", 8.6f, true);

            db.collection("movies").document(m1.getId()).set(m1)
                    .addOnFailureListener(e -> Log.e("DataSeeder", "Lỗi tạo phim 1: " + e.getMessage()));
            db.collection("movies").document(m2.getId()).set(m2);

            // Thêm lịch chiếu vào ngày mai và ngày mốt để có thể test đẩy thông báo luôn
            Date t1 = new Date(System.currentTimeMillis() + 86400000L); // +1 ngày
            Date t2 = new Date(System.currentTimeMillis() + 86400000L * 2); // +2 ngày
            Showtime st1 = new Showtime("st1", "movie1", "CGV_Vincom", t1, 50, 50, 120000);
            Showtime st2 = new Showtime("st2", "movie2", "CGV_Vincom", t2, 50, 50, 135000);

            db.collection("showtimes").document(st1.getId()).set(st1);
            db.collection("showtimes").document(st2.getId()).set(st2)
                    .addOnSuccessListener(aVoid -> {
                        prefs.edit().putBoolean("is_data_seeded_v2", true).apply();
                        Toast.makeText(context, "Sinh dữ liệu mẫu thành công!", Toast.LENGTH_LONG).show();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
