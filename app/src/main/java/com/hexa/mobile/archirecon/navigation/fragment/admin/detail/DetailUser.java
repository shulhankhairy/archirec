package com.hexa.mobile.archirecon.navigation.fragment.admin.detail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListUser;
import com.hexa.mobile.archirecon.network.ApiRegister;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.activity.LoginActivity;
import com.hexa.mobile.archirecon.activity.RegisterActivity;
import com.hexa.mobile.archirecon.adapter.AdapterPemesanan;
import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.item.pemesanan_item;
import com.hexa.mobile.archirecon.item.user_item;
import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.navigation.Navigationdrawer;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListUser;
import com.hexa.mobile.archirecon.network.ApiRegister;
import com.hexa.mobile.archirecon.network.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUser extends Fragment {
    EditText nama,no_hp,email,password,alamat;
    Button btn_daftar,btn_remove,btn_blok;
    View myView;
    ProgressDialog loading;
    ApiRegister userService;
    String id;
    String idx,namax,hpx,usernamex,passwordx,alamatx,statusx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.activity_detail_user, container, false);
        nama        = myView.findViewById(R.id.nama);
        no_hp       = myView.findViewById(R.id.no_hp);
        email       = myView.findViewById(R.id.email);
        password    = myView.findViewById(R.id.password);
        alamat      = myView.findViewById(R.id.alamat);
        btn_daftar  = myView.findViewById(R.id.btn_daftar);
        btn_remove  = myView.findViewById(R.id.btn_remove);
        btn_blok  = myView.findViewById(R.id.btn_blok);
        userService = ApiUtils.getUserServices2();
        id = getArguments().getString("id");
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
                registerAction();
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Apakah Anda yakin ingin Menghapus Data Ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                RemoveUser(id);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        btn_blok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        list(id);
        return myView;
    }

    public void list(final String id){
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://www.logiswebmedia.com/archirecon/archirecon-android/detail_user.php?id="+id)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONArray jos = new JSONArray(jsonData);
                    for (int i = 0; i < jos.length(); i++){
                        JSONObject data = jos.getJSONObject(i);
                        loading.dismiss();
                        idx = data.getString("id");
                        namax  = data.getString("nama");
                        hpx  =data.getString("hp");
                        usernamex  =String.valueOf(data.getString("username"));
                        passwordx  =data.getString("password");
                        alamatx  =data.getString("alamat");
                        statusx = data.getString("status");
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
                        nama.setText(namax);
                        no_hp.setText(hpx);
                        email.setText(usernamex);
                        password.setText(passwordx);
                        alamat.setText(alamatx);
                        if(statusx.equals("active")){
                            btn_blok.setText("Blokir Akun Ini");
                            btn_blok.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAbuAbu));
                            btn_blok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    StatusUser(id,"deactived");

                                }
                            });
                        }else{
                            btn_blok.setText("Aktifkan Akun Ini");
                            btn_blok.setBackgroundColor(getActivity().getResources().getColor(R.color.colorGreen));
                            btn_blok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    StatusUser(id,"active");
                                }
                            });
                        }

            }

        };
        task.execute(0);
    }

    public void registerAction() {
        final String namax = nama.getText().toString();
        final String nohpx = no_hp.getText().toString();
        final String emailx = email.getText().toString();
        final String passwordx = password.getText().toString();
        final String alamatx = alamat.getText().toString();

        if (namax.equals("") || nohpx.equals("") || emailx.equals("") || passwordx.equals("") || alamatx.equals("")) {
            loading.dismiss();
            Toast.makeText(getActivity(), "Tidak boleh ada data yang kosong", Toast.LENGTH_LONG).show();
        } else {
            Call<Register> call = userService.EditUserRequest(idx, namax, nohpx, emailx, passwordx, alamatx);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        Register data = response.body();
                        if (data.getMessage().equals("success")) {
                            Fragment fragment = null;
                            fragment = new FragmentListUser();
                            if (fragment != null) {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.commit();
                            }
                            Toast.makeText(getActivity(), "Akun Anda berhasil di Rubah", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Email telah terdaftar", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


        public void RemoveUser(String id){
            Call<Register> call = userService.RemoveRequest(id);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if(response.isSuccessful()){
                        loading.dismiss();
                        Register data = response.body();
                        if(data.getSuccess().equals("success")){
                            Fragment fragment = null;
                            fragment = new FragmentListUser();
                            if (fragment != null) {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.commit();
                            }
                            Toast.makeText(getActivity(), "Akun  berhasil di Hapus", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Email telah terdaftar", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    public void StatusUser(String id,String status){
        Call<Register> call = userService.EditStatusRequest(id,status);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    Register data = response.body();
                    if(data.getMessage().equals("success")){
                        Fragment fragment = null;
                        fragment = new FragmentListUser();
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                        }
                        Toast.makeText(getActivity(), "Status Akun  berhasil di Perbarui", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), "Email telah terdaftar", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }









    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Detail User");
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
                    fragment = new FragmentListUser();
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
