package com.pressthatbutton.thirtysecondsmash.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pressthatbutton.thirtysecondsmash.R;

public class UserAdapter extends ArrayAdapter<User>{
    public UserAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent,false);
        }
        TextView txtUserName= (TextView)convertView.findViewById(R.id.txtUserName_user);
        txtUserName.setText(user.getUserName());
        TextView txtUserID= (TextView)convertView.findViewById(R.id.txtUserID_user);
        txtUserID.setText(user.getUserID());
        return convertView;
    }

}
