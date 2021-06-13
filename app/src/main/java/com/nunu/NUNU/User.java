package com.nunu.NUNU;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class User extends Fragment {
    private LineChart lineChart;
    Option option;
    TextView userTextView;
    TextView leftSightTextView;
    TextView rightSightTextView;
    InitSetting initSetting;

    
    public User(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        option = new Option();
        initSetting = new InitSetting();
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_user, container, false);
        initInfo(rootView);
        initGraph(rootView);
        return rootView;
    }


    private void initInfo(ViewGroup rootView){
        SimpleDateFormat sdf = new SimpleDateFormat("k");
        Calendar cal = Calendar.getInstance();
        String h = sdf.format(cal.getTime());
        int hour = Integer.parseInt(h);

        Context context = getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();

        userTextView = rootView.findViewById(R.id.username);
        String name = db.UserDao().getName();
        userTextView.setText(name);


        leftSightTextView = rootView.findViewById(R.id.leftSight);
        String leftSight = db.UserDao().getLeft();
        leftSightTextView.setText(leftSight);

        rightSightTextView = rootView.findViewById(R.id.rightSight);
        String rightSight = db.UserDao().getRight();
        rightSightTextView.setText(rightSight);

    }

    private void initGraph(ViewGroup rootView){
        lineChart = rootView.findViewById(R.id.sightchart);
        Context context = getContext();
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();
        List<Entry> leftEntry = new ArrayList<>();
        leftEntry.add(new Entry(1,(float)0.0));
        for(int i = 0; i< db.UserDao().getAll().size();i++){
            double leftSight = Double.parseDouble(db.UserDao().getAll().get(i).getLeftSight());
            leftEntry.add(new Entry(i+2, (float) leftSight));
        }
        List<Entry> rightEntry = new ArrayList<>();
        rightEntry.add(new Entry(1,(float)0.0));
        for(int i = 0; i< db.UserDao().getAll().size();i++){
            double rightSight = Double.parseDouble(db.UserDao().getAll().get(i).getRightSight());
            rightEntry.add(new Entry(i+2, (float) rightSight));
        }
        LineDataSet set1 = new LineDataSet(leftEntry, "왼쪽 시력");
        LineDataSet set2 = new LineDataSet(rightEntry, "오른쪽 시력");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(true);
        set1.setDrawValues(true);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(Color.parseColor("#646EFF"));
        set2.setValueTextColor(Color.parseColor("#646EFF"));
        set2.setLineWidth(1.5f);
        set2.setDrawCircles(true);
        set2.setDrawValues(true);
        set2.setFillAlpha(65);
        set2.setHighLightColor(Color.rgb(220, 180, 117));
        set2.setDrawCircleHole(true);


        LineData chartData = new LineData();
        chartData.addDataSet(set1);
        chartData.addDataSet(set2);
        lineChart.setData(chartData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.TRANSPARENT);
        xAxis.enableGridDashedLine(8, 24, 0);
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        //lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
    }


}