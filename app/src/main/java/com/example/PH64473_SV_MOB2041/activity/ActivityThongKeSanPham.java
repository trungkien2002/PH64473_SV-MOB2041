package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class ActivityThongKeSanPham extends AppCompatActivity {

    private RecyclerView rcvThongKeSanPham;

    static class ThongKeSP {
        String ten;
        int soLuong;
        ThongKeSP(String ten, int soLuong) { this.ten = ten; this.soLuong = soLuong; }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_san_pham);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thống kê sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        rcvThongKeSanPham = findViewById(R.id.rcv_ThongKeSanPham);
        rcvThongKeSanPham.setLayoutManager(new LinearLayoutManager(this));

        setupDatePicker(findViewById(R.id.edt_TuNgay));
        setupDatePicker(findViewById(R.id.edt_DenNgay));

        setupDemoData();
    }

    private void setupDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            new android.app.DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                editText.setText(date);
            }, calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void setupDemoData() {
        List<ThongKeSP> list = new ArrayList<>();
        list.add(new ThongKeSP("Trà xanh Ito En", 150));
        list.add(new ThongKeSP("Bánh Pocky", 120));
        list.add(new ThongKeSP("Mì Udon", 95));
        list.add(new ThongKeSP("Nước ngọt Coca", 80));
        list.add(new ThongKeSP("Snack Khoai tây", 60));

        rcvThongKeSanPham.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_thong_ke_san_pham, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ThongKeSP item = list.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenSanPham);
                TextView tvSoLuong = holder.itemView.findViewById(R.id.tv_SoLuongDaBan);
                tvTen.setText(item.ten);
                tvSoLuong.setText(String.valueOf(item.soLuong));
            }

            @Override
            public int getItemCount() { return list.size(); }
        });
    }
}