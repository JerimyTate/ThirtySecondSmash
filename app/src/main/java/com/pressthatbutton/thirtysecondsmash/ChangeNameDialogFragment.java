package com.pressthatbutton.thirtysecondsmash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.pressthatbutton.thirtysecondsmash.R;

public class ChangeNameDialogFragment extends DialogFragment {

    public TextView currentName;
    public EditText newName;

    public ParseUser parseUser;
    private String _currentNameOfUser = "Unknown User"; //Not yet set.

    public ChangeNameDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_name_dialog, container, false);
        SetUpFragmentXML();

        // Inflate the layout for this fragment
        return view;
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
                        if (newName.getText().toString().trim().isEmpty()) {
                            //Empty Field
                            failedNameChange();
                        } else {
                            //Not empty field
                            ChangeNameParseUser(newName.getText().toString());
                        }
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        failedNameChange();
                    }
                });
        SetUpFragmentXML();
        return builder.create();
    }

    private void SetUpFragmentXML(){
        parseUser = ParseUser.getCurrentUser();

        currentName = (TextView)getView().findViewById(R.id.txt_display_current_name);
        currentName.setText(_currentNameOfUser);

        newName = (EditText)getView().findViewById(R.id.edit_txt_new_name);
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean keepOriginal = true;
                StringBuilder stringBuilder = new StringBuilder(end - start);
                for (int i = start; i<end;++i){
                    char c = source.charAt(i);
                    if(isCharAllowed(c)){
                        stringBuilder.append(c);
                    }else{
                        keepOriginal = false;
                    }
                }
                if(keepOriginal){
                    return null;
                }else {
                    return stringBuilder;
                }
            }

            private boolean isCharAllowed(char c){
                return Character.isLetterOrDigit(c) || Character.isSpaceChar(c)
                        || Character.valueOf(c).compareTo('@')==0 || Character.valueOf(c).compareTo('.')==0;
            }
        };
        newName.setFilters(new InputFilter[]{filter});

        if(parseUser.getSessionToken()==null){
            //No user??
        }else {
            _currentNameOfUser = parseUser.getUsername();
        }
    }

    //Displays toast
    private void failedNameChange(){
        Toast.makeText(getContext(),"Name not changed.",Toast.LENGTH_SHORT).show();
    }

    private void ChangeNameParseUser(final String newUserName) {
        //Get Old Name
        final String previousUserName = _currentNameOfUser;

        //Set New Name
        parseUser.setUsername(newUserName);
        //Get New Name
        _currentNameOfUser = parseUser.getUsername();

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    //Save did not succeed.
                    Toast.makeText(getContext(), "Error! Parse Exception Code: " + e.getCode(), Toast.LENGTH_LONG).show();
                } else {
                    //Object saved successfully.
                    Toast.makeText(getContext(), "Changed username from"+previousUserName+
                            " to:" + _currentNameOfUser + ".", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
