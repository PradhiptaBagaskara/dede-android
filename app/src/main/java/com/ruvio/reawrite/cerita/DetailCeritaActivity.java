package com.ruvio.reawrite.cerita;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruvio.reawrite.MainActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.squareup.picasso.Picasso;

public class DetailCeritaActivity extends AppCompatActivity {
    TextView deskripsi,author;
    ImageView img;
    Button btnBaca;
    String iJudul,iAuthor,iImg,iDiskripsi,iIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            iJudul = bundle.getString("judul");
            iAuthor = bundle.getString("penulis");
            iImg =  bundle.getString("img");
            iDiskripsi = bundle.getString("diskripsi");
            iIsi = bundle.getString("isi");
        }else {
            iJudul = bundle.getString("judul");
            iAuthor = bundle.getString("penulis");
            iImg =  bundle.getString("img");
            iDiskripsi = bundle.getString("diskripsi");
            iIsi = bundle.getString("isi");
        }

        SessionManager sm = new SessionManager(DetailCeritaActivity.this);
        sm.checkLogin();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        ab.setTitle(bundle.getString("judul"));



        deskripsi= (TextView) findViewById(R.id.diskripsi);
        author = (TextView) findViewById(R.id.author);
        img = (ImageView) findViewById(R.id.imgDetail);
        btnBaca = (Button) findViewById(R.id.btnBacaCerita);

        author.setText(iAuthor);
        deskripsi.setText(iDiskripsi);
        btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BacaCeritaActivity.class);
                intent.putExtra("penulis", iAuthor);
                intent.putExtra("judul", iJudul);
                intent.putExtra("isi", iIsi);
                intent.putExtra("img", iImg);
                intent.putExtra("diskripsi", iDiskripsi);
                startActivity(intent);
            }
        });

        Picasso.with(getApplicationContext())
                .load(iImg)
                .into(img);



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
