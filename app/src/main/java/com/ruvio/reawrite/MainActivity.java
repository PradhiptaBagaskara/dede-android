package com.ruvio.reawrite;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ruvio.reawrite.activity.MasukanActivity;
import com.ruvio.reawrite.activity.ProfilSettingActivity;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.home.HomeFragment;
import com.ruvio.reawrite.kategori.KategoriFragment;
import com.ruvio.reawrite.profile.ProfileFragment;
import com.ruvio.reawrite.tulis.TulisFragment;


public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    SwipeRefreshLayout swp;
    private int aktifFragment;
    SessionManager sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        ab.show();

        sm = new SessionManager(MainActivity.this);
        sm.checkLogin();
        if (sm.Login()){
            halaman(0);

        }




        swp = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);


        setActionBar(0);

//        Set Default Halaman
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

//        Init halaman Untuk menu Bottom Nav
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Kategori"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tulis, "Tulis"))
                .addItem(new BottomNavigationItem(R.drawable.ic_person, "Profil"))
                .setFirstSelectedPosition(0)
                .initialise();
//        bottomNavigationBar.setAutoHideEnabled(false);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                halaman(position);
                setActionBar(position);
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
    public void setActionBar(int position){
        ActionBar ab = getSupportActionBar();

        switch (position){
            case 0:
                ab.setDisplayShowHomeEnabled(true);
                ab.setLogo(R.drawable.ic_toolbar);
                ab.setDisplayUseLogoEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
//                setActionBarTitle("Reawrite");
                break;
            case 1:
                ab.setDisplayShowHomeEnabled(true);
                ab.setLogo(R.drawable.ic_toolbar);
                ab.setDisplayUseLogoEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
                break;
            case 2:
                ab.setDisplayShowHomeEnabled(true);
                ab.setLogo(R.drawable.ic_toolbar);
                ab.setDisplayUseLogoEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
                break;
            case 3:
                ab.setDisplayShowHomeEnabled(true);
                ab.setLogo(R.drawable.ic_toolbar);
                ab.setDisplayUseLogoEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
                break;
        }
    }


    private void halaman(int index){
        Fragment frg = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag;
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
        fragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment, tag)
                .commit();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.setting) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        MenuItem settingsItem = menu.findItem(R.id.setting);
//        // set your desired icon here based on a flag if you like
//        settingsItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_settings));
//
//        return super.onPrepareOptionsMenu(menu);
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_umum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.btnMasukan:
                 intent =new Intent(this, MasukanActivity.class);
                startActivity(intent);
                break;
            case R.id.settingProfil:
                intent =new Intent(this, ProfilSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                sm.logout();
                break;
        }
        return false;
    }
}
