package com.hexa.mobile.archirecon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.network.ApiVerifikasi;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.network.ApiVerifikasi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    EditText kode;
    Button verifikasi;
    ProgressDialog loading;
    ApiVerifikasi userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        kode        = findViewById(R.id.kode);
        verifikasi  = findViewById(R.id.btn_daftar);
        userService = ApiUtils.getUserServices7();

        final String ids = getIntent().getStringExtra("id");

        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(VerificationActivity.this, null, "Harap Tunggu...", true, false);
                verication(ids);
            }
        });
    }

    public void verication(final String ids){
        final String kodex      = kode.getText().toString();
        if(kodex.equals("")){
            loading.dismiss();
            Toast.makeText(VerificationActivity.this, "Masukkan kode verifikasi Anda", Toast.LENGTH_LONG).show();
        }else{
            Call<Register> call = userService.verifikasiRequest(ids,kodex);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if(response.isSuccessful()){
                        Register data = response.body();
                        if(data.getMessage().equals("success")){
                            Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(
                                    VerificationActivity.this, "Akun anda berhasil dibuat", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(
                                    VerificationActivity.this, "Kode yang Anda masukkan Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(VerificationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
