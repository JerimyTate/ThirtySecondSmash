package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PostGameActivity extends AppCompatActivity {

    public Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        btnPlayAgain = (Button)findViewById(R.id.btnplayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {StartGame(v);}
        });

    }

    protected void StartGame(View view)
    {
        Intent intent = new Intent(PostGameActivity.this, GameActivity.class);
        startActivity(intent);
    }

}
