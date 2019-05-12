package com.example.user.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class desktop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
    }

    public void click(View v){
        Intent it = new Intent(desktop.this,MainActivity.class);
        startActivity(it);
        finish();
    }
}
