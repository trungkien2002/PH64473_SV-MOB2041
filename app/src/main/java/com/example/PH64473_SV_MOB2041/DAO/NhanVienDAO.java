package com.example.PH64473_SV_MOB2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.PH64473_SV_MOB2041.database.DbHelper;
import com.example.PH64473_SV_MOB2041.model.NhanVien;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private DbHelper dbHelper;

    public NhanVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new NhanVien(
                        cursor.getString(0), // maNV
                        cursor.getString(1), // hoTen
                        cursor.getString(2), // matKhau
                        cursor.getString(3), // sdt
                        cursor.getString(4)  // loaiTaiKhoan
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(NhanVien nv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maNV", nv.getMaNV());
        values.put("hoTen", nv.getHoTen());
        values.put("matKhau", nv.getMatKhau());
        values.put("sdt", nv.getSdt());
        values.put("loaiTaiKhoan", nv.getLoaiTaiKhoan());
        long check = db.insert("NhanVien", null, values);
        return check != -1;
    }

    public boolean update(NhanVien nv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", nv.getHoTen());
        values.put("matKhau", nv.getMatKhau());
        values.put("sdt", nv.getSdt());
        values.put("loaiTaiKhoan", nv.getLoaiTaiKhoan());
        long check = db.update("NhanVien", values, "maNV=?", new String[]{nv.getMaNV()});
        return check != -1;
    }

    public boolean delete(String maNV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("NhanVien", "maNV=?", new String[]{maNV});
        return check != -1;
    }

    // Chức năng kiểm tra đăng nhập
    public boolean checkLogin(String user, String pass) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien WHERE maNV=? AND matKhau=?", new String[]{user, pass});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
}
