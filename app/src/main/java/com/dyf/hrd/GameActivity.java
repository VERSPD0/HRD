package com.dyf.hrd;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Stack;


public class GameActivity extends AppCompatActivity {
    static final String GAMEID = "game.id";
    public int _base, _width, _height;
    public int gameBoard[][];
    private int data[][];
    private int levelID;
    private Stack<Step> stepStack;
    private TextView stepCount;
    private LevelViewModel levelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String game_id = intent.getStringExtra(GAMEID);
        levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
        levelID = Integer.valueOf(game_id);
        data = levelViewModel.getData(levelID);
        initData();
    }

    private void initData() {
        gameBoard = new int[5][4];
        stepStack = new Stack<>();
        stepCount = findViewById(R.id.stepCount);
        stepCount.setText(Integer.toString(0));
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
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                gameBoard[i][j] = 0;
            }
        }
        for(int i = 0; i < 10; i++) {
            int block[] = data[i];
            int row = block[0] / 4;
            int column = block[0] % 4;
            gameBoard[row][column] = 1;
            int width = block[2] - 1;
            int height = block[1] - 1;
            gameBoard[row + width][column + height] = 1;
            gameBoard[row + width][column] = 1;
            gameBoard[row][column + height] = 1;
            Button button = findViewById(boxID[i]);
            if (width == height && width == 1) {
                button.setBackground(getResources().getDrawable(R.drawable.cue_style));
            }
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
                    if (!canMove(v, xD, yD)) return;
                    final ConstraintLayout.LayoutParams layout = (ConstraintLayout.LayoutParams)v.getLayoutParams();
                    record(v, xD, yD);
                    Animation animation = new TranslateAnimation(x, xD, y, yD);
                    animation.setDuration(200);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layout.leftMargin += xD;
                            layout.topMargin += yD;
                            v.setLayoutParams(layout);
                            victoryCheck(v);
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

    private boolean canMove(View v, int x, int y) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)v.getLayoutParams();
        int width = v.getWidth() / _base;
        int height = v.getHeight() / _base;
        int left = layoutParams.leftMargin / _base;
        int top = layoutParams.topMargin / _base;
        int delta;
        if (x != 0) {
            if (x < 0)
                delta = -1;
            else
                delta = width;
            try {
                if (gameBoard[top][left + delta] == 0 && gameBoard[top + height - 1][left + delta] == 0) {
                    if (x < 0) {
                        gameBoard[top][left + width - 1] = gameBoard[top + height - 1][left + width - 1] = 0;
                        gameBoard[top][left - 1] = gameBoard[top + height - 1][left - 1] = 1;
                    } else {
                        gameBoard[top][left] = gameBoard[top + height - 1][left] = 0;
                        gameBoard[top][left + width] = gameBoard[top + height - 1][left + width] = 1;
                    }
                    return true;
                } else
                    return false;
            } catch (Exception e) {
                return false;
            }
        } else {
            if (y < 0)
                delta = -1;
            else
                delta = height;
            try {
                if (gameBoard[top + delta][left] == 0 && gameBoard[top + delta][left + width - 1] == 0) {
                    if (y < 0) {
                        gameBoard[top + height - 1][left] = gameBoard[top + height - 1][left + width - 1] = 0;
                        gameBoard[top -1][left] = gameBoard[top -1][left + width - 1] = 1;
                    } else {
                        gameBoard[top][left] = gameBoard[top][left + width - 1] = 0;
                        gameBoard[top + height][left] = gameBoard[top + height][left + width - 1] = 1;
                    }
                    return true;
                } else
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
//        return true;
    }

    private void victoryCheck(View v) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)v.getLayoutParams();
        int row = layoutParams.topMargin / _base;
        int column = layoutParams.leftMargin / _base;
        int width = v.getWidth() / _base;
        int height = v.getHeight() / _base;
        if (row == 3 && column == 1 && width == 2 && height == 2) {
            String step = stepCount.getText().toString();
            levelViewModel.updateStep(levelID, Integer.valueOf(step));
            Toast toast = Toast.makeText(this, "过关啦！！！", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    private void record(View v, int deltaLeft, int deltaTop) {
        stepStack.push(new Step(v.getId(), deltaLeft, deltaTop));
        stepCount.setText(Integer.toString(stepStack.size()));
    }

    void print() {
        for(int[] l:gameBoard) {
            String line = "";
            for (int i = 0; i < 4; i++)
                line += Integer.toString(l[i]) + " ";
            Log.d("game bord", line);
        }
    }

    public void undo(View view) {
        if (!stepStack.empty()) {
            Step step = stepStack.pop();
            step.undo(this);
        }

        stepCount.setText(Integer.toString(stepStack.size()));
    }

    public void reset(View view) {
        initData();
    }
}
