package com.pressthatbutton.thirtysecondsmash.UserScore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pressthatbutton.thirtysecondsmash.R;

import java.util.ArrayList;
import java.util.List;

public class AllScoresAdapter extends ArrayAdapter<Score> {
    public static final int LIST_MENU_ITEM_LAYOUT = android.support.design.R.layout.abc_list_menu_item_layout;
    private static List<Score> _scores = new ArrayList<>();
    private static Context _context;

    public AllScoresAdapter(Context context, int resource, List<Score> scores) {
        super(context, resource, scores);
        _context = context;
        _scores.clear();
        _scores.addAll(scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(_context);

        //All High Scores
        View scoreAllItemView = inflater.inflate(LIST_MENU_ITEM_LAYOUT,parent,false);

        //For AllHighScores
        try {
            Score tempScoreHolder = _scores.get(position);
            TextView txtAllScoresItem = (TextView) scoreAllItemView.findViewById(R.id.txt_all_scores_item);
            txtAllScoresItem.setText(tempScoreHolder.getScore().toString());
            TextView txtAllScoresItemOwner = (TextView) scoreAllItemView.findViewById(R.id.txt_all_scores_item_user_name);
            txtAllScoresItemOwner.setText(tempScoreHolder.getOwner().getUsername());
        }catch (Exception e){
            Log.d("MyApp", "AllScoresAdapter Error: "+e.getMessage());
            e.printStackTrace();
        }

        return scoreAllItemView;
    }
}
