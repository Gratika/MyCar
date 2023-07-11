package com.gatikahome.mycar;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.GestureDetectorCompat;

public class SwipeTouchListener implements View.OnTouchListener {
    private GestureDetectorCompat  gestureDetector;

    public SwipeTouchListener(Context context) {
        gestureDetector = new GestureDetectorCompat(
                context,
                new SwipeGestureListener()
        );
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();
            if (Math.abs(deltaX) > Math.abs(deltaY)
                    && Math.abs(deltaX) > SWIPE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (deltaX < 0) {
                    // Выполните действие для свайпа влево
                    // Например, перейдите на другой экран или выполните какие-то действия
                    // в вашем приложении.
                } else {
                    // Выполните действие для свайпа вправо
                    // Например, вернитесь на предыдущий экран или выполните другие действия.
                }
                return true;
            }
            return false;
        }
    }
}

