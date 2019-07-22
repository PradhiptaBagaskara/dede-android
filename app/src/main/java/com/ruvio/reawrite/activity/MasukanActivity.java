package com.ruvio.reawrite.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.chinalwb.are.AREditor;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.login.LoginActivity;
import com.ruvio.reawrite.network.ApiServices;
import com.ruvio.reawrite.network.InitRetro;
import com.ruvio.reawrite.network.ResponseNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasukanActivity extends AppCompatActivity {

    private AREditor arEditor;
    ApiServices apiServices;
    SessionManager sm;
    HashMap<String, String> map;

    KProgressHUD hud;

    private void showProgress(){
        hud = KProgressHUD.create(MasukanActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setWindowColor(getResources().getColor(R.color.colorAccent))
                .setAnimationSpeed(2);
        hud.show();
    }



    private void progressDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 500);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masukan);

        apiServices = InitRetro.InitApi().create(ApiServices.class);
        ActionBar ab  = getSupportActionBar();
        ab.setTitle("Kirim Masukan Anda");
        ab.setDisplayHomeAsUpEnabled(true);

        sm = new SessionManager(MasukanActivity.this);


        arEditor = this.findViewById(R.id.areditor);
        arEditor.setExpandMode(AREditor.ExpandMode.FULL);
        arEditor.setHideToolbar(false);
        arEditor.setBackgroundColor(getResources().getColor(R.color.content));
        arEditor.setToolbarAlignment(AREditor.ToolbarAlignment.BOTTOM);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_send_cerita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sendCerita:
//                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                showProgress();
                sendMasukan();

                break;
        }


        return false;
    }

    private void sendMasukan(){
        map = sm.getLogged();
        String masukan = arEditor.getHtml();
        String id = map.get(sm.SES_ID);
        String token = map.get(sm.SES_TOKEN);
        Call<ResponseNull> push = apiServices.postMasukan(id,token,masukan);
        push.enqueue(new Callback<ResponseNull>() {
            @Override
            public void onResponse(Call<ResponseNull> call, Response<ResponseNull> response) {
                if (response.isSuccessful()){
                    progressDismiss();
                    if (response.body().isStatus()){
                        Toast.makeText(getApplicationContext(), R.string.terimakasih_masukan, Toast.LENGTH_LONG).show();
                       finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please Try Again!", Toast.LENGTH_LONG).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseNull> call, Throwable t) {
                progressDismiss();
                Toast.makeText(getApplicationContext(), "Bad Connection! Please Try Again!", Toast.LENGTH_LONG).show();

            }
        });
    }


}
