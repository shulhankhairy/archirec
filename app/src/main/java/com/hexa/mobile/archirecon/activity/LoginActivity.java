package com.hexa.mobile.archirecon.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.model.Login;
import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.network.ApiLogin;
import com.hexa.mobile.archirecon.network.ApiSendEmail;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.model.Login;
import com.hexa.mobile.archirecon.model.Register;
import com.hexa.mobile.archirecon.navigation.Navigationdrawer;
import com.hexa.mobile.archirecon.network.ApiLogin;
import com.hexa.mobile.archirecon.network.ApiSendEmail;
import com.hexa.mobile.archirecon.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView daftar;
    Button btn_login;
    EditText username,password;
    private boolean loggedIn = false;
    Context mContext;
    ProgressDialog loading;
    ApiLogin userService;
    ApiSendEmail sendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        daftar      = findViewById(R.id.daftar);
        btn_login   = findViewById(R.id.btn_login);
        username    = findViewById(R.id.username);
        password    = findViewById(R.id.password);
        userService = ApiUtils.getUserServices();
        sendService = ApiUtils.getUserServices6();

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(LoginActivity.this, null, "Harap Tunggu...", true, false);
                action_login();
                /*Intent intent = new Intent(LoginActivity.this, Navigationdrawer.class);
                startActivity(intent);*/
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        String userid = sharedPreferences.getString(Config.KODE_SHARED_PREF, "");
        //Toast.makeText(LoginActivity.this,userid , Toast.LENGTH_LONG).show();

        if(loggedIn){

            Intent intent = new Intent(LoginActivity.this, Navigationdrawer.class);
            String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
            intent.putExtra("username",email);
            startActivity(intent);
        }
    }

    public boolean isEmail(String email, String password){
        return (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void action_login() {
        final String usernamex = username.getText().toString();
        final String passwordx = password.getText().toString();

        if(isEmail(usernamex,passwordx)){

            Call<Login> call = userService.loginRequest(usernamex,passwordx);
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if(response.isSuccessful()){
                        loading.dismiss();
                        Login data = response.body();
                        if(data.getMessage().equals("success")) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, usernamex);
                            editor.putString(Config.KODE_SHARED_PREF, data.getId());
                            editor.putString(Config.ALAMAT_SHARED_PREF, data.getAlamat());
                            editor.putString(Config.HP_SHARED_PREF, data.getHp());
                            editor.putString(Config.ROLE_SHARED_PREF, data.getRole());
                            editor.putString(Config.NAMA_SHARED_PREF, data.getNama());
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, Navigationdrawer.class);
                            startActivity(intent);
                        }else if(data.getMessage().equals("verifikasi")){
                            String ids       = data.getId();
                            String kode_user = data.getKode();
                            String namax     = data.getNama();
                            String emailx    = data.getEmail();

                            /*send_email(ids,emailStr,namaStr,kode_user);*/

                            Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                            intent.putExtra("id", ids);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Username dan Password yang Anda masukkan salah", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(LoginActivity.this, "Masukkan email dan password", Toast.LENGTH_LONG).show();
        }
    }

    public void send_email(final String ids, final String email, final String nama_user, final String kode_user){

        Call<Register> call = sendService.sendRequest(ids,email,nama_user,kode_user);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                    intent.putExtra("id", ids);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
