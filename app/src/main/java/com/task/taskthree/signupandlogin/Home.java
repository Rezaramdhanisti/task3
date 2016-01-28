package com.task.taskthree.signupandlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    TextView btnLogout;
    SharedPreferences get_shared_preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        Toast.makeText(Home.this, "Welcome : "+get_shared_preference.getString("email",""), Toast.LENGTH_SHORT).show();

        btnLogout = (TextView)findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sp_editor = get_shared_preference.edit();
                sp_editor.putString("email", "");
                sp_editor.putString("token_authentication", "");
                sp_editor.commit();

                Toast.makeText(Home.this, "Logout.", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Home.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(Home.this, "Welcome : "+get_shared_preference.getString("email",""), Toast.LENGTH_SHORT).show();
    }
}
