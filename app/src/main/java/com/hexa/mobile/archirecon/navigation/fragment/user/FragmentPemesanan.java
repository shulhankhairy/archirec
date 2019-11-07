package com.hexa.mobile.archirecon.navigation.fragment.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.model.Pemesanan;
import com.hexa.mobile.archirecon.network.ApiPemesanan;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.R;

import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.model.Pemesanan;
import com.hexa.mobile.archirecon.network.ApiPemesanan;
import com.hexa.mobile.archirecon.network.ApiUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPemesanan  extends Fragment {

    View myView;
    EditText namas, no_hps, alamat, pekerjaan, perusahaan;
    LinearLayout ll_button,llMasjid, llPesantren, llSekolah, llPerumahan, llRumah;
    Button btn_continue,btnPesan;
    ProgressDialog loading;
    ApiPemesanan userService;
    String userid, nama, no_hp, alamats, id_get, jenis;
    SharedPreferences sharedPreferences;
    CardView cardViewPesan;
    Spinner spinnerOptPesan;
    HashMap msgArray;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.fragment_pemesanan, container, false);

        declaration();
        action();

        return myView;
    }

    void declaration() {
        userService = ApiUtils.getUserServices3();
        sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        msgArray = new HashMap<String,String>();
        msgArray.put("rumah",
                "Nama :\n" +
                "Alamat Pemesan :\n" +
                "Lokasi Proyek :\n" +
                "Kavling Luas Berapa / Ukuran :\n" +
                "Paket Produk yang Dipilih Apa :\n" +
                "Desain Interior Ruang Apa saja :");
        msgArray.put("perumahan",
                        "Nama :\n" +
                        "Alamat Pemesan :\n" +
                        "Lokasi Proyek :\n" +
                        "Rumah Type Berapa Saja / Paket Apa :\n" +
                        "Site Luas Berapa / Paket Apa :\n" +
                        "Animasi Durasi Berapa menit :\n" +
                        "Desain Interior Ruang Apa saja :");
        msgArray.put("pesantren",
                "Nama :\n" +
                "Alamat Pemesan :\n" +
                "Lokasi Proyek :\n" +
                "Gedung Kelasnya Berapa Lantai :\n" +
                "Gedung Penginapan Ustadz Berapa Lantai :\n" +
                "Gedung Penginapan Santri Berapa Lantai :\n" +
                "Gedung Dapur & T Makan Berapa Lantai :\n" +
                "Gedung Lab & Perpus Berapa Lantai :\n" +
                "Gedung Kantor Admin Berapa Lantai :\n" +
                "Gedung Gudang Berapa Lantai :\n" +
                "Gedung Minimarket Berapa Lantai :\n" +
                "Lahan Site Berapa m2 :\n" +
                "Paket Produk yang Dipilih Apa @ Gedung :\n" +
                "Paket Produk Rencana Site Lahan Apa :");
        msgArray.put("masjid",
                "Nama :\n" +
                "Alamat Pemesan :\n" +
                "Lokasi Proyek :\n" +
                "Lahan Luas Berapa / Ukuran :\n" +
                "Paket Produk yang Dipilih Apa :");
        msgArray.put("sekolah",
                "Nama :\n" +
                "Alamat Pemesan :\n" +
                "Lokasi Proyek :\n" +
                "Lahan Luas Berapa / Ukuran :\n" +
                "Sekolah Lantai Berapa :\n" +
                "Paket Produk yang Dipilih Apa :");

        ll_button = myView.findViewById(R.id.ll_button);
        btn_continue = myView.findViewById(R.id.btn_continue);
        namas = myView.findViewById(R.id.nama);
        no_hps = myView.findViewById(R.id.no_hp);
        alamat = myView.findViewById(R.id.alamat);
        pekerjaan = myView.findViewById(R.id.pekerjaan);
        perusahaan = myView.findViewById(R.id.perusahaan);

        spinnerOptPesan = myView.findViewById(R.id.spinner_opt_jenis);
        cardViewPesan = myView.findViewById(R.id.card_input_pesan);

        llMasjid = myView.findViewById(R.id.ll_masjid);
        llPerumahan = myView.findViewById(R.id.ll_perumahan);
        llPesantren = myView.findViewById(R.id.ll_pesantren);
        llRumah = myView.findViewById(R.id.ll_rumah);
        llSekolah = myView.findViewById(R.id.ll_sekolah);

        btnPesan = myView.findViewById(R.id.btn_pesan);

        userid = sharedPreferences.getString(Config.KODE_SHARED_PREF, "");
        nama = sharedPreferences.getString(Config.NAMA_SHARED_PREF, "");
        no_hp = sharedPreferences.getString(Config.HP_SHARED_PREF, "");
        alamats = sharedPreferences.getString(Config.ALAMAT_SHARED_PREF, "");
        namas.setText(nama);
        no_hps.setText(no_hp);
        alamat.setText(alamats);
        validasiOnkeyup(namas);
        validasiOnkeyup(no_hps);
        validasiOnkeyup(alamat);
        validasiOnkeyup(pekerjaan);
        validasiOnkeyup(perusahaan);


    }

    void validasiOnkeyup(EditText edit){
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_continue.setBackgroundColor(getActivity().getResources().getColor(R.color.colorOrange));
                btn_continue.setTextColor(getActivity().getResources().getColor(R.color.colorPutih));
                btn_continue.setText("LANJUTKAN");
                action();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
    }

    void action() {
        spinnerOptPesan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equalsIgnoreCase("bangunan umum")){
                    llPerumahan.setVisibility(View.GONE);
                    llMasjid.setVisibility(View.GONE);
                    llSekolah.setVisibility(View.GONE);
                    llPesantren.setVisibility(View.GONE);
                    llRumah.setVisibility(View.GONE);
                    cardViewPesan.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.VISIBLE);
                }else if (selectedItem.equalsIgnoreCase("rumah")){
                    cardViewPesan.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.GONE);
                    llPerumahan.setVisibility(View.GONE);
                    llMasjid.setVisibility(View.GONE);
                    llSekolah.setVisibility(View.GONE);
                    llPesantren.setVisibility(View.GONE);
                    llRumah.setVisibility(View.VISIBLE);
                    btnPesan.setVisibility(View.VISIBLE);
                    jenis = msgArray.get("rumah").toString();
                }else if (selectedItem.equalsIgnoreCase("perumahan")){
                    cardViewPesan.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.GONE);
                    llPerumahan.setVisibility(View.VISIBLE);
                    llMasjid.setVisibility(View.GONE);
                    llSekolah.setVisibility(View.GONE);
                    llPesantren.setVisibility(View.GONE);
                    llRumah.setVisibility(View.GONE);
                    btnPesan.setVisibility(View.VISIBLE);
                    jenis = msgArray.get("perumahan").toString();
                }else if (selectedItem.equalsIgnoreCase("pesantren")){
                    cardViewPesan.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.GONE);
                    llPerumahan.setVisibility(View.GONE);
                    llMasjid.setVisibility(View.GONE);
                    llSekolah.setVisibility(View.GONE);
                    llPesantren.setVisibility(View.VISIBLE);
                    llRumah.setVisibility(View.GONE);
                    btnPesan.setVisibility(View.VISIBLE);
                    jenis = msgArray.get("pesantren").toString();
                }else if (selectedItem.equalsIgnoreCase("masjid")){
                    cardViewPesan.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.GONE);
                    llPerumahan.setVisibility(View.GONE);
                    llMasjid.setVisibility(View.VISIBLE);
                    llSekolah.setVisibility(View.GONE);
                    llPesantren.setVisibility(View.GONE);
                    llRumah.setVisibility(View.GONE);
                    btnPesan.setVisibility(View.VISIBLE);
                    jenis = msgArray.get("masjid").toString();
                }else if (selectedItem.equalsIgnoreCase("sekolah")){
                    cardViewPesan.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.GONE);
                    llPerumahan.setVisibility(View.GONE);
                    llMasjid.setVisibility(View.GONE);
                    llSekolah.setVisibility(View.VISIBLE);
                    llPesantren.setVisibility(View.GONE);
                    llRumah.setVisibility(View.GONE);
                    btnPesan.setVisibility(View.VISIBLE);
                    jenis = msgArray.get("sekolah").toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
                PemesananStepOne();
            }
        });

        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(myView,jenis);
            }
        });
    }

    public void PemesananStepOne() {
        userid = sharedPreferences.getString(Config.KODE_SHARED_PREF, "");
        nama = sharedPreferences.getString(Config.NAMA_SHARED_PREF, "");
        no_hp = sharedPreferences.getString(Config.HP_SHARED_PREF, "");
        alamats = sharedPreferences.getString(Config.ALAMAT_SHARED_PREF, "");
        final String idx = userid;
        final String namax = namas.getText().toString();
        final String nohpx = no_hps.getText().toString();
        final String alamatx = alamat.getText().toString();
        final String pekerjaanx = pekerjaan.getText().toString();
        final String perusahaanx = perusahaan.getText().toString();

        if (idx.equals("") || namax.equals("") || nohpx.equals("") || alamatx.equals("") || pekerjaanx.equals("")) {
            loading.dismiss();
            btn_continue.setBackgroundColor(getActivity().getResources().getColor(R.color.colorMerah));
            btn_continue.setTextColor(getActivity().getResources().getColor(R.color.colorPutih));
            btn_continue.setText("Data tidak boleh kosong");
            Toast.makeText(getActivity(), "Tidak boleh ada data yang kosong Kecuali Perusahaan", Toast.LENGTH_LONG).show();
        } else {
            Call<Pemesanan> call = userService.pemesananRequest(idx, namax, nohpx, alamatx, pekerjaanx, perusahaanx);
            call.enqueue(new Callback<Pemesanan>() {
                @Override
                public void onResponse(Call<Pemesanan> call, Response<Pemesanan> response) {
                    if (response.isSuccessful()) {
                        Pemesanan data = response.body();
                        if (data.getMessage().equals("success")) {
                            loading.dismiss();
                            id_get = data.getId().toString();
                            /*PemesananStepTwo(id_get);*/

                            FragmentPemesanan2 ldf = new FragmentPemesanan2();
                            Bundle args = new Bundle();
                            args.putString("id", id_get);
                            ldf.setArguments(args);
                            getFragmentManager().beginTransaction().replace(R.id.content_frame, ldf).commit();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Pemesanan> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void openWhatsApp(View view, String jenis){
        try {
//            String text = "This is a test";// Replace with your message.

            String toNumber = "6282333339949"; // Replace with mobile phone number without +Sign or leading zeros.

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+jenis));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Pemesanan Design");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment fragment = null;
                    fragment = new FragmentBerita();
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