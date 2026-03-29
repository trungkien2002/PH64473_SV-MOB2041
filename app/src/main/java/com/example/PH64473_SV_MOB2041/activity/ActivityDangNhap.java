package com.example.PH64473_SV_MOB2041.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.PH64473_SV_MOB2041.DAO.NhanVienDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.NhanVien;

import java.util.List;

public class ActivityDangNhap extends AppCompatActivity {

    private EditText edtTenTaiKhoan, edtMatKhau;
    private Button btnDangNhap;
    private NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        
        nhanVienDAO = new NhanVienDAO(this);

        View mainView = findViewById(R.id.main_login);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        edtTenTaiKhoan = findViewById(R.id.edt_TenTaiKhoan);
        edtMatKhau = findViewById(R.id.edt_MatKhau);
        btnDangNhap = findViewById(R.id.btn_DangNhap);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtTenTaiKhoan.getText().toString().trim();
                String pass = edtMatKhau.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(ActivityDangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra đăng nhập từ Database thực tế
                if (nhanVienDAO.checkLogin(user, pass)) {
                    // Lấy thông tin nhân viên để biết chức vụ (Role)
                    String role = "nhanvien"; // Mặc định
                    List<NhanVien> list = nhanVienDAO.getAll();
                    for (NhanVien nv : list) {
                        if (nv.getMaNhanVien().equals(user)) {
                            role = nv.getChucVu().equals("Quản lý") ? "admin" : "nhanvien";
                            break;
                        }
                    }

                    Intent intent = new Intent(ActivityDangNhap.this, ActivityManHinhChinh.class);
                    intent.putExtra("ROLE", role);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivityDangNhap.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
