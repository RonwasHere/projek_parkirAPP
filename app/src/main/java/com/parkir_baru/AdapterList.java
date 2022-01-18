package com.parkir_baru;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parkir_baru.Admin.AdminActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    Context contex;
    ArrayList<HashMap<String, String>> list_data;
    String url = ServerURL.url+"simpant.php?mode=delete";
    public AdapterList(HistoryActivity historyActivity, ArrayList<HashMap<String, String>> list_data){
        this.contex = historyActivity;
        this.list_data = list_data;
    }


    @Override
    public AdapterList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterList.ViewHolder holder, int position) {
        holder.tv_nama_mall.setText(list_data.get(position).get("nama_mall"));
        holder.tv_plat.setText(list_data.get(position).get("plat"));
        holder.tv_lantai.setText(list_data.get(position).get("lantai"));
        holder.tv_hari.setText(list_data.get(position).get("hari"));
        holder.tv_tanggal.setText(list_data.get(position).get("tanggal"));
        holder.tv_jammasuk.setText(list_data.get(position).get("jammasuk"));
        holder.tv_jamkeluar.setText(list_data.get(position).get("jamkeluar"));
        holder.tv_harga.setText(list_data.get(position).get("harga"));
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contex);
                builder.setTitle("Delete Tiket");
                builder.setMessage("Confirm to delete ?");
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
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
                                        v.getContext().startActivity(new Intent(contex, HomeActivity.class));

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
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nama_mall;
        TextView tv_plat;
        TextView tv_lantai;
        TextView tv_hari;
        TextView tv_tanggal;
        TextView tv_jammasuk;
        TextView tv_jamkeluar;
        TextView tv_harga;
        ImageButton tv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nama_mall = itemView.findViewById(R.id.tv_nama_mall);
            tv_plat = itemView.findViewById(R.id.tv_plat);
            tv_lantai = itemView.findViewById(R.id.tv_lantai);
            tv_hari = itemView.findViewById(R.id.tv_hari);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_jammasuk = itemView.findViewById(R.id.tv_jammasuk);
            tv_jamkeluar = itemView.findViewById(R.id.tv_jamkeluar);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
