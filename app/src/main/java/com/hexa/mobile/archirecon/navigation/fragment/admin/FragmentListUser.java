package com.hexa.mobile.archirecon.navigation.fragment.admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexa.mobile.archirecon.adapter.AdapterUser;
import com.hexa.mobile.archirecon.item.user_item;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentBerita;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewClickListener;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewTouchListener;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.adapter.AdapterBerita;
import com.hexa.mobile.archirecon.adapter.AdapterUser;
import com.hexa.mobile.archirecon.item.berita_item;
import com.hexa.mobile.archirecon.item.user_item;
import com.hexa.mobile.archirecon.navigation.fragment.admin.detail.DetailUser;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentBerita;
import com.hexa.mobile.archirecon.navigation.fragment.user.detail.DetailBerita;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewClickListener;
import com.hexa.mobile.archirecon.tools.other.RecyclerViewTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class FragmentListUser extends Fragment {


    View myView;
    RecyclerView recy;
    private ArrayList<user_item> list_user;
    private AdapterUser adapter_list;
    ProgressDialog loading;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.fragment_user, container, false);
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        recy = (RecyclerView) myView.findViewById(R.id.recy);
        recyler_jenis();
        return myView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Data User");
    }

    void recyler_jenis(){
        list_user = new ArrayList<>();
        adapter_list = new AdapterUser(getActivity(), list_user);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recy.setLayoutManager(mLayoutManager);
        recy.setHasFixedSize(true);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter_list);
        listUser();

        recy.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recy, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String id = list_user.get(position).getId();
                DetailUser fragment = new DetailUser();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
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

    void listUser(){
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://www.logiswebmedia.com/archirecon/archirecon-android/list_user.php")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONArray jos = new JSONArray(jsonData);
                    loading.dismiss();
                    for (int i = 0; i < jos.length(); i++){
                        JSONObject jo = jos.getJSONObject(i);
                        user_item data = new user_item(jo.getString("id"),jo.getString("nama"),jo.getString("hp"),jo.getString("username"),jo.getString("alamat"));
                        list_user.add(data);
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

            }

        };
        task.execute(0);


    }
}
