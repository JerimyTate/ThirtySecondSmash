package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pressthatbutton.thirtysecondsmash.UserScore.AllScoresAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

import java.util.ArrayList;
import java.util.List;

public class ShowAllHighScores extends AppCompatActivity {
    public Button btnAllToMain;
    public Button btnAllToOwn;

    public ListView lvAllScores;
    private static List<Score> _scores = new ArrayList<>();

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

        Toast.makeText(getBaseContext(), "Loading All High Scores...", Toast.LENGTH_LONG);
        try {
            _scores.clear();
            ParseQuery<Score> query = ParseQuery.getQuery(Score.class);
            query.orderByDescending("score");
            query.setLimit(100);
            query.findInBackground(new FindCallback<Score>() {
                @Override
                public void done(List<Score> list, ParseException e) {
                    if (e == null) {
                        Log.d("MyApp","ShowAllHighScores Score List size: "+list.size());
                        _scores.addAll(list);
                    } else {
                        Toast.makeText(getBaseContext(), "Error! ParseException code: " + e.getCode(), Toast.LENGTH_LONG);
                        e.printStackTrace();
                    }
                }
            });
            lvAllScores = (ListView)findViewById(R.id.all_score_list_view);
            lvAllScores.setAdapter(new AllScoresAdapter(getBaseContext(), AllScoresAdapter.LIST_MENU_ITEM_LAYOUT, _scores));
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
