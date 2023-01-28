package com.widxy.widxynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    String img, judul, deskripsi, tgl, penulis, sumber;
    ImageView tvImg;
    TextView tvJudul, tvDeskripsi, tvTgl, tvPenulis, tvSumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img = getIntent().getStringExtra("imgNews");
        judul = getIntent().getStringExtra("titleNews");
        deskripsi = getIntent().getStringExtra("contentNews");
        tgl = getIntent().getStringExtra("dateNews");
        penulis = getIntent().getStringExtra("authorNews");
        sumber = getIntent().getStringExtra("sourceNews");

        bindView();

        Glide.with(getApplicationContext())
                .load(img).into(tvImg);
        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvTgl.setText(tgl);
        tvSumber.setText("Lihat Lebih Lengkap");
        tvPenulis.setText(penulis);

        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void sumber (View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sumber)));
    }
    private void bindView() {
        tvImg = findViewById(R.id.img);
        tvJudul = findViewById(R.id.judul);
        tvDeskripsi = findViewById(R.id.deskripsi);
        tvPenulis = findViewById(R.id.penulis);
        tvTgl = findViewById(R.id.tgl);
        tvSumber = findViewById(R.id.sumber);
    }

    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

}