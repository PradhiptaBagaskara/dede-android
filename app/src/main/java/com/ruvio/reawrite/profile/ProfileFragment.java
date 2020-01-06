package com.ruvio.reawrite.profile;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.network.ApiServices;
import com.ruvio.reawrite.network.InitRetro;
import com.ruvio.reawrite.profile.api.ResponseProfile;
import com.ruvio.reawrite.profile.api.Result;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ruvio.reawrite.network.InitRetro.API_URL;


public class ProfileFragment extends Fragment {


    private ImageView imgProfil;
    private TextView uname;
    SessionManager sm;
    HashMap<String,String> sesi;
    TextView nama;
    private KProgressHUD hud;

    String img, username, bio, path, pathImg;
    ApiServices apiServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profil_frag, container, false);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        sm = new SessionManager(getActivity());
        imgProfil = (ImageView) v.findViewById(R.id.imgProfil);
        uname = (TextView) v.findViewById(R.id.btnLogout);
        nama = (TextView) v.findViewById(R.id.namaProfil);
        sesi = sm.getLogged();
        path = API_URL+ "uploads/user/";
        apiServices = InitRetro.InitApi().create(ApiServices.class);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();


        loadProfile();




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

    private void loadProfile(){
        showProgress();
       Call<ResponseProfile> responseProfileCall = apiServices.getProfile(sesi.get(sm.SES_TOKEN));
       responseProfileCall.enqueue(new Callback<ResponseProfile>() {
           @Override
           public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
               progressDismiss();
               Result items = response.body().getResult();

               if (response.isSuccessful()){
                   img = items.getFotoUser();
                   if (!TextUtils.isEmpty(img)){
                       pathImg = path + img;
                       Picasso.with(getActivity())
                               .load(pathImg)
                               .into(imgProfil);
//            Log.d("poto", "onViewCreated: " + path+img);
                   }
                   nama.setText(items.getNamaUser());
                   uname.setText("@"+items.getUsername());
                   Log.d("status", "onResponse: sukses " + items.getAuthKey());
               }
           }

           @Override
           public void onFailure(Call<ResponseProfile> call, Throwable t) {
               progressDismiss();

               t.printStackTrace();

           }
       });
    }
}
