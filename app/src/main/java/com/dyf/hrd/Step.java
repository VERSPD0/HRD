package com.dyf.hrd;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class Step {
    int buttonID;
//    int from[];  //[left, top]
//    int to[];
    int deltaTop, deltaLeft;

    public Step(int btn, int left, int top) {
        buttonID = btn;
//        from = f;
//        to = t;
        deltaLeft = left;
        deltaTop = top;
    }

    public void undo(Activity activity) {
        final Button button = activity.findViewById(buttonID);
        final ConstraintLayout.LayoutParams layout = (ConstraintLayout.LayoutParams)button.getLayoutParams();
        Animation animation = new TranslateAnimation(0, -deltaLeft, 0, -deltaTop);
        animation.setDuration(200);
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
    }
}
