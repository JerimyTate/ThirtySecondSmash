package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowAllHighScores extends AppCompatActivity {
    public Button btnAllToMain;
    public Button btnAllToOwn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_high_scores);

        btnAllToMain = (Button)findViewById(R.id.btnAllToMain);
        btnAllToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMain(v);
            }
        });

        btnAllToOwn = (Button)findViewById(R.id.btnAllToOwn);
        btnAllToOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMyHighScores(v);
            }
        });
    }

    //Launch Own High Scores when clicked
    private void ShowMyHighScores(View view)
    {
        Intent intent = new Intent(ShowAllHighScores.this, ShowOwnHighScores.class);
        startActivity(intent);
        super.finish();
    }

    //Return to Main Activity when clicked
    private void BackToMain(View view)
    {
        super.finish();
    }
}
