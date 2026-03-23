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

import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class ActivityDanhMuc extends AppCompatActivity {

    private RecyclerView rcvDanhMuc;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

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
            getSupportActionBar().setTitle("Quản lý Danh mục");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rcvDanhMuc = findViewById(R.id.rcv_DanhMuc);
        rcvDanhMuc.setLayoutManager(new LinearLayoutManager(this));

        setupDemoData();
    }

    private void setupDemoData() {
        List<DanhMuc> list = new ArrayList<>();
        list.add(new DanhMuc("DM001", "Nước giải khát", "20/03/2024"));
        list.add(new DanhMuc("DM002", "Bánh kẹo", "21/03/2024"));
        list.add(new DanhMuc("DM003", "Mì ăn liền", "22/03/2024"));
        list.add(new DanhMuc("DM004", "Gia vị", "23/03/2024"));
        list.add(new DanhMuc("DM005", "Đồ gia dụng", "24/03/2024"));

        rcvDanhMuc.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_danh_muc, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                DanhMuc dm = list.get(position);
                TextView tvMa = holder.itemView.findViewById(R.id.tv_MaDanhMuc);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenDanhMuc);
                TextView tvNgay = holder.itemView.findViewById(R.id.tv_NgayTao);

                tvMa.setText(dm.getMaDanhMuc());
                tvTen.setText(dm.getTenDanhMuc());
                tvNgay.setText(dm.getNgayTaoDanhMuc());

                holder.itemView.findViewById(R.id.btn_Edit).setOnClickListener(v -> 
                    Toast.makeText(ActivityDanhMuc.this, "Sửa: " + dm.getTenDanhMuc(), Toast.LENGTH_SHORT).show());
                holder.itemView.findViewById(R.id.btn_Delete).setOnClickListener(v -> 
                    Toast.makeText(ActivityDanhMuc.this, "Xóa: " + dm.getTenDanhMuc(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}