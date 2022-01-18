package com.parkir_baru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PesanActivity extends AppCompatActivity {
    private EditText nama_mall, plat, lantai, hari, tglcalender, jammasuk, jamkeluar;
    private ImageView calender, masuk, keluar;
    private Button btnSimpan;
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

        nama_mall = findViewById(R.id.nama_mall);
        plat = findViewById(R.id.plat);
        lantai = findViewById(R.id.lantai);
        hari = findViewById(R.id.hari);
        calender = findViewById(R.id.calender);
        masuk = findViewById(R.id.masuk);
        keluar = findViewById(R.id.keluar);
        jammasuk = findViewById(R.id.jammasuk);
        jamkeluar = findViewById(R.id.jamkeluar);
        btnSimpan = findViewById(R.id.btnSimpan);
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
                params.put("nama_mall", nama_mall.getText().toString());
                params.put("plat", plat.getText().toString());
                params.put("lantai", lantai.getText().toString());
                params.put("hari", hari.getText().toString());
                params.put("tanggal", tglcalender.getText().toString());
                params.put("jammasuk", jammasuk.getText().toString());
                params.put("jamkeluar", jamkeluar.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(PesanActivity.this).add(stringRequest);
    }

    private void tampiltgl(){ //ini buat tampil tanggal
        String tmp = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(tmp, Locale.getDefault());
        tglcalender.setText(sdf.format(cal.getTime()));
    }

}