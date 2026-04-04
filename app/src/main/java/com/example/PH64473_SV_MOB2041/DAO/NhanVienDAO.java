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
                        cursor.getString(0), // MaNhanVien
                        cursor.getString(1), // TenNhanVien
                        cursor.getString(2), // DiaChi
                        cursor.getString(3), // ChucVu
                        cursor.getDouble(4), // Luong
                        cursor.getString(5), // MatKhau
                        cursor.getString(6), // GioiTinh
                        cursor.getString(7), // SoDienThoai
                        cursor.getString(8)  // NgayVaoLam
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(NhanVien nv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaNhanVien", nv.getMaNhanVien());
        values.put("TenNhanVien", nv.getTenNhanVien());
        values.put("DiaChi", nv.getDiaChi());
        values.put("ChucVu", nv.getChucVu());
        values.put("Luong", nv.getLuong());
        values.put("MatKhau", nv.getMatKhau());
        values.put("GioiTinh", nv.getGioiTinh());
        values.put("SoDienThoai", nv.getSoDienThoai());
        values.put("NgayVaoLam", nv.getNgayVaoLam());
        long check = db.insert("NhanVien", null, values);
        return check != -1;
    }

    public boolean update(NhanVien nv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenNhanVien", nv.getTenNhanVien());
        values.put("DiaChi", nv.getDiaChi());
        values.put("ChucVu", nv.getChucVu());
        values.put("Luong", nv.getLuong());
        values.put("MatKhau", nv.getMatKhau());
        values.put("GioiTinh", nv.getGioiTinh());
        values.put("SoDienThoai", nv.getSoDienThoai());
        values.put("NgayVaoLam", nv.getNgayVaoLam());
        long check = db.update("NhanVien", values, "MaNhanVien=?", new String[]{nv.getMaNhanVien()});
        return check != -1;
    }

    public boolean delete(String maNV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("NhanVien", "MaNhanVien=?", new String[]{maNV});
        return check != -1;
    }

    public boolean checkLogin(String user, String pass) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien WHERE MaNhanVien=? AND MatKhau=?", new String[]{user, pass});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Cập nhật mật khẩu cho tài khoản hiện tại
    public boolean updatePass(String user, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MatKhau", newPass);
        long check = db.update("NhanVien", values, "MaNhanVien=?", new String[]{user});
        return check != -1;
    }

    // Tìm kiếm nhân viên theo tên hoặc mã
    public List<NhanVien> search(String query) {
        List<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM NhanVien WHERE TenNhanVien LIKE ? OR MaNhanVien LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + query + "%", "%" + query + "%"});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new NhanVien(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
