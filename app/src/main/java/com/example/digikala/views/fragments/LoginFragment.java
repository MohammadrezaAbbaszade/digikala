package com.example.digikala.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.digikala.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private Button mRegisterButton;
    private EditText mPhoneNumberEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        return view;
    }
    private void init(View view)
    {
        mEmailEditText=view.findViewById(R.id.fragment_login_email_edit_text);
        mFirstNameEditText=view.findViewById(R.id.fragment_login_first_name_edit_text);
        mLastNameEditText=view.findViewById(R.id.fragment_last_name_edit_text);
        mPhoneNumberEditText=view.findViewById(R.id.fragment_login_phone_number_edit_text);
        mPasswordEditText=view.findViewById(R.id.fragment_login_password_edit_text);
        mRegisterButton=view.findViewById(R.id.fragment_login_register_button);

    }

}
