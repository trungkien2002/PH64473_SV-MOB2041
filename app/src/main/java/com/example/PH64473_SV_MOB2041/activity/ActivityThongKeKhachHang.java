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

import com.example.PH64473_SV_MOB2041.DAO.KhachHangDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.KhachHang;

import java.util.List;
import java.util.Locale;

public class ActivityThongKeKhachHang extends AppCompatActivity {

    private RecyclerView rcvThongKeKhachHang;
    private KhachHangDAO khachHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_khach_hang);

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
            getSupportActionBar().setTitle("Thống kê khách hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rcvThongKeKhachHang = findViewById(R.id.rcv_ThongKeKhachHang);
        rcvThongKeKhachHang.setLayoutManager(new LinearLayoutManager(this));

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
        List<KhachHang> list = khachHangDAO.getAll();

        rcvThongKeKhachHang.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_thong_ke_khach_hang, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                KhachHang item = list.get(position);
                ((TextView) holder.itemView.findViewById(R.id.tv_MaKH)).setText(item.getMaKhachHang());
                ((TextView) holder.itemView.findViewById(R.id.tv_TenKH)).setText(item.getTenKhachHang());
                ((TextView) holder.itemView.findViewById(R.id.tv_SdtKH)).setText(item.getSoDienThoai());
                ((TextView) holder.itemView.findViewById(R.id.tv_DoanhThu)).setText(String.format(Locale.getDefault(), "%,d VND", 0)); // Tạm thời để 0
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}
