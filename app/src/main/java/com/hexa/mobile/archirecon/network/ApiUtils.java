package com.hexa.mobile.archirecon.network;

public class ApiUtils {

    public static final String BASE_URL = "http://www.logiswebmedia.com/archirecon/archirecon-android/";
//    public static final String BASE_URL = "http://31.170.166.166/archirecon/archirecon-android/";

    public static ApiLogin getUserServices(){
        return ApiClient.getClient(BASE_URL).create(ApiLogin.class);
    }

    public static ApiRegister getUserServices2(){
        return ApiClient.getClient(BASE_URL).create(ApiRegister.class);
    }

    public static ApiPemesanan getUserServices3(){
        return ApiClient.getClient(BASE_URL).create(ApiPemesanan.class);
    }

    public static ApiPemesanan2 getUserServices4(){
        return ApiClient.getClient(BASE_URL).create(ApiPemesanan2.class);
    }

    public static ApiPemesanan3 getUserServices5(){
        return ApiClient.getClient(BASE_URL).create(ApiPemesanan3.class);
    }

    public static ApiSendEmail getUserServices6(){
        return ApiClient.getClient(BASE_URL).create(ApiSendEmail.class);
    }

    public static ApiVerifikasi getUserServices7(){
        return ApiClient.getClient(BASE_URL).create(ApiVerifikasi.class);
    }

}
