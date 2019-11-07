package com.hexa.mobile.archirecon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemesanan2 {
    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("message")
    @Expose
    private String message;
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    private String id, ukuran_lahan, arah_hadap_lahan;
    int lebar_jalan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUkuran_lahan() {
        return ukuran_lahan;
    }

    public void setUkuran_lahan(String ukuran_lahan) {
        this.ukuran_lahan = ukuran_lahan;
    }

    public String getArah_hadap_lahan() {
        return arah_hadap_lahan;
    }

    public void setArah_hadap_lahan(String arah_hadap_lahan) {
        this.arah_hadap_lahan = arah_hadap_lahan;
    }

    public int getLebar_jalan() {
        return lebar_jalan;
    }

    public void setLebar_jalan(int lebar_jalan) {
        this.lebar_jalan = lebar_jalan;
    }

}
