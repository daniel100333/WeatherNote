package com.example.daniel.weathernote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Daniel on 3/4/2016.
 */
public class LoginFragment extends Fragment {

    private TextInputLayout mEmailTextInputLayout;
    private EditText mEmailField;
    private Button mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmailTextInputLayout = (TextInputLayout) v.findViewById(R.id.email_text_input_layout);
        mEmailField = (EditText) v.findViewById(R.id.input_email);

        mLoginButton = (Button) v.findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), NoteListActivity.class);
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailField.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailTextInputLayout.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailTextInputLayout.setError(null);
        }

        return valid;
    }

}
