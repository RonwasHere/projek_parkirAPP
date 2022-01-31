package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.parkir_baru.PesanActivity;
import com.parkir_baru.R;
import com.parkir_baru.ServerURL;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HariActivity extends AppCompatActivity {

    private String nama;
    private TextView tvtampil;
    private Button btngo;
    private ImageView calender, calender2;
    private long vara, varb; //unutk pengecekan tanggal awal + akir
    private TextView tglawal, tglakir;  //tglawal & tglakir ==> hrs dilempar ke php u/query
                                        //ini ada nilainya jadi bisa dilempar (lihar brs 103, 104)
    final Calendar cal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            tampiltgl();
        }

        private void tampiltgl() {
            String tmp = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(tmp, Locale.getDefault());
            tglawal.setText(sdf.format(cal.getTime()));
            try {//ini untuk pengecekan tgl awal, agar tgl awal tidak lebih bsr dr tgl akir
                vara = sdf.parse(tglawal.getText().toString()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };//end date picker calender


    final Calendar cale2 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cale2.set(Calendar.YEAR,year);
            cale2.set(Calendar.MONTH,month);
            cale2.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            tampiltgl2();
        }
    };

    private void tampiltgl2() {
        String tmp = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(tmp, Locale.getDefault());
        tglakir.setText(sdf.format(cale2.getTime()));
        try { ///ini untuk pengecekan tgl awal, agar tgl awal tidak lebih bsr dr tgl akir
            varb = sdf.parse(tglakir.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hari);
        tglawal = findViewById(R.id.tglawal);
        tglakir = findViewById(R.id.tglakir);
        btngo = findViewById(R.id.btngo);
        calender = findViewById(R.id.calender);
        calender2 = findViewById(R.id.calender2);
        //tangkep (nama) dari spinnertunj activity
        nama = getIntent().getStringExtra("nama");
        tvtampil = findViewById(R.id.tvtampil);
        tvtampil.setText(nama);//ngeset jadi nama mall, begitu dirun muncul mall tunjungan


        //tgl awal
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HariActivity.this,date,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //tgl akir
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HariActivity.this,date2,
                        cale2.get(Calendar.YEAR),
                        cale2.get(Calendar.MONTH),
                        cale2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //button go, lempar nama mall, tgl awal, tgl akir, lempar ke php
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vara > varb){
                    Toast.makeText(HariActivity.this, "tgl awal tidak boleh lebih dari tgl akir", Toast.LENGTH_LONG).show();
                }else {
                    //kalok tgl awal kecil, brati muncul
                    Intent i = new Intent(HariActivity.this, DetailActivity.class);
                    i.putExtra("nama", nama); //ini lempar nama mall lagi, buat proses php
                    i.putExtra("tanggalawal", tglawal.getText().toString()); //lempar tanggal awal ke php + ke detail activity
                    i.putExtra("tanggalakir", tglakir.getText().toString()); //lempar tanggal akir ke php + ke detail activity
                    //"name" yg lempar dan tangkep hrs sama
                    startActivity(i);
                }
            }
        });
    }//ini Oncrate Bundle
}