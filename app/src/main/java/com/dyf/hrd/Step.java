package com.dyf.hrd;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class Step {
    int buttonID;
    int deltaTop, deltaLeft;

    public Step(int btn, int left, int top) {
        buttonID = btn;
        deltaLeft = left;
        deltaTop = top;
    }

    public void undo(final GameActivity activity) {
        final Button button = activity.findViewById(buttonID);
        final ConstraintLayout.LayoutParams layout = (ConstraintLayout.LayoutParams)button.getLayoutParams();
        int base = activity._base;
        int width, height, left, top;
        width = layout.width / base - 1;
        height = layout.height / base - 1;
        left = layout.leftMargin / base;
        top = layout.topMargin / base;
        Animation animation = new TranslateAnimation(0, -deltaLeft, 0, -deltaTop);
        animation.setDuration(1);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.leftMargin -= deltaLeft;
                layout.topMargin -= deltaTop;
                button.setLayoutParams(layout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animation.setFillEnabled(true);
        button.startAnimation(animation);

        activity.gameBoard[top][left] = 0;
        activity.gameBoard[top][left + width] = 0;
        activity.gameBoard[top + height][left] = 0;
        activity.gameBoard[top + height][left + width] = 0;

        top -= deltaTop / base;
        left -= deltaLeft / base;

        activity.gameBoard[top][left] = 1;
        activity.gameBoard[top][left + width] = 1;
        activity.gameBoard[top + height][left] = 1;
        activity.gameBoard[top + height][left + width] = 1;
    }
}
