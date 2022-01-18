package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

public class LaporanActivity extends AppCompatActivity {
    private RecyclerView lvlaporan;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        String url = ServerURL.url+ "simpanadmin.php?mode=bacamall"; //belom disesuaikan
        lvlaporan = findViewById(R.id.lvlaporan);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvlaporan.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(LaporanActivity.this);
        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsoonRootObject = new JSONObject(response);
                    JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                    for (int a=0; a < jsonArray.length(); a++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nama_mall", json.getString("nama_mall"));
                        list_data.add(map);
                        AdapterListLaporan adapter = new AdapterListLaporan(LaporanActivity.this, list_data);
                        lvlaporan.setAdapter(adapter);
                    }
                }catch (Exception e){
                    Toast.makeText(LaporanActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LaporanActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {

        };
        requestQueue.add(stringRequest);

    }// ini oncrate bundle
}