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
                        cursor.getString(0), // maSP
                        cursor.getString(1), // tenSP
                        cursor.getDouble(2), // giaBan
                        cursor.getInt(3)    // soLuong
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(SanPham sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSP", sp.getMaSP());
        values.put("tenSP", sp.getTenSP());
        values.put("giaBan", sp.getGiaBan());
        values.put("soLuong", sp.getSoLuong());
        // maDM can be added if your model has it, or pass as extra parameter
        long check = db.insert("SanPham", null, values);
        return check != -1;
    }

    public boolean update(SanPham sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSP", sp.getTenSP());
        values.put("giaBan", sp.getGiaBan());
        values.put("soLuong", sp.getSoLuong());
        long check = db.update("SanPham", values, "maSP=?", new String[]{sp.getMaSP()});
        return check != -1;
    }

    public boolean delete(String maSP) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("SanPham", "maSP=?", new String[]{maSP});
        return check != -1;
    }
}
