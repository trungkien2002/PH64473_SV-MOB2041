package com.example.PH64473_SV_MOB2041.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityDanhMuc extends AppCompatActivity {

    private RecyclerView rcvDanhMuc;
    private FloatingActionButton btnAdd;
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

        btnAdd = findViewById(R.id.btn_AddDanhMuc);
        btnAdd.setOnClickListener(v -> showDialogAdd());

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
        list = danhMucDAO.getAll();
        danhMucAdapter = new DanhMucAdapter(this, list);
        rcvDanhMuc.setAdapter(danhMucAdapter);
    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chinh_sua_danh_muc, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtMa = view.findViewById(R.id.edt_MaDanhMuc);
        EditText edtTen = view.findViewById(R.id.edt_TenDanhMuc);
        Button btnSave = view.findViewById(R.id.btn_CapNhatDanhMuc);
        Button btnHuy = view.findViewById(R.id.btn_HuyDanhMuc);

        btnSave.setText("Thêm mới");
        btnHuy.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String ngay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if (ma.isEmpty() || ten.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            DanhMuc dm = new DanhMuc(ma, ten, ngay);
            if (danhMucDAO.insert(dm)) {
                Toast.makeText(this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                loadData();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Mã danh mục đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        android.view.MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm kiếm danh mục...");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list = danhMucDAO.search(newText);
                danhMucAdapter = new DanhMucAdapter(ActivityDanhMuc.this, list);
                rcvDanhMuc.setAdapter(danhMucAdapter);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
