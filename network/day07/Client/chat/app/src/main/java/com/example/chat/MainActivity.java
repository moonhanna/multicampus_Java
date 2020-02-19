package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);

    }

    public void login(View v){

        String ip = et1.getText().toString();
        String port = et2.getText().toString();
        String id = et3.getText().toString();

        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        intent.putExtra("ip",ip);
        intent.putExtra("port",port);
        intent.putExtra("id",id);
        startActivity(intent);

    }

}
