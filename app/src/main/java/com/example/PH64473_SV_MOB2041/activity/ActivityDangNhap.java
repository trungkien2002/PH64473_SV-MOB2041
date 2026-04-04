package com.example.PH64473_SV_MOB2041.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
    private CheckBox cbGhiNho;
    private ImageView ivHienThiMatKhau;
    private NhanVienDAO nhanVienDAO;
    private SharedPreferences sharedPreferences;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        
        nhanVienDAO = new NhanVienDAO(this);
        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);

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
        cbGhiNho = findViewById(R.id.cb_GhiNhoMatKhau);
        ivHienThiMatKhau = findViewById(R.id.iv_HienThiMatKhau);

        // Đọc dữ liệu ghi nhớ mật khẩu
        edtTenTaiKhoan.setText(sharedPreferences.getString("USERNAME", ""));
        edtMatKhau.setText(sharedPreferences.getString("PASSWORD", ""));
        cbGhiNho.setChecked(sharedPreferences.getBoolean("REMEMBER", false));

        // Tính năng ẩn hiện mật khẩu
        ivHienThiMatKhau.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                edtMatKhau.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivHienThiMatKhau.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // Icon ẩn
            } else {
                edtMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ivHienThiMatKhau.setImageResource(android.R.drawable.ic_menu_view); // Icon hiện
            }
            edtMatKhau.setSelection(edtMatKhau.getText().length());
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtTenTaiKhoan.getText().toString().trim();
                String pass = edtMatKhau.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(ActivityDangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra đăng nhập
                if (nhanVienDAO.checkLogin(user, pass)) {
                    // Lưu thông tin ghi nhớ mật khẩu
                    rememberUser(user, pass, cbGhiNho.isChecked());

                    // Lấy role
                    String role = "nhanvien";
                    List<NhanVien> list = nhanVienDAO.getAll();
                    for (NhanVien nv : list) {
                        if (nv.getMaNhanVien().equals(user)) {
                            role = nv.getChucVu().equals("Quản lý") ? "admin" : "nhanvien";
                            break;
                        }
                    }

                    Intent intent = new Intent(ActivityDangNhap.this, ActivityManHinhChinh.class);
                    intent.putExtra("ROLE", role);
                    intent.putExtra("USERNAME", user); // Truyền username để dùng cho đổi mật khẩu
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivityDangNhap.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm lưu thông tin đăng nhập vào SharedPreferences
    private void rememberUser(String user, String pass, boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();
    }
}
