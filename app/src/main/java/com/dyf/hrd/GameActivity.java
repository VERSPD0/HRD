package com.dyf.hrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
    static final String GAMEID = "game.id";
    private int base, width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String game_id = intent.getStringExtra(GAMEID);
        Log.d(GAMEID, game_id);
        int boxID[] = {
                R.id.box0,
                R.id.box1,
                R.id.box2,
                R.id.box3,
                R.id.box4,
                R.id.box5,
                R.id.box6,
                R.id.box7,
                R.id.box8,
                R.id.box9,
        };
//        Button gameBox = findViewById(R.id.game_box);
        width = (int)getResources().getDimension(R.dimen.box_width);
        height = (int)getResources().getDimension(R.dimen.box_height);
        base = width / 4;
        int data[][] = {
                {0, 1, 1}, {3, 1, 1}, {4, 1, 1}, {7, 1, 1},
                {12, 1, 2}, {13, 1, 2}, {14, 1, 2}, {15, 1, 2},
                {9, 2, 1}, {1, 2, 2}
        };
        for(int i = 0; i < 10; i++) {
            int block[] = data[i];
            int row = block[0] / 4;
            int column = block[0] % 4;
            Button button = findViewById(boxID[i]);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(button.getLayoutParams());
            params.topMargin = row * base;
            params.leftMargin = column * base;
            params.width = block[1] * base;
            params.height = block[2] * base;
            params.topToTop = R.id.game_box;
            params.leftToLeft = R.id.game_box;
            button.setLayoutParams(params);
        }
    }

    public void back(View view) {
        Button button = findViewById(R.id.game_box);
        Log.d("-----------", Integer.toString(button.getWidth()));
    }
}
