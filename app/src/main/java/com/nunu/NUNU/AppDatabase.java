package com.nunu.NUNU;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserInfo.class,Note.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase INSTANCE;
    public abstract UserDao UserDao(); //UserDao 인터페이스
    //public abstract LensDao LensDao(); //LensDao 인터페이스

    public static AppDatabase getAppDatabase(Context context){
        if(INSTANCE ==null) {
            //동시에 2개의 INSTANCE가 생성되는 것을 막기위한 synchronized
            synchronized (AppDatabase.class) {
                if(INSTANCE==null) {
                    //데이터 베이스 생성부분
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"lens_database")
                            .build();
                }
            }
        }
        return  INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
