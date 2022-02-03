package com.parkir_baru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterViewAnimator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView lvtiket;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;


    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, HomeActivity.class));
            }
        });

        String url = ServerURL.url+"simpant.php?mode=baca";
        lvtiket = (RecyclerView)findViewById(R.id.lvtiket);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvtiket.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(HistoryActivity.this);
        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsoonRootObject = new JSONObject(response);
                    JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nama_mall", json.getString("nama_mall"));
                        map.put("plat", json.getString("plat"));
                        map.put("lantai", json.getString("lantai"));
                        map.put("tanggal", json.getString("tanggal"));
                        map.put("jammasuk", json.getString("jammasuk"));
                        map.put("jamkeluar", json.getString("jamkeluar"));
                        //munculin harga tiket
                        map.put("harga", json.getString("harga"));
                        list_data.add(map);
                        AdapterList adapter = new AdapterList(HistoryActivity.this, list_data);
                        lvtiket.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {

        };
        requestQueue.add(stringRequest);


    } //ini oncrate bundle
}