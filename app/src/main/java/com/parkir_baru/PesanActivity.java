package com.parkir_baru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PesanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText plat, lantai, tglcalender, jammasuk, jamkeluar;
    private ImageView calender, masuk, keluar;
    private Button btnSimpan;
    private Spinner sp1;
    private String nama;
    ArrayList<String> listmall = new ArrayList<>();
    ArrayAdapter<String> mallAdapter;
    RequestQueue requestQueue;
    String url = ServerURL.url+"spinmall.php";
    int thour, tminute;
    final Calendar cal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            tampiltgl();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        plat = findViewById(R.id.plat);
        lantai = findViewById(R.id.lantai);
        sp1 = findViewById(R.id.sp1);
        calender = findViewById(R.id.calender);
        masuk = findViewById(R.id.masuk);
        keluar = findViewById(R.id.keluar);
        jammasuk = findViewById(R.id.jammasuk);
        jamkeluar = findViewById(R.id.jamkeluar);
        btnSimpan = findViewById(R.id.btnSimpan);
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("mall");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String namaMall = jsonObject.getString("nama_mall");
                        listmall.add(namaMall);
                        mallAdapter = new ArrayAdapter<>(PesanActivity.this,
                                android.R.layout.simple_spinner_item, listmall);
                        mallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(mallAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        sp1.setOnItemSelectedListener(this);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PesanActivity.this,date,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tglcalender = findViewById(R.id.tglcalender);
        masuk.setOnClickListener(new View.OnClickListener() { //nyimpen jam masuk
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                thour = c.get(Calendar.HOUR_OF_DAY);
                tminute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(PesanActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jammasuk.setText(hourOfDay + ":"+minute);
                    }
                },thour,tminute,true );
                timePickerDialog.show();

            }
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ca = Calendar.getInstance();
                thour = ca.get(Calendar.HOUR_OF_DAY);
                tminute = ca.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(PesanActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jamkeluar.setText(hourOfDay + ":"+minute);
                    }
                },thour,tminute,true);
                timePickerDialog.show();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {//ini button simpan ke database
            @Override
            public void onClick(View v) {
                simpandata(ServerURL.url+"simpant.php?mode=simpan");

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
                    Toast.makeText(PesanActivity.this, status, Toast.LENGTH_LONG).show();
                    if(status.equals("Data tersimpan")){
                        Intent intent = new Intent(PesanActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(PesanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PesanActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_mall", nama);
                params.put("plat", plat.getText().toString());
                params.put("lantai", lantai.getText().toString());

                params.put("tanggal", tglcalender.getText().toString());
                params.put("jammasuk", jammasuk.getText().toString());
                params.put("jamkeluar", jamkeluar.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(PesanActivity.this).add(stringRequest);
    }

    private void tampiltgl(){ //ini buat tampil tanggal
        String tmp = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(tmp, Locale.getDefault());
        tglcalender.setText(sdf.format(cal.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        nama = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}