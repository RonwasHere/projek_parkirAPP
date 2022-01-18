package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parkir_baru.BayarActivity;
import com.parkir_baru.DenahActivity;
import com.parkir_baru.HistoryActivity;
import com.parkir_baru.PesanActivity;
import com.parkir_baru.R;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title_view;
    public CardView c1, c2, c3, c4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        title_view = findViewById(R.id.title_view);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.c1://tambah data mall
                i = new Intent(this, SimpanActivity.class);
                startActivity(i);
                break;
            case R.id.c2://Lihat data mall
                i = new Intent(this, LihatActivity.class);
                startActivity(i);
                break;
            case R.id.c3://laporan data mall
                i = new Intent(this, LaporanActivity.class);
                startActivity(i);
                break;
            case R.id.c4: //log out, keluar aplikasi
                finish();
                break;
        }
    }
}