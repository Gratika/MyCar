package com.gatikahome.mycar.bdClass;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static final String DATABASE_NAME = "myCar.db"; // название бд
    private static final int version = 1; // версия базы данных
    public static final String CAR_TABLE = "car_model"; // название таблицы в бд
    // названия столбцов
    public static final String CAR_ID = "_id";
    public static final String CAR_MODEL = "model";

    public static final String IMAGE_TABLE = "car_image"; // название таблицы в бд
    // названия столбцов
    public static final String IMAGE_ID = "_id";
    public static final String IMAGE_NO = "No";
    public static final String IMAGE_URL = "url";
    public static final String IMAGE_CAR_ID = "car_id";

    private Context myContext;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        this.myContext=context;
        //DB_PATH =context.getFilesDir().getPath()+File.separator + DATABASE_NAME;
        DB_PATH = myContext.getDatabasePath(DATABASE_NAME).getPath();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void create_db(){

        File file = new File(DB_PATH);
        //File file = myContext.getDatabasePath(DATABASE_NAME);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(
                InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
