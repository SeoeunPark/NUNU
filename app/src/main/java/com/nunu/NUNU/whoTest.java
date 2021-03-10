package com.nunu.NUNU;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class whoTest extends Fragment {

    private MyDialog Dialog_Listener;

    public whoTest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_who, container, false);
        Context context = container.getContext();

        View.OnClickListener Confirm = new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_Listener.dismiss();
            }
        };

        Button ma = (Button) view.findViewById(R.id.ma);
        ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("정상일까? 난시일까?");
//                builder.setMessage("당신은 난시입니다!");
//                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//                builder.create().show();

                Dialog_Listener = new MyDialog(getContext(),Confirm,"정상일까? 난시일까?","당신은 난시입니다!");
                Dialog_Listener.show();
            }
        });
        Button a = (Button) view.findViewById(R.id.a);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("정상일까? 난시일까?");
//                builder.setMessage("당신은 정상입니다!");
//                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//                builder.create().show();
                Dialog_Listener = new MyDialog(getContext(),Confirm,"정상일까? 난시일까?","당신은 정상입니다!");
                Dialog_Listener.show();
            }
        });

        return view;
    }
}