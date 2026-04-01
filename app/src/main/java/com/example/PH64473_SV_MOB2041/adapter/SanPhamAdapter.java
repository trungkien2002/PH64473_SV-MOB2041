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

import com.example.PH64473_SV_MOB2041.DAO.SanPhamDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.SanPham;

import java.util.List;
import java.util.Locale;

/**
 * Adapter hiển thị danh sách Sản phẩm
 */
public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> list;
    private SanPhamDAO dao;

    public SanPhamAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
        this.dao = new SanPhamDAO(context);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
            holder = new ViewHolder();
            holder.tvTenSP = convertView.findViewById(R.id.tv_TenSanPham);
            holder.tvGiaSP = convertView.findViewById(R.id.tv_GiaSanPham);
            holder.tvTonKho = convertView.findViewById(R.id.tv_NgayTao); 
            holder.btnEdit = convertView.findViewById(R.id.btn_Edit);
            holder.btnDelete = convertView.findViewById(R.id.btn_Delete);
            holder.btnAddToCart = convertView.findViewById(R.id.btn_ThemGioHang);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SanPham sp = list.get(position);
        holder.tvTenSP.setText(sp.getTenSanPham());
        // Định dạng hiển thị tiền tệ VND
        holder.tvGiaSP.setText(String.format(Locale.getDefault(), "%,.0f VND", sp.getDonGia()));
        holder.tvTonKho.setText(String.valueOf(sp.getSoLuongTonKho()));

        // Sự kiện Xóa sản phẩm
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Xóa sản phẩm " + sp.getTenSanPham() + "?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                if (dao.delete(sp.getMaSanPham())) {
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        });

        // Sự kiện Sửa sản phẩm
        holder.btnEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Sửa: " + sp.getTenSanPham(), Toast.LENGTH_SHORT).show();
        });

        // Sự kiện thêm vào giỏ hàng
        holder.btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(context, "Đã thêm " + sp.getTenSanPham() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTenSP, tvGiaSP, tvTonKho;
        ImageView btnEdit, btnDelete, btnAddToCart;
    }
}
