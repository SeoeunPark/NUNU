package com.nunu.NUNU;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigationView;
    private final static int STRETCHING =1;
    private final static int CALENDAR = 2;
    private final static int HOME = 3;
    private final static int USER = 4;
    private final static int SETTING= 5;

    //Lens fragment1; // 렌즈 fragment
    Stretching fragment1;
    Calendar fragment2;
    Lens fragment3; // 홈 fragment
    User fragment4; // 유저 fragment
    Option fragment5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (MeowBottomNavigation) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.show(3,true);

        bottomNavigationView.add(new MeowBottomNavigation.Model(1,R.drawable.eye));
        bottomNavigationView.add(new MeowBottomNavigation.Model(2,R.drawable.calendar));
        bottomNavigationView.add(new MeowBottomNavigation.Model(3,R.drawable.home));
        bottomNavigationView.add(new MeowBottomNavigation.Model(4,R.drawable.person));
        bottomNavigationView.add(new MeowBottomNavigation.Model(5,R.drawable.setting));
        bottomNavigationView.setPointerIcon(null);
        //프래그먼트 생성
        fragment1 = new Stretching();
        fragment2 = new Calendar();
        fragment3 = new Lens();
        fragment4 = new User();
        fragment5 = new Option();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment3).commitAllowingStateLoss();

        navi_bar(); // 메뉴 선택
    }
    //bottomnavigationview의 아이콘을 선택 했을때 fragment 띄우기
    public void navi_bar(){
        bottomNavigationView.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                case STRETCHING:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                    //select_fragment = new Lens();
                    break;
                case CALENDAR:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
                    //select_fragment = new EyeTest();
                    break;
                case HOME:
                    //select_fragment = new User();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                    break;
                case USER:
                    //select_fragment = new User();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment4).commitAllowingStateLoss();
                    break;
                case SETTING:
                    //select_fragment = new User();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment5).commitAllowingStateLoss();
                    break;
            }
            }
        });
        bottomNavigationView.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Fragment select_fragment = null;

                //getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,select_fragment).commit();
            }
        });
        bottomNavigationView.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }
}