package com.nunu.NUNU;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class EditMonthly extends AppCompatActivity {

        public static final String EXTRA_REPLY = "com.example.NUNU.REPLY";
        public static final String EXTRA_ID = "eid";
        public static final String EXTRA_NAME = "ename";
        public static final String EXTRA_TYPE = "etype";
        public static final String EXTRA_CNT = "ecnt";
        public static final String EXTRA_PERIOD = "eperiod";
        public static final String EXTRA_CL = "ecl";
        public static final String EXTRA_START = "estart";
        public static final String EXTRA_END = "eend";

        Calendar myCalendar = Calendar.getInstance();
        private Button epallete;
        private int eposi;
        private EditText eone_name;
        private EditText eone_cnt;
        private String eclname;  // 렌즈 색
        private String o_dday;
        private Button ecancel; //X 버튼
        private EditText eone_type; // 렌즈유형
        private EditText ecycle; // 렌즈유형
        private LensDao mLensDao;
        private String bcolorhex;
        private int eperiod;
        private int eid;
        Lens lens = new Lens();
        Note note;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_monthly);

            EditText et_Date = (EditText) findViewById(R.id.eMonthly_end);
            eone_name = (EditText)findViewById(R.id.eMonthly_name);
            eone_cnt = (EditText)findViewById(R.id.eMonthly_cnt);
            epallete = (Button) findViewById(R.id.eMonthly_color);
            ecycle = (EditText) findViewById(R.id.eMonthly_cycle);
            ecancel = (Button) findViewById(R.id.eto_main);
            eone_type = (EditText)findViewById(R.id.eMonthly_type);
            Button o_save = findViewById(R.id.eMonthly_save);

            //값 받아오기
            Intent intent = getIntent();

            eclname = intent.getExtras().getString("cl");

            if (eclname.equals("오렌지")) {
                // epallete.setBackgroundColor(Color.parseColor("#c35817"));
                epallete.setBackgroundColor(Color.rgb(195,88,23));
                eclname ="오렌지";
            } else if (eclname.equals("연갈색")) {
                //epallete.setBackgroundColor(Color.parseColor("#966f33"));
                epallete.setBackgroundColor(Color.rgb(150,111,51));
                eclname ="연갈색";
            }  else if (eclname.equals("갈색")) {
                //epallete.setBackgroundColor(Color.parseColor("#493d26"));
                epallete.setBackgroundColor(Color.rgb(73,61,38));
                eclname ="갈색";
            } else if (eclname.equals("회색")) {
                //epallete.setBackgroundColor(Color.parseColor("#657383"));
                epallete.setBackgroundColor(Color.rgb(101,115,131));
                eclname ="회색";
            } else if (eclname.equals("검정색")) {
                //epallete.setBackgroundColor(Color.parseColor("#000000"));
                epallete.setBackgroundColor(Color.rgb(0,0,0));
                eclname ="검정색";
            }  else if (eclname.equals("노란색")) {
                //epallete.setBackgroundColor(Color.parseColor("#fff380"));
                epallete.setBackgroundColor(Color.rgb(255,243,128));
                eclname ="노란색";
            } else if (eclname.equals("녹색")) {
                //epallete.setBackgroundColor(Color.parseColor("#387c44"));
                epallete.setBackgroundColor(Color.rgb(56,124,68));
                eclname ="녹색";
            } else if (eclname.equals("분홍색")) {
                //epallete.setBackgroundColor(Color.parseColor("#e38aae"));
                epallete.setBackgroundColor(Color.rgb(227,138,174));
                eclname ="분홍색";
            } else if (eclname.equals("보라색")) {
                //epallete.setBackgroundColor(Color.parseColor("#9172ec"));
                epallete.setBackgroundColor(Color.rgb(145,114,236));
                eclname ="보라색";
            } else {
                //epallete.setBackgroundColor(Color.parseColor("#4863ad"));
                epallete.setBackgroundColor(Color.rgb(72,99,173));
                eclname ="파랑색";
            }
            //detail에서 받아온 값 보여주기
            eid = intent.getExtras().getInt("id"); //id 값
            eone_name.setText(intent.getExtras().getString("name"));
            eone_type.setText(intent.getExtras().getString("type"));
            eone_cnt.setText(Integer.toString(intent.getExtras().getInt("cnt")));
            eperiod = intent.getExtras().getInt("period"); //1 이면 원데이 2이면 먼슬리
            ecycle.setText(intent.getExtras().getString("start"));
            et_Date.setText(intent.getExtras().getString("end"));


            eone_type.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    type();
                }
            });

            o_save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent replyIntent = new Intent();
                    if (TextUtils.isEmpty(eone_name.getText()) || TextUtils.isEmpty(eone_type.getText()) ||
                            TextUtils.isEmpty(eclname) || TextUtils.isEmpty(eone_cnt.getText()) ||
                            TextUtils.isEmpty(et_Date.getText())) {
                        Toast.makeText(context, "값을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        //String word = mEditWordView.getText().toString();
                        replyIntent.putExtra("eid",eid);
                        replyIntent.putExtra("ename", eone_name.getText().toString()); //name 이란 이름으로 one_name에 들어간 text 저장
                        replyIntent.putExtra("etype", eone_type.getText().toString());
                        replyIntent.putExtra("ecnt",Integer.parseInt(eone_cnt.getText().toString()));
                        replyIntent.putExtra("eperiod",eperiod); //1 이면 원데이 2이면 먼슬리
                        replyIntent.putExtra("estart",ecycle.getText().toString());
                        replyIntent.putExtra("ecl", eclname);
                        replyIntent.putExtra("eend",et_Date.getText().toString());

                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }
                }
            });
            //유효기간
            et_Date.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new DatePickerDialog(com.nunu.NUNU.EditMonthly.this,myDatePicker,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            //x 버튼
            ecancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            //컬러피커
            epallete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openColorPicker();
                }
            });
        } // end of onCreate


        //날짜 입력
        DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel_period();
            }
        };

        private void updateLabel_period() {
            String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
            EditText et_date = (EditText) findViewById(R.id.eMonthly_end);
            et_date.setText(sdf.format(myCalendar.getTime()));
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
                            epallete.setBackgroundColor(color);
                            eposi = position;
                            //

                            if(eposi == 0){
                                eclname = "오렌지";
                            }else if(eposi == 1){
                                eclname = "연갈색";
                            }else if(eposi == 2){
                                eclname = "갈색";
                            }else if(eposi == 3) {
                                eclname = "회색";
                            }else if(eposi == 4){
                                eclname = "검정색";
                            }else if(eposi == 5){
                                eclname = "노란색";
                            }else if(eposi == 6){
                                eclname = "녹색";
                            }else if(eposi ==7){
                                eclname = "파랑색";
                            }else if(eposi ==8){
                                eclname = "분홍색";
                            }else if(eposi ==9) {
                                eclname = "보라색";
                            }else{
                                eclname ="파랑색";
                            }
                        }
                        @Override
                        public void onCancel() {
                            // Cancel 버튼 클릭 시 이벤트
                        }
                    }).show();  // dialog 생성
            //색 잘 들어가는지 확인
            // Toast.makeText(getApplicationContext(), clname, Toast.LENGTH_SHORT).show();
        }


        final Context context = this;

        public void type(){
            {
                final CharSequence[] items ={"소프트렌즈","하드렌즈","미용렌즈"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("옵션 선택");

                alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText type = (EditText) findViewById(R.id.eMonthly_type);
                        type.setText(items[id]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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
