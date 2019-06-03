package com.dyf.hrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import java.security.cert.TrustAnchor;

public class GameActivity extends AppCompatActivity {
    static final String GAMEID = "game.id";
    private int _base, _width, _height;

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
        _width = (int)getResources().getDimension(R.dimen.box_width);
        _height = (int)getResources().getDimension(R.dimen.box_height);
        _base = _width / 4;
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
            params.topMargin = row * _base;
            params.leftMargin = column * _base;
            params.width = block[1] * _base;
            params.height = block[2] * _base;
            params.topToTop = R.id.game_box;
            params.leftToLeft = R.id.game_box;
            button.setLayoutParams(params);
            button.setOnTouchListener(new View.OnTouchListener() {
                int lastX, lastY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int eventType = event.getAction();
                    switch (eventType) {
                        case MotionEvent.ACTION_DOWN:
                            lastX = (int)event.getX();
                            lastY = (int)event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            int deltaX = (int)event.getX() - lastX;
                            int deltaY = (int)event.getY() - lastY;
                            int width, height;
                            width = v.getWidth();
                            height = v.getHeight();
                            if (Math.abs(deltaX) > 5 || Math.abs(deltaY) > 5) {
                                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                                    if (deltaX > 0) {
                                        // go right
                                        wipeTo(v, 0, _base, 0, 0);
                                    } else {
                                        // go left
                                        wipeTo(v, 0, -_base, 0, 0);
                                    }
                                } else {
                                    if (deltaY > 0) {
                                        // go down
                                        wipeTo(v, 0, 0, 0, _base);
                                    } else {
                                        // go up
                                        wipeTo(v, 0, 0, 0, -_base);
                                    }
                                }
                            }
                            return true;
                    }
                    return false;
                }

                private void wipeTo(final View v, int x, final int xD, int y, final int yD) {
                    Animation animation = new TranslateAnimation(x, xD, y, yD);
                    animation.setDuration(200);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ConstraintLayout.LayoutParams layout = (ConstraintLayout.LayoutParams)v.getLayoutParams();
                            layout.leftMargin += xD;
                            layout.topMargin += yD;
                            v.setLayoutParams(layout);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    animation.setFillEnabled(true);
                    v.startAnimation(animation);
                }
            });
        }
    }

}
