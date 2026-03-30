package com.example.PH64473_SV_MOB2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.PH64473_SV_MOB2041.database.DbHelper;
import com.example.PH64473_SV_MOB2041.model.KhachHang;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private DbHelper dbHelper;

    public KhachHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM KhachHang", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new KhachHang(
                        cursor.getString(0), // MaKhachHang
                        cursor.getString(1), // TenKhachHang
                        cursor.getString(2), // DiaChi
                        cursor.getString(3), // SoDienThoai
                        cursor.getString(4)  // Email
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Hàm thống kê Top Khách hàng chi tiêu nhiều nhất
    public List<KhachHang> getTopKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Join KhachHang với HoaDon để tính tổng tiền (TongTien là cột trong HoaDon)
        String sql = "SELECT kh.MaKhachHang, kh.TenKhachHang, kh.SoDienThoai, SUM(hd.TongTien) as DoanhThu " +
                     "FROM KhachHang kh " +
                     "JOIN HoaDon hd ON kh.MaKhachHang = hd.MaKhachHang " +
                     "GROUP BY kh.MaKhachHang " +
                     "ORDER BY DoanhThu DESC LIMIT 10";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(cursor.getString(0));
                kh.setTenKhachHang(cursor.getString(1));
                kh.setSoDienThoai(cursor.getString(2));
                kh.setEmail(cursor.getString(3)); // Tạm thời dùng cột Email để chứa doanh thu
                list.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(KhachHang kh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaKhachHang", kh.getMaKhachHang());
        values.put("TenKhachHang", kh.getTenKhachHang());
        values.put("DiaChi", kh.getDiaChi());
        values.put("SoDienThoai", kh.getSoDienThoai());
        values.put("Email", kh.getEmail());
        long check = db.insert("KhachHang", null, values);
        return check != -1;
    }

    public boolean update(KhachHang kh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenKhachHang", kh.getTenKhachHang());
        values.put("DiaChi", kh.getDiaChi());
        values.put("SoDienThoai", kh.getSoDienThoai());
        values.put("Email", kh.getEmail());
        long check = db.update("KhachHang", values, "MaKhachHang=?", new String[]{kh.getMaKhachHang()});
        return check != -1;
    }

    public boolean delete(String maKH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("KhachHang", "MaKhachHang=?", new String[]{maKH});
        return check != -1;
    }
}
