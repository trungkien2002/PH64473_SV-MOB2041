package com.example.PH64473_SV_MOB2041.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PH64473_SV_MOB2041.db";
    private static final int DB_VERSION = 3; // Tăng version để cập nhật cột mới

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Thực thể Danh mục
        String createTableDanhMuc = "CREATE TABLE DanhMuc (" +
                "MaDanhMuc TEXT PRIMARY KEY NOT NULL, " +
                "TenDanhMuc TEXT NOT NULL, " +
                "NgayTao TEXT NOT NULL)";
        db.execSQL(createTableDanhMuc);

        // 2. Thực thể Sản phẩm
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "MaSanPham TEXT PRIMARY KEY NOT NULL, " +
                "MaDanhMuc TEXT NOT NULL, " +
                "TenSanPham TEXT NOT NULL, " +
                "DonGia REAL NOT NULL, " +
                "SoLuongTonKho INTEGER NOT NULL, " +
                "DonViTinh TEXT NOT NULL, " +
                "NgayNhap TEXT NOT NULL, " +
                "FOREIGN KEY(MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc))";
        db.execSQL(createTableSanPham);

        // 3. Thực thể Khách hàng
        String createTableKhachHang = "CREATE TABLE KhachHang (" +
                "MaKhachHang TEXT PRIMARY KEY NOT NULL, " +
                "TenKhachHang TEXT NOT NULL, " +
                "DiaChi TEXT NOT NULL, " +
                "SoDienThoai TEXT NOT NULL, " +
                "Email TEXT)";
        db.execSQL(createTableKhachHang);

        // 4. Thực thể Nhân viên
        String createTableNhanVien = "CREATE TABLE NhanVien (" +
                "MaNhanVien TEXT PRIMARY KEY NOT NULL, " +
                "TenNhanVien TEXT NOT NULL, " +
                "DiaChi TEXT NOT NULL, " +
                "ChucVu TEXT NOT NULL, " +
                "Luong REAL NOT NULL, " +
                "MatKhau TEXT NOT NULL)";
        db.execSQL(createTableNhanVien);

        // 5. Thực thể Hóa đơn
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "MaHoaDon TEXT PRIMARY KEY NOT NULL, " +
                "MaNhanVien TEXT NOT NULL, " +
                "MaKhachHang TEXT NOT NULL, " +
                "NgayLap TEXT NOT NULL, " +
                "TongTien INTEGER NOT NULL, " +
                "FOREIGN KEY(MaNhanVien) REFERENCES NhanVien(MaNhanVien), " +
                "FOREIGN KEY(MaKhachHang) REFERENCES KhachHang(MaKhachHang))";
        db.execSQL(createTableHoaDon);

        // 6. Thực thể Hóa đơn chi tiết
        String createTableHoaDonChiTiet = "CREATE TABLE HoaDonChiTiet (" +
                "MaChiTietHoaDon TEXT PRIMARY KEY NOT NULL, " +
                "MaHoaDon TEXT NOT NULL, " +
                "MaSanPham TEXT NOT NULL, " +
                "SoLuong INTEGER NOT NULL, " +
                "DonGia REAL NOT NULL, " +
                "FOREIGN KEY(MaHoaDon) REFERENCES HoaDon(MaHoaDon), " +
                "FOREIGN KEY(MaSanPham) REFERENCES SanPham(MaSanPham))";
        db.execSQL(createTableHoaDonChiTiet);

        // Dữ liệu mẫu ban đầu
        db.execSQL("INSERT INTO NhanVien VALUES ('admin', 'Admin', 'Hà Nội', 'Quản lý', 20000000, 'admin')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM01', 'Hàng tiêu dùng', '2024-05-20')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH01', 'Nguyễn Văn A', 'HCM', '0901234567', 'a@gmail.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS HoaDonChiTiet");
            db.execSQL("DROP TABLE IF EXISTS HoaDon");
            db.execSQL("DROP TABLE IF EXISTS NhanVien");
            db.execSQL("DROP TABLE IF EXISTS KhachHang");
            db.execSQL("DROP TABLE IF EXISTS SanPham");
            db.execSQL("DROP TABLE IF EXISTS DanhMuc");
            onCreate(db);
        }
    }
}
