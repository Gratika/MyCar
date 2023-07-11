package com.gatikahome.mycar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.gatikahome.mycar.bdClass.DatabaseHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    Spinner carList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor carModelCursor;
    Cursor modelImageCursor;
    SimpleCursorAdapter carModelAdapter;
    ImageView imageCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carList = findViewById(R.id.car_model);
        imageCar = findViewById(R.id.image_car);
        //imageCar.setOnTouchListener(new SwipeTouchListener(this,modelImageCursor,imageCar,screenWidth(),screenHeight()));

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();

    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //заповнюємо випадаючий список
        getSpinnerData();
        //завантажуємо перше зображення
        getImageData(carList.getSelectedItemId());
        findViewById(R.id.btn_next).setOnClickListener(new onClickArrowButton(modelImageCursor,
                screenWidth(), screenHeight(),imageCar));
        findViewById(R.id.btn_previous).setOnClickListener(new onClickArrowButton(modelImageCursor,
                screenWidth(), screenHeight(),imageCar));

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        carModelCursor.close();
        modelImageCursor.close();
    }
    private void getSpinnerData(){
        //получаем данные из бд в виде курсора
        carModelCursor = db.rawQuery("select * from " + DatabaseHelper.CAR_TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.CAR_ID,DatabaseHelper.CAR_MODEL};
        // создаем адаптер, передаем в него курсор
        carModelAdapter = new SimpleCursorAdapter(this, R.layout.dropdown_two_item_1line,
                carModelCursor, headers, new int[]{R.id.text1, R.id.text2}, 0);
        carList.setAdapter(carModelAdapter);
    }
    private void getImageData(Long carId){
        modelImageCursor = db.rawQuery("select * from "+DatabaseHelper.IMAGE_TABLE+" where "
                             +DatabaseHelper.IMAGE_CAR_ID+"=?",new String[]{String.valueOf(carId)});
        if (modelImageCursor.moveToFirst()){
            String imageUrl = modelImageCursor.getString(2);
             runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadImage(imageUrl);
                }
            });
        }
    }
    private void loadImage(String imageUrl){
        Picasso.get()
                .load(imageUrl)
                .resize(screenWidth(), screenHeight())
                .centerInside()
                .into(imageCar, new Callback() {
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
    private int screenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int screenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}