package com.parkir_baru;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.parkir_baru.ServerURL.url;

public class RegisterActivity extends AppCompatActivity {
    private EditText nama, alamat, nomer_telepon, jenis_kelamin, username, password;
    private Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        nomer_telepon = findViewById(R.id.nomer_telepon);
        jenis_kelamin = findViewById(R.id.jenis_kelamin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnRegist = findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 
                Regist(ServerURL.url+"register.php?mode=simpan");
            }
        });
    } // ini oncrate bundle
    private void Regist(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsoonRootObject = new JSONObject(response);
                            JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String status = jsonObject.optString("status").trim();
                            Toast.makeText(RegisterActivity.this, status, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);

                        }catch (Exception e){
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama.getText().toString());
                params.put("alamat", alamat.getText().toString());
                params.put("nomer_telepon", nomer_telepon.getText().toString());
                params.put("jenis_kelamin", jenis_kelamin.getText().toString());
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }//
}