package com.hexa.mobile.archirecon.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hexa.mobile.archirecon.activity.LoginActivity;
import com.hexa.mobile.archirecon.config.Config;
import com.kosalgeek.android.photoutil.MainActivity;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.activity.LoginActivity;
import com.hexa.mobile.archirecon.config.Config;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentBerita;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentPemesanan;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListPemesanan;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListUser;

public class Navigationdrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name,email;
    SharedPreferences sharedPreferences;
    NavigationView navigationView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String  nama = sharedPreferences.getString(Config.NAMA_SHARED_PREF, "");
        String  emails = sharedPreferences.getString(Config.EMAIL_SHARED_PREF, "");
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View view = navView.getHeaderView(0);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        name.setText("- "+nama +" -");
        email.setText(emails);

        setSupportActionBar(toolbar);

//        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
//            Intent intent = new Intent();
//            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//            startActivity(intent);
//        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_artikel);
        hideItem();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigationdrawer, menu);
        return true;
    }

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String role = sharedPreferences.getString(Config.ROLE_SHARED_PREF, "");
//        Toast.makeText(Navigationdrawer.this, role, Toast.LENGTH_LONG).show();

        // User
        if(role.equals("user")) {
            nav_Menu.findItem(R.id.nav_pesan).setVisible(true);
            nav_Menu.findItem(R.id.nav_artikel).setVisible(true);
            nav_Menu.findItem(R.id.nav_contact).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_list_pemesanan).setVisible(false);
            nav_Menu.findItem(R.id.nav_user).setVisible(false);
            //Admin
        }else {
            nav_Menu.findItem(R.id.nav_pesan).setVisible(false);
            nav_Menu.findItem(R.id.nav_artikel).setVisible(true);
            nav_Menu.findItem(R.id.nav_contact).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_list_pemesanan).setVisible(true);
            nav_Menu.findItem(R.id.nav_user).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_pesan:
                fragment = new FragmentPemesanan();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_contact:
                fragment = new FragmentBerita();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_list_pemesanan:
                fragment = new FragmentListPemesanan();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_user:
                fragment = new FragmentListUser();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_artikel:
                fragment = new FragmentBerita();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logout:
                logout_action();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    private void logout_action(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda yakin ingin keluar?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences preferences = getApplication().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(Config.EMAIL_SHARED_PREF, "");
                        editor.putString(Config.KODE_SHARED_PREF, "");
                        editor.putString(Config.ALAMAT_SHARED_PREF, "");
                        editor.putString(Config.HP_SHARED_PREF, "");
                        editor.putString(Config.NAMA_SHARED_PREF, "");
                        editor.commit();

                        Intent intent = new Intent(Navigationdrawer.this, LoginActivity.class);
                        startActivity(intent);
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





}
