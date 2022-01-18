package com.parkir_baru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parkir_baru.Admin.AdapterListLihat;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterListMacam extends RecyclerView.Adapter<AdapterListMacam.ViewHolder> {
    Context contex;
    ArrayList<HashMap<String, String>> list_data;
    String urlGambarPlgn = "http://192.168.100.7/barupa/gambar/";
    public AdapterListMacam(MallActivity mallActivity, ArrayList<HashMap<String, String>> list_data) {
        this.contex = mallActivity;
        this.list_data = list_data;
    }

    @NonNull
    @Override
    public AdapterListMacam.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_macam_mall, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListMacam.ViewHolder holder, int position) {
        holder.tv_nama_mall.setText(list_data.get(position).get("nama_mall"));
        holder.tv_kapasitas.setText(list_data.get(position).get("kapasitas"));
        holder.tv_alamat.setText(list_data.get(position).get("alamat"));
        Glide.with(holder.itemView.getContext())
                .load(urlGambarPlgn+list_data.get(position).get("gambarmall"))
                .into(holder.gambarMallPlgn);
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama_mall;
        TextView tv_alamat;
        TextView tv_kapasitas;
        ImageView gambarMallPlgn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_mall = itemView.findViewById(R.id.tv_nama_mall);
            tv_kapasitas = itemView.findViewById(R.id.tv_kapasitas);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            gambarMallPlgn = itemView.findViewById(R.id.gambarMallPlgn);
        }
    }
}
