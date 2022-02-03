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

public class TunjunganActivity extends AppCompatActivity { //ini untuk nampil nama bln + mall, u/laporanyya
    private RecyclerView lvtunj;
    private TextView jdl;
    private String nama;
    private String bulan;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunjungan);  //   ADAPTER LISTTUNJ ADA DIBAWAH

        jdl = findViewById(R.id.jdl);
        //tangkep
        nama = getIntent().getStringExtra("nama");
        bulan = getIntent().getStringExtra("bulan");

        if(bulan.equals("01")){
            jdl.setText("DATA TRANSAKSI PER BULAN JANUARI");
        }else if(bulan.equals("02"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN PEBRUARI");
        }else if(bulan.equals("03"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN MARET");
        }else if(bulan.equals("04"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN APRIL");
        }else if(bulan.equals("05"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN MEI");
        }else if(bulan.equals("06"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN JUNI");
        }else if(bulan.equals("07"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN JULI");
        }else if(bulan.equals("08"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN AGUSTUS");
        }else if(bulan.equals("09"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN SEPTEMBER");
        }else if(bulan.equals("10"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN OKTOBER");
        }else if(bulan.equals("11"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN NOVEMBER");
        }else if(bulan.equals("12"))
        {
            jdl.setText("DATA TRANSAKSI PER BULAN DESEMBER");
        }
        //mulai rv
        String url = ServerURL.url+ "simpanadmin.php?mode=bacatunj";
        lvtunj = findViewById(R.id.lvtunj);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvtunj.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(TunjunganActivity.this);
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

                        map.put("tanggal", json.getString("tanggal"));
                        map.put("jammasuk", json.getString("jammasuk"));
                        map.put("jamkeluar", json.getString("jamkeluar"));
                        map.put("harga", json.getString("harga"));
                        list_data.add(map);
                        AdapterListTunj adapter = new AdapterListTunj(TunjunganActivity.this, list_data);
                        lvtunj.setAdapter(adapter);
                    }
                }catch (Exception e){
                    Toast.makeText(TunjunganActivity.this, "aaa", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TunjunganActivity.this, "bbb", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama",nama);
                params.put("bulan", bulan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//ini oncrate bundle

    public static class AdapterListTunj extends RecyclerView.Adapter<AdapterListTunj.ViewHolder> {
        Context contex;
        ArrayList<HashMap<String, String>> list_data;
        public AdapterListTunj(TunjunganActivity mainActivity, ArrayList<HashMap<String, String>> list_data){
            this.contex = mainActivity;
            this.list_data = list_data;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tunjungan, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv_plat.setText(list_data.get(position).get("plat"));
            holder.tv_lantai.setText(list_data.get(position).get("lantai"));

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

            TextView tv_tanggal;
            TextView tv_jammasuk;
            TextView tv_jamkeluar;
            TextView tv_harga;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_plat = itemView.findViewById(R.id.tv_plat);
                tv_lantai = itemView.findViewById(R.id.tv_lantai);

                tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
                tv_jammasuk = itemView.findViewById(R.id.tv_jammasuk);
                tv_jamkeluar = itemView.findViewById(R.id.tv_jamkeluar);
                tv_harga = itemView.findViewById(R.id.tv_harga);
            }
        }
    }
}