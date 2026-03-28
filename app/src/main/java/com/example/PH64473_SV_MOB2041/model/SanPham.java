package com.example.PH64473_SV_MOB2041.model;

public class SanPham {
    private String maSanPham;
    private String maDanhMuc;
    private String tenSanPham;
    private double donGia;
    private int soLuongTonKho;
    private String donViTinh;
    private String ngayNhap;

    public SanPham() {
    }

    public SanPham(String maSanPham, String maDanhMuc, String tenSanPham, double donGia, int soLuongTonKho, String donViTinh, String ngayNhap) {
        this.maSanPham = maSanPham;
        this.maDanhMuc = maDanhMuc;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuongTonKho = soLuongTonKho;
        this.donViTinh = donViTinh;
        this.ngayNhap = ngayNhap;
    }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
    public String getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(String maDanhMuc) { this.maDanhMuc = maDanhMuc; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public int getSoLuongTonKho() { return soLuongTonKho; }
    public void setSoLuongTonKho(int soLuongTonKho) { this.soLuongTonKho = soLuongTonKho; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }
}
