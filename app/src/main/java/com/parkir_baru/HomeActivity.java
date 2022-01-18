package com.parkir_baru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title_view;
    private ImageView person;
    public CardView c1, c2, c3, c4, c5, c6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        title_view = findViewById(R.id.title_view);
        person = findViewById(R.id.person);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfilActivity.class));
            }
        });
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.c1:
                i = new Intent(this,PesanActivity.class);
                startActivity(i);
                break;
            case R.id.c2://bayar
                i = new Intent(this,BayarActivity.class);
                startActivity(i);
                break;
            case R.id.c3://history
                i = new Intent(this,HistoryActivity.class);
                startActivity(i);
                break;
            case R.id.c4: //denah
                i = new Intent(this,DenahActivity.class);
                startActivity(i);
                break;
            case R.id.c5://mall
                i = new Intent(this,MallActivity.class);
                startActivity(i);
                break;
            case R.id.c6: //logout
                finish();
                break;
        }
    }
}