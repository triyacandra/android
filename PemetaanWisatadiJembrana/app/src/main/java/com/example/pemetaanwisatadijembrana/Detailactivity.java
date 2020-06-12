package com.example.pemetaanwisatadijembrana;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Detailactivity extends AppCompatActivity implements View.OnClickListener{
    TextView datanama, datatelepun, dataalamat;
    Button btncall;
    ImageView datagambar;

    public  static  String id, nama, telepun, alamat, gambar;
    public static Double latitude, longtitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);

        datanama=findViewById(R.id.data_nama);
        dataalamat=findViewById(R.id.data_alamat);
        datagambar=findViewById(R.id.img_data);
        datatelepun=findViewById(R.id.data_telpun);
        btncall=findViewById(R.id.btn_call);


        Picasso.get().load(gambar).into(datagambar);

        datanama.setText(nama);
        datatelepun.setText(telepun);
        dataalamat.setText(alamat);
        btncall.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent call= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telepun));
        startActivity(call);
    }


}
