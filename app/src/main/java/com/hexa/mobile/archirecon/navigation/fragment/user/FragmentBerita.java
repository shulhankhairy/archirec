package com.hexa.mobile.archirecon.navigation.fragment.user;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hexa.mobile.archirecon.adapter.AdapterBerita;
import com.hexa.mobile.archirecon.item.berita_item;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewClickListener;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewTouchListener;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.activity.LoginActivity;
import com.hexa.mobile.archirecon.adapter.AdapterBerita;
import com.hexa.mobile.archirecon.item.berita_item;
import com.hexa.mobile.archirecon.navigation.Navigationdrawer;
import com.hexa.mobile.archirecon.navigation.fragment.admin.detail.DetailPesanan;
import com.hexa.mobile.archirecon.navigation.fragment.admin.detail.DetailUser;
import com.hexa.mobile.archirecon.navigation.fragment.user.detail.DetailBerita;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewClickListener;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class FragmentBerita extends Fragment {

    View myView;
    RecyclerView recy;
    private ArrayList<berita_item> list_berita;
    private AdapterBerita adapter_list;
    ProgressDialog loading;
    String link;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.activity_fragmentberita, container, false);
        recy = (RecyclerView) myView.findViewById(R.id.recy);
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        recyler_jenis();
        Check();
        return myView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Archirecon");
    }

    void recyler_jenis() {
        list_berita = new ArrayList<>();
        adapter_list = new AdapterBerita(getActivity(), list_berita);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recy.setLayoutManager(mLayoutManager);
        recy.setHasFixedSize(true);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter_list);
        listBerita();
        recy.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recy, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                String id = list_berita.get(position).getId();
                String content = list_berita.get(position).getContent();
                String title = list_berita.get(position).getTitle();
                String image = list_berita.get(position).getImage_link();
                DetailBerita fragment = new DetailBerita();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("content", content);
                bundle.putString("title", title);
                bundle.putString("image", image);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                //                Toast.makeText(getApplicationContext(), jenis_item.get(position).getId() + " Rate This Film", Toast.LENGTH_SHORT).show();

            }
        }));
        adapter_list.notifyDataSetChanged();
    }

    void listBerita() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("https://www.googleapis.com/blogger/v3/blogs/7148201742435900946/posts?key=AIzaSyBnc7hZDJ27ljiQEfyTntHBySaITecpNGc")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONObject jo = new JSONObject(jsonData);
                    JSONArray Jarray = jo.getJSONArray("items");
                    loading.dismiss();
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);

//                        Spanned spanned = Html.fromHtml(object.getString("content"));
                        link = object.getString("content");
                        berita_item data = new berita_item(object.getString("id"), object.getString("title"), object.getString("content"),extrack(link));
                        list_berita.add(data);
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
                adapter_list.notifyDataSetChanged();
//                Toast.makeText(getActivity(), extrack(link), Toast.LENGTH_LONG).show();


            }

        };
        task.execute(0);

    }
    void Check(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Toast.makeText(getActivity(), "Permission Already", Toast.LENGTH_LONG).show();

            } else {
                requestPermission();
            }
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getActivity(), "This permission is required to view and upload the photos stored on your device.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(),
                            "Permission accepted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),
                            "Permission denied", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public String extrack(String url_a) {
        String text = url_a;
        String imgRegex = "<[iI][mM][gG][^>]+[sS][rR][cC]\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        String imgSrc = "";
        Pattern p = Pattern.compile(imgRegex);
        Matcher m = p.matcher(text);

        if (m.find()) {
            imgSrc = m.group(1);
        }
        return imgSrc;
    }
}

