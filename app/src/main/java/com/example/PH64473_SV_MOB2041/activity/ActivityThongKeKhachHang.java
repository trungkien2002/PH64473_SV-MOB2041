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
import java.util.Locale;

public class ActivityThongKeKhachHang extends AppCompatActivity {

    private RecyclerView rcvThongKeKhachHang;

    static class ThongKeKH {
        String ma;
        String ten;
        String sdt;
        double doanhThu;

        ThongKeKH(String ma, String ten, String sdt, double doanhThu) {
            this.ma = ma;
            this.ten = ten;
            this.sdt = sdt;
            this.doanhThu = doanhThu;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_khach_hang);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        List<ThongKeKH> list = new ArrayList<>();
        list.add(new ThongKeKH("KH001", "Nguyễn Văn An", "0912345678", 50000000));
        list.add(new ThongKeKH("KH002", "Trần Thị Bình", "0987654321", 35000000));
        list.add(new ThongKeKH("KH003", "Lê Văn Cường", "0905123456", 20000000));
        list.add(new ThongKeKH("KH004", "Phạm Minh Đức", "0934567890", 15000000));
        list.add(new ThongKeKH("KH005", "Hoàng Kim Yến", "0978123456", 10000000));

        rcvThongKeKhachHang.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_thong_ke_khach_hang, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ThongKeKH item = list.get(position);
                ((TextView) holder.itemView.findViewById(R.id.tv_MaKH)).setText(item.ma);
                ((TextView) holder.itemView.findViewById(R.id.tv_TenKH)).setText(item.ten);
                ((TextView) holder.itemView.findViewById(R.id.tv_SdtKH)).setText(item.sdt);
                ((TextView) holder.itemView.findViewById(R.id.tv_DoanhThu)).setText(String.format(Locale.getDefault(), "%,.0f VND", item.doanhThu));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}