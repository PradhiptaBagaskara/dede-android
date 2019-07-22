package com.ruvio.reawrite.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.ruvio.reawrite.network.InitRetro.API_URL;

public class ProfilSettingActivity extends AppCompatActivity {
    ImageView imgedit;
    TextView mail,username, nama, password;
    SessionManager sm;
    HashMap<String, String> map;
    String uname, email, name, pass, img, pathImg;
    private String path = API_URL + "uploads/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_setting);
        ActionBar ab  = getSupportActionBar();
        ab.setTitle("Edit Profil");
        ab.setDisplayHomeAsUpEnabled(true);
        imgedit = (ImageView) findViewById(R.id.editProfil);
        mail = (TextView) findViewById(R.id.editEmail);
        username = (TextView) findViewById(R.id.editUsername);
        nama = (TextView) findViewById(R.id.editNama);
        password = (TextView) findViewById(R.id.editPassword);

        sm = new SessionManager(getApplicationContext());
        map = sm.getLogged();

        uname = map.get(sm.SES_USERNAME);
        nama.setText(map.get(sm.SES_NAMA));
        mail.setText(map.get(sm.SES_EMAIL));
        username.setText(uname);

        img = map.get(sm.SES_PHOTO);
        pathImg = path + img;
        if (!TextUtils.isEmpty(img)){
            Picasso.with(ProfilSettingActivity.this)
                    .load(pathImg)
                    .into(imgedit);
//            Log.d("poto", "onViewCreated: " + path+img);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSave){
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        }

        return false;
    }
}
