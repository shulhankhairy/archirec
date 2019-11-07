package com.hexa.mobile.archirecon.navigation.fragment.admin.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListPemesanan;
import com.hexa.mobile.archirecon.network.ApiPemesanan;
import com.hexa.mobile.archirecon.network.ApiPemesanan2;
import com.hexa.mobile.archirecon.network.ApiPemesanan3;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.item.user_item;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListPemesanan;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListUser;
import com.hexa.mobile.archirecon.network.ApiPemesanan;
import com.hexa.mobile.archirecon.network.ApiPemesanan2;
import com.hexa.mobile.archirecon.network.ApiPemesanan3;
import com.hexa.mobile.archirecon.network.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;


public class DetailPesanan extends Fragment {


    View myView;
    EditText namas, no_hps, alamat, pekerjaan, perusahaan, panjang, lebar, lebar_lahan, arah_hadap;
    ImageView denah_rumah, tampak_depan, tampak_kanan, tampak_kiri,tampak_jalan;
    EditText kebutuhan_ruang;
    LinearLayout ll_button;
    Button btn_continue;
    ProgressDialog loading;
    ApiPemesanan userService;
    ApiPemesanan2 userService2;
    ApiPemesanan3 userService3;
    String db_nama, db_hp, db_alamat, db_pekerjaan, db_perusahaan, db_panjang, db_lebar,db_lebar_lahan, db_arah_hadap,db_kebutuhan_ruang, db_denah_rumah, db_tampak_depan, db_tampak_kanan, db_tampak_kiri,db_tampak_jalan;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout filecom.hexa.mobile.archirecon.R.layout.activity_detailpesanan
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.activity_detailpesanan, container, false);
        declaration();
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        String id = getArguments().getString("id");
        Detail(id);
        return myView;
    }

    void declaration() {
        userService = ApiUtils.getUserServices3();
        userService2 = ApiUtils.getUserServices4();
        userService3 = ApiUtils.getUserServices5();
        sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

//        Image
        kebutuhan_ruang = myView.findViewById(R.id.kebutuhan_ruang);
        denah_rumah = myView.findViewById(R.id.denah_rumah);
        tampak_depan = myView.findViewById(R.id.tampak_depan);
        tampak_kanan = myView.findViewById(R.id.tampak_kanan);
        tampak_kiri = myView.findViewById(R.id.tampak_kiri);
        tampak_jalan = myView.findViewById(R.id.tampak_jalan);
        ll_button = myView.findViewById(R.id.ll_button);
        btn_continue = myView.findViewById(R.id.btn_continue);
        namas = myView.findViewById(R.id.nama);
        no_hps = myView.findViewById(R.id.no_hp);
        alamat = myView.findViewById(R.id.alamat);
        pekerjaan = myView.findViewById(R.id.pekerjaan);
        perusahaan = myView.findViewById(R.id.perusahaan);
        panjang = myView.findViewById(R.id.panjang);
        lebar = myView.findViewById(R.id.lebar);
        lebar_lahan = myView.findViewById(R.id.lebar_lahan);
        arah_hadap = myView.findViewById(R.id.arah_hadap);
        namas.setEnabled(false);
        no_hps.setEnabled(false);
        alamat.setEnabled(false);
        pekerjaan.setEnabled(false);
        perusahaan.setEnabled(false);
        panjang.setEnabled(false);
        kebutuhan_ruang.setEnabled(false);
        lebar.setEnabled(false);
        lebar_lahan.setEnabled(false);
        arah_hadap.setEnabled(false);

    }

    void Detail(final String id){
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://www.logiswebmedia.com/archirecon/archirecon-android/detail_pemesanan.php?id="+id)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONArray jos = new JSONArray(jsonData);
                    for (int i = 0; i < jos.length(); i++){
                        JSONObject jo = jos.getJSONObject(i);
                        loading.dismiss();
                        db_nama=jo.getString("nama");
                        db_hp=jo.getString("hp");
                        db_alamat=jo.getString("alamat");
                        db_pekerjaan=jo.getString("pekerjaan");
                        db_perusahaan=jo.getString("perusahaan");
                        db_panjang=jo.getString("ukuran_lahan");
                        db_lebar=jo.getString("ukuran_lahan");
                        db_lebar_lahan=jo.getString("lebar_jalan");
                        db_arah_hadap = jo.getString("arah_hadap_lahan");
                        db_kebutuhan_ruang=jo.getString("img_kebutuhan_ruang");
                        db_denah_rumah=jo.getString("img_denah");
                        db_tampak_depan=jo.getString("img_foto_depan");
                        db_tampak_kanan=jo.getString("img_foto_kanan");
                        db_tampak_kiri=jo.getString("img_foto_kiri");
                        db_tampak_jalan=jo.getString("img_foto_jalan");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End Of Content");
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
//                adapter_list.notifyDataSetChanged();
                namas.setText(db_nama);
                no_hps.setText(db_hp);
                alamat.setText(db_alamat);
                pekerjaan.setText(db_pekerjaan);
                perusahaan.setText(db_perusahaan);
                String panjang_db = db_panjang.substring(0, db_panjang.lastIndexOf(","));
                panjang.setText(panjang_db);
                String lebar_db = db_lebar.substring(db_lebar.lastIndexOf(",") + 1);
                lebar.setText(lebar_db);
                lebar_lahan.setText(db_lebar_lahan);
                arah_hadap.setText(db_arah_hadap);
                kebutuhan_ruang.setText(db_kebutuhan_ruang);
                /*ImageView(db_kebutuhan_ruang,kebutuhan_ruang);*/
                ImageView(db_denah_rumah,denah_rumah);
                ImageView(db_tampak_depan,tampak_depan);
                ImageView(db_tampak_kanan,tampak_kanan);
                ImageView(db_tampak_kiri,tampak_kiri);
                ImageView(db_tampak_jalan,tampak_jalan);



            }

        };
        task.execute(0);
    }

    void ImageView(String link , ImageView image){
        Glide.with(getActivity())
                .load(link)
                .into(image);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Detail Pemesanan");
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Fragment fragment = null;
                    fragment = new FragmentListPemesanan();
                    if (fragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
