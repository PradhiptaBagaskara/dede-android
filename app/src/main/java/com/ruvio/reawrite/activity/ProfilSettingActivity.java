package com.ruvio.reawrite.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ruvio.reawrite.MainActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.network.ApiServices;
import com.ruvio.reawrite.network.InitRetro;
import com.ruvio.reawrite.profile.api.ResponseProfile;
import com.ruvio.reawrite.profile.api.Result;
import com.ruvio.reawrite.tulis.ResponseTulis;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ruvio.reawrite.network.InitRetro.API_URL;

public class ProfilSettingActivity extends AppCompatActivity {
    ImageView imgedit;
    TextView mail,username, nama, password;
    SessionManager sm;
    HashMap<String, String> sesi;
    String uname, email, name, pass, img, pathImg;
    private String path = API_URL + "uploads/user/";
    final int REQUEST_GALLERY = 9544;
    String part_img, gambar;
    Bitmap bm;
    Boolean permisi = false;
    private KProgressHUD hud;
    ApiServices apiServices;
    File imageFile;
    Call<ResponseProfile> response;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_setting);
        ActionBar ab  = getSupportActionBar();
        ab.setTitle("Edit Profil");
        ab.setDisplayHomeAsUpEnabled(true);

        Window window = ProfilSettingActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.content));

        apiServices = InitRetro.InitApi().create(ApiServices.class);

        imgedit = (ImageView) findViewById(R.id.editProfil);
        mail = (TextView) findViewById(R.id.editEmail);
        username = (TextView) findViewById(R.id.editUsername);
        nama = (TextView) findViewById(R.id.editNama);
        password = (TextView) findViewById(R.id.editPassword);

        sm = new SessionManager(getApplicationContext());
        sesi = sm.getLogged();

        loadProfile();


        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(ProfilSettingActivity.this)
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
            showProgress();
            String auth_key = sesi.get(sm.SES_TOKEN);

            String fName = nama.getText().toString();
            String fEmail = mail.getText().toString();
            String fUsername = username.getText().toString();
//            fUsername.replace("@", "");
            String fPass = password.getText().toString();

            RequestBody auth = RequestBody.create(MediaType.parse("text/plain"), auth_key);
            RequestBody sUsername = RequestBody.create(MediaType.parse("text/plain"), fUsername);
            RequestBody sNama = RequestBody.create(MediaType.parse("text/plain"), fName);
            RequestBody sEmail = RequestBody.create(MediaType.parse("text/plain"), fEmail);
            RequestBody sPassword = RequestBody.create(MediaType.parse("text/plain"), fPass);

            if (part_img != null){
                gambar = part_img;
                imageFile = new File(gambar);

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("img", imageFile.getName(), requestBody);


                response = apiServices.postProfile(auth, sNama, sUsername,sEmail, sPassword, partImage);

            }else {
                response = apiServices.postProfileNoImg(auth, sNama, sUsername,sEmail, sPassword);
            }
            response.enqueue(new Callback<ResponseProfile>() {
                @Override
                public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                    progressDismiss();
                    ResponseProfile items = response.body();
                    if (response.isSuccessful()){
                        Boolean stts = response.body().isStatus();
                        if (stts){
                            Toast.makeText(getApplicationContext(), items.getMsg(), Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        }else {
                            Toast.makeText(getApplicationContext(), items.getMsg(), Toast.LENGTH_LONG).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseProfile> call, Throwable t) {
                    progressDismiss();
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection!", Toast.LENGTH_LONG).show();

                }
            });



        }

        return false;
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
                Cursor cursor = getApplicationContext().getContentResolver().query(dataimage,imageprojection,null,null,null);

                if (cursor != null)
                {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_img    = cursor.getString(indexImage);

                    if (part_img != null)
                    {
                        File image = new File(part_img);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = 1;
                        options.inSampleSize = calculateInSampleSize(options, 600,300);
                        options.inJustDecodeBounds = false;
                        bm = BitmapFactory.decodeFile(image.getAbsolutePath(), options);

                        imgedit.setImageBitmap(bm);


                    }else
                    {
                        Toast.makeText(getApplicationContext(), "gagal memilih gambar", Toast.LENGTH_LONG);
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
                    Toast.makeText(getApplicationContext(), "Please give your permission.", Toast.LENGTH_LONG).show();
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


    private void showProgress(){
        hud = KProgressHUD.create(ProfilSettingActivity.this)
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
                        Picasso.with(getApplicationContext())
                                .load(pathImg)
                                .into(imgedit);
//            Log.d("poto", "onViewCreated: " + path+img);
                    }
                    nama.setText(items.getNamaUser());
                    username.setText(items.getUsername());
                    mail.setText(items.getEmail());
//                    Log.d("status", "onResponse: sukses " + items.getAuthKey());
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
