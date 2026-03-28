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
        // Câu truy vấn join để lấy tên nhân viên và khách hàng nếu cần, 
        // nhưng ở đây ta lấy theo model đơn giản trước.
        Cursor cursor = db.rawQuery("SELECT maHD, maNV, maKH, ngayLap, tongTien FROM HoaDon", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new HoaDon(
                        cursor.getString(0), // maHD
                        cursor.getString(1), // tenNV (tạm thời để maNV)
                        cursor.getString(2), // tenKH (tạm thời để maKH)
                        cursor.getString(3), // ngayLap
                        cursor.getDouble(4)  // tongTien
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(HoaDon hd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHD", hd.getMaHD());
        values.put("maNV", hd.getTenNV()); // Lưu mã NV vào cột maNV
        values.put("maKH", hd.getTenKH()); // Lưu mã KH vào cột maKH
        values.put("ngayLap", hd.getNgayLap());
        values.put("tongTien", hd.getTongTien());
        long check = db.insert("HoaDon", null, values);
        return check != -1;
    }

    public boolean update(HoaDon hd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ngayLap", hd.getNgayLap());
        values.put("tongTien", hd.getTongTien());
        long check = db.update("HoaDon", values, "maHD=?", new String[]{hd.getMaHD()});
        return check != -1;
    }

    public boolean delete(String maHD) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("HoaDon", "maHD=?", new String[]{maHD});
        return check != -1;
    }
}
