package com.hexa.mobile.archirecon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemesanan3 {
    @SerializedName("success")
    @Expose
    private String success;
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    private String img_denah, img_foto_depan, img_foto_jalan, img_foto_kanan,img_foto_kiri,img_ruang,id;

    public String getImg_denah() {
        return img_denah;
    }

    public void setImg_denah(String img_denah) {
        this.img_denah = img_denah;
    }

    public String getImg_foto_depan() {
        return img_foto_depan;
    }

    public void setImg_foto_depan(String img_foto_depan) {
        this.img_foto_depan = img_foto_depan;
    }

    public String getImg_foto_jalan() {
        return img_foto_jalan;
    }

    public void setImg_foto_jalan(String img_foto_jalan) {
        this.img_foto_jalan = img_foto_jalan;
    }

    public String getImg_foto_kanan() {
        return img_foto_kanan;
    }

    public void setImg_foto_kanan(String img_foto_kanan) {
        this.img_foto_kanan = img_foto_kanan;
    }

    public String getImg_foto_kiri() {
        return img_foto_kiri;
    }

    public void setImg_foto_kiri(String img_foto_kiri) {
        this.img_foto_kiri = img_foto_kiri;
    }

    public String getImg_ruang() {
        return img_ruang;
    }

    public void setImg_ruang(String img_ruang) {
        this.img_foto_kanan = img_ruang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
