package com.gatikahome.mycar;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.GestureDetectorCompat;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SwipeTouchListener implements View.OnTouchListener {
    private GestureDetectorCompat  gestureDetector;
    private  final Cursor imageCursor;
    private  final ImageView imageView;
    private  final int screenWidth;
    private final int screenHeight;

    public SwipeTouchListener(Context context, Cursor imageCursor, ImageView imageView, int screenWidth, int screenHeight) {
        gestureDetector = new GestureDetectorCompat(
                context,
                new SwipeGestureListener()
        );
        this.imageCursor = imageCursor;
        this.imageView = imageView;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
                    // действие для свайпа влево
                    Log.d("Swap", "Swape to left");
                    if (imageCursor.moveToPrevious()){
                        String imageUrl = imageCursor.getString(2);
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                loadImage(imageUrl);
                            }
                        });
                    }
                } else {
                    Log.d("Swap", "Swape to right");
                    // Выполните действие для свайпа вправо
                    if (imageCursor.moveToNext()){
                        String imageUrl = imageCursor.getString(2);
                         imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                loadImage(imageUrl);
                            }
                        });
                    }
                }
                return true;
            }
            return false;
        }
    }
    private void loadImage(String imageUrl){
        Picasso.get()
                .load(imageUrl)
                .resize(screenWidth, screenHeight)
                .centerInside()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Картинка успешно загружена и отображена
                        Log.d("image", "Success");
                    }

                    @Override
                    public void onError(Exception e) {
                        // Обработка ошибки загрузки или отображения изображения
                        Log.d("image", "error: "+e.getMessage());
                    }
                });


    }
}

