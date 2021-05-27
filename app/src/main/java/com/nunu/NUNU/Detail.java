package com.nunu.NUNU;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Detail extends AppCompatActivity {
    private static final String TAG= "Detail";
    public static final int EDIT_NOTE_REQUEST =2;
    private Button cancel;
    private ImageView dimage;
    private TextView dday;
    private TextView dname;
    private TextView dtype;
    private TextView dminus;
    private TextView dcnt;
    private TextView dtype_om;
    private TextView dperiod_s;
    private TextView dperiod_e;
    private TextView dinfo;

    private String idday;
    private String idname;
    private String idtype;
    private String idcnt;
    private String idtype_om;
    private String idperiod_s;
    private String idperiod_e;
    private String idinfo;
    private String idid;
    private String  idcl;
    NotificationManager manager;
    NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_detail);


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth()*0.8);
        int height = (int) (display.getHeight()*0.65);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
        this.setFinishOnTouchOutside(false);

        cancel = (Button) findViewById(R.id.go_out);
        dminus =(TextView) findViewById(R.id.d_day_text2); // - 표시
        dday = (TextView) findViewById(R.id.d_day_text3); //d-day 남은 기간 text
        dname = (TextView) findViewById(R.id.detail_name); //렌즈 이름
        dtype = (TextView) findViewById(R.id.detail_type); //소프트/하드/미용
        dcnt = (TextView) findViewById(R.id.detail_type2);
        dtype_om = (TextView) findViewById(R.id.type_om);
        dperiod_s = (TextView) findViewById(R.id.detail_period_start);
        dperiod_e = (TextView) findViewById(R.id.detail_period_end);
        dinfo = (TextView) findViewById(R.id.detail_info);
        dimage = (ImageView) findViewById(R.id.nunu_ch); //누누 캐릭터

        //intent로 값 받아오기
        idid =  Integer.toString(intent.getExtras().getInt("id"));
        idname = intent.getExtras().getString("name");
        idtype = intent.getExtras().getString("type");
        idcnt = Integer.toString(intent.getExtras().getInt("cnt"));
        idtype_om = Integer.toString(intent.getExtras().getInt("period")); //1 이면 원데이 2이면 먼슬리
        idcl = intent.getExtras().getString("cl"); //값 잘 들어감
        idperiod_s = intent.getExtras().getString("start");
        idperiod_e = intent.getExtras().getString("end");

        //디데이 구하기
        String str = idperiod_e;
        String[] array = str.split("/");
        int cyear = Integer.parseInt(array[0]);
        int cmonth = Integer.parseInt(array[1]);
        int cday = Integer.parseInt(array[2]);

        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.of(cyear, cmonth, cday);
        long substract = ChronoUnit.DAYS.between(fromDate, toDate);

        //-
        if(substract<0){
            dminus.setText("+");
            dday.setText(Integer.toString(Math.abs((int)substract)));
        }else if(substract==0){
            dminus.setText("-");
            dday.setText("DAY");
        }else{
            if(substract==1){
                showNoti();
            }
            dminus.setText("-");
            dday.setText(Integer.toString((int)substract));
        }

        //원데이인지 / 먼슬리인지
        if (idtype_om.equals("1")) {
            idtype_om = "원데이 렌즈";
            dinfo.setText("원데이 렌즈는 반드시 한번만 사용해주세요.\n일반형 1회용 렌즈는 보통 8시간의 착용시간을\n권장합니다.");
        } else {
            dperiod_s.setVisibility(View.VISIBLE);
            dperiod_s.setText("착용 주기: "+idperiod_s);
            idtype_om = "기간 렌즈";
            dinfo.setText("기간 렌즈는 한번 개봉하고 사용기한이 지나면\n 반드시 폐기를 해주어야 합니다.\n 개봉하지 않은 렌즈도 5년이 지나면 버려야 합니다. ");
        }

        if (idcl.equals("오렌지")) {
            dimage.setImageResource(R.drawable.orange);
        } else if (idcl.equals("연갈색")) {
            dimage.setImageResource(R.drawable.mocha);
        } else if (idcl.equals("갈색")) {
            dimage.setImageResource(R.drawable.wood);
        } else if (idcl.equals("회색")) {
            dimage.setImageResource(R.drawable.gray);
        } else if (idcl.equals("검정색")) {
            dimage.setImageResource(R.drawable.black);
        } else if (idcl.equals("노란색")) {
            dimage.setImageResource(R.drawable.yellow);
        } else if (idcl.equals("녹색")) {
            dimage.setImageResource(R.drawable.green);
        } else if (idcl.equals("분홍색")) {
            dimage.setImageResource(R.drawable.pink);
        } else if (idcl.equals("보라색")) {
            dimage.setImageResource(R.drawable.purple);
        } else {
            dimage.setImageResource(R.drawable.blue);
        }

        dname.setText(idname);
        dtype.setText(idtype);
        dcnt.setText(idcnt);
        dtype_om.setText(idtype_om);
        dperiod_e.setText(idperiod_e);




        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stay, R.anim.sliding_down);
            }
        });


    }


    public void showNoti(){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel( new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT) );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        builder.setContentTitle("D-1");
        builder.setContentText(idname+" 렌즈 유효기간이 하루 남았습니다!");
        builder.setSmallIcon(R.drawable.ic_noti);
        //builder.setSmallIcon(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ? R.drawable.appicon : R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        manager.notify(1,notification);
    }

    }