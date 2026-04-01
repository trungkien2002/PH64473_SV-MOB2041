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

import com.example.PH64473_SV_MOB2041.DAO.HoaDonDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.HoaDon;

import java.util.List;
import java.util.Locale;

/**
 * Adapter hiển thị danh sách Hóa đơn
 */
public class HoaDonAdapter extends BaseAdapter {
    private Context context;
    private List<HoaDon> list;
    private HoaDonDAO dao;

    public HoaDonAdapter(Context context, List<HoaDon> list) {
        this.context = context;
        this.list = list;
        this.dao = new HoaDonDAO(context);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hoa_don, parent, false);
            holder = new ViewHolder();
            holder.tvMaHD = convertView.findViewById(R.id.tv_MaHD);
            holder.tvTenNV = convertView.findViewById(R.id.tv_TenNV);
            holder.tvTenKH = convertView.findViewById(R.id.tv_TenKH);
            holder.tvNgayLap = convertView.findViewById(R.id.tv_NgayLap);
            holder.tvTongTien = convertView.findViewById(R.id.tv_TongTien);
            holder.btnDelete = convertView.findViewById(R.id.iv_delete_HoaDon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HoaDon hd = list.get(position);
        holder.tvMaHD.setText(hd.getMaHoaDon());
        // Mã nhân viên và mã khách hàng hiển thị tạm thời, có thể dùng DAO để lấy tên tương ứng
        holder.tvTenNV.setText(hd.getMaNhanVien()); 
        holder.tvTenKH.setText(hd.getMaKhachHang()); 
        holder.tvNgayLap.setText(hd.getNgayLap());
        holder.tvTongTien.setText(String.format(Locale.getDefault(), "%,d VND", hd.getTongTien()));

        // Chức năng xóa hóa đơn
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Xóa hóa đơn " + hd.getMaHoaDon() + "?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                if (dao.delete(hd.getMaHoaDon())) {
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa hóa đơn", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvMaHD, tvTenNV, tvTenKH, tvNgayLap, tvTongTien;
        ImageView btnDelete;
    }
}
