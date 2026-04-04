package com.example.PH64473_SV_MOB2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.PH64473_SV_MOB2041.database.DbHelper;
import com.example.PH64473_SV_MOB2041.model.SanPham;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(
                        cursor.getString(0), // MaSanPham
                        cursor.getString(1), // MaDanhMuc
                        cursor.getString(2), // TenSanPham
                        cursor.getDouble(3), // DonGia
                        cursor.getInt(4),    // SoLuongTonKho
                        cursor.getString(5), // DonViTinh
                        cursor.getString(6)  // NgayNhap
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Hàm thống kê Top Sản Phẩm bán chạy (Lấy từ bảng HoaDonChiTiet)
    public List<SanPham> getTopBanChay() {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Truy vấn join giữa SanPham và HoaDonChiTiet để tính tổng số lượng bán
        String sql = "SELECT sp.MaSanPham, sp.TenSanPham, SUM(ct.SoLuong) as TongBan " +
                     "FROM SanPham sp " +
                     "JOIN HoaDonChiTiet ct ON sp.MaSanPham = ct.MaSanPham " +
                     "GROUP BY sp.MaSanPham " +
                     "ORDER BY TongBan DESC LIMIT 10";
        
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                SanPham sp = new SanPham();
                sp.setMaSanPham(cursor.getString(0));
                sp.setTenSanPham(cursor.getString(1));
                sp.setSoLuongTonKho(cursor.getInt(2)); // Tạm thời dùng thuộc tính này để chứa số lượng đã bán
                list.add(sp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(SanPham sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaSanPham", sp.getMaSanPham());
        values.put("MaDanhMuc", sp.getMaDanhMuc());
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("DonGia", sp.getDonGia());
        values.put("SoLuongTonKho", sp.getSoLuongTonKho());
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        long check = db.insert("SanPham", null, values);
        return check != -1;
    }

    public boolean update(SanPham sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaDanhMuc", sp.getMaDanhMuc());
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("DonGia", sp.getDonGia());
        values.put("SoLuongTonKho", sp.getSoLuongTonKho());
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        long check = db.update("SanPham", values, "MaSanPham=?", new String[]{sp.getMaSanPham()});
        return check != -1;
    }

    public boolean delete(String maSP) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("SanPham", "MaSanPham=?", new String[]{maSP});
        return check != -1;
    }

    public List<SanPham> search(String query) {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM SanPham WHERE TenSanPham LIKE ? OR MaSanPham LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + query + "%", "%" + query + "%"});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
