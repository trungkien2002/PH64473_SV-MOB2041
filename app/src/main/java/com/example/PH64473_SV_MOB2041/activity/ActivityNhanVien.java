package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PH64473_SV_MOB2041.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityNhanVien extends AppCompatActivity {

    private RecyclerView rcvNhanVien;

    // Inner class for Demo Data if model is not yet created, 
    // but based on R.id.tv_TenNV, it's expected.
    static class NhanVien {
        String ten;
        String sdt;
        NhanVien(String ten, String sdt) { this.ten = ten; this.sdt = sdt; }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Nhân viên");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rcvNhanVien = findViewById(R.id.rcv_DanhMuc); // Using existing ID from XML
        rcvNhanVien.setLayoutManager(new LinearLayoutManager(this));

        setupDemoData();
    }

    private void setupDemoData() {
        List<NhanVien> list = new ArrayList<>();
        list.add(new NhanVien("Nguyễn Văn Quản Lý", "0901234567"));
        list.add(new NhanVien("Lê Thị Thu Ngân", "0911223344"));
        list.add(new NhanVien("Trần Văn Kho", "0922334455"));
        list.add(new NhanVien("Phạm Thị Bán Hàng", "0933445566"));
        list.add(new NhanVien("Vũ Văn Giao Hàng", "0944556677"));

        rcvNhanVien.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_nhan_vien, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                NhanVien nv = list.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenNV);
                TextView tvSdt = holder.itemView.findViewById(R.id.tv_SdtNV);

                tvTen.setText(nv.ten);
                tvSdt.setText(nv.sdt);
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}