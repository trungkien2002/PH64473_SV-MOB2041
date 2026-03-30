package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Locale;

public class ActivityThongKeKhachHang extends AppCompatActivity {

    private RecyclerView rcvThongKeKhachHang;
    private KhachHangDAO khachHangDAO;
    private Button btnThongKe;
    private EditText edtTuNgay, edtDenNgay;

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
            getSupportActionBar().setTitle("Top khách hàng tiêu biểu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        edtTuNgay = findViewById(R.id.edt_TuNgay);
        edtDenNgay = findViewById(R.id.edt_DenNgay);
        btnThongKe = findViewById(R.id.btn_ThongKeKhachHang);
        rcvThongKeKhachHang = findViewById(R.id.rcv_ThongKeKhachHang);
        rcvThongKeKhachHang.setLayoutManager(new LinearLayoutManager(this));

        setupDatePicker(edtTuNgay);
        setupDatePicker(edtDenNgay);

        // Chỉ load dữ liệu khi người dùng nhấn nút THỐNG KÊ
        btnThongKe.setOnClickListener(v -> {
            loadData();
            Toast.makeText(this, "Đã cập nhật thống kê khách hàng", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupDatePicker(EditText editText) {
        if (editText == null) return;
        editText.setOnClickListener(v -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            new android.app.DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, (month + 1), dayOfMonth);
                editText.setText(date);
            }, calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void loadData() {
        List<KhachHang> list = khachHangDAO.getTopKhachHang();

        if (list.isEmpty()) {
            Toast.makeText(this, "Chưa có dữ liệu mua hàng", Toast.LENGTH_SHORT).show();
        }

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
                
                double doanhThu = 0;
                try {
                    doanhThu = Double.parseDouble(item.getEmail());
                } catch (Exception e) {}
                
                ((TextView) holder.itemView.findViewById(R.id.tv_DoanhThu)).setText(String.format(Locale.getDefault(), "%,.0f VND", doanhThu));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }
}
