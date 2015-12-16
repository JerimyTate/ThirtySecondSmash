package com.pressthatbutton.thirtysecondsmash.UserScore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseQueryAdapter;
import com.pressthatbutton.thirtysecondsmash.R;

public class OwnScoreAdapter extends ParseQueryAdapter<Score> {
    private static Context _context;
    private String textKey;

    public OwnScoreAdapter(Context context, QueryFactory<Score> queryFactory) {
        super(context, queryFactory);
        _context = context;
    }

    @Override
    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    @Override
    public View getItemView(Score object, View v, ViewGroup parent) {
        if (v == null) {
            v = this.getDefaultView2(_context);
        }

        //For default next page loading.
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

        //Score
        TextView textView2;
        try {
            textView2 = (TextView) v.findViewById(R.id.txt_own_score_item);
        } catch (ClassCastException ex) {
            throw new IllegalStateException(
                    "Your object views must have a TextView whose id attribute is 'R.id.txt_own_score_item'", ex);
        }
        if (textView2 != null) {
            if (this.textKey == null) {
                textView2.setText(object.getObjectId());
            } else if (object.get(this.textKey) != null) {
                textView2.setText(object.getScore().toString());
            } else {
                textView2.setText(null);
            }
        }
        return v;
    }

    //Modification from ParseUI code.
    private View getDefaultView2(Context context) {
        LinearLayout view = new LinearLayout(context);
        view.setPadding(8, 4, 8, 4);

        //Score
        TextView textView2 = new TextView(context);
        textView2.setId(R.id.txt_own_score_item);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView2.setPadding(8, 4, 0, 0);
        view.addView(textView2);

        //Carry-over from original code. This is the next-page TextView
        TextView textView = new TextView(context);
        textView.setId(android.R.id.text1);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setPadding(8, 0, 0, 0);
        view.addView(textView);
        return view;
    }
}
