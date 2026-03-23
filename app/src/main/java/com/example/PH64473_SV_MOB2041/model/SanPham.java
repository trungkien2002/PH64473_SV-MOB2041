package com.example.PH64473_SV_MOB2041.model;

public class SanPham {
    private String maSP;
    private String tenSP;
    private double giaBan;
    private int soLuong;

    public SanPham(String maSP, String tenSP, double giaBan, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public String getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public double getGiaBan() { return giaBan; }
    public int getSoLuong() { return soLuong; }
}