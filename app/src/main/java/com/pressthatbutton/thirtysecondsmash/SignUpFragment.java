package com.pressthatbutton.thirtysecondsmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpFragment extends Fragment{
    private EditText edit_txt_password;
    private EditText edit_txt_username;
    private Button btn_register;
    private Button btn_cancel;

    private ParseUser parseUser = AppParse._parseUser;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        edit_txt_username = (EditText)view.findViewById(R.id.edit_txt_signup_user_name);
        edit_txt_password = (EditText)view.findViewById(R.id.edit_txt_signup_password);
        btn_register = (Button)view.findViewById(R.id.btn_register);
        btn_cancel = (Button)view.findViewById(R.id.btn_signup_cancel);

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
        edit_txt_username.setFilters(new InputFilter[]{filter});
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_txt_username.getText().toString().trim().isEmpty() || edit_txt_password.getText().length() < 8) {
                    //Empty Field
                    Toast.makeText(getContext(), "Error! Username was blank and/or Password length was under 8 characters.", Toast.LENGTH_SHORT).show();
                    Log.d("MyApp","SignUpFragment Empty Field Error.");
                } else {
                    //Not empty field
                    parseUser.setUsername(edit_txt_username.getText().toString());
                    parseUser.setPassword(edit_txt_password.getText().toString());
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getContext(), "Signed up as " + parseUser.getUsername(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "Error! ParseException code: " + e.getCode(), Toast.LENGTH_LONG).show();
                                Log.d("MyApp", "Error! ParseException code: " + e.getCode());
                                e.printStackTrace();
                            }
                        }
                    });
                }
                hideFragment();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
            }
        });
        return view;
    }

    private void hideFragment(){
        LoginScreen.ll_log_in_components.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.container_signup_fragment));
        fragmentTransaction.commit();
    }
}
