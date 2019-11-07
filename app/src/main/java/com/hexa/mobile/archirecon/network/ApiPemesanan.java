package com.hexa.mobile.archirecon.network;

import com.hexa.mobile.archirecon.model.Pemesanan;
import com.hexa.mobile.archirecon.model.Pemesanan;
import com.hexa.mobile.archirecon.model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiPemesanan {

    @FormUrlEncoded
    @POST("pemesanan.php")
    Call<Pemesanan> pemesananRequest(@Field("id_user") String id_user,
                                     @Field("nama") String nama,
                                     @Field("no_hp") String no_hp,
                                     @Field("alamat") String alamat,
                                     @Field("pekerjaan") String pekerjaan,
                                     @Field("perusahaan") String perusahaan);
}