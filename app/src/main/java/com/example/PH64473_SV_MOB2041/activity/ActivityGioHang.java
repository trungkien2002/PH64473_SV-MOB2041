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
import com.example.PH64473_SV_MOB2041.model.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityGioHang extends AppCompatActivity {

    private RecyclerView rvGioHang;
    private TextView tvTongTien;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Giỏ hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rvGioHang = findViewById(R.id.rv_GioHang);
        tvTongTien = findViewById(R.id.tv_TongTien);

        rvGioHang.setLayoutManager(new LinearLayoutManager(this));

        setupDemoCart();
    }

    private void setupDemoCart() {
        List<SanPham> cartList = new ArrayList<>();
        cartList.add(new SanPham("SP001", "Trà xanh Ito En", 10000, 2));
        cartList.add(new SanPham("SP002", "Bánh Pocky", 25000, 1));
        cartList.add(new SanPham("SP003", "Mì Udon", 5000, 4));
        cartList.add(new SanPham("SP004", "Nước ngọt Coca", 15000, 3));
        cartList.add(new SanPham("SP005", "Snack Khoai tây", 12000, 5));

        double total = 0;
        for (SanPham sp : cartList) {
            total += sp.getGiaBan() * sp.getSoLuong();
        }
        tvTongTien.setText(String.format(Locale.getDefault(), "%,.0f đ", total));

        rvGioHang.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_gio_hang, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                SanPham sp = cartList.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenSanPham);
                TextView tvGia = holder.itemView.findViewById(R.id.tv_GiaSanPham);
                TextView tvSoLuong = holder.itemView.findViewById(R.id.tv_SoLuong);

                tvTen.setText(sp.getTenSP());
                tvGia.setText(String.format(Locale.getDefault(), "%,.0f đ", sp.getGiaBan()));
                tvSoLuong.setText(String.valueOf(sp.getSoLuong()));
            }

            @Override
            public int getItemCount() {
                return cartList.size();
            }
        });
    }
}