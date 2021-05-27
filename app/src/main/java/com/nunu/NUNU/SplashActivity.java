package com.nunu.NUNU;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public SharedPreferences prefs; // 키-값 쌍이 포함된 파일을 가리키는 SharedPreferences 객체 선언하기
    Fragment InitSetting = new InitSetting();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2000); //대기 2초로 설정
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        prefs = getSharedPreferences("Pref", MODE_PRIVATE); // 생성하기
        checkFirstRun();

        finish();
    }

    // 앱을 설치 후 첫 실행인지 확인하는 코드
    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent newIntent = new Intent(this, InitInfo.class);
            startActivity(newIntent);
            //getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, InitSetting).commitAllowingStateLoss();
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }else{
            Intent newIntent = new Intent(this, MainActivity.class);
            startActivity(newIntent);
        }
    }

}
