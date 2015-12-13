package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

public class PostGameActivity extends AppCompatActivity {

    public Button btnPlayAgain;
    public Button btnPostGameToMain;
    public TextView LastGameScore;
    private String _stringVal;
    ParseObject gameScore = new ParseObject("GameScore");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        //
        LastGameScore = (TextView) findViewById(R.id.txtYourScore);
        _stringVal = Integer.toString(GameActivity.GameScore);
        LastGameScore.setText(_stringVal);


        gameScore.put("score", _stringVal);
        gameScore.put("playerName", MainActivity.PlayerName);

        ///This line crashes the app for me
        gameScore.saveInBackground();


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
