package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parkir_baru.R;
import com.parkir_baru.ServerURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private Button btnEdit;
    private EditText et_kapasitas, et_alamat;
    private String nama, kap, alt; //sama kayak projek lama (editactivity)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nama = getIntent().getStringExtra("tv_nama_mall");
        kap = getIntent().getStringExtra("tv_kapasitas");
        alt = getIntent().getStringExtra("tv_alamat");
        et_alamat = findViewById(R.id.et_alamat);
        et_kapasitas = findViewById(R.id.et_kapasitas);
        et_alamat.setText(alt);
        et_kapasitas.setText(kap);
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editmall(ServerURL.url+"simpanadmin.php?mode=edit");

            }
        });
    }// ini oncreate bundle

    //====update data / edit data====
    private void editmall(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsoonRootObject = new JSONObject(response);
                    JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String status = jsonObject.optString("status").trim();
                    Toast.makeText(EditActivity.this, status, Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(), LihatActivity.class);
                        startActivity(i);
                        finish();


                }catch (Exception e){
                    Toast.makeText(EditActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kapasitas", et_kapasitas.getText().toString());
                params.put("alamat", et_alamat.getText().toString());
                params.put("nama_mall", nama); //nama dari brs 29
                return params;
            }
        };
        Volley.newRequestQueue(EditActivity.this).add(stringRequest);
    }
}