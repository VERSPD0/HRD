package com.dyf.hrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void selectGame(View view) {
        String tag = view.getTag().toString();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.GAMEID, tag);
        startActivity(intent);
    }

    public void switchToBuiltin(View view) {
    }

    public void switchToDIY(View view) {
    }
}
