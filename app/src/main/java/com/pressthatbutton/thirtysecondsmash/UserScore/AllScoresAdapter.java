package com.pressthatbutton.thirtysecondsmash.UserScore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQueryAdapter;
import com.pressthatbutton.thirtysecondsmash.R;

public class AllScoresAdapter extends ParseQueryAdapter<Score> {
    private static Context _context;
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

        //Username
        TextView textView2;
        try {
            textView2 = (TextView) v.findViewById(R.id.txt_all_scores_item_user_name);
        } catch (ClassCastException ex) {
            throw new IllegalStateException(
                    "Your object views must have a TextView whose id attribute is 'txt_all_scores_item_user_name'", ex);
        }
        if (textView2 != null) {
            String Name = "Unknown User";
            try {
                Name = object.getOwner().fetch().getUsername();
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

        //Score
        TextView textView3;
        try {
            textView3 = (TextView) v.findViewById(R.id.txt_all_scores_item);
        } catch (ClassCastException ex) {
            throw new IllegalStateException(
                    "Your object views must have a TextView whose id attribute is 'R.id.txt_all_scores_item'", ex);
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

    //Modification from ParseUI code.
    private View getDefaultView2(Context context) {
        LinearLayout view = new LinearLayout(context);
        view.setPadding(8, 4, 8, 4);

        //Username
        TextView textView2 = new TextView(context);
        textView2.setId(R.id.txt_all_scores_item_user_name);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView2.setPadding(8, 4, 0, 0);
        view.addView(textView2);

        //Score
        TextView textView3 = new TextView(context);
        textView3.setId(R.id.txt_all_scores_item);
        textView3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView3.setPadding(8, 10, 0, 4);
        view.addView(textView3);

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
