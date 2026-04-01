package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PH64473_SV_MOB2041.DAO.DanhMucDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.adapter.DanhMucAdapter;
import com.example.PH64473_SV_MOB2041.model.DanhMuc;

import java.util.List;

public class ActivityDanhMuc extends AppCompatActivity {

    private RecyclerView rcvDanhMuc;
    private DanhMucDAO danhMucDAO;
    private List<DanhMuc> list;
    private DanhMucAdapter danhMucAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        // Khởi tạo DAO
        danhMucDAO = new DanhMucDAO(this);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Danh mục");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Ánh xạ RecyclerView
        rcvDanhMuc = findViewById(R.id.rcv_DanhMuc);
        rcvDanhMuc.setLayoutManager(new LinearLayoutManager(this));

        // Xử lý Window Insets
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Load dữ liệu lần đầu
        loadData();
    }

    private void loadData() {
        // Lấy danh sách từ database
        list = danhMucDAO.getAll();
        
        // Khởi tạo Adapter và gắn vào RecyclerView
        danhMucAdapter = new DanhMucAdapter(this, list);
        rcvDanhMuc.setAdapter(danhMucAdapter);
    }
    
    // Bạn có thể gọi loadData() từ các Activity khác hoặc sau khi thêm mới
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
