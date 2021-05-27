package com.nunu.NUNU;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InitInfo extends AppCompatActivity {
    public EditText set_name;
    public EditText set_left;
    public EditText set_right;
    public Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initinfo);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "userinfo-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        set_name = findViewById(R.id.set_name);
        set_left = findViewById(R.id.set_left);
        set_right = findViewById(R.id.set_right);
        add_button = findViewById(R.id.add_button);


        // 버튼 클릭시 실행되는 메소드
        add_button.setOnClickListener(new Button.OnClickListener() {
            // 사용자에게 입력받은 값이 올바른 값인지 판별하는 함수
            public void insertNum(){
                Boolean check = true;
                Boolean check2 = true;
                if(check){
                    char tmp;
                    int count = 0;
                    for(int i=0; i<set_left.getText().toString().length();i++) {
                        tmp = set_left.getText().toString().charAt(i);
                        int num = (int)tmp;
                        if(!(num==45 || num==46 || (num>=48 && num<=57))) {
                            count++;
                        }
                    }
                    if(count!=0){
                        Toast.makeText(InitInfo.this, "좌안 시력을 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        check=true;
                    }else{
                        check = false;
                    }
                }else if(check2){
                    char tmp;
                    int count = 0;
                    for(int i=0; i<set_right.getText().toString().length();i++) {
                        tmp = set_right.getText().toString().charAt(i);
                        int num = (int)tmp;
                        if(!(num==45 || num==46 || (num>=48 && num<=57))) {
                            count++;
                        }
                    }
                    if(count!=0){
                        Toast.makeText(InitInfo.this, "우안 시력을 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        check2=true;
                    }else{
                        check2 = false;
                    }
                }
            }
            @Override
            public void onClick(View v) {
                SimpleDateFormat fdate = new SimpleDateFormat("MM/dd");
                Date date = new Date();
                insertNum();

                //사용자가 입력해야할 모든 것을 입력했는지 확인하는 코드
                if(TextUtils.isEmpty(set_name.getText().toString())){
                    Toast.makeText(InitInfo.this,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(set_left.getText().toString())){
                    Toast.makeText(InitInfo.this,"좌안 시력을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(set_right.getText().toString())) {
                    Toast.makeText(InitInfo.this, "우안 시력을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if((Float.valueOf(set_left.getText().toString())>=-20.0 && Float.valueOf(set_left.getText().toString())<=10.0)==false) {
                    Toast.makeText(InitInfo.this, "좌안 시력이 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
                }else if((Float.valueOf(set_right.getText().toString())>=-20.0 && Float.valueOf(set_right.getText().toString())<=10.0)==false) {
                    Toast.makeText(InitInfo.this, "우안 시력이 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(InitInfo.this,"정보가 입력되었습니다.",Toast.LENGTH_SHORT).show();
                    db.UserDao().insert(new UserInfo(set_name.getText().toString(),set_left.getText().toString(),set_right.getText().toString(),fdate.format(date)));
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    db.close();
                    startActivity(intent);
                }

            }

        });

    }
}