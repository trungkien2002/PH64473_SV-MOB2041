package com.example.PH64473_SV_MOB2041.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.PH64473_SV_MOB2041.R;
import com.google.android.material.button.MaterialButton;

public class ActivityManHinhChinh extends AppCompatActivity {

    private TextView tvTieuDeThongKe;
    private MaterialButton btnBaoCaoDoanhThu, btnTopSanPham, btnTopKhachHang, btnQuanLyNhanVien;
    private MaterialButton btnQuanLySanPham, btnQuanLyKhachHang, btnQuanLyHoaDon, btnQuanLyDanhMuc, btnDoiMatKhau, btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Trang Chủ");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view
        tvTieuDeThongKe = findViewById(R.id.tv_TieuDeThongKe);
        btnBaoCaoDoanhThu = findViewById(R.id.mtb_BaoCaoDoanhThu);
        btnTopSanPham = findViewById(R.id.mtb_TopSanPham);
        btnTopKhachHang = findViewById(R.id.mtb_TopKhachHang);
        btnQuanLyNhanVien = findViewById(R.id.mtb_QuanlyNhanVien);

        btnQuanLySanPham = findViewById(R.id.mtb_QuanLySanPham);
        btnQuanLyKhachHang = findViewById(R.id.mtb_QuanLyKhachHang);
        btnQuanLyHoaDon = findViewById(R.id.mtb_QuanLyHoaDon);
        btnQuanLyDanhMuc = findViewById(R.id.mtb_QuanLyDanhMuc);
        btnDoiMatKhau = findViewById(R.id.mtb_DoiMatKhau);
        btnDangXuat = findViewById(R.id.mtb_DangXuat);

        // Nhận role từ Intent
        String role = getIntent().getStringExtra("ROLE");

        // Phân quyền hiển thị
        if (role != null && role.equals("nhanvien")) {
            tvTieuDeThongKe.setVisibility(View.GONE);
            btnBaoCaoDoanhThu.setVisibility(View.GONE);
            btnTopSanPham.setVisibility(View.GONE);
            btnTopKhachHang.setVisibility(View.GONE);
            btnQuanLyNhanVien.setVisibility(View.GONE);
        }

        // Thiết lập sự kiện click chuyển màn hình
        btnBaoCaoDoanhThu.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityThongKeDoanhThu.class));
        });

        btnTopSanPham.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityThongKeSanPham.class));
        });

        btnTopKhachHang.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityThongKeKhachHang.class));
        });

        btnQuanLySanPham.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivitySanPham.class));
        });

        btnQuanLyDanhMuc.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityDanhMuc.class));
        });

        btnQuanLyKhachHang.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityKhachHang.class));
        });

        btnQuanLyNhanVien.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityNhanVien.class));
        });

        btnQuanLyHoaDon.setOnClickListener(v -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityHoaDon.class));
        });

        btnDangXuat.setOnClickListener(v -> {
            showLogoutDialog();
        });

        btnDoiMatKhau.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityManHinhChinh.this, ActivityDoiMatKhau.class);
            intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
            startActivity(intent);
        });
    }

    private void showLogoutDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
        builder.setPositiveButton("Đăng xuất", (dialog, which) -> {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityDangNhap.class));
            finish();
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            startActivity(new Intent(ActivityManHinhChinh.this, ActivityDangNhap.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}