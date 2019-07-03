package com.ruvio.reawrite.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ruvio.reawrite.R;


public class ProfileFragment extends Fragment {


    private ImageView imgProfil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profil_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        Bundle bundle = getArguments();

        imgProfil = (ImageView) view.findViewById(R.id.imgProfil);
        LinearLayout latar = (LinearLayout) view.findViewById(R.id.latarBelakang);
        latar.setBackgroundResource(R.drawable.side_nav_bar);

//        imgProfil.setImageDrawable(Drawable.createFromPath(String.valueOf(tes)));
        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "STRING MESSAGE", Toast.LENGTH_LONG).show();
            }
        });

    }
}
