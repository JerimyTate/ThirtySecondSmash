package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pressthatbutton.thirtysecondsmash.UserScore.AllScoresAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.OwnScoreAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    SoundPool plusOneSound;
    SoundPool minusOneSound;

    int plusOneID;
    int minusOneID;

    public static int gameScore;
    public ParseUser parseUser;

    ///////////////SETTINGS///////////////////
    //settings for determining how often button will switch
    public int minPercent = 0;
    public int maxPercent = 100;
    public int percentSwitch = 10;

    //variables to load game length
    public int GameTime = 30000; //milliseconds
    public int DecrementRate = 1000; //milliseconds
    ///////////////SETTINGS///////////////////

    private TextView countdown_number;
    private TextView _value;
    private TextView high_score;

    public int _counter = 0;
    private String _stringVal;
    private Button increaseCount;
    private Button decreaseCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        increaseCount = (Button) findViewById(R.id.btnPlusOne);
        decreaseCount = (Button) findViewById(R.id.btnMinusOne);
        plusOneSound = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        minusOneSound = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);

        plusOneID = plusOneSound.load(this, R.raw.plusone, 1);
        minusOneID = minusOneSound.load(this, R.raw.minusone, 1);

        high_score = (TextView)findViewById(R.id.txt_highest_score);
        AsyncTask<Void,Void,Void> asyncLoadHighestScore = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    parseUser = ParseUser.getCurrentUser();
                    ParseQuery<Score> query = ParseQuery.getQuery("Score");
                    query.orderByDescending("score");
                    query.whereMatchesQuery("owner",
                            parseUser.getQuery().whereEqualTo("username",parseUser.getUsername()));
                    query.setLimit(1);
                    query.findInBackground(new FindCallback<Score>() {
                        @Override
                        public void done(List<Score> list, ParseException e) {
                            Log.d("MyApp", "GameActivity, returned Score List size (should be 1 or 0): " + list.size());
                            if (e == null) {
                                if(list.size()>0){
                                    high_score.setText(list.get(0).getScore().toString());
                                }else{
                                    Log.d("MyApp", "Empty. GameActivity ParseQuery findInBackground. No scores recorded.");
                                }
                            } else {
                                Log.d("MyApp", "Error! GameActivity ParseQuery findInBackground. ParseException code: " + e.getCode());
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e){
                    Log.d("MyApp", "GameActivity ParseQuery Error: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };
        asyncLoadHighestScore.execute();

        _value = (TextView) findViewById(R.id.txt_game_score);
        countdown_number = (TextView) findViewById(R.id.txt_countdown_number);
        new CountDownTimer(GameTime, DecrementRate) {
            //30 Seconds
            public void onTick(long millisUntilFinished) {
                countdown_number.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdown_number.setText("End");
                GameOver();
            }
        }.start();

        //beginnings of an incrementing counter
        increaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _value = (TextView) findViewById(R.id.txt_game_score);
                if (countdown_number.getText() != "End") {
                    incrementScore();

                    if (randInt(minPercent, maxPercent) < percentSwitch) {
                        switchModes();
                        new CountDownTimer(1000, 1000) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                switchBack();
                            }
                        }.start();
                    }
                }
            }
        });

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

    public void incrementScore() {
        _counter++;
        _stringVal = Integer.toString(_counter);
        _value.setText(_stringVal);
        plusOneSound.play(plusOneID, 1, 1, 1, 0, 1);
    }

    public void decrementScore() {
        _counter--;
        _stringVal = Integer.toString(_counter);
        _value.setText(_stringVal);
        minusOneSound.play(minusOneID, 1, 1, 1, 0, 1);
    }

    //sets decrease button to visible and increase button to visible
    public void switchModes() {
        increaseCount.setVisibility(View.GONE);
        decreaseCount.setVisibility(View.VISIBLE);
    }

    //sets increase button visible and decrease invisible
    public void switchBack() {
        increaseCount.setVisibility(View.VISIBLE);
        decreaseCount.setVisibility(View.GONE);
    }

    public void GameOver() {
        gameScore = _counter;
        Intent intent = new Intent(GameActivity.this, PostGameActivity.class);
        startActivity(intent);
        super.finish();
    }

    /**
     * Returns a psuedo-random number between minPercent and maxPercent, inclusive.
     * The difference between minPercent and maxPercent can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than minPercent.
     * @return Integer between minPercent and maxPercent, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
