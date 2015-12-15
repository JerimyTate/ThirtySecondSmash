package com.pressthatbutton.thirtysecondsmash.UserScore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQueryAdapter;
import com.pressthatbutton.thirtysecondsmash.R;

import java.util.ArrayList;
import java.util.List;

public class AllScoresAdapter extends ParseQueryAdapter<Score> {
    public static final int LIST_MENU_ITEM_LAYOUT = android.support.design.R.layout.abc_list_menu_item_layout;
    private List<Score> _scores = new ArrayList<>();
    private static Context _context;
    private static TextView txtAllScoresItem;
    private static TextView txtAllScoresItemOwner;
    private String textKey2;
    private String textKey;

    @Override
    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    public void setTextKey2(String textKey2) {
        this.textKey2 = textKey2;
    }

    public AllScoresAdapter(Context context, QueryFactory<Score> queryFactory) {
        super(context, queryFactory);
        _context = context;
    }

    @Override
    public View getItemView(Score object, View v, ViewGroup parent) {
        if (v == null) {
            v = this.getDefaultView2(_context);
        }

        TextView textView;
        try {
            textView = (TextView) v.findViewById(android.R.id.text1);
        } catch (ClassCastException ex) {
            throw new IllegalStateException(
                    "Your object views must have a TextView whose id attribute is 'android.R.id.text1'", ex);
        }
        if (textView != null) {
            if (this.textKey == null) {
                textView.setText(object.getObjectId());
            } else if (object.get(this.textKey) != null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(null);
            }
        }

        TextView textView2;
        try {
            textView2 = (TextView) v.findViewById(R.id.txt_all_scores_item_user_name);
        } catch (ClassCastException ex) {
            throw new IllegalStateException(
                    "Your object views must have a TextView whose id attribute is 'android.R.id.text2'", ex);
        }
        if (textView2 != null) {
            String Name = "Unknown User";
            try {
                Name = object.getOwner().fetch().getUsername() + ": ";
            }catch (ParseException e){
                e.printStackTrace();
            }
            if (this.textKey2 == null) {
                textView2.setText(object.getObjectId());
            } else if (object.get(this.textKey2) != null) {
                textView2.setText(Name);
            } else {
                textView2.setText(null);
            }
        }
        TextView textView3;
        try {
            textView3 = (TextView) v.findViewById(R.id.txt_all_scores_item);
        } catch (ClassCastException ex) {
            throw new IllegalStateException(
                    "Your object views must have a TextView whose id attribute is 'android.R.id.text3'", ex);
        }
        if (textView3 != null) {
            if (this.textKey == null) {
                textView3.setText(object.getObjectId());
            } else if (object.get(this.textKey) != null) {
                textView3.setText(object.getScore().toString());
            } else {
                textView3.setText(null);
            }
        }
        return v;
    }
    private View getDefaultView2(Context context) {
        LinearLayout view = new LinearLayout(context);
        view.setPadding(8, 4, 8, 4);

        TextView textView2 = new TextView(context);
        textView2.setId(R.id.txt_all_scores_item_user_name);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView2.setPadding(8, 4, 0, 0);
        view.addView(textView2);

        TextView textView3 = new TextView(context);
        textView3.setId(R.id.txt_all_scores_item);
        textView3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView3.setPadding(8, 10, 0, 4);
        view.addView(textView3);

        TextView textView = new TextView(context);
        textView.setId(android.R.id.text1);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setPadding(8, 0, 0, 0);
        view.addView(textView);
        return view;
    }

/*public AllScoresAdapter(Context context, int resource, List<Score> scores) {
        super(context, resource, scores);
        _context = context;
        _scores.clear();
        _scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(_context).inflate(LIST_MENU_ITEM_LAYOUT, parent, false);
        //All High Scores
        txtAllScoresItem = (TextView) convertView.findViewById(R.id.txt_all_scores_item);
        txtAllScoresItemOwner = (TextView) convertView.findViewById(R.id.txt_all_scores_item_user_name);

        //For AllHighScores
        try {
            txtAllScoresItem.setText(_scores.get(position).getScore().toString());
            txtAllScoresItemOwner.setText(_scores.get(position).getOwner().fetchIfNeeded().getUsername());
        }catch (Exception e){
            Log.d("MyApp", "AllScoresAdapter Error: "+e.getMessage());
            e.printStackTrace();
        }

        return convertView;
    }*/
}
