package com.nunu.NUNU;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChangeName extends Fragment {

    User User = new User();
    TextView nowText;
    EditText change_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_change_name, container, false);
        ButtonAction(rootView);
        getNameDB(rootView);
        return rootView;
    }


    private void getNameDB(ViewGroup rootView){
        Context context = getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        nowText = rootView.findViewById(R.id.nowText);
        String name = db.UserDao().getName();
        nowText.setText("현재 등록된 이름은 "+name+"이에요.");
        db.close();
    }

    private void changeName(ViewGroup rootView){
        Context context = getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        change_name = rootView.findViewById(R.id.change_name);
        db.close();
    }
    private void ButtonAction(ViewGroup rootView) {
        Context context = getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        change_name = rootView.findViewById(R.id.change_name);
        SimpleDateFormat fdate = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        ImageButton gobackbtn = (ImageButton)rootView.findViewById(R.id.exit);
        db.close();
        gobackbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, User).commitAllowingStateLoss();
            }
        });
        ImageButton donebtn = (ImageButton)rootView.findViewById(R.id.done);
        donebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(change_name.getText().toString())){
                    Toast.makeText(context,"변경할 이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    db.UserDao().insert(new UserInfo(change_name.getText().toString(),db.UserDao().getLeft(),db.UserDao().getRight(),fdate.format(date)));
                    Toast.makeText(context,"이름이 변경되었습니다.",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, User).commitAllowingStateLoss();
                    db.close();
                }
            }
        });
    }


}