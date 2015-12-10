package com.pressthatbutton.thirtysecondsmash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pressthatbutton.thirtysecondsmash.R;

public class ChangeNameDialogFragment extends DialogFragment {

    public TextView currentName;
    public EditText newName;
    private String _currentNameOfUser = "Unknown User"; //Not yet set.

    public ChangeNameDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentName = (TextView)getView().findViewById(R.id.txt_display_current_name);
        //NEED TO DO
        //Get current user name!
        currentName.setText(_currentNameOfUser);
        newName = (EditText)getView().findViewById(R.id.edit_txt_new_name);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_name_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //null parent because it is a dialog
        builder.setView(inflater.inflate(R.layout.fragment_change_name_dialog,null))
        .setPositiveButton(R.string.name_change_dialog_okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Change User's Name
                //NEED TO DO
                dismiss();
            }
        })
        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                Toast.makeText(getContext(),"Name not changed.",Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }
}
