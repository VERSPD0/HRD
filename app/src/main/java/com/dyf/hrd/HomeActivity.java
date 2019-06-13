package com.dyf.hrd;

import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        TextView title = findViewById(R.id.textView);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams)title.getLayoutParams();
        lp.topMargin = (int)(200.0 / 1184 * size.y);
        title.setLayoutParams(lp);
        Button button = findViewById(R.id.start_button);
        lp = (ConstraintLayout.LayoutParams)button.getLayoutParams();
        lp.topMargin = (int)(696.0 / 1184 * size.y);
        button.setLayoutParams(lp);
    }


    public void startGame(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void diyGame(View view) {
        Intent intent = new Intent(this, DIYActivity.class);
        startActivity(intent);
    }
}
