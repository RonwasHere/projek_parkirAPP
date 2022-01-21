package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parkir_baru.PesanActivity;
import com.parkir_baru.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HariActivity extends AppCompatActivity {
    private String nama;
    private String tanggalawal; //ini nantik buat dilempar
    private String tanggalakir;
    private TextView tvtampil;
    private Button btngo;
    private ImageView calender, calender2;
    private EditText tglawal, tglakir;  //tglawal & tglakir ==> hrs dilempar ke php u/query
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
            String tmp = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(tmp, Locale.getDefault());
            tglawal.setText(sdf.format(cal.getTime()));
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
        String tmp = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(tmp, Locale.getDefault());
        tglakir.setText(sdf.format(cale2.getTime()));
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
                Intent i = new Intent(HariActivity.this, DetailActivity.class);
                i.putExtra("nama", nama); //ini lempar nama mall lagi, buat proses php
                i.putExtra("tanggalawal", tanggalawal); //lempar tanggal awal ke php
                i.putExtra("tanggalakir", tanggalakir); //lempar tanggal akir ke php
                startActivity(i);
            }
        });
    }//ini Oncrate Bundle


}