package com.parkir_baru.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parkir_baru.PesanActivity;
import com.parkir_baru.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterListLaporan extends RecyclerView.Adapter<AdapterListLaporan.ViewHolder> {
    Context contex;
    ArrayList<HashMap<String, String>> list_data;
    public AdapterListLaporan(LaporanActivity mainActivity, ArrayList<HashMap<String, String>> list_data){
        this.contex = mainActivity;
        this.list_data = list_data;
    }

    @NonNull
    @Override
    public AdapterListLaporan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_laporan, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListLaporan.ViewHolder holder, int position) {
        holder.tv_nama_mall.setText(list_data.get(position).get("nama_mall"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(v.getContext(), SpinnerTunjActivity.class);
                pindah.putExtra("nama", holder.tv_nama_mall.getText());//ini lempar nama mall
                v.getContext().startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nama_mall;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_mall = itemView.findViewById(R.id.tv_nama_mall);
        }
    }
}
