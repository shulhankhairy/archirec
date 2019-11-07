package com.hexa.mobile.archirecon.network;
import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRegister {

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerRequest(@Field("nama") String nama,
                                   @Field("no_hp") String no_hp,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("hapus_user.php")
    Call<Register> RemoveRequest(@Field("id") String id);

    @FormUrlEncoded
    @POST("edit_user.php")
    Call<Register> EditUserRequest(@Field("id") String id,
                                   @Field("nama") String nama,
                                   @Field("no_hp") String no_hp,
                                   @Field("username") String email,
                                   @Field("password") String password,
                                   @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("status_user.php")
    Call<Register> EditStatusRequest(@Field("id") String id,
                                   @Field("status") String status);
}
