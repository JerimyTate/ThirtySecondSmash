package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.AllScoresAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

import java.util.ArrayList;
import java.util.List;

public class ShowAllHighScores extends AppCompatActivity {
    public Button btnAllToMain;
    public Button btnAllToOwn;

    public ListView lvAllScores;

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

        try {
            AllScoresAdapter parseQueryAdapter = new AllScoresAdapter(this,new ParseQueryAdapter.QueryFactory<Score>(){
                public ParseQuery<Score> create(){
                    ParseQuery<Score> query = new ParseQuery("Score");
                    query.orderByDescending("score");
                    return query;
                }
            });
            parseQueryAdapter.setTextKey("score");
            parseQueryAdapter.setTextKey2("owner");
            parseQueryAdapter.setPaginationEnabled(true);
            parseQueryAdapter.setObjectsPerPage(12);
            lvAllScores = (ListView) findViewById(R.id.all_score_list_view);
            lvAllScores.setAdapter(parseQueryAdapter);
        }catch (Exception e){
            Log.d("MyApp","ShowAllHighScore ParseQuery Error: "+e.getMessage());
            e.printStackTrace();
        }
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
