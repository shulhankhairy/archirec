package com.hexa.mobile.archirecon.network;

import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiSendEmail {

    @FormUrlEncoded
    @POST("send_email.php")
    Call<Register> sendRequest(@Field("id") String id,
                               @Field("email") String email,
                               @Field("nama") String nama,
                               @Field("kode") String kode);

}
