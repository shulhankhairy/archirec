package com.hexa.mobile.archirecon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.item.berita_item;

import java.util.List;

public class AdapterBerita  extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {

    private Context mContext;
    private List<berita_item> albumList;
    int selected_position = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,content;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            image = (ImageView) view.findViewById(R.id.image);

        }
    }

    public AdapterBerita(Context mContext, List<berita_item> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_berita, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        berita_item album = albumList.get(position);
        holder.title.setText(album.getTitle());
        Spanned spanned = Html.fromHtml(album.getContent());
        holder.content.setText(spanned.toString().substring(0,50).replace("\n", "").replace("\r", "")+".....");
        Glide.with(mContext)
                .load(album.getImage_link())
                .into(holder.image);

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */

    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }



}






