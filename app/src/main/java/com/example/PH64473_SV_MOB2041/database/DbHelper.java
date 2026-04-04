package com.example.PH64473_SV_MOB2041.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PH64473_SV_MOB2041.db";
    private static final int DB_VERSION = 7;

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
                "MatKhau TEXT NOT NULL, " +
                "GioiTinh TEXT NOT NULL, " +
                "SoDienThoai TEXT NOT NULL, " +
                "NgayVaoLam TEXT NOT NULL)";
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


        // 1. Nhân viên
        db.execSQL("INSERT INTO NhanVien VALUES ('admin', 'Quản lý 1', 'Hà Nội', 'Quản lý', 20000000, 'admin', 'Nam', '0123456789', '2024-01-01')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv01', 'Nguyễn Văn A', 'Hà Nội', 'Nhân viên', 8000000, '123', 'Nam', '0987654321', '2024-02-01')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv02', 'Trần Thị B', 'Hà Nội', 'Nhân viên', 8500000, '123', 'Nữ', '0912345678', '2024-02-15')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv03', 'Lê Văn C', 'Hà Nội', 'Nhân viên', 7500000, '123', 'Nam', '0923456789', '2024-03-01')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv04', 'Phạm Thị D', 'Hà Nội', 'Nhân viên', 9000000, '123', 'Nữ', '0934567890', '2024-03-10')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv05', 'Hoàng Văn E', 'Hà Nội', 'Nhân viên', 8200000, '123', 'Nam', '0945678901', '2024-03-20')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv06', 'Vũ Thị F', 'Hà Nội', 'Nhân viên', 7800000, '123', 'Nữ', '0956789012', '2024-04-01')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv07', 'Đặng Văn G', 'Hà Nội', 'Nhân viên', 8100000, '123', 'Nam', '0967890123', '2024-04-10')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv08', 'Bùi Thị H', 'Hà Nội', 'Nhân viên', 8400000, '123', 'Nữ', '0978901234', '2024-04-15')");
        db.execSQL("INSERT INTO NhanVien VALUES ('nv09', 'Đỗ Văn I', 'Hà Nội', 'Nhân viên', 7900000, '123', 'Nam', '0989012345', '2024-04-20')");

        // 2. Danh mục
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM01', 'Đồ uống Nhật', '2024-05-01')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM02', 'Bánh kẹo Nhật', '2024-05-02')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM03', 'Gia vị Nhật', '2024-05-03')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM04', 'Mì Nhật', '2024-05-04')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM05', 'Hải sản khô', '2024-05-05')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM06', 'Trà Nhật', '2024-05-06')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM07', 'Gạo Nhật', '2024-05-07')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM08', 'Rượu Sake', '2024-05-08')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM09', 'Mỹ phẩm Nhật', '2024-05-09')");
        db.execSQL("INSERT INTO DanhMuc VALUES ('DM10', 'Đồ gia dụng', '2024-05-10')");

        // 3. Sản phẩm
        db.execSQL("INSERT INTO SanPham VALUES ('SP01', 'DM01', 'Trà xanh Ito En', 35000, 100, 'Chai', '2024-05-11')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP02', 'DM02', 'Bánh Pocky', 25000, 200, 'Hộp', '2024-05-12')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP03', 'DM03', 'Nước tương Kikkoman', 75000, 50, 'Chai', '2024-05-13')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP04', 'DM04', 'Mì Udon Nissin', 45000, 150, 'Gói', '2024-05-14')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP05', 'DM05', 'Rong biển khô', 120000, 30, 'Gói', '2024-05-15')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP06', 'DM06', 'Bột trà xanh Matcha', 150000, 20, 'Hộp', '2024-05-16')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP07', 'DM07', 'Gạo Koshihikari', 450000, 10, 'Túi 5kg', '2024-05-17')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP08', 'DM08', 'Rượu Sake Dassai', 1200000, 5, 'Chai', '2024-05-18')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP09', 'DM09', 'Sữa rửa mặt Hada Labo', 180000, 40, 'Tuýp', '2024-05-19')");
        db.execSQL("INSERT INTO SanPham VALUES ('SP10', 'DM10', 'Dao làm bếp Nhật', 850000, 15, 'Cái', '2024-05-20')");

        // 4. Khách hàng
        db.execSQL("INSERT INTO KhachHang VALUES ('KH01', 'Nguyễn Văn An', 'Hà Nội', '0911111111', 'an@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH02', 'Trần Thị Bình', 'Hải Phòng', '0922222222', 'binh@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH03', 'Lê Văn Cường', 'Đà Nẵng', '0933333333', 'cuong@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH04', 'Phạm Minh Đức', 'HCM', '0944444444', 'duc@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH05', 'Hoàng Kim Yến', 'Cần Thơ', '0955555555', 'yen@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH06', 'Vũ Anh Tuấn', 'Hải Dương', '0966666666', 'tuan@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH07', 'Đặng Thu Thảo', 'Nam Định', '0977777777', 'thao@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH08', 'Bùi Quang Huy', 'Quảng Ninh', '0988888888', 'huy@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH09', 'Đỗ Thùy Linh', 'Thanh Hóa', '0999999999', 'linh@gmail.com')");
        db.execSQL("INSERT INTO KhachHang VALUES ('KH10', 'Ngô Bảo Châu', 'Nghệ An', '0900000000', 'chau@gmail.com')");

        // 5. Hóa đơn
        db.execSQL("INSERT INTO HoaDon VALUES ('HD01', 'nv01', 'KH01', '2024-05-21', 105000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD02', 'nv02', 'KH02', '2024-05-22', 250000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD03', 'nv03', 'KH03', '2024-05-23', 75000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD04', 'nv04', 'KH04', '2024-05-24', 90000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD05', 'nv05', 'KH05', '2024-05-25', 120000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD06', 'nv01', 'KH06', '2024-05-26', 150000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD07', 'nv02', 'KH07', '2024-05-27', 450000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD08', 'nv03', 'KH08', '2024-05-28', 1200000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD09', 'nv04', 'KH09', '2024-05-29', 180000)");
        db.execSQL("INSERT INTO HoaDon VALUES ('HD10', 'nv05', 'KH10', '2024-05-30', 850000)");

        // 6. Hóa đơn chi tiết
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT01', 'HD01', 'SP01', 3, 35000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT02', 'HD02', 'SP02', 10, 25000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT03', 'HD03', 'SP03', 1, 75000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT04', 'HD04', 'SP04', 2, 45000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT05', 'HD05', 'SP05', 1, 120000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT06', 'HD06', 'SP06', 1, 150000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT07', 'HD07', 'SP07', 1, 450000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT08', 'HD08', 'SP08', 1, 1200000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT09', 'HD09', 'SP09', 1, 180000)");
        db.execSQL("INSERT INTO HoaDonChiTiet VALUES ('CT10', 'HD10', 'SP10', 1, 850000)");
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
