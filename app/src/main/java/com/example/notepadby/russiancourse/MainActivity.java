package com.example.notepadby.android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    EditText login;
    EditText password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submitLogin);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Введите логин", Toast.LENGTH_LONG).show();
                } else if (password.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Введите пароль", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    startActivity(intent);
                }
            }

        });
    }
}