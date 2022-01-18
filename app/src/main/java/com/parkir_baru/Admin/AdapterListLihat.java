package com.parkir_baru.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.parkir_baru.R;
import com.parkir_baru.ServerURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterListLihat extends RecyclerView.Adapter<AdapterListLihat.ViewHolder> {
    Context contex;
    ArrayList<HashMap<String, String>> list_data;
    String url = ServerURL.url+"simpanadmin.php?mode=delete";
    String urlGambar = ServerURL.url+"gambar/";
    public AdapterListLihat(LihatActivity mainActivity, ArrayList<HashMap<String, String>> list_data){
        this.contex = mainActivity;
        this.list_data = list_data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_lihat, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_nama_mall.setText(list_data.get(position).get("nama_mall"));
        holder.tv_kapasitas.setText(list_data.get(position).get("kapasitas"));
        holder.tv_alamat.setText(list_data.get(position).get("alamat"));
        Glide.with(holder.itemView.getContext())
                .load(urlGambar+list_data.get(position).get("gambarmall"))
                .into(holder.ivGambarMall);
        //di klik bisa hapus data
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contex);
                builder.setTitle("Delete Mall");
                builder.setMessage("Confirm to delete ?");
                builder.setNegativeButton("CANCEL ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsoonRootObject = new JSONObject(response);
                                    JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String status = jsonObject.optString("status").trim();
                                    if(status.equals("Data berhasil dihapus")){
                                        Toast.makeText(contex, "Delete Sukses", Toast.LENGTH_LONG).show();
                                        v.getContext().startActivity(new Intent(contex,AdminActivity.class));

                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(contex, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("nama_mall", list_data.get(position).get("nama_mall"));
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(contex);
                        requestQueue.add(stringRequest);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });//batas akiir holder tv_delete
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tiru file yg lama
                Intent pindah = new Intent (contex, EditActivity.class);
                pindah.putExtra("tv_nama_mall", list_data.get(position).get("nama_mall"));
                pindah.putExtra("tv_kapasitas", list_data.get(position).get("kapasitas"));
                pindah.putExtra("tv_alamat", list_data.get(position).get("alamat"));
                contex.startActivity(pindah);
            }
        });
    }//on bind view holder

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama_mall;
        TextView tv_kapasitas;
        TextView tv_alamat;
        ImageView ivGambarMall;
        ImageButton tv_delete;
        Button tv_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_mall = itemView.findViewById(R.id.tv_nama_mall);
            tv_kapasitas = itemView.findViewById(R.id.tv_kapasitas);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            ivGambarMall = itemView.findViewById(R.id.ivGambarMall);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_edit = itemView.findViewById(R.id.tv_edit);
        }
    }

}
