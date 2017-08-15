package com.example.izabela.analogdigitalclock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import static com.example.izabela.analogdigitalclock.R.id.countdown;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final EditText countdownNumber = (EditText) findViewById(R.id.countSecondsNumber);
        final TextView countdownView = (TextView) findViewById(countdown);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentMinute(0);
        timePicker.setCurrentHour(0);



        final Button startCountingButton = (Button) findViewById(R.id.countdownButton);
        final Button stopCountingButton = (Button) findViewById(R.id.stopTimer);

        View.OnClickListener onClickStartTimerListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("" + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                long secFromTimePicker = timePicker.getCurrentHour()*60*60+timePicker.getCurrentMinute()*60;

                if(secFromTimePicker != 0) {
                    timePicker.setEnabled(false);
                    startCountingButton.setEnabled(false);
                    stopCountingButton.setEnabled(true);
                    countDownTimer = getCountDownTimer(secFromTimePicker*1000);
                    countDownTimer.start();
                }
            }

            @NonNull
            private CountDownTimer getCountDownTimer(final Long timeToCountdown) {
                return new CountDownTimer(timeToCountdown, 1000) {
                    @Override
                    public void onTick(long l) {
                        long allSec = l / 1000;
                        long min = allSec/60;
                        long hours = min/60;
                        if(min>=60) {
                            min = min % 60;
                        }
                        if(hours>=24) {
                            hours = hours % 60;
                        }
                        long sec = allSec % 60;
                        countdownView.setText(hours + " : " + min + " : " + sec);

                    }

                    @Override
                    public void onFinish() {
                        countdownView.setText(R.string.done);
                        timePicker.setEnabled(true);
                        startCountingButton.setEnabled(true);
                        timePicker.setCurrentHour(0);
                        timePicker.setCurrentMinute(0);
                    }
                };
            }
        };
        startCountingButton.setOnClickListener(onClickStartTimerListener);


        View.OnClickListener onclickStopTimerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCountingButton.setEnabled(false);
                startCountingButton.setEnabled(true);
                timePicker.setEnabled(true);
                timePicker.setCurrentHour(0);
                timePicker.setCurrentMinute(0);
                countdownView.setText("");
                countDownTimer.cancel();

            }
        };

        stopCountingButton.setOnClickListener(onclickStopTimerListener);




    }
}
