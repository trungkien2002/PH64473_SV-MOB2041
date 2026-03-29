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

import com.example.PH64473_SV_MOB2041.DAO.NhanVienDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.NhanVien;

import java.util.List;

public class ActivityNhanVien extends AppCompatActivity {

    private RecyclerView rcvNhanVien;
    private NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        nhanVienDAO = new NhanVienDAO(this);

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

        loadData();
    }

    private void loadData() {
        List<NhanVien> list = nhanVienDAO.getAll();

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

                tvTen.setText(nv.getTenNhanVien());
                tvSdt.setText(nv.getMaNhanVien()); // Hoặc nv.getDiaChi() tùy UI
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}
