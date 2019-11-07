package com.hexa.mobile.archirecon.navigation.fragment.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hexa.mobile.archirecon.model.Pemesanan2;
import com.hexa.mobile.archirecon.network.ApiPemesanan2;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.model.Pemesanan2;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentPemesanan5;
import com.hexa.mobile.archirecon.network.ApiPemesanan2;
import com.hexa.mobile.archirecon.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPemesanan2 extends Fragment {

    View myView;
    EditText panjang, lebar, lebar_lahan, arah_hadap;
    LinearLayout ll_button;
    Button btn_continue;
    ProgressDialog loading;
    ApiPemesanan2 userService2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.fragment_pemesanan2, container, false);
        declaration();
        action();
        return myView;
    }

    private void declaration() {
        userService2    = ApiUtils.getUserServices4();
        panjang         = myView.findViewById(R.id.panjang);
        lebar           = myView.findViewById(R.id.lebar);
        lebar_lahan     = myView.findViewById(R.id.lebar_lahan);
        arah_hadap      = myView.findViewById(R.id.arah_hadap);
        ll_button       = myView.findViewById(R.id.ll_button);
        btn_continue    = myView.findViewById(R.id.btn_continue);
        validasiOnkeyup(panjang);
        validasiOnkeyup(lebar);
        validasiOnkeyup(lebar_lahan);
        validasiOnkeyup(arah_hadap);
    }

    void validasiOnkeyup(EditText edit) {
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

    private void action() {
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
                String value = getArguments().getString("id");
                PemesananStepTwo(value);
            }
        });
    }

    public void PemesananStepTwo(final String id_get){
        final String idx        = id_get;
        final String ukuranx    = panjang.getText().toString() +","+lebar.getText().toString();
        final String arahx      = arah_hadap.getText().toString();
        final String lebarx     = lebar_lahan.getText().toString();


        if(idx.equals("")  ||ukuranx.equals("") || arahx.equals("") || lebarx.equals("")){
            loading.dismiss();
            btn_continue.setBackgroundColor(getActivity().getResources().getColor(R.color.colorMerah));
            btn_continue.setTextColor(getActivity().getResources().getColor(R.color.colorPutih));
            btn_continue.setText("Data tidak boleh kosong");
            Toast.makeText(getActivity(), "Tidak boleh ada data yang kosong", Toast.LENGTH_LONG).show();
        }else{
            Call<Pemesanan2> call = userService2.pemesanan2Request(idx,ukuranx,arahx,lebarx);
            call.enqueue(new Callback<Pemesanan2>() {
                @Override
                public void onResponse(Call<Pemesanan2> call, Response<Pemesanan2> response) {
                    if(response.isSuccessful()) {
                        loading.dismiss();
                        Pemesanan2 data = response.body();
                        if (data.getMessage().equals("success")) {
                            /*pemesananStepThree(id_get);*/
                            FragmentPemesanan5 ldf = new FragmentPemesanan5();
                            Bundle args = new Bundle();
                            args.putString("id", id_get);
                            ldf.setArguments(args);
                            getFragmentManager().beginTransaction().replace(R.id.content_frame, ldf).commit();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Pemesanan2> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Pemesanan Design");
    }



}
