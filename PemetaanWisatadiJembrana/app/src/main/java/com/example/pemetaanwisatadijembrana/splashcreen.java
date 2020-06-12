package com.example.pemetaanwisatadijembrana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashcreen extends AppCompatActivity {

    private final int panjang_splash=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);

        new Handler() .postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tampil = new Intent(splashcreen.this,MainActivity.class);
                splashcreen.this.startActivity(tampil);
                splashcreen.this.finish();
            }
        }, panjang_splash);
    }
}
