package com.nunu.NUNU;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class Option extends Fragment {
    User User = new User();
    ChangeName ChangeName = new ChangeName();
    ChangeSight ChangeSight = new ChangeSight();
    InitSetting ChangeAll = new InitSetting();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_option, null);
        Context context=container.getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        ImageButton gobackbtn = (ImageButton)view.findViewById(R.id.goback_btn);
        gobackbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, User).commitAllowingStateLoss();
            }
        });

        ImageView goSettingbtn = (ImageView)view.findViewById(R.id.goSetting);
        goSettingbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivityForResult(intent, 0);
            }
        });
        Button changeName = (Button)view.findViewById(R.id.editname_btn);
        changeName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeName).commitAllowingStateLoss();
            }
        });

        Button changeSight = (Button)view.findViewById(R.id.editsight_btn);
        changeSight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeSight).commitAllowingStateLoss();
            }
        });

        Button deleteAll = (Button)view.findViewById(R.id.deleteAll_btn);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("데이터 초기화");
                builder.setMessage("데이터를 초기화하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.UserDao().deleteAll();
                        Toast.makeText(context,"데이터가 초기화 되었습니다.",Toast.LENGTH_SHORT).show();
                      //  Intent intent = new Intent(getActivity(),InitInfo.class);
                        //startActivity(intent);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeAll).commitAllowingStateLoss();
                    }
                });
                builder.setNegativeButton("아니오",null);

                builder.create().show();
            }
        });

        Button updateAll = (Button)view.findViewById(R.id.updateAll_btn);
        updateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeAll).commitAllowingStateLoss();
            }
        });
        return view;
    }



}