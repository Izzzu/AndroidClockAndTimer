package com.example.izabela.analogdigitalclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import static com.example.izabela.analogdigitalclock.R.id.countdown;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    NumberPicker hoursPicker;
    NumberPicker minPicker;
    NumberPicker secPicker;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private static final int requestCode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn = new Button(this);
        btn.setText("stop alarm");
        btn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, myIntent, 0);

        //final EditText countdownNumber = (EditText) findViewById(R.id.countSecondsNumber);
        final TextView countdownView = (TextView) findViewById(countdown);
        minPicker = (NumberPicker) findViewById(R.id.minPicker);
        secPicker = (NumberPicker) findViewById(R.id.secPicker);
        hoursPicker = (NumberPicker) findViewById(R.id.hoursPicker);
        hoursPicker.setMaxValue(24);
        minPicker.setMaxValue(60);
        secPicker.setMaxValue(60);

        final Button startCountingButton = (Button) findViewById(R.id.countdownButton);
        final Button stopCountingButton = (Button) findViewById(R.id.stopTimer);

        View.OnClickListener onClickStartTimerListener = new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                long secFromTimePicker = hoursPicker.getValue() * 60 * 60 + minPicker.getValue() * 60 + secPicker.getValue();

                if (secFromTimePicker != 0) {
                    setEnableTimePickers(false);
                    startCountingButton.setEnabled(false);
                    stopCountingButton.setEnabled(true);
                    countDownTimer = getCountDownTimer(secFromTimePicker * 1000);
                    countDownTimer.start();
                    alarmManager.set(AlarmManager.RTC_WAKEUP, secFromTimePicker*1000, pendingIntent);

                }
            }

            @NonNull
            private CountDownTimer getCountDownTimer(final Long timeToCountdown) {
                return new CountDownTimer(timeToCountdown, 1000) {
                    @Override
                    public void onTick(long l) {
                        long allSec = l / 1000;
                        long min = allSec / 60;
                        long hours = min / 60;
                        if (min >= 60) {
                            min = min % 60;
                        }
                        if (hours >= 24) {
                            hours = hours % 60;
                        }
                        long sec = allSec % 60;
                        countdownView.setText(hours + " : " + min + " : " + sec);

                    }

                    @Override
                    public void onFinish() {
                        countdownView.setText(R.string.done);
                        setEnableTimePickers(true);
                        startCountingButton.setEnabled(true);
                        resetPickers();

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
                setEnableTimePickers(true);
                resetPickers();
                countdownView.setText("");
                countDownTimer.cancel();

            }
        };

        stopCountingButton.setOnClickListener(onclickStopTimerListener);




    }

    private void resetPickers() {
        hoursPicker.setValue(0);
        minPicker.setValue(0);
        secPicker.setValue(0);
    }

    private void setEnableTimePickers(boolean enabled) {
        hoursPicker.setEnabled(enabled);
        minPicker.setEnabled(enabled);
        secPicker.setEnabled(enabled);
    }


}
