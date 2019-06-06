package com.dyf.hrd;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DIYActivity extends AppCompatActivity {
    private List<Button> buttonList = new ArrayList<>();
    private ConstraintLayout diyPage;
    private GridLayout gridLayout;
    private EditText gameTitle;
    private int _width, _height, _base, _gameBoardLeft, _gameBoardTop;
    private int gameBoard[][];
    private Button gameBox;
    private int[] btnID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);
        diyPage = findViewById(R.id.diy_page);
        gridLayout = findViewById(R.id.gridLayout);
        gameTitle = findViewById(R.id.level_name);
        gameBox = findViewById(R.id.game_box);
        init();
    }

    private void init() {
        btnID = new int[]{
                R.id.sample_small_box,
                R.id.sample_cue_box,
                R.id.sample_vertical_box,
                R.id.sample_horizontal_box
        };
        int[] btnNum = {4, 1, 4, 1};
        gameBoard = new int[5][4];
//        _gameBoardLeft = (int)gameBox.getX();
//        _gameBoardTop = (int)gameBox.getY();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 4; j++)
                gameBoard[i][j] = 0;
        final DIYActivity context = this;
        _width = (int)getResources().getDimension(R.dimen.box_width);
        _height = (int)getResources().getDimension(R.dimen.box_height);
        _base = _width / 4;
        for (int i = 0; i < btnID.length; i++) {
            Button button = findViewById(btnID[i]);
            button.setText(Integer.toString(btnNum[i]));
            button.setTag(btnNum[i]);
            button.setOnTouchListener(new View.OnTouchListener() {
                int lastX, lastY;
                Button tempButton;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int eventType = event.getAction();

                    int mWidth, mHeight, width ,height;
                    mWidth = v.getWidth();
                    mHeight = v.getHeight();
                    if (mWidth == mHeight) {
                        if (v.getId() == R.id.sample_small_box) {
                            width = height = _base;
                        } else {
                            width = height = _base * 2;
                        }

                    } else {
                        if (mHeight > mWidth) {
                            height = 2 * _base;
                            width = _base;
                        } else {
                            height = _base;
                            width = 2 * _base;
                        }
                    }
                    if (((int)v.getTag()) == 0)
                        return true;
                    switch (eventType) {
                        case MotionEvent.ACTION_DOWN:
                            lastY = (int)event.getRawY();
                            lastX = (int)event.getRawX();
                            tempButton = new Button(context);
                            ConstraintLayout.LayoutParams lp =
                                    new ConstraintLayout.LayoutParams(width, height);
                            lp.leftToLeft = R.id.diy_page;
                            lp.topToTop = R.id.diy_page;
                            lp.leftMargin = v.getLeft() + gridLayout.getLeft() + (mWidth - width) / 2;
                            lp.topMargin = v.getTop() + gridLayout.getTop() + (mHeight - height) / 2;
                            tempButton.setLayoutParams(lp);
                            diyPage.addView(tempButton);

                            Button sample = findViewById(getSampleID(tempButton));
                            int num = Integer.valueOf(sample.getText().toString());
                            sample.setText(Integer.toString(--num));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int newX, newY;
                            newX = (int)event.getRawX();
                            newY = (int)event.getRawY();
                            ConstraintLayout.LayoutParams newlp =
                                    (ConstraintLayout.LayoutParams)tempButton.getLayoutParams();
                            newlp.topMargin += newY - lastY;
                            newlp.leftMargin += newX - lastX;
                            tempButton.setLayoutParams(newlp);
                            lastX = newX;
                            lastY = newY;
                            break;
                        case MotionEvent.ACTION_UP:
                            if (canPutDown(tempButton))
                                putDownButton(tempButton);
                            else
                                removeButton(tempButton);
                            return true;
                    }
                    return false;
                }
            });
        }
    }

    private int getSampleID(Button button) {
        int width, height;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)button.getLayoutParams();
        width = layoutParams.width;
        height = layoutParams.height;
        if (width == 2 * _base) {
            if (height == 2 * _base)
                return R.id.sample_cue_box;
            else
                return R.id.sample_horizontal_box;
        } else {
            if (height == 2 * _base)
                return R.id.sample_vertical_box;
            else
                return R.id.sample_small_box;
        }
    }

    private boolean canPutDown(Button button) {
        int _left, _top, left, top, width, height;
        if (outOfBox(button))
            return false;
        else {
            _left = button.getLeft();
            _top = button.getTop();
            left = (int)(_left - gameBox.getLeft() + 0.4 * _base) / _base;
            top = (int)(_top - gameBox.getTop() + 0.4 * _base) / _base;
            width = button.getWidth() / _base - 1;
            height = button.getHeight() / _base - 1;
            if (    gameBoard[top][left] == 0 &&
                    gameBoard[top + height][left + width] == 0 &&
                    gameBoard[top][left + width] == 0 &&
                    gameBoard[top + height][left] == 0 ) {
                return true;
            } else
                return false;
        }
    }

    private boolean outOfBox(Button button) {
        int _left, _top, width, height;
        _left = button.getLeft();
        _top = button.getTop();
        width = button.getWidth() / _base - 1;
        height = button.getHeight() / _base - 1;
        if ((_left - gameBox.getLeft() + 0.4 * _base) < 0 ||
                (_top - gameBox.getTop() + 0.4 * _base) < 0 ||
                (int)((_left - gameBox.getLeft() + (0.4 + width) * _base) / _base) > 3 ||
                (int)((_top - gameBox.getTop() + (0.4 + height) * _base) / _base) > 4)
            return true;
        else
            return false;
    }

    private void putDownButton(Button button) {
        int _left, _top, left, top, width, height;
        _left = button.getLeft();
        _top = button.getTop();
        left = (int)((_left - gameBox.getLeft() + 0.4 * _base) / _base);
        top = (int)((_top - gameBox.getTop() + 0.4 * _base) / _base);
        width = button.getWidth() / _base - 1;
        height = button.getHeight() / _base - 1;
        gameBoard[top][left] = 1;
        gameBoard[top + height][left + width] = 1;
        gameBoard[top][left + width] = 1;
        gameBoard[top + height][left] = 1;
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams)button.getLayoutParams();
        lp.leftMargin = gameBox.getLeft() + left * _base ;
        lp.topMargin = gameBox.getTop() + top * _base;
        button.setLayoutParams(lp);
        button.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY, width, height, _left, _top;
            ConstraintLayout.LayoutParams lp;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventType = event.getAction();
                switch (eventType) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int)event.getRawX();
                        lastY = (int)event.getRawY();
                        _left = (v.getLeft() - gameBox.getLeft()) / _base;
                        _top = (v.getTop() - gameBox.getTop()) / _base;
                        width = v.getWidth() / _base - 1;
                        height = v.getHeight() / _base - 1;
                        gameBoard[_top][_left] = 0;
                        gameBoard[_top + height][_left + width] = 0;
                        gameBoard[_top + height][_left] = 0;
                        gameBoard[_top][_left + width] = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int newX, newY;
                        newX = (int)event.getRawX();
                        newY = (int)event.getRawY();
                        lp = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                        lp.leftMargin += newX - lastX;
                        lp.topMargin += newY - lastY;
                        v.setLayoutParams(lp);
                        lastX = newX;
                        lastY = newY;
                        break;
                    case MotionEvent.ACTION_UP:
                        int left, top;
                        if (canPutDown((Button)v)) {
                            left = (int)((v.getLeft() - gameBox.getLeft() + 0.4 * _base) / _base);
                            top = (int)((v.getTop() - gameBox.getTop() + 0.4 * _base) / _base);
                            gameBoard[top][left] = 1;
                            gameBoard[top + height][left + width] = 1;
                            gameBoard[top][left + width] = 1;
                            gameBoard[top + height][left] = 1;

                            lp = (ConstraintLayout.LayoutParams)v.getLayoutParams();
                            lp.leftMargin = gameBox.getLeft() + left * _base;
                            lp.topMargin = gameBox.getTop() + top * _base;
                            v.setLayoutParams(lp);
                        } else if(!outOfBox((Button)v)) {
                            lp = (ConstraintLayout.LayoutParams)v.getLayoutParams();
                            lp.leftMargin = gameBox.getLeft() + _left * _base;
                            lp.topMargin = gameBox.getTop() + _top * _base;
                            gameBoard[_top][_left] = 1;
                            gameBoard[_top + height][_left + width] = 1;
                            gameBoard[_top + height][_left] = 1;
                            gameBoard[_top][_left + width] = 1;
                            v.setLayoutParams(lp);
                        } else {
                            removeButton((Button)v);
                        }
                        return true;
                }

                return false;
            }
        });

        buttonList.add(button);

        Button sample = findViewById(getSampleID(button));
        int num = (int)sample.getTag();
        sample.setTag(--num);
    }

    void print() {
        for(int[] l:gameBoard) {
            String line = "";
            for (int i = 0; i < 4; i++)
                line += Integer.toString(l[i]) + " ";
            Log.d("game bord", line);
        }
    }

    private void removeButton(Button button) {
        diyPage.removeView(button);
        buttonList.remove(button);
        Button sample = findViewById(getSampleID(button));
        int num = Integer.valueOf(sample.getText().toString()) + 1;
        sample.setText(Integer.toString(num));
        sample.setTag(num);
    }

    private void check() throws Exception {
        if (buttonList.size() != 10)
            throw new Exception("还有" + Integer.toString(10 - buttonList.size()) + "个没有添加哦~");
        if (gameTitle.getText().toString() == "" || gameTitle.getText().toString().isEmpty())
            throw new Exception("关卡名字不能为空哦~");
    }

    public void save(View view) {
//        Log.d("[[[[[[[[[[", Boolean.toString(gameTitle.getText().toString().isEmpty()));
//        Log.d("content", "=" + gameTitle.getText().toString() + "=");
        try {
            check();
            int data[][] = new int[10][3];
            Button btn;
            int baseLeft, baseTop;
            baseLeft = gameBox.getLeft();
            baseTop = gameBox.getTop();
            for (int i = 0; i < 10; i++) {
                btn = buttonList.get(i);
                int left, top, width, height;
                left = (btn.getLeft() - baseLeft) / _base;
                top = (btn.getTop() - baseTop) / _base;
                width = btn.getWidth() / _base;
                height = btn.getHeight() / _base;
                data[i][0] = left + top * 4;
                data[i][1] = width;
                data[i][2] = height;
            }
            Level level = new Level(Arrays.deepToString(data), gameTitle.getText().toString());
            LevelViewModel levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
            levelViewModel.insert(level);
            Toast toast = Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
