package com.task.taskthree.signupandlogin;

import android.content.Intent;
import android.content.SharedPreferences;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText emailIn,passIn;
    Retrofit retrofit;
    Gson gson;
    LoginApi login_api;
//    String email,pass,auth,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        emailIn = (EditText)findViewById(R.id.email);
        passIn = (EditText)findViewById(R.id.password);

        SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);

        if(get_shared_preference.getString("token_authentication","").equals("")){

            btnLogin = (Button)findViewById(R.id.btn_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new ApiConnect().execute("http://private-fc0d3-geza.apiary-mock.com");
                    if (emailIn.getText().toString().equals("")||passIn.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "Please insert Email/Password.", Toast.LENGTH_SHORT).show();
                    }else {
                        get_retrofit("http://private-fc0d3-geza.apiary-mock.com");
                    }
                }
            });


        }else{
            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            finish();
        }

    }

    public void get_retrofit(String url_target){
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        retrofit = new Retrofit.Builder().baseUrl(url_target)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        login_api = retrofit.create(LoginApi.class);

        // // implement interface for get all user

        Call<Users> call = login_api.getUsers();
        call.enqueue(new Callback<Users>() {
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                int status = response.code();
                String emailTemp = "", passTemp, authTemp = "",statusUser="";

                Log.e("Response Status ", String.valueOf(status));

                for (Users.UserItem user : response.body().getUsers()) {
                    emailTemp = user.getEmail();
                    passTemp = user.getPassword();
                    authTemp = user.getToken_authentication();

//                    Log.e("Response Status ",emailTemp+", "+passTemp+", "+authTemp);
                    if (emailTemp.equals(emailIn.getText().toString()) && passTemp.equals(passIn.getText().toString())) {
                        statusUser="terdaftar";
                        break;
                    }
                }
                if(statusUser.equals("terdaftar")){
                    SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);

                    SharedPreferences.Editor sp_editor = set_shared_preference.edit();

                    sp_editor.putString("email", emailTemp);
                    sp_editor.putString("token_authentication", authTemp);
                    sp_editor.commit();

                    Intent i = new Intent(LoginActivity.this, Home.class);
                    startActivity(i);
                    finish();
                    //Toast.makeText(LoginActivity.this, "test.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Fails Login.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("OnFailure ", t.toString());

            }
        });
    }

    /*public String get_data(String url_target){
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        retrofit = new Retrofit.Builder().baseUrl(url_target)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        login_api = retrofit.create(LoginApi.class);

        // // implement interface for get all user

        Call<Users> call = login_api.getUsers();
        call.enqueue(new Callback<Users>() {
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                int status = response.code();
                String emailTemp="", passTemp="", authTemp="", statusTemp="";

                Log.e("Response Status ", String.valueOf(status));

                for (Users.UserItem user : response.body().getUsers()) {
                    emailTemp = user.getEmail();
                    passTemp = user.getPassword();
                    authTemp = user.getToken_authentication();

//                    Log.e("Response Status ",emailTemp+", "+passTemp+", "+authTemp);
                    if (emailTemp.equals(emailIn.getText().toString()) && passTemp.equals(passIn.getText().toString())) {
                        statusTemp="terdaftar";
                        statusUser(statusTemp,emailTemp,passTemp,authTemp);
                        break;
                    } else {
                        statusTemp="";
                        statusUser(statusTemp,"","","");
                    }
                }
                Log.e("Response retro ",emailTemp+", "+passTemp+", "+authTemp+", "+statusTemp);
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("OnFailure ", t.toString());

            }
        });


        Log.e("Response Status2 ", email + ", " + pass + ", " + auth);

        Log.e("Response LoginStatus ",  String.valueOf(result));
        return result;
    }

    public void statusUser(String status, String emailP, String passP, String authP){
        email = emailP;
        pass = passP;
        auth = authP;
        result = status;
    }

    public void checkLogin(String val){

        Log.e("Response Login ", val);
        if(val.equals("terdaftar")){
            SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);

            SharedPreferences.Editor sp_editor = set_shared_preference.edit();

            sp_editor.putString("email", email);
            sp_editor.putString("token_authentication", auth);
            sp_editor.commit();

            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            finish();
            //Toast.makeText(LoginActivity.this, "test.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LoginActivity.this, "Fails Login.", Toast.LENGTH_SHORT).show();
        }


    }

    class ApiConnect extends AsyncTask<String, String, String> {



        @Override

        protected String doInBackground(String... params) {
            Log.e("Response Status ", params[0]);
            return get_data(params[0]);
        }


        @Override

        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                checkLogin(s);

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

    }*/
}