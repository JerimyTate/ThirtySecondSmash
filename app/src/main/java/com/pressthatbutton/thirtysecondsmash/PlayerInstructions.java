package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayerInstructions extends AppCompatActivity {

    public TextView txtInstruction1;
    public TextView txtInstruction2;
    public Button btnHelpToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_instructions);

        String tempText ="When the button is <font color=#9bbb59>GREEN</font>, each press is +1 to score.";
        txtInstruction1 = (TextView)findViewById(R.id.txt_instructions_1);
        txtInstruction1.setText(Html.fromHtml(tempText));

        tempText = "When the button is <font color=#c0504d>RED</font>, each press is -1 to score.";
        txtInstruction2 = (TextView)findViewById(R.id.txt_instructions_2);
        txtInstruction2.setText(Html.fromHtml(tempText));

        btnHelpToMain = (Button)findViewById(R.id.btnHelpToMain);
        btnHelpToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMain(v);
            }
        });
    }

    //Return to Main Activity when clicked
    private void BackToMain(View view)
    {
        Intent intent = new Intent(PlayerInstructions.this, MainActivity.class);
        startActivity(intent);
    }
}
