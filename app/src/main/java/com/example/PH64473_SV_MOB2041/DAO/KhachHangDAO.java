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
                        cursor.getString(0), // maKH
                        cursor.getString(1), // tenKH
                        cursor.getString(2)  // sdt
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(KhachHang kh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKH", kh.getMaKH());
        values.put("tenKH", kh.getTenKH());
        values.put("sdt", kh.getSdt());
        long check = db.insert("KhachHang", null, values);
        return check != -1;
    }

    public boolean update(KhachHang kh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKH", kh.getTenKH());
        values.put("sdt", kh.getSdt());
        long check = db.update("KhachHang", values, "maKH=?", new String[]{kh.getMaKH()});
        return check != -1;
    }

    public boolean delete(String maKH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("KhachHang", "maKH=?", new String[]{maKH});
        return check != -1;
    }
}
