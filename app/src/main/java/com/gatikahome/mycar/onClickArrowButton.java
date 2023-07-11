package com.gatikahome.mycar;

import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class onClickArrowButton implements View.OnClickListener {
   private String imageUrl;
    private  final Cursor imageCursor;
    private  final int screenWidth;
    private final int screenHeight;
    private  final ImageView imageView;

    public onClickArrowButton(Cursor imageCursor, int screenWidth, int screenHeight, ImageView imageView) {
        this.imageCursor = imageCursor;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.imageView = imageView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_next)
        {
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
        if (view.getId()==R.id.btn_previous)
        {
            if (imageCursor.moveToPrevious()){
                String imageUrl = imageCursor.getString(2);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadImage(imageUrl);
                    }
                });
            }
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
