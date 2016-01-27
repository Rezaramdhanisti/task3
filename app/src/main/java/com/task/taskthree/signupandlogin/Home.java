package com.task.taskthree.signupandlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.tech.NfcBarcode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    TextView btnLogout;
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
        //finish();
        SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        Toast.makeText(Home.this, "Login Success.", Toast.LENGTH_SHORT).show();

        btnLogout = (TextView)findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
                SharedPreferences.Editor sp_editor = set_shared_preference.edit();
                sp_editor.putString("token_authentication", "");
                sp_editor.commit();

                Intent i = new Intent(Home.this, LoginActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(Home.this, "Logout.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
