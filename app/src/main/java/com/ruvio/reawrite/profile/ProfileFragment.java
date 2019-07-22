package com.ruvio.reawrite.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.ruvio.reawrite.network.InitRetro.API_URL;


public class ProfileFragment extends Fragment {


    private ImageView imgProfil;
    private Button btnLogout;
    SessionManager sm;
    HashMap<String,String> sesi;
    TextView nama;

    String img, username, bio, path, pathImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profil_frag, container, false);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        sm = new SessionManager(getActivity());
        btnLogout = (Button) v.findViewById(R.id.btnLogout);
        nama = (TextView) v.findViewById(R.id.namaProfil);
        sesi = sm.getLogged();
        path = API_URL+ "uploads/user/";

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        imgProfil = (ImageView) view.findViewById(R.id.imgProfil);

        username = sesi.get(sm.SES_NAMA);
        nama.setText(username);
        img = sesi.get(sm.SES_PHOTO);
        pathImg = path + img;
        if (!TextUtils.isEmpty(img)){
            Picasso.with(getActivity())
                    .load(pathImg)
                    .into(imgProfil);
//            Log.d("poto", "onViewCreated: " + path+img);
        }



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sm.logout();
            }
        });

    }
}
