package com.dyf.hrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void startGame(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void continueGame(View view) {
    }

    public void diyGame(View view) {
    }

    public void settingGame(View view) {
    }
}
