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

import com.example.PH64473_SV_MOB2041.DAO.KhachHangDAO;
import com.example.PH64473_SV_MOB2041.R;
import com.example.PH64473_SV_MOB2041.model.KhachHang;

import java.util.List;

/**
 * Adapter hiển thị danh sách Khách hàng
 * Kế thừa BaseAdapter để quản lý hiển thị trên ListView
 */
public class KhachHangAdapter extends BaseAdapter {
    private Context context;
    private List<KhachHang> list;
    private KhachHangDAO dao;

    public KhachHangAdapter(Context context, List<KhachHang> list) {
        this.context = context;
        this.list = list;
        this.dao = new KhachHangDAO(context);
    }

    // Trả về số lượng phần tử trong danh sách
    @Override
    public int getCount() {
        return list.size();
    }

    // Trả về đối tượng tại vị trí position
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    // Trả về ID của phần tử (thường dùng chính vị trí position)
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Xử lý hiển thị từng dòng (item) trên giao diện
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // Nếu view chưa tồn tại, thực hiện inflate từ layout XML item_khach_hang
            convertView = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
            holder = new ViewHolder();
            // Ánh xạ các view từ layout
            holder.tvMaKH = convertView.findViewById(R.id.tv_MaKH);
            holder.tvTenKH = convertView.findViewById(R.id.tv_TenKH);
            holder.tvSdtKH = convertView.findViewById(R.id.tv_SdtKH);
            holder.btnEdit = convertView.findViewById(R.id.btn_Edit);
            holder.btnDelete = convertView.findViewById(R.id.btn_Delete);
            convertView.setTag(holder);
        } else {
            // Tái sử dụng ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu khách hàng tại vị trí position
        KhachHang kh = list.get(position);
        holder.tvMaKH.setText("Mã: " + kh.getMaKhachHang());
        holder.tvTenKH.setText("Tên: " + kh.getTenKhachHang());
        holder.tvSdtKH.setText("SĐT: " + kh.getSoDienThoai());

        // Xử lý sự kiện Xóa khách hàng
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa khách hàng " + kh.getTenKhachHang() + "?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                // Gọi DAO thực hiện xóa trong database
                if (dao.delete(kh.getMaKhachHang())) {
                    // Xóa thành công thì xóa khỏi list dữ liệu và cập nhật giao diện
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa khách hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        });

        // Xử lý sự kiện Sửa khách hàng (hiện tại mới chỉ hiện Toast)
        holder.btnEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Mở form sửa khách hàng: " + kh.getTenKhachHang(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    // Lớp Holder để tối ưu hóa việc tìm view
    private static class ViewHolder {
        TextView tvMaKH, tvTenKH, tvSdtKH;
        ImageView btnEdit, btnDelete;
    }
}
