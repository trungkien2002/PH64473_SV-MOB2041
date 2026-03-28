package com.example.PH64473_SV_MOB2041.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PH64473_SV_MOB2041.db";
    private static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Bảng Nhân Viên
        String createTableNhanVien = "CREATE TABLE NhanVien (" +
                "maNV TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL, " +
                "sdt TEXT, " +
                "loaiTaiKhoan TEXT NOT NULL)"; // admin hoặc nhanvien
        db.execSQL(createTableNhanVien);

        // 2. Bảng Danh Mục
        String createTableDanhMuc = "CREATE TABLE DanhMuc (" +
                "maDanhMuc TEXT PRIMARY KEY, " +
                "tenDanhMuc TEXT NOT NULL, " +
                "ngayTaoDanhMuc TEXT)";
        db.execSQL(createTableDanhMuc);

        // 3. Bảng Sản Phẩm
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "maSP TEXT PRIMARY KEY, " +
                "tenSP TEXT NOT NULL, " +
                "giaBan REAL NOT NULL, " +
                "soLuong INTEGER NOT NULL, " +
                "maDanhMuc TEXT, " +
                "FOREIGN KEY(maDanhMuc) REFERENCES DanhMuc(maDanhMuc))";
        db.execSQL(createTableSanPham);

        // 4. Bảng Khách Hàng
        String createTableKhachHang = "CREATE TABLE KhachHang (" +
                "maKH TEXT PRIMARY KEY, " +
                "tenKH TEXT NOT NULL, " +
                "sdt TEXT)";
        db.execSQL(createTableKhachHang);

        // 5. Bảng Hóa Đơn
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "maHD TEXT PRIMARY KEY, " +
                "maNV TEXT, " +
                "maKH TEXT, " +
                "ngayLap TEXT NOT NULL, " +
                "tongTien REAL NOT NULL, " +
                "FOREIGN KEY(maNV) REFERENCES NhanVien(maNV), " +
                "FOREIGN KEY(maKH) REFERENCES KhachHang(maKH))";
        db.execSQL(createTableHoaDon);

        // 6. Bảng Chi Tiết Hóa Đơn
        String createTableChiTietHoaDon = "CREATE TABLE ChiTietHoaDon (" +
                "maCTHD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maHD TEXT, " +
                "maSP TEXT, " +
                "soLuong INTEGER NOT NULL, " +
                "donGia REAL NOT NULL, " +
                "FOREIGN KEY(maHD) REFERENCES HoaDon(maHD), " +
                "FOREIGN KEY(maSP) REFERENCES SanPham(maSP))";
        db.execSQL(createTableChiTietHoaDon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ChiTietHoaDon");
            db.execSQL("DROP TABLE IF EXISTS HoaDon");
            db.execSQL("DROP TABLE IF EXISTS KhachHang");
            db.execSQL("DROP TABLE IF EXISTS SanPham");
            db.execSQL("DROP TABLE IF EXISTS DanhMuc");
            db.execSQL("DROP TABLE IF EXISTS NhanVien");
            onCreate(db);
        }
    }
}
