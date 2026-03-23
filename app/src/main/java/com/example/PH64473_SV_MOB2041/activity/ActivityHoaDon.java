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
import com.example.PH64473_SV_MOB2041.model.HoaDon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityHoaDon extends AppCompatActivity {

    private RecyclerView rvHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

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
            getSupportActionBar().setTitle("Quản lý Hóa đơn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rvHoaDon = findViewById(R.id.rv_HoaDon);
        rvHoaDon.setLayoutManager(new LinearLayoutManager(this));

        setupDemoData();
    }

    private void setupDemoData() {
        List<HoaDon> list = new ArrayList<>();
        list.add(new HoaDon("HD001", "Admin", "Nguyễn Văn An", "2024-05-01", 150000));
        list.add(new HoaDon("HD002", "Nhân viên A", "Trần Thị Bình", "2024-05-02", 250000));
        list.add(new HoaDon("HD003", "Admin", "Lê Văn Cường", "2024-05-03", 50000));
        list.add(new HoaDon("HD004", "Nhân viên B", "Phạm Minh Đức", "2024-05-04", 450000));
        list.add(new HoaDon("HD005", "Admin", "Hoàng Kim Yến", "2024-05-05", 120000));

        rvHoaDon.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_hoa_don, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                HoaDon hd = list.get(position);
                ((TextView) holder.itemView.findViewById(R.id.tv_MaHD)).setText(hd.getMaHD());
                ((TextView) holder.itemView.findViewById(R.id.tv_TenNV)).setText(hd.getTenNV());
                ((TextView) holder.itemView.findViewById(R.id.tv_TenKH)).setText(hd.getTenKH());
                ((TextView) holder.itemView.findViewById(R.id.tv_NgayLap)).setText(hd.getNgayLap());
                ((TextView) holder.itemView.findViewById(R.id.tv_TongTien)).setText(String.format(Locale.getDefault(), "%,.0f đ", hd.getTongTien()));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}