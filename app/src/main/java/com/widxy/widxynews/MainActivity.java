package com.widxy.widxynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView news;
    private NewsAdapter adapter;
    List<News> list = new ArrayList<>();
    ProgressDialog loading;
    ApiService api;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news = findViewById(R.id.news);
        api = Server.getApiService();
        adapter = new NewsAdapter(MainActivity.this, list);

        news.setHasFixedSize(true);
        news.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        news.setAdapter(adapter);
        refresh();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void refresh() {
        loading = new ProgressDialog(MainActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        showDialog();
        api.getListAllNews("id", BuildConfig.NEWS_API_TOKEN).enqueue(new Callback<ResponNews>() {
            @Override
            public void onResponse(Call<ResponNews> call, Response<ResponNews> response) {
                if (response.isSuccessful()){
                    hideDialog();
                    list = response.body().getNewsList();
                    news.setAdapter(new NewsAdapter(MainActivity.this, list));
                    adapter.notifyDataSetChanged();
                } else {
                    hideDialog();
                    Toast.makeText(MainActivity.this, "Gagal mengambil data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponNews> call, Throwable t) {
                hideDialog();
                Toast.makeText(MainActivity.this, "Gagal menyambung ke internet !", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
                // super.onBackPressed();
                moveTaskToBack(true);
            } else {
                Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            }
            backPressed = System.currentTimeMillis();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.business) {
            Intent bus = new Intent(MainActivity.this, BisnisActivity.class);
            startActivity(bus);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.entertainment) {
            Intent enter = new Intent(MainActivity.this, EntertaimentActivity.class);
            startActivity(enter);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.health) {
            Intent heal = new Intent(MainActivity.this, HealthActivity.class);
            startActivity(heal);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.science) {
            Intent scien = new Intent(MainActivity.this, ScienceActivity.class);
            startActivity(scien);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.sports) {
            Intent sport = new Intent(MainActivity.this, SportsActivity.class);
            startActivity(sport);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.technology) {
            Intent tech = new Intent(MainActivity.this, TechnologyActivity.class);
            startActivity(tech);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.about) {
            Intent about = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(about);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda yakin ingin keluar ?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}