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
    private Button decreaseCount;

    private TextView _value;

    public int GameTime=5000;
    public int DecrementRate=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        increaseCount = (Button)findViewById(R.id.btnPlusOne);
        decreaseCount = (Button)findViewById(R.id.btnMinusOne);

        _value = (TextView) findViewById(R.id.txt_game_score);
        countdown_number = (TextView)findViewById(R.id.txt_countdown_number);
        new CountDownTimer(GameTime, DecrementRate) {
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
                    incrementScore();
                }
            }
        }
        );

        decreaseCount.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 _value = (TextView) findViewById(R.id.txt_game_score);
                 if(countdown_number.getText()!="End") {
                     decrementScore();
                 }
             }
         }
        );
    }

    public void incrementScore(){

        _counter++;
        _stringVal = Integer.toString(_counter);
        _value.setText(_stringVal);
    }
    public void decrementScore(){

        _counter--;
        _stringVal = Integer.toString(_counter);
        _value.setText(_stringVal);

    }

    public void switchModes(){

        increaseCount.setVisibility(View.GONE);
        decreaseCount.setVisibility(View.VISIBLE);

    }

    public void switchBack(){

        increaseCount.setVisibility(View.VISIBLE);
        decreaseCount.setVisibility(View.GONE);

    }

    public void GameOver(){

        //load Post Game Activity

    }

}
