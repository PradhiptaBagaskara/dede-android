package com.ruvio.reawrite.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.MainActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.GridMarginDecoration;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.cerita.DetailCeritaActivity;
import com.ruvio.reawrite.home.api.GetCeritaItem;
import com.ruvio.reawrite.home.api.ResponseGetCerita;
import com.ruvio.reawrite.network.ApiServices;
import com.ruvio.reawrite.network.InitRetro;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ruvio.reawrite.network.InitRetro.API_URL;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ApiServices apiServices;
    private KProgressHUD hud;
    SwipeRefreshLayout swp;
    SessionManager sm;
    private GridLayoutManager gridLayoutManager;
    HashMap<String,String> sesi;
    String key;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleHome);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridMarginDecoration(getActivity(), 2, 2, 2, 2));
        sm = new SessionManager(getActivity());

        sm.checkLogin();
        sesi = sm.getLogged();
        key = sesi.get(sm.SES_TOKEN);

        Log.d("sesi", "onCreateView: " + key);
        apiServices = InitRetro.InitApi().create(ApiServices.class);
        showProgress();
        loadCerita();
        swp = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swp.setRefreshing(false);
                showProgress();
                loadCerita();
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
        }

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

    private void loadCerita(){
        Call<ResponseGetCerita> getResponse = apiServices.getAllCerita();
        getResponse.enqueue(new Callback<ResponseGetCerita>() {
            @Override
            public void onResponse(Call<ResponseGetCerita> call, Response<ResponseGetCerita> response) {
                List<GetCeritaItem> ceritaItems = response.body().getResult();
                if (response.isSuccessful()){
                    progressDismiss();
                    HomeAdapter homeAdapter = new HomeAdapter(getActivity(), ceritaItems);
                    recyclerView.setAdapter(homeAdapter);
                }else {
                    progressDismiss();
                    Toast.makeText(getActivity(), "Server May Down Please Try Again", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseGetCerita> call, Throwable t) {
                progressDismiss();
                Toast.makeText(getActivity(), "Server May Down Please Try Again", Toast.LENGTH_LONG).show();
            }
        });



    }

//    start adapter

    public  class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

        List<GetCeritaItem> getCeritaItems;
        Context context;

        public HomeAdapter (Context mCOntext, List<GetCeritaItem> mGetCeritaItem){
            context = mCOntext;
            getCeritaItems = mGetCeritaItem;
        }


        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cerita_item, parent, false);
            MyViewHolder mViewHolder = new MyViewHolder(mView);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, final int position) {
            holder.mTextViewId.setText(getCeritaItems.get(position).getJudul());
            final String urlGambarBerita = API_URL + "uploads/"+ getCeritaItems.get(position).getImg();
            Picasso.with(context)
                    .load(urlGambarBerita)
                    .into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailCeritaActivity.class);
                    intent.putExtra("penulis", getCeritaItems.get(position).getUsername());
                    intent.putExtra("diskripsi", getCeritaItems.get(position).getDiskripsi());
                    intent.putExtra("isi", getCeritaItems.get(position).getIsi());
                    intent.putExtra("img", urlGambarBerita);
                    intent.putExtra("judul", getCeritaItems.get(position).getJudul());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return getCeritaItems.size();

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

//    end adapter

}
