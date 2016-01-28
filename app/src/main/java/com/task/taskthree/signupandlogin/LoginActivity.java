package com.task.taskthree.signupandlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    String hasil ;
    String email,pass,auth;

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
                    new ApiConnect().execute("http://private-fc0d3-geza.apiary-mock.com");
                }
            });
        }else{
            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String get_data(String url_target){

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        retrofit = new Retrofit.Builder().baseUrl(url_target)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        login_api = retrofit.create(LoginApi.class);

        // // implement interface for get all user

        Call<Users> call = login_api.getUsers();
        call.enqueue(new Callback<Users>() {
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                int status = response.code();
                String emailTemp,passTemp,authTemp;

                Log.e("Response Status ", String.valueOf(status));

                for (Users.UserItem user : response.body().getUsers()) {
                    emailTemp = user.getEmail();
                    passTemp = user.getPassword();
                    authTemp = user.getToken_authentication();
                    if (emailTemp.equals(emailIn.getText().toString())&&passTemp.equals(passIn.getText().toString())){
                            email = emailTemp;
                            pass = passTemp;
                            auth = authTemp;
                            hasil = "terdaftar";
                    }else{
                        hasil =  "";
                    }
                }
            }

            @Override

            public void onFailure(Throwable t) {

                Log.e("OnFailure ", t.toString());

            }
        });
        return hasil;
    }

    public void checkLogin(String hasil){
        Log.e("Response Login ", hasil);
        if(hasil.equals("terdaftar")){
            SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);

            SharedPreferences.Editor sp_editor = set_shared_preference.edit();

            sp_editor.putString("email", email);
            sp_editor.putString("token_authentication", auth);
            sp_editor.commit();

            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(LoginActivity.this, "Fails Login.", Toast.LENGTH_SHORT).show();
        }
    }

    class ApiConnect extends AsyncTask<String, String, String> {



        @Override

        protected String doInBackground(String... params) {
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

    }
}