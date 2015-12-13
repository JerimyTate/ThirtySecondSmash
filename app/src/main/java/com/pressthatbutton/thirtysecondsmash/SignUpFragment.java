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
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpFragment extends DialogFragment {
    private EditText btnPassword;
    private EditText btnUsername;

    private ParseUser parseUser = AppParse._parseUser;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        btnUsername = (EditText)view.findViewById(R.id.edit_txt_signup_user_name);
        btnPassword = (EditText)view.findViewById(R.id.edit_txt_signup_password);

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
                return Character.isLetterOrDigit(c) || Character.isSpaceChar(c) || Character.valueOf(c).compareTo('_')==0
                        || Character.valueOf(c).compareTo('-')==0 || Character.valueOf(c).compareTo('.')==0;
            }
        };
        btnUsername.setFilters(new InputFilter[]{filter});
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //null parent because it is a dialog
        builder.setView(inflater.inflate(R.layout.fragment_sign_up, null))
                .setTitle(R.string.signup_dialog_title)
                .setPositiveButton(R.string.signup_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (btnUsername.getText().toString().trim().isEmpty() || btnPassword.getText().length()<8) {
                            //Empty Field
                            Toast.makeText(getContext(), "Error! Username was blank and/or Password length was under 8 characters.", Toast.LENGTH_SHORT).show();
                        } else {
                            //Not empty field
                            parseUser.setUsername(btnUsername.getText().toString());
                            parseUser.setPassword(btnPassword.getText().toString());
                            parseUser.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e==null){
                                        Toast.makeText(getContext(),"Signed up as "+parseUser.getUsername(),Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getContext(),"Error! ParseException code: "+e.getCode(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.name_change_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}
