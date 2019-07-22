package com.ruvio.reawrite.kategori;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.GridMarginDecoration;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.cerita.DetailCeritaActivity;
import com.ruvio.reawrite.kategori.api.KategoriServices;
import com.ruvio.reawrite.kategori.cerita.ByIdItem;
import com.ruvio.reawrite.kategori.cerita.ResponseById;
import com.ruvio.reawrite.network.InitRetro;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ruvio.reawrite.network.InitRetro.API_URL;

public class ByIdActivity extends AppCompatActivity {
    KategoriServices kategoriServices;
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    SessionManager sm;
    HashMap<String,String> sesi;
    KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerita);
        kategoriServices = InitRetro.InitApi().create(KategoriServices.class);
        recyclerView = (RecyclerView) findViewById(R.id.ceritaView);

        sm = new SessionManager(ByIdActivity.this);
        sm.checkLogin();
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        showProgress();
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridMarginDecoration(getApplicationContext(), 2, 2, 2, 2));
        tampilCerita(getApplicationContext());

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        ab.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void tampilCerita(Context context){
        final String kategori;
        String key;
        String id_kategori;
        sesi = sm.getLogged();
        key = sesi.get(sm.SES_TOKEN);

        Bundle extras = getIntent().getExtras();
        kategori = extras.getString("KATEGORI_NAME");

        id_kategori = extras.getString("ID_KATEGORI");
        Call<ResponseById> getResponse = kategoriServices.postById(id_kategori, key);
        getResponse.enqueue(new Callback<ResponseById>() {
            @Override
            public void onResponse(Call<ResponseById> call, Response<ResponseById> response) {
                List<ByIdItem> byIdItemList = response.body().getResult();
                if (response.isSuccessful()){
                    progressDismiss();
                    ByIdAdapter byIdAdapter = new ByIdAdapter(getApplicationContext(), byIdItemList);
                    ActionBar ab = getSupportActionBar();
                    ab.setTitle(kategori);
                    Boolean msg = response.body().isStatus();
                    if (msg){
                        recyclerView.setAdapter(byIdAdapter);
                    }else {
                        Toast.makeText(getApplicationContext(), "key salah", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseById> call, Throwable t) {
                progressDismiss();
                Toast.makeText(getApplicationContext(), "Bad Connections!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgress(){
        hud = KProgressHUD.create(ByIdActivity.this)
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


  public  class ByIdAdapter  extends RecyclerView.Adapter<ByIdAdapter.MyViewHolder>{

        List<ByIdItem> byIdItems;
        Context context;

        public ByIdAdapter (Context mCOntext, List<ByIdItem> mbyIdItems){
            context = mCOntext;
            byIdItems = mbyIdItems;
        }


        @Override
        public ByIdAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cerita_item, parent, false);
            MyViewHolder mViewHolder = new MyViewHolder(mView);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ByIdAdapter.MyViewHolder holder, final int position) {
            holder.mTextViewId.setText(byIdItems.get(position).getJudul());
            final String urlGambarBerita = API_URL + "uploads/"+ byIdItems.get(position).getImg();
            Picasso.with(getApplicationContext())
                    .load(urlGambarBerita)
                    .into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), DetailCeritaActivity.class);
                    intent.putExtra("penulis", byIdItems.get(position).getUsername());
                    intent.putExtra("diskripsi", byIdItems.get(position).getDiskripsi());
                    intent.putExtra("isi", byIdItems.get(position).getIsi());
                    intent.putExtra("img", urlGambarBerita);
                    intent.putExtra("judul", byIdItems.get(position).getJudul());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return byIdItems.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewId;
            public ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);

                mTextViewId = (TextView) itemView.findViewById(R.id.ceritaJudul);
                imageView = (ImageView) itemView.findViewById(R.id.ceritaImg);
            }
        }

    }

}
