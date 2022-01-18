package com.parkir_baru.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parkir_baru.HomeActivity;
import com.parkir_baru.PesanActivity;
import com.parkir_baru.R;
import com.parkir_baru.ServerURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpanActivity extends AppCompatActivity {
    private EditText nama_mall, kapasitas, alamat;
    private Button btnSimpan, pilihGambar;
    private ImageView gambarMall;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan);
        nama_mall = findViewById(R.id.nama_mall);
        kapasitas = findViewById(R.id.kapasitas);
        alamat = findViewById(R.id.alamat);
        pilihGambar = findViewById(R.id.pilihGambar);
        gambarMall = findViewById(R.id.gambarMall);

        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilGambar();
            }
        });


        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {//button simpan ke db mall
            @Override
            public void onClick(View v) {
                simpandata(ServerURL.url+"simpanadmin.php?mode=simpan");
            }
        });
    }//ini oncrate bundle

    private void simpandata(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsoonRootObject = new JSONObject(response);
                    JSONArray jsonArray = jsoonRootObject.optJSONArray("result");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String status = jsonObject.optString("status").trim();
                    Toast.makeText(SimpanActivity.this, status, Toast.LENGTH_LONG).show();
                    if(status.equals("Data tersimpan")){ //jika sdh simpan, balike ke menu admin
                        Intent intent = new Intent(SimpanActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(SimpanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SimpanActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_mall", nama_mall.getText().toString());
                params.put("alamat", alamat.getText().toString());
                params.put("kapasitas", kapasitas.getText().toString());
                params.put("gambarmall", getStringImage(bitmap));
                return params;
            }
        };
        Volley.newRequestQueue(SimpanActivity.this).add(stringRequest);

    }

    private void ambilGambar()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                gambarMall.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }
}