package com.pressthatbutton.thirtysecondsmash.UserScore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pressthatbutton.thirtysecondsmash.R;

public class ScoreAdapter extends ParseQueryAdapter<Score>{
    public ScoreAdapter(Context context, ParseQueryAdapter.QueryFactor<Score> queryFactor) {
        super(context, queryFactor);
    }

    @Override
    public View getView(Score score, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_score, parent,false);
            holder = new ViewHolder();
            holder.score = (TextView) view.findViewById(R.id.txtScore);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        TextView txtScore= holder.score;
        txtScore.setText(score.getScore());
        TextView txtUserID= (TextView)convertView.findViewById(R.id.txtUserID_user);
        txtUserID.setText(user.getUserID());
        return view;
    }

    private static class ViewHolder{
        TextView score;
    }
}
