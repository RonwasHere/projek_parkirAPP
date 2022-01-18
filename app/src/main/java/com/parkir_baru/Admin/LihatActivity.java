package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
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

public class LihatActivity extends AppCompatActivity {
    private RecyclerView lvtiketlihat;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);

        String url = ServerURL.url+ "simpanadmin.php?mode=baca";
        lvtiketlihat = findViewById(R.id.lvtiketlihat);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvtiketlihat.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(LihatActivity.this);
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
                        map.put("kapasitas", json.getString("kapasitas"));
                        map.put("alamat", json.getString("alamat"));
                        map.put("gambarmall", json.getString("gambarmall"));
                        list_data.add(map);
                        AdapterListLihat adapter = new AdapterListLihat(LihatActivity.this, list_data);
                        lvtiketlihat.setAdapter(adapter);
                    }
                }catch (Exception e){
                    Toast.makeText(LihatActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LihatActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {

        };
        requestQueue.add(stringRequest);

    }//ini oncreate bundle
}