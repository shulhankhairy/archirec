package com.hexa.mobile.archirecon.network;

import com.hexa.mobile.archirecon.model.Pemesanan2;
import com.hexa.mobile.archirecon.model.Pemesanan;
import com.hexa.mobile.archirecon.model.Pemesanan2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiPemesanan2 {
    @FormUrlEncoded
    @POST("pemesanan2.php")
    Call<Pemesanan2> pemesanan2Request(@Field("id") String id,
                                       @Field("ukuran_lahan") String ukuran_lahan,
                                       @Field("arah_hadap_lahan") String arah_hadap_lahan,
                                       @Field("lebar_jalan") String lebar_jalan);
}
