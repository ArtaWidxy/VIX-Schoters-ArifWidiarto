package com.widxy.widxynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.widxy.widxynews.adapter.NewsAdapter;
import com.widxy.widxynews.api.ApiService;
import com.widxy.widxynews.api.Server;
import com.widxy.widxynews.entity.News;
import com.widxy.widxynews.entity.ResponNews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsActivity extends AppCompatActivity {
    private RecyclerView news;
    private NewsAdapter adapter;
    List<News> list = new ArrayList<>();
    final String category = "sports";
    ProgressDialog loading;
    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        news = findViewById(R.id.news);
        api = Server.getApiService();
        adapter = new NewsAdapter(SportsActivity.this, list);

        news.setHasFixedSize(true);
        news.setLayoutManager(new LinearLayoutManager(SportsActivity.this));
        news.setAdapter(adapter);
        refresh();

        //membuat back button toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void refresh() {
        loading = new ProgressDialog(SportsActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        showDialog();
        api.getListNews("id", category, BuildConfig.NEWS_API_TOKEN).enqueue(new Callback<ResponNews>() {
            @Override
            public void onResponse(Call<ResponNews> call, Response<ResponNews> response) {
                if (response.isSuccessful()){
                    hideDialog();
                    list = response.body().getNewsList();
                    news.setAdapter(new NewsAdapter(SportsActivity.this, list));
                    adapter.notifyDataSetChanged();
                } else {
                    hideDialog();
                    Toast.makeText(SportsActivity.this, "Gagal mengambil data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponNews> call, Throwable t) {
                hideDialog();
                Toast.makeText(SportsActivity.this, "Gagal menyambung ke internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        if (!loading.isShowing())
            loading.show();
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(SportsActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SportsActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}