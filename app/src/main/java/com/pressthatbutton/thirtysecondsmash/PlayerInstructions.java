package com.pressthatbutton.thirtysecondsmash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        txtInstruction1 = (TextView)findViewById(R.id.txt_instructions_1);
        txtInstruction2 = (TextView)findViewById(R.id.txt_instructions_2);
        btnHelpToMain = (Button)findViewById(R.id.btnHelpToMain);
    }
}
