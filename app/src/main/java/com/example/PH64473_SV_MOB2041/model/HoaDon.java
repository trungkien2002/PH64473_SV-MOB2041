package com.example.PH64473_SV_MOB2041.model;

public class HoaDon {
    private String maHD;
    private String tenNV;
    private String tenKH;
    private String ngayLap;
    private double tongTien;

    public HoaDon(String maHD, String tenNV, String tenKH, String ngayLap, double tongTien) {
        this.maHD = maHD;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public String getMaHD() { return maHD; }
    public String getTenNV() { return tenNV; }
    public String getTenKH() { return tenKH; }
    public String getNgayLap() { return ngayLap; }
    public double getTongTien() { return tongTien; }
}