package com.hexa.mobile.archirecon.navigation.fragment.user.detail;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentBerita;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.navigation.fragment.admin.FragmentListUser;
import com.hexa.mobile.archirecon.navigation.fragment.user.FragmentBerita;
import com.hexa.mobile.archirecon.tools.URLImageParser;


public class DetailBerita extends Fragment {


    View myView;
    ImageView image_view;
    TextView text_title;
    WebView text_berita;
    ProgressDialog loading;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        myView = inflater.inflate(R.layout.activity_detailberita, container, false);
//        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        image_view = myView.findViewById(R.id.image_view);
        text_berita = myView.findViewById(R.id.text_berita);
        text_title = myView.findViewById(R.id.text_title);
        String id = getArguments().getString("id");
        String content = getArguments().getString("content");
        Spanned spanned = Html.fromHtml(content);
        String title = getArguments().getString("title");
        String image = getArguments().getString("image");
        text_title.setText(title);
//        text_berita.setText(Html.fromHtml(content, new URLImageParser(text_berita, getActivity()), null));
        text_berita.loadDataWithBaseURL("file:///android_res/drawable/", content, "text/html", "UTF-8", null);
        Glide.with(getActivity())
                .load(image)
                .into(image_view);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Artikel");
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
