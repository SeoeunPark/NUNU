package com.nunu.NUNU;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import static android.speech.tts.TextToSpeech.ERROR;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Stretching extends Fragment {
    private TextToSpeech tts;
    Stretching2 next = new Stretching2();
    private long timeCountInMilliSeconds = 1 * 60000;

    // 현재 타이머 상태
    private enum TimerStatus {
        STARTED,
        STOPPED
    }


    private TimerStatus timerStatus = TimerStatus.STOPPED;

    private ProgressBar progressBarCircle;
    private TextView timeText;
    private ImageView play;
    private ImageView pause;
    private CountDownTimer countDownTimer;
    private ImageView next_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_stretching, container, false);
        initViews(rootView);

        // 음성출력하기 위해 한국어 설정
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        // 실행 버튼 클릭시 발생하는 이벤트
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //멈춤 버튼 보이기
                pause.setVisibility(View.VISIBLE);
                tts.speak("10초간 천장을 올려다보세요.",TextToSpeech.QUEUE_FLUSH,null);
                //실행 버튼 숨기기
                play.setVisibility(View.GONE);
                startStop();
            }
        });

        // 멈춤 버튼 클릭시 발생하는 이벤트
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 실행 버튼 보이기
                play.setVisibility(View.VISIBLE);
                // 멈춤 버튼 숨기기
                pause.setVisibility(View.GONE);
                stopCountDownTimer();
            }
        });


        // 다음 스트레칭으로 이동할 수 있는 화살표 버튼 클릭시 발생하는 이벤트
        next_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setProgressBarValues();
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                timerStatus = TimerStatus.STOPPED;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, next).commitAllowingStateLoss();
            }
        });


        return rootView;

    }

    // 화면 구성
    private void initViews(View view) {
        progressBarCircle = (ProgressBar) view.findViewById(R.id.progressBarCircle);
        timeText = (TextView) view.findViewById(R.id.timeText);
        play = (ImageView) view.findViewById(R.id.play);
        pause = (ImageView) view.findViewById(R.id.pause);
        next_btn = (ImageView) view.findViewById(R.id.next_btn);
    }


    private void reset() {
        stopCountDownTimer();
    }


    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {
            setTimerValues();
            setProgressBarValues();
            timerStatus = TimerStatus.STARTED;
            startCountDownTimer();

        } else {
            timerStatus = TimerStatus.STARTED;
            startCountDownTimer();
        }

    }

    private void setTimerValues() {
        timeCountInMilliSeconds =  10 * 1000;
    }

    private void takeOverTimerValues(long milliSeconds) {
        timeCountInMilliSeconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds));
    }

    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText(hmsTimeFormatter(millisUntilFinished));
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
               // timeText.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                timerStatus = TimerStatus.STOPPED;
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, next).commitAllowingStateLoss();
            }
        }.start();
        countDownTimer.start();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValues() {
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d",
                //  TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;

    }

}