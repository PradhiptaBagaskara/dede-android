package com.ruvio.reawrite.tulis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.kategori.api.KategoriItem;
import com.ruvio.reawrite.kategori.api.KategoriServices;
import com.ruvio.reawrite.kategori.api.ResponseKategori;
import com.ruvio.reawrite.network.InitRetro;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class TulisFragment extends Fragment {
    KategoriServices kategoriServices;
    Button btn;
    EditText judul,diskripsi;
    String id_kat = null;
    final List<String> idk = new ArrayList<String>();
    MaterialBetterSpinner mySpinner;
    KProgressHUD hud;
    ImageView imgTulis;
    final int REQUEST_GALLERY = 9544;
    String part_img;
    Boolean permisi = false;
    byte[] passImg;
    String bStream;
    ArrayAdapter<String> adapter;
    List<String> listSpinner;
    Bitmap bm;
    SessionManager sm;
    HashMap map;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tulis_frag, container, false);

        sm = new SessionManager(getActivity());
        sm.checkLogin();
        map = sm.getLogged();

        mySpinner = (MaterialBetterSpinner) view.findViewById(R.id.spinnerku);
        kategoriServices = InitRetro.InitApi().create(KategoriServices.class);
        btn = (Button) view.findViewById(R.id.btnTulis);
        judul = (EditText) view.findViewById(R.id.inputJudul);
        diskripsi =(EditText) view.findViewById(R.id.inputDiskripsi);
        imgTulis = (ImageView) view.findViewById(R.id.imgTulis);
        listSpinner = new ArrayList<String>();
        if (listSpinner == null){
            listSpinner.add("test");
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, listSpinner);
        mySpinner.setAdapter(adapter);


        showProgress();





        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        tampilKategori();






    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (TextUtils.isEmpty(part_img)){
//                imgTulis.setEr
                Toast.makeText(getActivity(), "Image Harus Di isi!", Toast.LENGTH_LONG).show();
            }else if (TextUtils.isEmpty(judul.getText())){
                judul.setError("Judul Harus Di isi!");
//                Toast.makeText(getActivity(), "Judul Harus Di isi!", Toast.LENGTH_LONG).show();

            }
            else if (TextUtils.isEmpty(id_kat)){
                mySpinner.setError("Kategori Harus Di isi!");
//                Toast.makeText(getActivity(), "Kategori Harus Di isi!", Toast.LENGTH_LONG).show();

            }
            else if (TextUtils.isEmpty(diskripsi.getText())){
                diskripsi.setError("diskripsi Harus Di isi!");

            }else {
                String idku = map.get(sm.SES_ID).toString();
                Intent intent = new Intent(getActivity(), TulisCerita.class);
                intent.putExtra("id_kategori", id_kat);
                intent.putExtra("id_user", idku);
                intent.putExtra("judul", judul.getText().toString());
                intent.putExtra("diskripsi", diskripsi.getText().toString());
                intent.putExtra("img", part_img);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }





        }
    });


    imgTulis.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Dexter.withActivity(getActivity())
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, REQUEST_GALLERY);}

                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, REQUEST_GALLERY);
                        }
                    }).check();


        }
    });



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
                    final List<KategoriItem> kategoriItems = response.body().getResult();


                    for (int i = 0; i < kategoriItems.size(); i++) {
                        listSpinner.add(kategoriItems.get(i).getNamaKategori());
                        idk.add(kategoriItems.get(i).getIdKategori());

                    }

//                    adapter = new ArrayAdapter<String>(getActivity(),
//                            android.R.layout.simple_dropdown_item_1line, listSpinner);
//                    mySpinner.setAdapter(adapter);
                    mySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final int Size = Integer.valueOf(idk.get(i));
                            id_kat = String.valueOf(Size);


                        }
                    });






                }else {
                    Toast.makeText(getActivity(), "Server May Down Please Try Again", Toast.LENGTH_LONG).show();
                    progressDismiss();

                }

            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                progressDismiss();
                Toast.makeText(getActivity(), "Server May Down Please Try Again", Toast.LENGTH_LONG).show();
                listSpinner.add("Bad Connections");
//                adapter = new ArrayAdapter<String>(getActivity(),
//                        android.R.layout.simple_dropdown_item_1line, listSpinner);
//                mySpinner.setAdapter(adapter);
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_GALLERY)
            {
                Uri dataimage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(dataimage,imageprojection,null,null,null);

                if (cursor != null)
                {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_img    = cursor.getString(indexImage);

                    if (part_img != null)
                    {
                        File image = new File(part_img);

//                        Picasso.with(getActivity())
//                                .load(image)
//                                .centerInside()
//                                .resize(600, 200)
//                                .into(imgTulis);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = 1;
                        options.inSampleSize = calculateInSampleSize(options, 600,300);
                        options.inJustDecodeBounds = false;
                        bm = BitmapFactory.decodeFile(image.getAbsolutePath(), options);

                        imgTulis.setImageBitmap(bm);


                    }else
                    {
                        Toast.makeText(getActivity(), "gagal memilih gambar", Toast.LENGTH_LONG);
                    }

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_GALLERY: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisi =true;


                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }





}
