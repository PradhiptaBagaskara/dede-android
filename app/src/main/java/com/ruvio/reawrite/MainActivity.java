package com.ruvio.reawrite;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ruvio.reawrite.home.HomeFragment;
import com.ruvio.reawrite.kategori.KategoriFragment;
import com.ruvio.reawrite.profile.ProfileFragment;
import com.ruvio.reawrite.tulis.TulisFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    SwipeRefreshLayout swp;
    private int aktifFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setLogo(R.drawable.ic_menu_camera);
        ab.setDisplayUseLogoEnabled(true);

        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        ab.show();

        swp = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);


        setActionBar(0);

//        Set Default Halaman
        halaman(0);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

//        Init halaman Untuk menu Bottom Nav
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Kategori"))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Tulis"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Profil"))
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
        switch (position){
            case 0:
                setActionBarTitle("Reawrite");
                break;
            case 1:
                setActionBarTitle("Kategori");
                break;
            case 2:
                setActionBarTitle("Tulis");
                break;
            case 3:
                setActionBarTitle("Profil");
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


}
