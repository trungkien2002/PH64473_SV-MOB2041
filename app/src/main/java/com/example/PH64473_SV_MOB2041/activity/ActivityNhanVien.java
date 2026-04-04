package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.PH64473_SV_MOB2041.DAO.NhanVienDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.NhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityNhanVien extends AppCompatActivity {

    private RecyclerView rcvNhanVien;
    private FloatingActionButton btnAdd;
    private NhanVienDAO nhanVienDAO;
    private List<NhanVien> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        nhanVienDAO = new NhanVienDAO(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Nhân viên");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rcvNhanVien = findViewById(R.id.rcv_DanhMuc); 
        rcvNhanVien.setLayoutManager(new LinearLayoutManager(this));
        
        btnAdd = findViewById(R.id.btn_AddNhanVien);
        btnAdd.setOnClickListener(v -> showDialogAddOrEdit(null));

        loadData();
    }

    private void loadData() {
        list = nhanVienDAO.getAll();
        updateAdapter(list);
    }

    private void updateAdapter(List<NhanVien> newList) {
        rcvNhanVien.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_nhan_vien, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                NhanVien nv = newList.get(position);
                TextView tvMa = holder.itemView.findViewById(R.id.tv_MaNV);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenNV);
                TextView tvSdt = holder.itemView.findViewById(R.id.tv_SdtNV);
                ImageView btnEdit = holder.itemView.findViewById(R.id.btn_Edit);
                ImageView btnDelete = holder.itemView.findViewById(R.id.btn_Delete);

                tvMa.setText("ID: " + nv.getMaNhanVien());
                tvTen.setText(nv.getTenNhanVien());
                tvSdt.setText(nv.getSoDienThoai());

                btnEdit.setOnClickListener(v -> showDialogAddOrEdit(nv));
                btnDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityNhanVien.this);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn muốn xóa nhân viên " + nv.getTenNhanVien() + "?");
                    builder.setPositiveButton("Xóa", (dialog, which) -> {
                        if (nhanVienDAO.delete(nv.getMaNhanVien())) {
                            Toast.makeText(ActivityNhanVien.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
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

    private void showDialogAddOrEdit(NhanVien nvEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chinh_sua_nhan_vien, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        TextView tvTitle = view.findViewById(R.id.tv_TieuDe); // Assuming you might add this or use existing
        AutoCompleteTextView spnGioiTinh = view.findViewById(R.id.spn_GioiTinh);
        EditText edtMa = view.findViewById(R.id.edt_MaNhanVien);
        EditText edtTen = view.findViewById(R.id.edt_TenNhanVien);
        EditText edtMatKhau = view.findViewById(R.id.edt_MatKhau);
        EditText edtSdt = view.findViewById(R.id.edt_SoDienThoaiNhanVien);
        EditText edtDiaChi = view.findViewById(R.id.edt_DiaChiNhanVien);
        EditText edtLuong = view.findViewById(R.id.edt_LuongNhanVien);
        EditText edtNgay = view.findViewById(R.id.edt_NgayVaoLam);
        Button btnSave = view.findViewById(R.id.btn_CapNhatNhanVien);
        Button btnHuy = view.findViewById(R.id.btn_HuyNhanVien);

        String[] gioiTinhs = {"Nam", "Nữ"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gioiTinhs);
        spnGioiTinh.setAdapter(adapterGT);

        if (nvEdit != null) {
            edtMa.setText(nvEdit.getMaNhanVien());
            edtMa.setEnabled(false); // Không cho sửa mã
            edtTen.setText(nvEdit.getTenNhanVien());
            edtMatKhau.setText(nvEdit.getMatKhau());
            edtSdt.setText(nvEdit.getSoDienThoai());
            edtDiaChi.setText(nvEdit.getDiaChi());
            edtLuong.setText(String.valueOf(nvEdit.getLuong()));
            edtNgay.setText(nvEdit.getNgayVaoLam());
            spnGioiTinh.setText(nvEdit.getGioiTinh(), false);
            btnSave.setText("Cập nhật");
        } else {
            btnSave.setText("Thêm mới");
        }

        btnHuy.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String matKhau = edtMatKhau.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();
            String luongStr = edtLuong.getText().toString().trim();
            String ngay = edtNgay.getText().toString().trim();
            String gioiTinh = spnGioiTinh.getText().toString();

            if (ma.isEmpty() || ten.isEmpty() || matKhau.isEmpty() || sdt.isEmpty() || luongStr.isEmpty() || gioiTinh.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double luong;
            try {
                luong = Double.parseDouble(luongStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Lương phải là số", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nvEdit == null) {
                // Thêm mới
                NhanVien nv = new NhanVien(ma, ten, diaChi, "nhanvien", luong, matKhau, gioiTinh, sdt, ngay);
                if (nhanVienDAO.insert(nv)) {
                    Toast.makeText(this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Mã nhân viên đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Cập nhật
                nvEdit.setTenNhanVien(ten);
                nvEdit.setMatKhau(matKhau);
                nvEdit.setSoDienThoai(sdt);
                nvEdit.setDiaChi(diaChi);
                nvEdit.setLuong(luong);
                nvEdit.setNgayVaoLam(ngay);
                nvEdit.setGioiTinh(gioiTinh);
                if (nhanVienDAO.update(nvEdit)) {
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
        searchView.setQueryHint("Tìm kiếm nhân viên...");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<NhanVien> filteredList = nhanVienDAO.search(newText);
                updateAdapter(filteredList);
                return true;
            }
        });
        return true;
    }
}
