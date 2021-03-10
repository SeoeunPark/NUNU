package com.nunu.NUNU;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigationView;
    private final static int LENS = 1;
    private final static int HOME = 2;
    private final static int PERSON = 3;

    Lens fragment1; // 렌즈 fragment
    EyeTest fragment2; // 홈 fragment
    User fragment3; // 유저 fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (MeowBottomNavigation) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.add(new MeowBottomNavigation.Model(1,R.drawable.eye));
        bottomNavigationView.add(new MeowBottomNavigation.Model(2,R.drawable.test));
        bottomNavigationView.add(new MeowBottomNavigation.Model(3,R.drawable.person));
        //프래그먼트 생성
        fragment1 = new Lens();
        fragment2 = new EyeTest();
        fragment3 = new User();

        //제일 처음 보여지는 창
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commit();
        navi_bar(); // 메뉴 선택
    }
    //bottomnavigationview의 아이콘을 선택 했을때 fragment 띄우기
    public void navi_bar(){
        bottomNavigationView.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });
        bottomNavigationView.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Fragment select_fragment = null;
                switch (item.getId()){
                    case LENS:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                        //select_fragment = new Lens();
                        break;
                    case HOME:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
                        //select_fragment = new EyeTest();
                        break;
                    case PERSON:
                        //select_fragment = new User();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                        break;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,select_fragment).commit();
            }
        });
    }
}