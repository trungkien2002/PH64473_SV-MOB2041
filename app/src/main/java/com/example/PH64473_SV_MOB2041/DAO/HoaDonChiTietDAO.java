package com.example.PH64473_SV_MOB2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.PH64473_SV_MOB2041.database.DbHelper;
import com.example.PH64473_SV_MOB2041.model.HoaDonChiTiet;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    private DbHelper dbHelper;

    public HoaDonChiTietDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Lấy tất cả chi tiết hóa đơn trong hệ thống
    public List<HoaDonChiTiet> getAll() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HoaDonChiTiet", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new HoaDonChiTiet(
                        cursor.getString(0), // MaChiTietHoaDon
                        cursor.getString(1), // MaHoaDon
                        cursor.getString(2), // MaSanPham
                        cursor.getInt(3),    // SoLuong
                        cursor.getDouble(4)  // DonGia
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Lấy danh sách chi tiết của một hóa đơn cụ thể
    public List<HoaDonChiTiet> getAllByMaHD(String maHD) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HoaDonChiTiet WHERE MaHoaDon=?", new String[]{maHD});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new HoaDonChiTiet(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Phương thức thêm hóa đơn chi tiết
    public boolean insert(HoaDonChiTiet ct) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaChiTietHoaDon", ct.getMaChiTietHoaDon());
        values.put("MaHoaDon", ct.getMaHoaDon());
        values.put("MaSanPham", ct.getMaSanPham());
        values.put("SoLuong", ct.getSoLuong());
        values.put("DonGia", ct.getDonGia());
        long check = db.insert("HoaDonChiTiet", null, values);
        return check != -1;
    }
}
