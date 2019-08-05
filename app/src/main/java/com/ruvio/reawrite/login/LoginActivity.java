package com.ruvio.reawrite.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ruvio.reawrite.MainActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.adapter.SessionManager;
import com.ruvio.reawrite.login.api.ResponseUser;
import com.ruvio.reawrite.login.api.UserItem;
import com.ruvio.reawrite.network.ApiServices;
import com.ruvio.reawrite.network.InitRetro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private UserLoginTask mAuthTask = null;
    private EditText mPasswordView, mEmailView;
    private View mProgressView;
    private View mLoginFormView;

    private SessionManager sm;
    ApiServices apiServices;
    private boolean login = false;
    private Button btnLogin, btnRegister;
    KProgressHUD hud;

    private void showProgress(){
        hud = KProgressHUD.create(LoginActivity.this)
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar ab = getSupportActionBar();
        ab.hide();
        apiServices = InitRetro.InitApi().create(ApiServices.class);
        mEmailView = (EditText) findViewById(R.id.etEmail);

        sm = new SessionManager(LoginActivity.this);
        sm.logged();
        mPasswordView = (EditText) findViewById(R.id.etPassword);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
                    showProgress();


                    String mail = mEmailView.getText().toString();
                    String password = mPasswordView.getText().toString();

                    Call<ResponseUser> login = apiServices.getUser(mail, password);

                    login.enqueue(new Callback<ResponseUser>() {
                        @Override
                        public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                            if (response.isSuccessful()){
                                progressDismiss();
                                if (response.body().isStatus()) {
                                    String id = response.body().getResult().toString();
                                    String token = response.body().getResult().toString();
                                    List<UserItem> ab = response.body().getResult();
//                                UserItem userItem = (UserItem) response.body().getResult();
                                    String email = ab.get(0).getEmail();
                                    String id_user = ab.get(0).getIdUser();
                                    String username = ab.get(0).getUsername();
                                    String nama = ab.get(0).getUsername();
                                    String auth = ab.get(0).getAuthKey();
                                    String poto = ab.get(0).getFotoUser();
                                    Log.d("tag", "onResponse: " + poto);
                                    sm.storeLogin(id_user,nama,email,username,poto,auth);


                                    Log.d("hasil", id + " / " + token + " / status login: " + sm.Login());
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), response.body().getMsg().toString(), Toast.LENGTH_LONG).show();

                                }
                                //
                            }else {

                                progressDismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseUser> call, Throwable t) {
                            progressDismiss();
                            t.printStackTrace();

                            Toast.makeText(getApplicationContext(), "Bad Connection!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });



    }

    private boolean attemptLogin() {
        if (mAuthTask != null) {
            return true;
        }else
        {


            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }
            if (TextUtils.isEmpty(password))
            {
                mPasswordView.setError(getString(R.string.error_field_required));
                focusView = mPasswordView;
                cancel = true;
            }
            else if (!isPasswordValid(password))
            {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            }
            else {

                return true;

            }
            return false;
        }

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//            showProgress(false);
        }
    }
}


