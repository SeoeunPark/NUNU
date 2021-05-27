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

public class modifyname extends AppCompatActivity {
    TextView nowText;
    EditText change_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyname);
        ButtonAction();
        getNameDB();
    }

    //현재 등록된 이름을 데이터베이스에서 가져오는 메소드
    private void getNameDB(){
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        nowText = findViewById(R.id.nowText);
        String name = db.UserDao().getName();
        nowText.setText("현재 등록된 이름은 "+name+"이에요.");
        db.close();
    }

    // 각 버튼별로 클릭 시 발생하는 메소드
    private void ButtonAction() {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        change_name = findViewById(R.id.change_name);
        SimpleDateFormat fdate = new SimpleDateFormat("MM-dd");
        Date date = new Date();

        //닫기버튼 클릭시 발생하는 메소드
        ImageButton gobackbtn = (ImageButton)findViewById(R.id.exit);
        db.close();
        gobackbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //완료버튼 클릭시 발생하는 메소드
        ImageButton donebtn = (ImageButton)findViewById(R.id.done);
        donebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(change_name.getText().toString())){
                    Toast.makeText(getApplicationContext(),"변경할 이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    db.UserDao().insert(new UserInfo(change_name.getText().toString(),db.UserDao().getLeft(),db.UserDao().getRight(),fdate.format(date)));
                    Toast.makeText(getApplicationContext(),"이름이 변경되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                    db.close();
                }
            }
        });
    }

}