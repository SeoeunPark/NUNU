package com.nunu.NUNU;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class Option extends Fragment {
    User User = new User();
    modifyname ChangeName = new modifyname();
    modifysight ChangeSight = new modifysight();
    InitSetting ChangeAll = new InitSetting();
    Dialog delete_all_dia;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        delete_all_dia = new Dialog(getActivity());
        delete_all_dia.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        delete_all_dia.setContentView(R.layout.all_infodelete_dialog);
        View view = inflater.inflate(R.layout.fragment_option, null);
        Context context=container.getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();

        ImageView changeName = (ImageView)view.findViewById(R.id.changename);
        changeName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeName).commitAllowingStateLoss();
                        Intent intent = new Intent(getActivity(), modifyname.class);
                        startActivity(intent);
            }
        });

        ImageView changeSight = (ImageView)view.findViewById(R.id.changesight);
        changeSight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeSight).commitAllowingStateLoss();
                Intent intent = new Intent(getActivity(), modifysight.class);
                startActivity(intent);
            }
        });

        ImageView deleteAll = (ImageView)view.findViewById(R.id.deletedata);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_all_dia.show();
                delete_all_dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
                Button noBtn = delete_all_dia.findViewById(R.id.noBtn);
                Button yesBtn = delete_all_dia.findViewById(R.id.yesBtn);
                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 원하는 기능 구현
                        db.UserDao().deleteAll();
                        Toast.makeText(context,"데이터가 초기화 되었습니다.",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeAll).commitAllowingStateLoss();
                        delete_all_dia.dismiss(); // 다이얼로그 닫기
                    }
                });
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 원하는 기능 구현
                        delete_all_dia.dismiss(); // 다이얼로그 닫기
                    }
                });
            }
        });

        ImageView updateAll = (ImageView)view.findViewById(R.id.setdata);
        updateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeAll).commitAllowingStateLoss();
            }
        });
        return view;
    }



}