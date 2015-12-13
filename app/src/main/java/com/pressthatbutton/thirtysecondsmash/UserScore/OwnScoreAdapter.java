package com.pressthatbutton.thirtysecondsmash.UserScore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pressthatbutton.thirtysecondsmash.R;

import java.util.List;

public class OwnScoreAdapter extends ArrayAdapter<Score> {
    public static final int LIST_MENU_ITEM_LAYOUT = android.support.design.R.layout.abc_list_menu_item_layout;
    private final List<Score> _scores;

    public OwnScoreAdapter(Context context, int resource, List<Score> scores) {
        super(context, resource, scores);
        this._scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)convertView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Own High Scores
        View scoreOwnItemView = inflater.inflate(LIST_MENU_ITEM_LAYOUT,parent,false);

        //For OwnHighScores
        TextView txtOwnScoreItem = (TextView) scoreOwnItemView.findViewById(R.id.txt_own_score_item);
        txtOwnScoreItem.setText(_scores.get(position).getScore());

        return scoreOwnItemView;
    }
}
