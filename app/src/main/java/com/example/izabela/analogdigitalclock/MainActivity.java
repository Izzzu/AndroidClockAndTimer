package com.example.izabela.analogdigitalclock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.izabela.analogdigitalclock.R.id.countdown;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText countdownNumber = (EditText) findViewById(R.id.countSecondsNumber);
        final TextView countdownView = (TextView) findViewById(countdown);


        final Button startCountingButton = (Button) findViewById(R.id.countdownButton);
        final Button stopCountingButton = (Button) findViewById(R.id.stopTimer);

        View.OnClickListener onClickStartTimerListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String number = countdownNumber.getText().toString();
                long timeToCountdown = 0;
                if(!number.isEmpty() && number != null) {
                     timeToCountdown = Long.parseLong(number);
                }
                if(timeToCountdown != 0) {
                    countdownNumber.setEnabled(false);
                    startCountingButton.setEnabled(false);
                    stopCountingButton.setEnabled(true);
                    countDownTimer = getCountDownTimer(timeToCountdown*1000);
                    countDownTimer.start();
                }
            }

            @NonNull
            private CountDownTimer getCountDownTimer(final Long timeToCountdown) {
                return new CountDownTimer(timeToCountdown, 1000) {
                    @Override
                    public void onTick(long l) {
                        countdownView.setText(String.valueOf(l / 1000));

                    }

                    @Override
                    public void onFinish() {
                        countdownView.setText(R.string.done);
                        countdownNumber.setEnabled(true);
                        startCountingButton.setEnabled(true);
                        countdownNumber.setText("");
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
                countdownNumber.setEnabled(true);
                countdownNumber.setText("");
                countdownView.setText("");
                countDownTimer.cancel();

            }
        };

        stopCountingButton.setOnClickListener(onclickStopTimerListener);




    }
}
