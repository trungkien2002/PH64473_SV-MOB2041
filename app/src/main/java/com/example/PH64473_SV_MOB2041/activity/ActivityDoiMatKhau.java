package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.example.PH64473_SV_MOB2041.DAO.NhanVienDAO;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.PH64473_SV_MOB2041.R;

public class ActivityDoiMatKhau extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText edtMatKhauCu, edtMatKhauMoi, edtXacNhanMatKhau;
    private TextInputLayout tilMatKhauCu, tilMatKhauMoi, tilXacNhanMatKhau;
    private Button btnCapNhat, btnHuy;
    private NhanVienDAO nhanVienDAO;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doi_mat_khau);
        
        nhanVienDAO = new NhanVienDAO(this);
        username = getIntent().getStringExtra("USERNAME");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Đổi mật khẩu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        edtMatKhauCu = findViewById(R.id.edt_MatKhauCu);
        edtMatKhauMoi = findViewById(R.id.edt_MatKhauMoi);
        edtXacNhanMatKhau = findViewById(R.id.edt_XacNhanMatKhau);
        tilMatKhauCu = findViewById(R.id.til_MatKhauCu);
        tilMatKhauMoi = findViewById(R.id.til_MatKhauMoi);
        tilXacNhanMatKhau = findViewById(R.id.til_XacNhanMatKhau);
        btnCapNhat = findViewById(R.id.btn_CapNhatMatkhau);
        btnHuy = findViewById(R.id.btn_Huy);

        btnHuy.setOnClickListener(v -> finish());

        btnCapNhat.setOnClickListener(v -> {
            String mkCu = edtMatKhauCu.getText().toString().trim();
            String mkMoi = edtMatKhauMoi.getText().toString().trim();
            String mkXacNhan = edtXacNhanMatKhau.getText().toString().trim();

            tilMatKhauCu.setError(null);
            tilMatKhauMoi.setError(null);
            tilXacNhanMatKhau.setError(null);

            if (mkCu.isEmpty()) {
                tilMatKhauCu.setError("Vui lòng nhập mật khẩu cũ");
                return;
            }
            if (mkMoi.isEmpty()) {
                tilMatKhauMoi.setError("Vui lòng nhập mật khẩu mới");
                return;
            }
            if (mkXacNhan.isEmpty()) {
                tilXacNhanMatKhau.setError("Vui lòng xác nhận mật khẩu mới");
                return;
            }

            // Kiểm tra mật khẩu cũ có đúng không
            if (!nhanVienDAO.checkLogin(username, mkCu)) {
                tilMatKhauCu.setError("Mật khẩu cũ không chính xác");
                return;
            }

            if (!mkMoi.equals(mkXacNhan)) {
                tilXacNhanMatKhau.setError("Mật khẩu mới không trùng khớp");
                return;
            }

            // Cập nhật mật khẩu mới vào database
            if (nhanVienDAO.updatePass(username, mkMoi)) {
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}