package com.spark.android.personaltimeline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.sql.SQLException;


public class Login extends ActionBarActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText editTextPassword;
    Button btnSignin, btnShowHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            // for onetime signup
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            Intent intent = new Intent();
            intent.setClass(this,SignUp.class);
            startActivity(intent);

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
// create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        try {
            loginDataBaseAdapter = loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //show hint
        btnShowHint=(Button) findViewById(R.id.buttonShowHint);
        final String showHint= loginDataBaseAdapter.getHint();
        btnShowHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), showHint, Toast.LENGTH_LONG).show();
            }
        });

        btnSignin = (Button) findViewById(R.id.buttonSignIn);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
                String password=editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(password);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword)) {
                    Toast.makeText(Login.this, "Congrats: Login Successful", Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(getApplicationContext(),MyCalendarActivity.class);
                    startActivity(intent);
                }
               else
                {
                    Toast.makeText(Login.this, "Wrong Password ", Toast.LENGTH_LONG).show();
                }
            }


        });
    }


}
