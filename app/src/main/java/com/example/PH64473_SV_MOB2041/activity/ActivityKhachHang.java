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
import com.example.PH64473_SV_MOB2041.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class ActivityKhachHang extends AppCompatActivity {

    private RecyclerView rcvKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Khách hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rcvKhachHang = findViewById(R.id.rcv_DanhMuc); // ID defined in XML
        rcvKhachHang.setLayoutManager(new LinearLayoutManager(this));

        setupDemoData();
    }

    private void setupDemoData() {
        List<KhachHang> list = new ArrayList<>();
        list.add(new KhachHang("KH001", "Nguyễn Văn An", "0912345678"));
        list.add(new KhachHang("KH002", "Trần Thị Bình", "0987654321"));
        list.add(new KhachHang("KH003", "Lê Văn Cường", "0905123456"));
        list.add(new KhachHang("KH004", "Phạm Minh Đức", "0934567890"));
        list.add(new KhachHang("KH005", "Hoàng Kim Yến", "0978123456"));

        rcvKhachHang.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_khach_hang, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                KhachHang kh = list.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenKH);
                TextView tvSdt = holder.itemView.findViewById(R.id.tv_SdtKH);

                tvTen.setText(kh.getTenKH());
                tvSdt.setText(kh.getSdt());
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}