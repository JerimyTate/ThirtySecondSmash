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

public class OwnScoreAdapter extends ArrayAdapter<Score> {
    public static final int LIST_MENU_ITEM_LAYOUT = android.support.design.R.layout.abc_list_menu_item_layout;
    private static List<Score> _scores = new ArrayList<>();
    private static Context _context;
    private TextView txtOwnScoreItem;

    public OwnScoreAdapter(Context context, int resource, List<Score> scores) {
        super(context, resource, scores);
        _context = context;
        _scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(_context).inflate(LIST_MENU_ITEM_LAYOUT, parent, false);

        //Own High Scores
        txtOwnScoreItem = (TextView) convertView.findViewById(R.id.txt_own_score_item);

        //For OwnHighScores
        try {
            txtOwnScoreItem.setText( _scores.get(position).getScore().toString());
        }catch (Exception e){
            Log.d("MyApp","OwnScoreAdapter Error: "+e.getMessage());
            e.printStackTrace();
        }

        return convertView;
    }
}
