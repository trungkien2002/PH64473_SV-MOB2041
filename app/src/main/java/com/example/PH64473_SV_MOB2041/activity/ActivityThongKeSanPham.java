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

import com.example.PH64473_SV_MOB2041.DAO.SanPhamDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.SanPham;

import java.util.List;

public class ActivityThongKeSanPham extends AppCompatActivity {

    private RecyclerView rcvThongKeSanPham;
    private SanPhamDAO sanPhamDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_san_pham);

        sanPhamDAO = new SanPhamDAO(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thống kê sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        rcvThongKeSanPham = findViewById(R.id.rcv_ThongKeSanPham);
        rcvThongKeSanPham.setLayoutManager(new LinearLayoutManager(this));

        setupDatePicker(findViewById(R.id.edt_TuNgay));
        setupDatePicker(findViewById(R.id.edt_DenNgay));

        loadData();
    }

    private void setupDatePicker(EditText editText) {
        if (editText == null) return;
        editText.setOnClickListener(v -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            new android.app.DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                editText.setText(date);
            }, calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void loadData() {
        List<SanPham> list = sanPhamDAO.getAll();

        rcvThongKeSanPham.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_thong_ke_san_pham, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                SanPham item = list.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenSanPham);
                TextView tvSoLuong = holder.itemView.findViewById(R.id.tv_SoLuongDaBan);
                tvTen.setText(item.getTenSanPham());
                tvSoLuong.setText(String.valueOf(item.getSoLuongTonKho())); // Tạm thời dùng tồn kho
            }

            @Override
            public int getItemCount() { return list.size(); }
        });
    }
}
