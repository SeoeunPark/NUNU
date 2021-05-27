package com.nunu.NUNU;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class modifysight extends AppCompatActivity {
    TextView showleft;
    TextView showright;
    EditText editleft;
    EditText editright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifysight);
        ButtonAction();
        getSightDB();

    }

    //현재 등록된 시력을 데이터베이스에서 가져오는 메소드
    private void getSightDB(){
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        showleft = findViewById(R.id.showleft);
        String left = db.UserDao().getLeft();
        showleft.setText("좌안 시력 : "+left);
        showright = findViewById(R.id.showright);
        String right = db.UserDao().getRight();
        showright.setText("우안 시력 "+right);
        db.close();
    }

    // 각 버튼별로 클릭 시 발생하는 메소드
    private void ButtonAction() {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        editleft = findViewById(R.id.editleft);
        editright = findViewById(R.id.editright);
        ImageButton gobackbtn = (ImageButton)findViewById(R.id.exit2);
        SimpleDateFormat fdate = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        db.close();

        //닫기버튼 클릭시 발생하는 메소드
        gobackbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //완료버튼 클릭시 발생하는 메소드
        ImageButton donebtn = (ImageButton)findViewById(R.id.done2);
        final Boolean[] check = {true};
        final Boolean[] check2 = {true};
        donebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checknum();

                // 사용자가 모든 입력값을 입력하였는지 확인하는 조건문
                if(TextUtils.isEmpty(editleft.getText().toString())){
                    Toast.makeText(getApplicationContext(),"변경할 좌안 시력을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(editright.getText().toString())){
                    Toast.makeText(getApplicationContext(),"변경할 우안 시력을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if((Float.valueOf(editleft.getText().toString())>=-20.0 && Float.valueOf(editleft.getText().toString())<=10.0)==false) {
                    Toast.makeText(getApplicationContext(), "좌안 시력이 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
                }else if((Float.valueOf(editright.getText().toString())>=-20.0 && Float.valueOf(editright.getText().toString())<=10.0)==false) {
                    Toast.makeText(getApplicationContext(), "우안 시력이 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    db.UserDao().insert(new UserInfo(db.UserDao().getName(),editleft.getText().toString(),editright.getText().toString(),fdate.format(date)));
                    Toast.makeText(getApplicationContext(),"시력이 변경되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                    db.close();
                }
            }

            // 사용자가 입력한 시력에 숫자와 . 이외의 문자가 들어있는지 판별
            public void checknum(){
                if(check[0]){
                    char tmp;
                    int count = 0;
                    for(int i=0; i<editleft.getText().toString().length();i++) {
                        tmp = editleft.getText().toString().charAt(i);
                        int num = (int)tmp;
                        if(!(num==45 || num==46 || (num>=48 && num<=57))) {
                            count++;
                        }
                    }
                    if(count!=0){
                        Toast.makeText(getApplicationContext(), "좌안 시력을 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        check[0] =true;
                    }else{
                        check[0] = false;
                    }
                }else if(check2[0]){
                    char tmp;
                    int count = 0;
                    for(int i=0; i<editright.getText().toString().length();i++) {
                        tmp = editright.getText().toString().charAt(i);
                        int num = (int)tmp;
                        if(!(num==45 || num==46 || (num>=48 && num<=57))) {
                            count++;
                        }
                    }
                    if(count!=0){
                        Toast.makeText(getApplicationContext(), "우안 시력을 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        check2[0] =true;
                    }else{
                        check2[0] = false;
                    }
                }
            }
        });
    }
}