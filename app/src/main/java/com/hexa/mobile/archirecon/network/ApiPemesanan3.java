package com.hexa.mobile.archirecon.network;

import com.hexa.mobile.archirecon.model.Pemesanan3;
import com.hexa.mobile.archirecon.model.Pemesanan2;
import com.hexa.mobile.archirecon.model.Pemesanan3;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiPemesanan3 {
    @Multipart
    @POST("pemesanan3.php")
    Call<Pemesanan3> pemesanan3Request(@Part("id") RequestBody id,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody img_denah,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody img_foto_depan,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody img_foto_jalan,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody img_foto_kanan,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody img_foto_kiri,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody img_ruang);
}