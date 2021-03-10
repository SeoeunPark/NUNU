package com.nunu.NUNU;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class letterTest extends Fragment {
    public EditText letter;
    public Button submit;
    private MyDialog Dialog_Listener;

    public letterTest() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_letter_test, container, false);
        letter = view.findViewById(R.id.letter);
        submit = view.findViewById(R.id.submit);

        View.OnClickListener Confirm = new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_Listener.dismiss();
            }
        };

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(letter.getText().toString())) {
                    Toast.makeText(getContext(), "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (letter.getText().toString().equals("지금 이 글씨가 보인다면 당신은 난시입니다")) {
                    Dialog_Listener = new MyDialog(getContext(), Confirm, "정상일까? 난시일까?", "사진의 답과 동일합니다.\n당신은 난시입니다!");
                    Dialog_Listener.show();
                } else if (letter.getText().toString().equals("지금이글씨가보인다면당신은난시입니다")){
                    Dialog_Listener = new MyDialog(getContext(), Confirm, "정상일까? 난시일까?", "사진의 답과 동일합니다.\n당신은 난시입니다!");
                    Dialog_Listener.show();
                }else{
                    Dialog_Listener = new MyDialog(getContext(),Confirm,"정상일까? 난시일까?","당신은 정상입니다.");
                    Dialog_Listener.show();
                }

            }
        });
        return view;

    }
}