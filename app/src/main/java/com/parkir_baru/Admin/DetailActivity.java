package com.parkir_baru.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parkir_baru.R;
import com.parkir_baru.ServerURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity { //ini u/ tampil laporan per hari
    private RecyclerView lvdetail;
    private String nama; //buat nerima dari hari activity, lalu proses php
    private String tanggalawal; //buat nerima dari hari activity, lalu proses php
    private String tanggalakir; //buat nerima dari hari activity, lalu proses php
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail); //ADAPTER LIST DETAIL ADA DIBAWAH

        //tangkep
        nama = getIntent().getStringExtra("nama");
        tanggalawal = getIntent().getStringExtra("tanggalawal");
        tanggalakir = getIntent().getStringExtra("tanggalakir");
        //mulai RV
        String url = ServerURL.url+ "simpanadmin.php?mode=bacadetail";
        lvdetail = findViewById(R.id.lvtdetail);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvdetail.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(DetailActivity.this);
        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsoonRootObject = new JSONObject(response);
                    JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                    for (int a=0; a < jsonArray.length(); a++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("plat", json.getString("plat"));
                        map.put("lantai", json.getString("lantai"));
                        map.put("hari", json.getString("hari"));
                        map.put("tanggal", json.getString("tanggal"));
                        map.put("jammasuk", json.getString("jammasuk"));
                        map.put("jamkeluar", json.getString("jamkeluar"));
                        map.put("harga", json.getString("harga"));
                        list_data.add(map);
                        AdapterListDetail adapter = new AdapterListDetail(DetailActivity.this, list_data);
                        lvdetail.setAdapter(adapter);
                    }

                }catch (Exception e){
                    Toast.makeText(DetailActivity.this, "aaa", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, "bbb", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama",nama);
                params.put("tanggalawal", tanggalawal);
                params.put("tanggalakir", tanggalakir);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//ini Oncrate Bundle

    //ADAPTER
    public static class AdapterListDetail extends RecyclerView.Adapter<DetailActivity.AdapterListDetail.ViewHolder> {
        Context contex;
        ArrayList<HashMap<String, String>> list_data;
        public AdapterListDetail(DetailActivity mainActivity, ArrayList<HashMap<String, String>> list_data){
            this.contex = mainActivity;
            this.list_data = list_data;
        }
        @NonNull
        @Override
        public DetailActivity.AdapterListDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailActivity.AdapterListDetail.ViewHolder holder, int position) {
            holder.tv_plat.setText(list_data.get(position).get("plat"));
            holder.tv_lantai.setText(list_data.get(position).get("lantai"));
            holder.tv_hari.setText(list_data.get(position).get("hari"));
            holder.tv_tanggal.setText(list_data.get(position).get("tanggal"));
            holder.tv_jammasuk.setText(list_data.get(position).get("jammasuk"));
            holder.tv_jamkeluar.setText(list_data.get(position).get("jamkeluar"));
            holder.tv_harga.setText(list_data.get(position).get("harga"));

        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_plat;
            TextView tv_lantai;
            TextView tv_hari;
            TextView tv_tanggal;
            TextView tv_jammasuk;
            TextView tv_jamkeluar;
            TextView tv_harga;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_plat = itemView.findViewById(R.id.tv_plat);
                tv_lantai = itemView.findViewById(R.id.tv_lantai);
                tv_hari = itemView.findViewById(R.id.tv_hari);
                tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
                tv_jammasuk = itemView.findViewById(R.id.tv_jammasuk);
                tv_jamkeluar = itemView.findViewById(R.id.tv_jamkeluar);
                tv_harga = itemView.findViewById(R.id.tv_harga);
            }
        }
    }
}