package com.ruvio.reawrite.cerita;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class BacaCeritaActivity extends AppCompatActivity {
    ImageView img;
    TextView penulis, isi;
    private String iJudul, iImg, iDiskripsi, iAuthor,iIsi;
    Html html;
    HtmlTextView htw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baca_cerita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        iJudul = bundle.getString("judul");
        iAuthor = bundle.getString("penulis");
        iImg =  bundle.getString("img");
        iDiskripsi = bundle.getString("diskripsi");
        iIsi = bundle.getString("isi");

        SessionManager sm = new SessionManager(BacaCeritaActivity.this);
        sm.checkLogin();



        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);


        img = (ImageView) findViewById(R.id.imgBacaCerita);
        penulis = (TextView) findViewById(R.id.authorBaca);
//        isi = (TextView) findViewById(R.id.isiBaca);
        htw = (HtmlTextView) findViewById(R.id.html_text);
        String str = bundle.getString("isi");

        penulis.setText(bundle.getString("penulis"));
//        isi.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
        htw.setHtml(iIsi,
                new HtmlHttpImageGetter(htw));
        Picasso.with(getApplicationContext())
                .load(bundle.getString("img"))
                .into(img);



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
