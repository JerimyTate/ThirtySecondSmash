package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.pressthatbutton.thirtysecondsmash.UserScore.OwnScoreAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;


public class ShowOwnHighScores extends AppCompatActivity {
    private Button btnOwnToMain;
    private Button btnOwnToAll;
    private TextView txt_username;

    public ParseUser parseUser = ParseUser.getCurrentUser();

    public ListView lvOwnScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_own_high_scores);

        txt_username = (TextView)findViewById(R.id.txt_own_score_username);
        try {
            txt_username.setText(parseUser.getUsername());
        }catch (Exception e){
            Log.d("MyApp","Error! ShowOwnHighScores set TextView with ParseUser name. Message: "+e.getMessage());
            e.printStackTrace();
        }

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

        try {
            parseUser = ParseUser.getCurrentUser();
            lvOwnScores = (ListView) findViewById(R.id.own_score_list_view);
            OwnScoreAdapter parseQueryAdapter = new OwnScoreAdapter(this,new ParseQueryAdapter.QueryFactory<Score>(){
                public ParseQuery<Score> create(){
                    ParseQuery<Score> query = new ParseQuery("Score");
                    query.orderByDescending("score");
                    query.whereEqualTo("owner",parseUser);
                    return query;
                }
            });
            parseQueryAdapter.setTextKey("score");
            parseQueryAdapter.setObjectsPerPage(20);
            parseQueryAdapter.setPaginationEnabled(true);

            lvOwnScores.setAdapter(parseQueryAdapter);
        }catch (Exception e){
            Log.d("MyApp", "ShowOwnHighScore ParseQuery Error: " + e.getMessage());
            e.printStackTrace();
        }
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
