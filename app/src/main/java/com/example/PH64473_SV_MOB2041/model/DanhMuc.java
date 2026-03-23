package com.example.PH64473_SV_MOB2041.model;

public class DanhMuc {
    private String maDanhMuc;
    private String tenDanhMuc;
    private String ngayTaoDanhMuc;

    public DanhMuc(String maDanhMuc, String tenDanhMuc, String ngayTaoDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.ngayTaoDanhMuc = ngayTaoDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getNgayTaoDanhMuc() {
        return ngayTaoDanhMuc;
    }

    public void setNgayTaoDanhMuc(String ngayTaoDanhMuc) {
        this.ngayTaoDanhMuc = ngayTaoDanhMuc;
    }
}
