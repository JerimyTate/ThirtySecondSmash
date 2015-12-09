package com.pressthatbutton.thirtysecondsmash;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private TextView countdown_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_game);

        countdown_number = (TextView)findViewById(R.id.txt_countdown_number);
            new CountDownTimer(30000, 1000) {
                  //30 Seconds
                            public void onTick(long millisUntilFinished) {
                               countdown_number.setText(""+millisUntilFinished / 1000);
                          }

                             public void onFinish() {
                               countdown_number.setText("End");
                           }
                    }.start();
    }


    public void CountdownFragment() {
        // Required empty public constructor
    }


}
