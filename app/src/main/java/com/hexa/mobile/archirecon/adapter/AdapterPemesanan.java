package com.hexa.mobile.archirecon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hexa.mobile.archirecon.item.pemesanan_item;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.item.pemesanan_item;
import com.hexa.mobile.archirecon.item.user_item;

import java.util.List;

public class AdapterPemesanan extends RecyclerView.Adapter<AdapterPemesanan.MyViewHolder> {

    private Context mContext;
    private List<pemesanan_item> albumList;
    int selected_position = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id,nama,no_hp,email,alamat,perusahaan;


        public MyViewHolder(View view) {
            super(view);
            nama = (TextView) view.findViewById(R.id.nama);
            no_hp = (TextView) view.findViewById(R.id.no_hp);
            email = (TextView) view.findViewById(R.id.email);
            alamat = (TextView) view.findViewById(R.id.alamat);
            perusahaan = (TextView) view.findViewById(R.id.perusahaan);

        }
    }

    public AdapterPemesanan(Context mContext, List<pemesanan_item> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_pemesanan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterPemesanan.MyViewHolder holder, final int position) {
        pemesanan_item album = albumList.get(position);
        holder.nama.setText(album.getNama());
        holder.no_hp.setText(album.getNo_hp());
        holder.email.setText(album.getEmail());
        holder.alamat.setText(album.getAlamat());
        holder.perusahaan.setText(album.getPerusahaan());
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








