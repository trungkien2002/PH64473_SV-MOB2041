package com.example.PH64473_SV_MOB2041.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PH64473_SV_MOB2041.DAO.DanhMucDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.DanhMuc;

import java.util.List;

/**
 * Adapter quản lý hiển thị danh sách Danh mục sử dụng RecyclerView
 */
public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    private Context context;
    private List<DanhMuc> list;
    private DanhMucDAO danhMucDAO;

    public DanhMucAdapter(Context context, List<DanhMuc> list) {
        this.context = context;
        this.list = list;
        this.danhMucDAO = new DanhMucDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item cho từng dòng
        View v = LayoutInflater.from(context).inflate(R.layout.item_danh_muc, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy dữ liệu tại vị trí hiện tại
        DanhMuc dm = list.get(position);
        
        // Hiển thị dữ liệu lên các view
        holder.tvTenDM.setText(dm.getTenDanhMuc());
        holder.tvNgayTao.setText(dm.getNgayTao());
        holder.tvMaDM.setText(dm.getMaDanhMuc());

        // Xử lý sự kiện nút Sửa
        holder.btnEdit.setOnClickListener(v -> {
            showUpdateDialog(dm);
        });

        // Xử lý sự kiện nút Xóa
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteDialog(dm, position);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    /**
     * Hiển thị dialog chỉnh sửa danh mục
     */
    private void showUpdateDialog(DanhMuc dm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_chinh_sua_danh_muc, null);
        builder.setView(view);
        
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtMaDM = view.findViewById(R.id.edt_MaDanhMuc);
        EditText edtTenDM = view.findViewById(R.id.edt_TenDanhMuc);
        Button btnCapNhat = view.findViewById(R.id.btn_CapNhatDanhMuc);
        Button btnHuy = view.findViewById(R.id.btn_HuyDanhMuc);

        // Đổ dữ liệu cũ vào form
        edtMaDM.setText(dm.getMaDanhMuc());
        edtMaDM.setEnabled(false); // Không cho phép sửa mã khóa chính
        edtTenDM.setText(dm.getTenDanhMuc());

        btnHuy.setOnClickListener(v -> dialog.dismiss());

        btnCapNhat.setOnClickListener(v -> {
            String tenDM = edtTenDM.getText().toString().trim();
            if (tenDM.isEmpty()) {
                Toast.makeText(context, "Tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            dm.setTenDanhMuc(tenDM);
            if (danhMucDAO.update(dm)) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Hiển thị dialog xác nhận xóa danh mục
     */
    private void showDeleteDialog(DanhMuc dm, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa danh mục: " + dm.getTenDanhMuc() + "?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            if (danhMucDAO.delete(dm.getMaDanhMuc())) {
                list.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại (Có thể danh mục này đang chứa sản phẩm)", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // Lớp ViewHolder để quản lý các view trong một item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaDM, tvTenDM, tvNgayTao;
        ImageView btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDM = itemView.findViewById(R.id.tv_MaDanhMuc);
            tvTenDM = itemView.findViewById(R.id.tv_TenDanhMuc);
            tvNgayTao = itemView.findViewById(R.id.tv_NgayTao);
            btnEdit = itemView.findViewById(R.id.btn_Edit);
            btnDelete = itemView.findViewById(R.id.btn_Delete);
        }
    }
}
