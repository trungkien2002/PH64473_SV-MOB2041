package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.PH64473_SV_MOB2041.DAO.KhachHangDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.KhachHang;

import java.util.List;

public class ActivityKhachHang extends AppCompatActivity {

    private RecyclerView rcvKhachHang;
    private KhachHangDAO khachHangDAO;
    private List<KhachHang> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        khachHangDAO = new KhachHangDAO(this);

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

        rcvKhachHang = findViewById(R.id.rcv_DanhMuc); // Keeping existing ID from XML
        rcvKhachHang.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        list = khachHangDAO.getAll();
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

                tvTen.setText(kh.getTenKhachHang());
                tvSdt.setText(kh.getSoDienThoai());

                holder.itemView.findViewById(R.id.btn_Delete).setOnClickListener(v -> {
                    if (khachHangDAO.delete(kh.getMaKhachHang())) {
                        Toast.makeText(ActivityKhachHang.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
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
