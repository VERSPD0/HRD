package com.dyf.hrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    static final String GAMEID = "game.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String game_id = intent.getStringExtra(GAMEID);
        Log.d(GAMEID, game_id);
    }

    public void showXY(View view) {
        Log.d("fdsfssfafdasfas", String.valueOf(view.getX()));
        Log.d("fdsfssfafdasfas", String.valueOf(view.getY()));
    }
}
