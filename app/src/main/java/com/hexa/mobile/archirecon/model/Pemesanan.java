package com.hexa.mobile.archirecon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pemesanan implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = -3260451251961433427L;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}