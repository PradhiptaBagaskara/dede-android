package com.ruvio.reawrite;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ruvio.reawrite.activity.MasukanActivity;
import com.ruvio.reawrite.activity.ProfilSettingActivity;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.home.HomeFragment;
import com.ruvio.reawrite.kategori.KategoriFragment;
import com.ruvio.reawrite.login.LoginActivity;
import com.ruvio.reawrite.profile.ProfileFragment;
import com.ruvio.reawrite.tulis.TulisFragment;


public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    SwipeRefreshLayout swp;
    private int aktifFragment;
    SessionManager sm;
    int rule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        ab.show();
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.content));

        sm = new SessionManager(MainActivity.this);
//        sm.checkLogin();

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("NotifApps", "NotifApps",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }





        swp = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);


        setActionBar(0, rule);

//        Set Default Halaman
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        if (sm.Login()){
            rule = 0;
            halaman(0, 0);
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "UKM"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_tulis, "Tulis"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_person, "Profil"))
                    .setFirstSelectedPosition(0)
                    .initialise();
        }else {
            rule = 1;
            halaman(0,rule);
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "UKM"))
                    .setFirstSelectedPosition(0)
                    .initialise();
        }
//        Init halaman Untuk menu Bottom Nav

//        bottomNavigationBar.setAutoHideEnabled(false);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                halaman(position, rule);
                setActionBar(position, rule);
                aktifFragment = position;

            }

            @Override
            public void onTabUnselected(int position) {


            }

            @Override
            public void onTabReselected(int position) {

            }
        });




    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    public void setActionBar(int position, int rule){
        ActionBar ab = getSupportActionBar();
        if (rule == 0) {

            switch (position) {
                case 0:
                    ab.setDisplayShowHomeEnabled(true);
                    ab.setLogo(R.drawable.tulisan_logo);
                    ab.setDisplayUseLogoEnabled(true);
                    ab.setDisplayShowTitleEnabled(false);
//                setActionBarTitle("Reawrite");
                    break;
                case 1:
                    ab.setDisplayShowHomeEnabled(true);
                    ab.setLogo(R.drawable.tulisan_logo);
                    ab.setDisplayUseLogoEnabled(true);
                    ab.setDisplayShowTitleEnabled(false);
                    break;
                case 2:
                    ab.setDisplayShowHomeEnabled(true);
                    ab.setLogo(R.drawable.tulisan_logo);
                    ab.setDisplayUseLogoEnabled(true);
                    ab.setDisplayShowTitleEnabled(false);
                    break;
                case 3:
                    ab.setDisplayShowHomeEnabled(true);
                    ab.setLogo(R.drawable.tulisan_logo);
                    ab.setDisplayUseLogoEnabled(true);
                    ab.setDisplayShowTitleEnabled(false);
                    break;
            }
        }else {
            switch (position) {
                case 0:
                    ab.setDisplayShowHomeEnabled(true);
                    ab.setLogo(R.drawable.tulisan_logo);
                    ab.setDisplayUseLogoEnabled(true);
                    ab.setDisplayShowTitleEnabled(false);
//                setActionBarTitle("Reawrite");
                    break;
                case 1:
                    ab.setDisplayShowHomeEnabled(true);
                    ab.setLogo(R.drawable.tulisan_logo);
                    ab.setDisplayUseLogoEnabled(true);
                    ab.setDisplayShowTitleEnabled(false);
                    break;

            }

        }

    }


    private void halaman(int index, int rule){
        Fragment frg = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag;

        if (rule == 0){
            switch (index){
                case 0:
                    HomeFragment homeFragment = new HomeFragment();
                    fragment = homeFragment;
                    tag = "HOME_FRAGMENT";
                    Log.d("jalan", "halaman: home");
                    break;
                case 1:
                    KategoriFragment kategoriFragment= new KategoriFragment();
                    fragment = kategoriFragment;
                    tag = "KATEGORI_FRAGMENT";
                    Log.d("jalan", "halaman: kategori");

                    break;
                case 2:
                    TulisFragment tulisFragment = new TulisFragment();
                    fragment = tulisFragment;
                    tag ="TULIS_FRAGMENT";
                    Log.d("jalan", "halaman: tulis");

                    break;
                case 3:
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragment = profileFragment;
                    tag ="PROFIL_FRAGMENT";
                    break;
                default:
                    fragment = null;
                    tag = null;
            }
        }
        else {
            switch (index){
                case 0:
                    HomeFragment homeFragment = new HomeFragment();
                    fragment = homeFragment;
                    tag = "HOME_FRAGMENT";
                    Log.d("jalan", "halaman: home");
                    break;
                case 1:
                    KategoriFragment kategoriFragment= new KategoriFragment();
                    fragment = kategoriFragment;
                    tag = "KATEGORI_FRAGMENT";
                    Log.d("jalan", "halaman: kategori");

                    break;

                default:
                    fragment = null;
                    tag = null;
            }
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment, tag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (rule == 0) {
            menuInflater.inflate(R.menu.menu_umum, menu);
        }else {
            menuInflater.inflate(R.menu.menu_no_login, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (rule == 0){
            switch (item.getItemId()){
//                case R.id.btnMasukan:
//                    intent =new Intent(this, MasukanActivity.class);
//                    startActivity(intent);
//                    break;
                case R.id.settingProfil:
                    intent = new Intent(this, ProfilSettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnLogout:
                    sm.logout();
                    break;
            }

        }else {
            switch (item.getItemId()){
                case R.id.settingProfil:
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }

        }
        return false;
    }
}
