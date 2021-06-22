package com.nunu.NUNU;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;


public class InitSetting extends Fragment {
    public EditText set_name;
    public EditText set_left;
    public EditText set_right;
    private TextView show_data;
    private Button out_btn;
    User user = new User();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_init_setting, container, false);
        initInfo(rootView);
        return rootView;
    }

    private void initInfo(ViewGroup rootView){
        Context context = getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();

        set_name = rootView.findViewById(R.id.set_name);
        set_left = rootView.findViewById(R.id.set_left);
        set_right = rootView.findViewById(R.id.set_right);
        out_btn = rootView.findViewById(R.id.out_button);

        out_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_layout, user).commit();
            }
        });

        rootView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat fdate = new SimpleDateFormat("MM/dd");
            Date date = new Date();
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(set_name.getText().toString())){
                    Toast.makeText(context,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(set_left.getText().toString())){
                    Toast.makeText(context,"좌안 시력을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(set_right.getText().toString())) {
                    Toast.makeText(context, "우안 시력을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if((Float.valueOf(set_left.getText().toString())>=-20.0 && Float.valueOf(set_left.getText().toString())<=10.0)==false) {
                    Toast.makeText(context, "좌안 시력이 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
                }else if((Float.valueOf(set_right.getText().toString())>=-20.0 && Float.valueOf(set_right.getText().toString())<=10.0)==false) {
                    Toast.makeText(context, "우안 시력이 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"정보가 입력되었습니다.",Toast.LENGTH_SHORT).show();
                    db.UserDao().insert(new UserInfo(set_name.getText().toString(),set_left.getText().toString(),set_right.getText().toString(),fdate.format(date)));
                    db.close();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, user).commitAllowingStateLoss();
                }
               insertNum();
            }

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
                        Toast.makeText(context, "좌안 시력을 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "우안 시력을 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        check2=true;
                    }else{
                        check2 = false;
                    }
                }
            }

        });


    }

}