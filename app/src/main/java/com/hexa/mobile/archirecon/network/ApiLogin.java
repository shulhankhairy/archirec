package com.hexa.mobile.archirecon.network;

import com.hexa.mobile.archirecon.model.Login;
import com.hexa.mobile.archirecon.model.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiLogin {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginRequest(@Field("username") String username,
                             @Field("password") String password);

}
