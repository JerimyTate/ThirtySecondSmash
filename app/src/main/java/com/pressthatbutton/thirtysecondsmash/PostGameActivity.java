package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

public class PostGameActivity extends AppCompatActivity {

    public Button btnPlayAgain;
    public Button btnPostGameToMain;
    public TextView LastGameScore;
    private String _stringVal;

    public Score score;
    public ParseUser parseUser = AppParse._parseUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        LastGameScore = (TextView) findViewById(R.id.txtYourScore);
        _stringVal = Integer.toString(GameActivity.gameScore);
        LastGameScore.setText(_stringVal);

        try {
            parseUser = ParseUser.getCurrentUser();
            score = new Score();
            score.setOwner(parseUser);
            score.setScore(GameActivity.gameScore);
            Log.d("MyApp","Before Save, Owner: "+score.getOwner().toString()+"; Score: "+score.getScore());
            score.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("MyApp", "Score saved. User is " + score.getOwner() + ". Score is: " + score.getScore());
                    } else if (e != null) {
                        Log.d("MyApp", "SaveCallBack Error! Score not saved. ParseException code: " + e.getCode());
                        e.printStackTrace();
                    } else {
                        Log.d("MyApp", "PostGameActivity score.saveInBackground. Other.");
                    }
                }
            });
        }catch (Exception e){
            Log.d("MyApp","Score error: "+e.getMessage());
            e.printStackTrace();
        }

        btnPlayAgain = (Button) findViewById(R.id.btnplayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartGame(v);
            }
        });

        btnPostGameToMain = (Button) findViewById(R.id.btnPostGameToMain);
        btnPostGameToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMain(v);
            }
        });
    }

    private void StartGame(View view) {
        Intent intent = new Intent(PostGameActivity.this, GameActivity.class);
        startActivity(intent);
        super.finish();
    }

    //Return to Main Activity when clicked
    private void BackToMain(View view)
    {
        super.finish();
    }
}
