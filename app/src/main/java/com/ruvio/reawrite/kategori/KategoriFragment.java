package com.ruvio.reawrite.kategori;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.KategoryActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.kategori.api.KategoriItem;
import com.ruvio.reawrite.kategori.api.KategoriServices;
import com.ruvio.reawrite.kategori.api.ResponseKategori;
import com.ruvio.reawrite.network.InitRetro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriFragment extends Fragment {
    public static final String TAG = "HOME_FRAGMENT";


    KategoriServices kategoriServices;
    private RecyclerView recyclerView;
    private KProgressHUD hud;
    SwipeRefreshLayout swp;

//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.kategori_frag, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleKategori);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        kategoriServices = InitRetro.InitApi().create(KategoriServices.class);
        showProgress();
        tampilKategori();
        swp = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swp.setRefreshing(false);
                showProgress();
                tampilKategori();
            }
        });



        return view;

    }

    private void showProgress(){
        hud = KProgressHUD.create(getActivity())
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

    private void tampilKategori(){
        Call<ResponseKategori> getResponse = kategoriServices.getKategoriResponse();
        getResponse.enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                List<KategoriItem> kategoriItemList = response.body().getResult();
                if (response.isSuccessful()){


                    progressDismiss();
                    boolean msg = response.body().isMsg();
                    if (msg){
                        Log.d("hasil", "onResponse: " +response.body().getResult().toString());
                        KategoriAdapter kategoriAdapter = new KategoriAdapter(kategoriItemList);
                        recyclerView.setAdapter(kategoriAdapter);
                    }

                }else {
                    progressDismiss();
                    Toast.makeText(getActivity(), "Server May Down Please Try Again", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                progressDismiss();
                Toast.makeText(getActivity(), "Server May Down Please Try Again", Toast.LENGTH_LONG).show();

                t.printStackTrace();
            }
        });

    }

    public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder>{

        List<KategoriItem> kategoriItems;
        public KategoriAdapter (List<KategoriItem> mKategoriItems){
            kategoriItems = mKategoriItems;
        }

        @Override
        public KategoriAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_item, parent, false);
            MyViewHolder mViewHolder = new MyViewHolder(mView);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull KategoriAdapter.MyViewHolder holder, final int position) {
            holder.mTextViewId.setText(kategoriItems.get(position).getKategori());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), KategoryActivity.class);
                    intent.putExtra("ID_Kategori", kategoriItems.get(position).getKategori());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return kategoriItems.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewId;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTextViewId = (TextView) itemView.findViewById(R.id.txtKategori);
            }
        }
    }
}
