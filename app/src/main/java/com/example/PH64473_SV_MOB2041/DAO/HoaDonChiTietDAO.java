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

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public List<HoaDonChiTiet> getAllByMaHD(String maHD) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        // Query join với bảng SanPham để lấy tên sản phẩm hiển thị
        String sql = "SELECT ct.maCTHD, ct.maHD, ct.maSP, ct.soLuong, ct.donGia, sp.tenSP " +
                     "FROM ChiTietHoaDon ct " +
                     "JOIN SanPham sp ON ct.maSP = sp.maSP " +
                     "WHERE ct.maHD = ?";
                     
        Cursor cursor = db.rawQuery(sql, new String[]{maHD});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                HoaDonChiTiet ct = new HoaDonChiTiet();
                ct.setMaCTHD(cursor.getInt(0));
                ct.setMaHD(cursor.getString(1));
                ct.setMaSP(cursor.getString(2));
                ct.setSoLuong(cursor.getInt(3));
                ct.setDonGia(cursor.getDouble(4));
                ct.setTenSP(cursor.getString(5));
                list.add(ct);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(HoaDonChiTiet ct) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHD", ct.getMaHD());
        values.put("maSP", ct.getMaSP());
        values.put("soLuong", ct.getSoLuong());
        values.put("donGia", ct.getDonGia());
        long check = db.insert("ChiTietHoaDon", null, values);
        return check != -1;
    }

    public boolean update(HoaDonChiTiet ct) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soLuong", ct.getSoLuong());
        values.put("donGia", ct.getDonGia());
        long check = db.update("ChiTietHoaDon", values, "maCTHD=?", new String[]{String.valueOf(ct.getMaCTHD())});
        return check != -1;
    }

    public boolean delete(int maCTHD) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("ChiTietHoaDon", "maCTHD=?", new String[]{String.valueOf(maCTHD)});
        return check != -1;
    }
    
    // Xóa tất cả chi tiết của một hóa đơn (khi xóa hóa đơn)
    public boolean deleteAllByMaHD(String maHD) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("ChiTietHoaDon", "maHD=?", new String[]{maHD});
        return check != -1;
    }
}
