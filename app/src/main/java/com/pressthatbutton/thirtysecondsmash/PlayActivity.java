package com.pressthatbutton.thirtysecondsmash;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayActivity extends AppCompatActivity implements AllHighScoresFragment.ListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.layout.fragment_all_high_scores,AllHighScoresFragment.class);
        fragmentTransaction.commit();
    }

    @Override
    public void itemClicked(String city)
}
