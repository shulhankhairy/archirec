package com.hexa.mobile.archirecon.network;

import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiVerifikasi {

    @FormUrlEncoded
    @POST("verifikasi.php")
    Call<Register> verifikasiRequest(@Field("id") String id,
                                     @Field("kode") String kode);
}
