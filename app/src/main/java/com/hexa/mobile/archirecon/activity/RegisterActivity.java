package com.hexa.mobile.archirecon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.network.ApiRegister;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText nama,no_hp,email,password,alamat;
    Button btn_daftar;
    ProgressDialog loading;
    ApiRegister userService;
    String namaStr, nohpStr, emailStr, pswdStr, alamatStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nama        = findViewById(R.id.nama);
        no_hp       = findViewById(R.id.no_hp);
        email       = findViewById(R.id.email);
        password    = findViewById(R.id.password);
        alamat      = findViewById(R.id.alamat);
        btn_daftar  = findViewById(R.id.btn_daftar);
        userService = ApiUtils.getUserServices2();
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(RegisterActivity.this, null, "Harap Tunggu...", true, false);
                registerAction();
            }
        });
    }

    public void registerAction(){
        namaStr = nama.getText().toString();
        nohpStr = no_hp.getText().toString();
        emailStr = email.getText().toString();
        pswdStr = password.getText().toString();
        alamatStr = alamat.getText().toString();

        if(isEmpty(namaStr)||isEmpty(nohpStr)||!isEmail(emailStr)||isEmpty(pswdStr)||isEmpty(alamatStr)){
            loading.dismiss();
            Toast.makeText(RegisterActivity.this, "Cek kembali data yang anda isikan", Toast.LENGTH_LONG).show();
        }else{
            if(pswdStr.trim().length()<6){
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, "Password minimal 6 karakter", Toast.LENGTH_LONG).show();
            }else{
                Call<Register> call = userService.registerRequest(namaStr, nohpStr, emailStr, pswdStr, alamatStr);
                call.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if(response.isSuccessful()){
                            loading.dismiss();
                            Register data = response.body();
                            if(data.getMessage().equals("success")){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "Akun Anda berhasil dibuat", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Email telah terdaftar", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public boolean isEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isEmpty(String input){
        return (TextUtils.isEmpty(input));
    }
}
