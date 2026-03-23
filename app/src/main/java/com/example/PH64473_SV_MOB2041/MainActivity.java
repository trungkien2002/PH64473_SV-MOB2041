package com.example.PH64473_SV_MOB2041;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.PH64473_SV_MOB2041.activity.ActivityDangNhap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Chờ 2 giây rồi chuyển sang màn hình đăng nhập
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, ActivityDangNhap.class));
                finish();
            }
        }, 2000);
    }
}