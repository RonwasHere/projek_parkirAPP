package com.parkir_baru.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parkir_baru.R;

public class SpinnerTunjActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String nama;
    private TextView et_tampil;
    private Button hasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_tunj);
        hasil = findViewById(R.id.hasil);
        //nama mall ditangkep
        nama = getIntent().getStringExtra("nama");
        et_tampil=findViewById(R.id.et_tampil);
        et_tampil.setText(nama); //ngeset txt view jadi nama bulan
        //spinner
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.bulan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //hasil
        hasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SpinnerTunjActivity.this, TunjunganActivity.class);
                i.putExtra("nama", nama);//ini lempar nama mall
                i.putExtra("bulan", spinner.getSelectedItem().toString());//ini lempar nama mall
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String item = parent.getItemAtPosition(position).toString();
//        if(parent.getItemAtPosition(position).equals("Januari")){ //klik bln lempar ke rv tampil bln
//            Intent i = new Intent(SpinnerTunjActivity.this, TunjunganActivity.class);
//            startActivity(i);
//        }else{
//
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}