package com.example.PH64473_SV_MOB2041.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ActivityKhachHang extends AppCompatActivity {

    private RecyclerView rcvKhachHang;
    private FloatingActionButton btnAdd;
    private KhachHangDAO khachHangDAO;
    private List<KhachHang> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        khachHangDAO = new KhachHangDAO(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Khách hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rcvKhachHang = findViewById(R.id.rcv_DanhMuc); 
        rcvKhachHang.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = findViewById(R.id.btn_AddKhachHang);
        btnAdd.setOnClickListener(v -> showDialogAddOrEdit(null));

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        loadData();
    }

    private void loadData() {
        list = khachHangDAO.getAll();
        updateAdapter(list);
    }

    private void updateAdapter(List<KhachHang> newList) {
        rcvKhachHang.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_khach_hang, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                KhachHang kh = newList.get(position);
                TextView tvMa = holder.itemView.findViewById(R.id.tv_MaKH);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenKH);
                TextView tvSdt = holder.itemView.findViewById(R.id.tv_SdtKH);
                ImageView btnEdit = holder.itemView.findViewById(R.id.btn_Edit);
                ImageView btnDelete = holder.itemView.findViewById(R.id.btn_Delete);

                tvMa.setText("ID: " + kh.getMaKhachHang());
                tvTen.setText(kh.getTenKhachHang());
                tvSdt.setText(kh.getSoDienThoai());

                btnEdit.setOnClickListener(v -> showDialogAddOrEdit(kh));
                btnDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityKhachHang.this);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn muốn xóa khách hàng " + kh.getTenKhachHang() + "?");
                    builder.setPositiveButton("Xóa", (dialog, which) -> {
                        if (khachHangDAO.delete(kh.getMaKhachHang())) {
                            Toast.makeText(ActivityKhachHang.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                });
            }

            @Override
            public int getItemCount() {
                return newList.size();
            }
        });
    }

    private void showDialogAddOrEdit(KhachHang khEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chinh_sua_khach_hang, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtMa = view.findViewById(R.id.edt_MaKhachHang);
        EditText edtTen = view.findViewById(R.id.edt_TenKhachHang);
        EditText edtSdt = view.findViewById(R.id.edt_SoDienThoaiKhachHang);
        Button btnSave = view.findViewById(R.id.btn_CapNhatKhachHang);
        Button btnHuy = view.findViewById(R.id.btn_HuyKhachHang);

        if (khEdit != null) {
            edtMa.setText(khEdit.getMaKhachHang());
            edtMa.setEnabled(false);
            edtTen.setText(khEdit.getTenKhachHang());
            edtSdt.setText(khEdit.getSoDienThoai());
            btnSave.setText("Cập nhật");
        } else {
            btnSave.setText("Thêm mới");
        }

        btnHuy.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();

            if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (khEdit == null) {
                KhachHang kh = new KhachHang(ma, ten, "", sdt, "");
                if (khachHangDAO.insert(kh)) {
                    Toast.makeText(this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Mã khách hàng đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            } else {
                khEdit.setTenKhachHang(ten);
                khEdit.setSoDienThoai(sdt);
                if (khachHangDAO.update(khEdit)) {
                    Toast.makeText(this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        android.view.MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm kiếm khách hàng...");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<KhachHang> filteredList = khachHangDAO.search(newText);
                updateAdapter(filteredList);
                return true;
            }
        });
        return true;
    }
}
