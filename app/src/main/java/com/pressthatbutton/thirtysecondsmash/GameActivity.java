package com.pressthatbutton.thirtysecondsmash;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private TextView countdown_number;
    public int _counter=0;
    private String _stringVal;
    private Button increaseCount;
    private TextView _value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        increaseCount = (Button)findViewById(R.id.btnPlusOne);

        countdown_number = (TextView)findViewById(R.id.txt_countdown_number);
        new CountDownTimer(5000, 1000) {
            //30 Seconds
            public void onTick(long millisUntilFinished) {
                countdown_number.setText(""+millisUntilFinished / 1000);
            }
            public void onFinish() {
                countdown_number.setText("End");
            }
        }.start();

        //beginnings of an incrementing counter
        increaseCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            _value = (TextView) findViewById(R.id.txt_game_score);
                if(countdown_number.getText()!="End") {

                    _counter++;
                    _stringVal = Integer.toString(_counter);
                    _value.setText(_stringVal);
                }



            }
        });
    }
}
