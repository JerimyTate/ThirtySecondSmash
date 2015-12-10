package com.pressthatbutton.thirtysecondsmash;

import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity{
    SoundPool plusOneSound;
    SoundPool minusOneSound;

    int plusOneID;
    int minusOneID;

    private TextView countdown_number;
    public int _counter=0;
    private String _stringVal;
    private Button increaseCount;
    private Button decreaseCount;

    private TextView _value;

    //variables to load game length
    public int GameTime=5000; //milliseconds
    public int DecrementRate=1000; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        increaseCount = (Button)findViewById(R.id.btnPlusOne);
        decreaseCount = (Button)findViewById(R.id.btnMinusOne);
        plusOneSound= new SoundPool(10, AudioManager.STREAM_MUSIC,1);
        minusOneSound= new SoundPool(10, AudioManager.STREAM_MUSIC,1);

        plusOneID=plusOneSound.load(this, R.raw.plusone,1);
        minusOneID=minusOneSound.load(this, R.raw.minusone,1);


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
                    if(ThreadLocalRandom.current().nextInt(0, 9 + 1)==0)
                    {
                        switchModes();
                        try {
                            Thread.sleep(1000);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        switchBack();

                    }

                }
            }
        }
        );

        decreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 _value = (TextView) findViewById(R.id.txt_game_score);
                 if (countdown_number.getText() != "End") {
                     decrementScore();
                 }
            }
         });
    }

    public void incrementScore(){
        _counter++;
        _stringVal = Integer.toString(_counter);
        _value.setText(_stringVal);
        plusOneSound.play(plusOneID,1,1,1,0,1);
    }

    public void decrementScore(){
        _counter--;
        _stringVal = Integer.toString(_counter);
        _value.setText(_stringVal);
        minusOneSound.play(minusOneID,1,1,1,0,1);
    }

    //sets decrease button to visible and increase button to visible
    public void switchModes(){
        increaseCount.setVisibility(View.GONE);
        decreaseCount.setVisibility(View.VISIBLE);
    }
    //sets increase button visible and decrease invisible
    public void switchBack(){
        increaseCount.setVisibility(View.VISIBLE);
        decreaseCount.setVisibility(View.GONE);
    }

    public void GameOver(){
        //load Post Game Activity
    }

}
