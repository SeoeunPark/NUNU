package com.nunu.NUNU;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class LensInsert extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.nunu.NUNU.REPLY";

    Calendar myCalendar = Calendar.getInstance();

    private int posi;
    private String clname; // 렌즈 색
    private Button cancel; //X 버튼
    private EditText lens_kind;//렌즈 종류
    private EditText lens_name;//렌즈 이름
    private EditText lens_type;//렌즈 유형
    private EditText lens_cnt;//렌즈 개수
    private EditText lens_cycle;//렌즈 주기
    private EditText lens_period;//렌즈 기간
    private Button pallete;//팔레트
    private Button lens_save;
    private EditText et_Date;
    private int kind_num; // 1은 원데이 2는 기간렌즈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens_insert);

        et_Date = (EditText) findViewById(R.id.Oneday_period);
        lens_kind = (EditText)findViewById(R.id.lens_kind);
        lens_name = (EditText)findViewById(R.id.lens_name);
        lens_type = (EditText) findViewById(R.id.lens_type);
        lens_cnt = (EditText) findViewById(R.id.lens_cnt);
        lens_cycle = (EditText)findViewById(R.id.lens_cycle);
        lens_period = (EditText)findViewById(R.id.lens_period);
        pallete = (Button)findViewById(R.id.lens_color);
        lens_save = findViewById(R.id.lens_save);
        cancel = (Button) findViewById(R.id.to_main);

        final Context context = this;

        lens_type.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                type();
            }
        });

        lens_kind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                kind();
            }
        });

        lens_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(lens_name.getText()) || TextUtils.isEmpty(lens_type.getText()) ||
                        TextUtils.isEmpty(clname)// || TextUtils.isEmpty(monthly_start.getText()) ||
                        || TextUtils.isEmpty(lens_period.getText()) || TextUtils.isEmpty(lens_cycle.getText()) ) {
                    Toast.makeText(context, "값을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    //String word = mEditWordView.getText().toString();
                    replyIntent.putExtra("name",lens_name.getText().toString()); //name 이란 이름으로 one_name에 들어간 text 저장
                    replyIntent.putExtra("type",lens_type.getText().toString());
                    replyIntent.putExtra("cnt",Integer.parseInt(lens_cnt.getText().toString()));
                    replyIntent.putExtra("period",kind_num); // 1은 원데이 2는 기간렌즈
                    replyIntent.putExtra("cl",clname);
                    replyIntent.putExtra("start",lens_cycle.getText().toString());
                    replyIntent.putExtra("end",lens_period.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        lens_period.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(LensInsert.this, myDatePicker_end, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //x 버튼
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        //컬러피커
        pallete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });
    } //end of onCreate

    DatePickerDialog.OnDateSetListener myDatePicker_end = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(java.util.Calendar.YEAR, year);
            myCalendar.set(java.util.Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel_end();
        }
    };

    private void updateLabel_end() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        EditText monthly_end = (EditText) findViewById(R.id.lens_period);
        monthly_end.setText(sdf.format(myCalendar.getTime()));
    }

    //색상 선택
    public void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // 렌즈 컬러 리스트
        colors.add("#c35817"); //orange
        colors.add("#966f33"); //wood
        colors.add("#493d26"); //mocha
        colors.add("#657383"); //gray
        colors.add("#000000"); //black
        colors.add("#fff380"); //yellow
        colors.add("#387c44"); //green
        colors.add("#4863ad"); //blue
        colors.add("#e38aae"); //pink
        colors.add("#9172ec"); //purple
        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        //layout.setBackgroundColor(color);  // OK 버튼 클릭 시 이벤트
                        pallete.setBackgroundColor(color);
                        posi = position;
                        //

                        if(posi == 0){
                            clname = "오렌지";
                        }else if(posi == 1){
                            clname = "연갈색";
                        }else if(posi == 2){
                            clname = "갈색";
                        }else if(posi == 3) {
                            clname = "회색";
                        }else if(posi == 4){
                            clname = "검정색";
                        }else if(posi == 5){
                            clname = "노란색";
                        }else if(posi == 6){
                            clname = "녹색";
                        }else if(posi ==7){
                            clname = "파랑색";
                        }else if(posi ==8){
                            clname = "분홍색";
                        }else if(posi ==9) {
                            clname = "보라색";
                        }else{
                            clname="파랑색";
                        }

                    }
                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
        //색 잘 들어가는지 확인
    }
    final Context context = this;
    //렌즈 타입 설정
    public void type(){
        {
            final CharSequence[] items ={"소프트렌즈","하드렌즈","미용렌즈"};
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("유형 선택");

            alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText type = (EditText) findViewById(R.id.lens_type);
                    type.setText(items[id]);
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    //렌즈 종류 설정
    public void kind(){
        {
            final CharSequence[] items ={"원데이렌즈","기간렌즈"};
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("렌즈종류 선택");

            alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText type = (EditText) findViewById(R.id.lens_kind);
                    type.setText(items[id]);
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            if(lens_kind.getText().toString()=="원데이렌즈") {
                    kind_num=1;
                    findViewById(R.id.imageView).setVisibility(View.VISIBLE);

            }else{
                    kind_num=2;
            }
        }
    }
    //메인스레드에서 데이터베이스에 접근할 수 없으므로 AsyncTask를 사용하도록 한다.
    public static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        private LensDao mLensDao;

        public insertAsyncTask(LensDao lensDao) {
            this.mLensDao = lensDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(Note... lens) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            mLensDao.insert(lens[0]);
            return null;
        }
    }

    }