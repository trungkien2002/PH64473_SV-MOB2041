package com.example.PH64473_SV_MOB2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.PH64473_SV_MOB2041.database.DbHelper;
import com.example.PH64473_SV_MOB2041.model.HoaDon;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private DbHelper dbHelper;

    public HoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HoaDon", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new HoaDon(
                        cursor.getString(0), // MaHoaDon
                        cursor.getString(1), // MaNhanVien
                        cursor.getString(2), // MaKhachHang
                        cursor.getString(3), // NgayLap
                        cursor.getInt(4)     // TongTien
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(HoaDon hd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaHoaDon", hd.getMaHoaDon());
        values.put("MaNhanVien", hd.getMaNhanVien());
        values.put("MaKhachHang", hd.getMaKhachHang());
        values.put("NgayLap", hd.getNgayLap());
        values.put("TongTien", hd.getTongTien());
        long check = db.insert("HoaDon", null, values);
        return check != -1;
    }

    public boolean update(HoaDon hd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaNhanVien", hd.getMaNhanVien());
        values.put("MaKhachHang", hd.getMaKhachHang());
        values.put("NgayLap", hd.getNgayLap());
        values.put("TongTien", hd.getTongTien());
        long check = db.update("HoaDon", values, "MaHoaDon=?", new String[]{hd.getMaHoaDon()});
        return check != -1;
    }

    public boolean delete(String maHD) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("HoaDon", "MaHoaDon=?", new String[]{maHD});
        return check != -1;
    }
}
