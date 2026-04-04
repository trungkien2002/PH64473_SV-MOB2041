package com.example.PH64473_SV_MOB2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.PH64473_SV_MOB2041.database.DbHelper;
import com.example.PH64473_SV_MOB2041.model.DanhMuc;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    private DbHelper dbHelper;

    public DanhMucDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<DanhMuc> getAll() {
        List<DanhMuc> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DanhMuc", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new DanhMuc(
                        cursor.getString(0), // MaDanhMuc
                        cursor.getString(1), // TenDanhMuc
                        cursor.getString(2)  // NgayTao
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insert(DanhMuc dm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaDanhMuc", dm.getMaDanhMuc());
        values.put("TenDanhMuc", dm.getTenDanhMuc());
        values.put("NgayTao", dm.getNgayTao());
        long check = db.insert("DanhMuc", null, values);
        return check != -1;
    }

    public boolean update(DanhMuc dm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenDanhMuc", dm.getTenDanhMuc());
        values.put("NgayTao", dm.getNgayTao());
        long check = db.update("DanhMuc", values, "MaDanhMuc=?", new String[]{dm.getMaDanhMuc()});
        return check != -1;
    }

    public boolean delete(String maDM) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("DanhMuc", "MaDanhMuc=?", new String[]{maDM});
        return check != -1;
    }

    public List<DanhMuc> search(String query) {
        List<DanhMuc> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM DanhMuc WHERE TenDanhMuc LIKE ? OR MaDanhMuc LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + query + "%", "%" + query + "%"});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new DanhMuc(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
