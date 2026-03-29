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

import com.example.PH64473_SV_MOB2041.DAO.HoaDonDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.HoaDon;

import java.util.List;
import java.util.Locale;

public class ActivityHoaDon extends AppCompatActivity {

    private RecyclerView rvHoaDon;
    private HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        hoaDonDAO = new HoaDonDAO(this);

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

        loadData();
    }

    private void loadData() {
        List<HoaDon> list = hoaDonDAO.getAll();

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
                ((TextView) holder.itemView.findViewById(R.id.tv_MaHD)).setText(hd.getMaHoaDon());
                ((TextView) holder.itemView.findViewById(R.id.tv_TenNV)).setText(hd.getMaNhanVien());
                ((TextView) holder.itemView.findViewById(R.id.tv_TenKH)).setText(hd.getMaKhachHang());
                ((TextView) holder.itemView.findViewById(R.id.tv_NgayLap)).setText(hd.getNgayLap());
                ((TextView) holder.itemView.findViewById(R.id.tv_TongTien)).setText(String.format(Locale.getDefault(), "%,d đ", hd.getTongTien()));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}
