package com.ruvio.reawrite.tulis;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chinalwb.are.AREditor;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.MainActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.firebase.Common;
import com.ruvio.reawrite.firebase.NotifService;
import com.ruvio.reawrite.firebase.Notification;
import com.ruvio.reawrite.firebase.Respon;
import com.ruvio.reawrite.firebase.Sender;
import com.ruvio.reawrite.network.ApiServices;
import com.ruvio.reawrite.network.InitRetro;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TulisCerita extends AppCompatActivity {
    AREditor arEditor;
    ApiServices apiServices;
    String img,judul,diskripsi, id_kategori, isi, id_user;
    KProgressHUD hud;
    SessionManager sm;
    HashMap<String,String> map;
    NotifService fcmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulis_cerita);

        Window window = TulisCerita.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.content));

        apiServices = InitRetro.InitApi().create(ApiServices.class);
        Bundle extra = getIntent().getExtras();
        img = extra.getString("img");
        judul = extra.getString("judul");
        id_kategori = extra.getString("id_kategori");
        diskripsi = extra.getString("diskripsi");
        id_user = extra.getString("id_user");

        fcmService = Common.getFCMClient();


        sm = new SessionManager(TulisCerita.this);
        sm.checkLogin();
        map = sm.getLogged();



        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Tulis Cerita");
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        arEditor = this.findViewById(R.id.areditor);
        arEditor.setExpandMode(AREditor.ExpandMode.FULL);
        arEditor.setHideToolbar(false);
        arEditor.setBackgroundColor(getResources().getColor(R.color.content));
        arEditor.setToolbarAlignment(AREditor.ToolbarAlignment.BOTTOM);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_send_cerita,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sendCerita:
                showProgress();
//
                final File imageFile = new File(img);
                String auth_key = map.get(sm.SES_TOKEN);
                isi = arEditor.getHtml();

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                RequestBody auth = RequestBody.create(MediaType.parse("text/plain"), auth_key);
                RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), id_user);
                RequestBody katId = RequestBody.create(MediaType.parse("text/plain"), id_kategori);
                RequestBody sJudul = RequestBody.create(MediaType.parse("text/plain"), judul);
                RequestBody sIsi = RequestBody.create(MediaType.parse("text/plain"), isi);
                RequestBody sDiskripsi = RequestBody.create(MediaType.parse("text/plain"), diskripsi);

                MultipartBody.Part partImage = MultipartBody.Part.createFormData("img", imageFile.getName(), requestBody);
                Call<ResponseTulis> response = apiServices.postCerita(auth, userId,katId,sJudul,sDiskripsi,sIsi, partImage);
                response.enqueue(new Callback<ResponseTulis>() {
                    @Override
                    public void onResponse(Call<ResponseTulis> call, Response<ResponseTulis> response) {
                        if (response.isSuccessful()){
                            Log.d("tag", "onResponse: " + response.body().toString());
                            progressDismiss();
                            Boolean status = response.body().isStatus();
                            if (status){
                                Log.d("sukses", "onResponse: sukses aplut");
                                Toast.makeText(getApplicationContext(), "sukses terpost", Toast.LENGTH_LONG).show();


//                                Intent intent = new Intent(TulisCerita.this, )
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "cek field yg kurang", Toast.LENGTH_LONG).show();

                            }

                            Notification notification = new Notification(judul,diskripsi);
                            Sender sender = new Sender("/topics/news", notification);
                            fcmService.sendNotification(sender).enqueue(new Callback<Respon>() {
                                @Override
                                public void onResponse(Call<Respon> call, Response<Respon> response) {
                                    if (response.isSuccessful()){
//                                        Toast.makeText(getApplicationContext(), "sukses postnotif", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<Respon> call, Throwable t) {
                                    t.printStackTrace();

                                }
                            });


                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTulis> call, Throwable t) {
                        progressDismiss();
                        Toast.makeText(getApplicationContext(), "bad koneksi", Toast.LENGTH_LONG).show();

                        t.printStackTrace();


                    }
                });



//                Toast.makeText(getApplicationContext(), arEditor.getHtml(), Toast.LENGTH_LONG).show();
                Log.d("areEdior", "onOptionsItemSelected: "+ arEditor.getHtml().toString());
                return true;

        }
        return false;
    }
    private void showProgress(){
        hud = KProgressHUD.create(TulisCerita.this)
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
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
