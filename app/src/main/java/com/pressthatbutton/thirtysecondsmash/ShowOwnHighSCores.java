package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.pressthatbutton.thirtysecondsmash.UserScore.OwnScoreAdapter;
import com.pressthatbutton.thirtysecondsmash.UserScore.Score;

import java.util.ArrayList;
import java.util.List;

public class ShowOwnHighScores extends AppCompatActivity {
    private Button btnOwnToMain;
    private Button btnOwnToAll;
    private TextView txt_username;

    public ParseUser parseUser = ParseUser.getCurrentUser();

    public ListView lvOwnScores;
    private static List<Score> own_scores_list = new ArrayList<>();

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
            lvOwnScores = (ListView) findViewById(R.id.own_score_list_view);
            ParseQueryAdapter<Score> parseQueryAdapter = new ParseQueryAdapter<>(this,new ParseQueryAdapter.QueryFactory<Score>(){
                public ParseQuery<Score> create(){
                    ParseQuery<Score> query = new ParseQuery("Score");
                    query.orderByDescending("score");
                    query.whereMatchesQuery("owner",
                            parseUser.getQuery().whereEqualTo("username", parseUser.getUsername()));
                    return query;
                }
            });
            parseQueryAdapter.setTextKey("score");
            parseQueryAdapter.setObjectsPerPage(10);
            parseQueryAdapter.setPaginationEnabled(true);

            /*own_scores_list.clear();
            ParseQuery<Score> query = ParseQuery.getQuery("Score");
            query.orderByDescending("score");
            query.whereMatchesQuery("owner",
                    parseUser.getQuery().whereEqualTo("username", parseUser.getUsername()));
            query.setLimit(10);
            query.findInBackground(new FindCallback<Score>() {
                @Override
                public void done(List<Score> list, ParseException e) {
                    if (e == null) {
                        Log.d("MyApp", "ShowOwnHighScores Score List size: " + list.size());
                        own_scores_list.addAll(list);
                    } else {
                        Log.d("MyApp", "Error! ShowOwnHighScores findInBackground ParseException code: " + e.getCode());
                        e.printStackTrace();
                    }
                }
            });*/
            lvOwnScores.setAdapter(new OwnScoreAdapter(getBaseContext(), OwnScoreAdapter.LIST_MENU_ITEM_LAYOUT, own_scores_list));
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
