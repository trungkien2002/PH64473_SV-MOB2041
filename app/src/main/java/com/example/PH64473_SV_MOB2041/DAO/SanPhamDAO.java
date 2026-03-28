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
}
