package com.example.PH64473_SV_MOB2041.model;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String sdt;

    public KhachHang(String maKH, String tenKH, String sdt) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
    }

    public String getMaKH() { return maKH; }
    public String getTenKH() { return tenKH; }
    public String getSdt() { return sdt; }
}