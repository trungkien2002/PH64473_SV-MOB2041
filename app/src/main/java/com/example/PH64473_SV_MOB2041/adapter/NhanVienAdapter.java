package com.example.PH64473_SV_MOB2041.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PH64473_SV_MOB2041.DAO.NhanVienDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.NhanVien;

import java.util.List;

/**
 * Adapter quản lý hiển thị danh sách Nhân viên
 */
public class NhanVienAdapter extends BaseAdapter {
    private Context context;
    private List<NhanVien> list;
    private NhanVienDAO dao;

    public NhanVienAdapter(Context context, List<NhanVien> list) {
        this.context = context;
        this.list = list;
        this.dao = new NhanVienDAO(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nhan_vien, parent, false);
            holder = new ViewHolder();
            holder.tvMaNV = convertView.findViewById(R.id.tv_MaNV);
            holder.tvTenNV = convertView.findViewById(R.id.tv_TenNV);
            holder.tvSdtNV = convertView.findViewById(R.id.tv_SdtNV);
            holder.btnEdit = convertView.findViewById(R.id.btn_Edit);
            holder.btnDelete = convertView.findViewById(R.id.btn_Delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NhanVien nv = list.get(position);
        holder.tvMaNV.setText("Mã: " + nv.getMaNhanVien());
        holder.tvTenNV.setText("Tên: " + nv.getTenNhanVien());
        holder.tvSdtNV.setText("Chức vụ: " + nv.getChucVu());

        // Chức năng xóa nhân viên
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên " + nv.getTenNhanVien() + "?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                if (dao.delete(nv.getMaNhanVien())) {
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        });

        // Chức năng sửa nhân viên
        holder.btnEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Mở form sửa nhân viên: " + nv.getTenNhanVien(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvMaNV, tvTenNV, tvSdtNV;
        ImageView btnEdit, btnDelete;
    }
}
