package com.nunu.NUNU;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;

import com.github.mikephil.charting.data.Entry;
import com.nunu.NUNU.EventDecorator;
import com.nunu.NUNU.OneDayDecorator;
import com.nunu.NUNU.SaturdayDecorator;
import com.nunu.NUNU.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;


public class LensCalendar extends Fragment {

    private List<Note> mDataItemList;
    private NoteAdapter mListAdapter;
    private CalendarDay date;

    String time,kcal,menu;
    String lens_box="";
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;
    MaterialCalendarView materialCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<String> result = new ArrayList<>();
        final NoteAdapter adapter = new NoteAdapter(getActivity());

        for(int i = 0; i< adapter.getItemCount();i++){
            String enddate = adapter.getNoteAt(i).getLens_end();
            result.add(enddate);
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        materialCalendarView = rootView.findViewById(R.id.calendarView);

        TextView calendar_date = rootView.findViewById(R.id.calendar_date);
        TextView calendar_name = rootView.findViewById(R.id.calendar_name);

        //오늘 날짜 출력
        date = CalendarDay.today();

        String today_Year = Integer.toString(date.getYear());
        String today_Month = Integer.toString(date.getMonth() + 1);
        String today_Day = Integer.toString(date.getDay());

        Log.i("Year test", today_Year + "");
        Log.i("Month test", today_Month + "");
        Log.i("Day test", today_Day + "");

        if(today_Month.length()==1){
            today_Month="0"+today_Month;
        }
        if(today_Day.length()==1){
            today_Day="0"+today_Day;
        }
        String today_show_Day = today_Year + "년 " + today_Month + "월 " + today_Day +"일 "+"만료 렌즈";
        String today_shot_Day = today_Year + "/" + today_Month + "/" + today_Day;

        String today_lens_box = "";

        for(int i = 0; i<adapter.getItemCount();i++){
            String today_enddate = adapter.getNoteAt(i).getLens_end();
            if(today_shot_Day.equals(today_enddate)){
                today_lens_box +="- "+adapter.getNoteAt(i).getLens_name()+"\n";
            }
        }

        if(today_lens_box.trim().length() == 0){
            calendar_name.setText("만료예정 렌즈가 없어요");
        }else{
            calendar_name.setText(today_lens_box);
        }
        calendar_date.setText(today_show_Day);



        super.onCreate(savedInstanceState);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        //점 표시
        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        //날짜 클릭했을 때
        materialCalendarView.setOnDateChangedListener((widget, date, selected) -> {

            lens_box ="";

            String Year = Integer.toString(date.getYear());
            String Month = Integer.toString(date.getMonth() + 1);
            String Day = Integer.toString(date.getDay());

            Log.i("Year test", Year + "");
            Log.i("Month test", Month + "");
            Log.i("Day test", Day + "");

            if(Month.length()==1){
                Month="0"+Month;
            }
            if(Day.length()==1){
                Day="0"+Day;
            }

            String shot_Day = Year + "/" + Month + "/" + Day;
            String show_Day = Year + "년 " + Month + "월 " + Day +"일 "+"만료 렌즈";

            Log.i("shot_Day test", shot_Day + "");
            Toast.makeText(getContext(), shot_Day , Toast.LENGTH_SHORT).show();

            for(int i = 0; i<adapter.getItemCount();i++){
                String enddate = adapter.getNoteAt(i).getLens_end();
                if(shot_Day.equals(enddate)){
                    lens_box +="- "+adapter.getNoteAt(i).getLens_name()+"\n";
                }
            }
            if(lens_box.trim().length() == 0){
                calendar_name.setText("만료예정 렌즈가 없어요");
            }else{
                calendar_name.setText(lens_box);
            }
                calendar_date.setText(show_Day);

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    materialCalendarView.clearSelection();
                }
            }, 2000);// 0.6초 정도 딜레이를 준 후 시작

        });
        return rootView;
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(ArrayList<String> Time_Result){
            this.Time_Result = Time_Result.toArray(new String[0]);
        }

        @Override
        protected List<CalendarDay>  doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                //CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split("/");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                calendar.set(year, month - 1, dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            if (isCancelled()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.parseColor("#3498DB"), calendarDays,getActivity()));
        }
    }
}