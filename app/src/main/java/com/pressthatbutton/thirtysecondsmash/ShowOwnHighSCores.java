package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pressthatbutton.thirtysecondsmash.UserScore.AllScoresAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.OwnScoreAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

import java.util.List;

public class ShowOwnHighScores extends AppCompatActivity {
    public Button btnOwnToMain;
    public Button btnOwnToAll;

    public ParseUser parseUser = AppParse._parseUser;

    public ListView lvOwnScores;
    private List<Score> _scores = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_own_high_scores);

        btnOwnToMain = (Button)findViewById(R.id.btnOwnToMain);
        btnOwnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMain(v);
            }
        });

        btnOwnToAll = (Button)findViewById(R.id.btnOwnToAll);
        btnOwnToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllHighScores(v);
            }
        });

        parseUser = ParseUser.getCurrentUser();

        Toast.makeText(getBaseContext(), "Loading Own Scores...", Toast.LENGTH_LONG);
        ParseQuery<Score> query = ParseQuery.getQuery(Score.class);
        query.findInBackground(new FindCallback<Score>() {
            @Override
            public void done(List<Score> list, ParseException e) {
                if (e == null) {
                    for(Score s: list){
                        if(s.getOwner().compareTo(parseUser.getUsername())==0){
                            _scores.add(s);
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Error! ParseException code: " + e.getCode(), Toast.LENGTH_LONG);
                }
            }
        });
        lvOwnScores = (ListView)findViewById(R.id.own_score_list_view);
        lvOwnScores.setAdapter(new AllScoresAdapter(getBaseContext(), OwnScoreAdapter.LIST_MENU_ITEM_LAYOUT, _scores));
    }

    //Launch All High Scores when clicked
    private void ShowAllHighScores(View view)
    {
        Intent intent = new Intent(ShowOwnHighScores.this, ShowAllHighScores.class);
        startActivity(intent);
        super.finish();
    }

    //Return to Main Activity when clicked
    private void BackToMain(View view)
    {
        super.finish();
    }
}
