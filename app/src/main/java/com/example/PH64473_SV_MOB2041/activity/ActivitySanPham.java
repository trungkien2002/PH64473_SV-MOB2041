package com.example.PH64473_SV_MOB2041.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PH64473_SV_MOB2041.DAO.SanPhamDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.SanPham;

import java.util.List;

public class ActivitySanPham extends AppCompatActivity {

    private RecyclerView rcvSanPham;
    private Toolbar toolbar;
    private ImageView imgGioHang;
    private SanPhamDAO sanPhamDAO;
    private List<SanPham> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        sanPhamDAO = new SanPhamDAO(this);

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
            getSupportActionBar().setTitle("Quản lý Sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        imgGioHang = findViewById(R.id.img_GioHang);
        imgGioHang.setOnClickListener(v -> {
            Intent intent = new Intent(ActivitySanPham.this, ActivityGioHang.class);
            startActivity(intent);
        });

        rcvSanPham = findViewById(R.id.rcv_SanPham);
        rcvSanPham.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        list = sanPhamDAO.getAll();
        rcvSanPham.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_san_pham, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                SanPham sp = list.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenSanPham);
                TextView tvGia = holder.itemView.findViewById(R.id.tv_GiaSanPham);
                TextView tvTonKho = holder.itemView.findViewById(R.id.tv_NgayTao);

                tvTen.setText(sp.getTenSanPham());
                tvGia.setText(String.format("%,.0f đ", sp.getDonGia()));
                tvTonKho.setText("Tồn kho: " + sp.getSoLuongTonKho());

                holder.itemView.findViewById(R.id.btn_Edit).setOnClickListener(v -> 
                    Toast.makeText(ActivitySanPham.this, "Sửa: " + sp.getTenSanPham(), Toast.LENGTH_SHORT).show());
                holder.itemView.findViewById(R.id.btn_Delete).setOnClickListener(v -> {
                    if (sanPhamDAO.delete(sp.getMaSanPham())) {
                        Toast.makeText(ActivitySanPham.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}
