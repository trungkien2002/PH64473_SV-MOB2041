package com.example.PH64473_SV_MOB2041.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import com.example.PH64473_SV_MOB2041.DAO.DanhMucDAO;
import com.example.PH64473_SV_MOB2041.DAO.SanPhamDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.DanhMuc;
import com.example.PH64473_SV_MOB2041.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivitySanPham extends AppCompatActivity {

    private RecyclerView rcvSanPham;
    private FloatingActionButton btnAdd;
    private ImageView imgGioHang;
    private SanPhamDAO sanPhamDAO;
    private DanhMucDAO danhMucDAO;
    private List<SanPham> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        sanPhamDAO = new SanPhamDAO(this);
        danhMucDAO = new DanhMucDAO(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        imgGioHang = findViewById(R.id.img_GioHang);
        imgGioHang.setOnClickListener(v -> {
            Intent intent = new Intent(ActivitySanPham.this, ActivityGioHang.class);
            startActivity(intent);
        });

        rcvSanPham = findViewById(R.id.rcv_SanPham);
        rcvSanPham.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = findViewById(R.id.btn_AddSanPham);
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
        list = sanPhamDAO.getAll();
        updateAdapter(list);
    }

    private void updateAdapter(List<SanPham> newList) {
        rcvSanPham.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_san_pham, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                SanPham sp = newList.get(position);
                TextView tvTen = holder.itemView.findViewById(R.id.tv_TenSanPham);
                TextView tvGia = holder.itemView.findViewById(R.id.tv_GiaSanPham);
                TextView tvTonKho = holder.itemView.findViewById(R.id.tv_NgayTao);
                ImageView btnEdit = holder.itemView.findViewById(R.id.btn_Edit);
                ImageView btnDelete = holder.itemView.findViewById(R.id.btn_Delete);

                tvTen.setText(sp.getTenSanPham());
                tvGia.setText(String.format("%,.0f đ", sp.getDonGia()));
                tvTonKho.setText("Tồn kho: " + sp.getSoLuongTonKho());

                btnEdit.setOnClickListener(v -> showDialogAddOrEdit(sp));
                btnDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySanPham.this);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn muốn xóa sản phẩm " + sp.getTenSanPham() + "?");
                    builder.setPositiveButton("Xóa", (dialog, which) -> {
                        if (sanPhamDAO.delete(sp.getMaSanPham())) {
                            Toast.makeText(ActivitySanPham.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
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

    private void showDialogAddOrEdit(SanPham spEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chinh_sua_san_pham, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        AutoCompleteTextView spnDanhMuc = view.findViewById(R.id.spn_DanhMuc);
        EditText edtMa = view.findViewById(R.id.edt_MaSanPham);
        EditText edtTen = view.findViewById(R.id.edt_TenSanPham);
        EditText edtGia = view.findViewById(R.id.edt_GiaSanPham);
        EditText edtSoLuong = view.findViewById(R.id.edt_SoLuongSanPham);
        EditText edtDonVi = view.findViewById(R.id.edt_DonViTinh);
        EditText edtNgay = view.findViewById(R.id.edt_NgayNhap);
        Button btnSave = view.findViewById(R.id.btn_CapNhatSanPham);
        Button btnHuy = view.findViewById(R.id.btn_HuySanPham);

        // Load danh mục cho Spinner
        List<DanhMuc> listDM = danhMucDAO.getAll();
        List<String> listTenDM = new ArrayList<>();
        for (DanhMuc dm : listDM) listTenDM.add(dm.getTenDanhMuc());
        ArrayAdapter<String> adapterDM = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listTenDM);
        spnDanhMuc.setAdapter(adapterDM);

        if (spEdit != null) {
            edtMa.setText(spEdit.getMaSanPham());
            edtMa.setEnabled(false);
            edtTen.setText(spEdit.getTenSanPham());
            edtGia.setText(String.valueOf(spEdit.getDonGia()));
            edtSoLuong.setText(String.valueOf(spEdit.getSoLuongTonKho()));
            edtDonVi.setText(spEdit.getDonViTinh());
            edtNgay.setText(spEdit.getNgayNhap());
            for (int i = 0; i < listDM.size(); i++) {
                if (listDM.get(i).getMaDanhMuc().equals(spEdit.getMaDanhMuc())) {
                    spnDanhMuc.setText(listDM.get(i).getTenDanhMuc(), false);
                    break;
                }
            }
            btnSave.setText("Cập nhật");
        } else {
            btnSave.setText("Thêm mới");
        }

        btnHuy.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();
            String donVi = edtDonVi.getText().toString().trim();
            String ngay = edtNgay.getText().toString().trim();
            
            String selectedTenDM = spnDanhMuc.getText().toString();
            int selectedIndex = listTenDM.indexOf(selectedTenDM);
            if (selectedIndex == -1) {
                Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                return;
            }
            String maDM = listDM.get(selectedIndex).getMaDanhMuc();

            if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty() || soLuongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double gia;
            int soLuong;
            try {
                gia = Double.parseDouble(giaStr);
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá và số lượng phải là số", Toast.LENGTH_SHORT).show();
                return;
            }

            if (spEdit == null) {
                SanPham sp = new SanPham(ma, maDM, ten, gia, soLuong, donVi, ngay);
                if (sanPhamDAO.insert(sp)) {
                    Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            } else {
                spEdit.setMaDanhMuc(maDM);
                spEdit.setTenSanPham(ten);
                spEdit.setDonGia(gia);
                spEdit.setSoLuongTonKho(soLuong);
                spEdit.setDonViTinh(donVi);
                spEdit.setNgayNhap(ngay);
                if (sanPhamDAO.update(spEdit)) {
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
        searchView.setQueryHint("Tìm kiếm sản phẩm...");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<SanPham> filteredList = sanPhamDAO.search(newText);
                updateAdapter(filteredList);
                return true;
            }
        });
        return true;
    }
}
