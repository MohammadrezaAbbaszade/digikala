package com.example.digikala.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.customermodels.CustomerBody;
import com.example.digikala.viewmodels.LoginViewModel;
import com.example.digikala.views.activities.MainActivity;
import com.example.digikala.views.changeFragment;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mRegisterTextview;
    private Button mLoginButton;
    private LoginViewModel mLoginViewModel;
    private changeFragment mChangeFragment;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof changeFragment) {
            mChangeFragment = (changeFragment) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeFragment = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        mRegisterTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeFragment.changeFragment(true);
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString().trim();
                if (!EMailValidation(email)) {
                    Toast.makeText(getActivity(), getString(R.string.wrong_email), Toast.LENGTH_SHORT).show();
                    return;
                }
                mLoginViewModel.getCustomer(email).observe(LoginFragment.this, new Observer<List<CustomerBody>>() {
                    @Override
                    public void onChanged(List<CustomerBody> customerBodies) {

                    }
                });
                mLoginViewModel.getCustomer(email).observe(LoginFragment.this, new Observer<List<CustomerBody>>() {
                    @Override
                    public void onChanged(List<CustomerBody> customerBodies) {
                        if (!customerBodies.isEmpty()) {
                            if (customerBodies.get(0).getCode() == 400) {
                                SharedPreferencesData.setCustomerLogedIn(getContext(), false);
                                Toast.makeText(getActivity(), getString(R.string.fix_email), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (customerBodies.get(0).getError() != null) {
                                SharedPreferencesData.setCustomerLogedIn(getContext(), false);
                                Toast.makeText(getActivity(), "خطای اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SharedPreferencesData.setCustomerEmail(getContext(), customerBodies.get(0).getEmail());
                            SharedPreferencesData.setCustomertId(getContext(), customerBodies.get(0).getId());
                            SharedPreferencesData.setCustomerName(getContext(), customerBodies.get(0).getFirstName()+" "+customerBodies.get(0).getLastName());
                            SharedPreferencesData.setCustomerLogedIn(getContext(), true);
                            Toast.makeText(getActivity(), getString(R.string.login_successfull),
                                    Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else {
                            SharedPreferencesData.setCustomerLogedIn(getContext(), false);
                            Toast.makeText(getActivity(), getString(R.string.fix_email), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    private void init(View view) {
        mEmailEditText = view.findViewById(R.id.fragment_login_email_edit_text);
        mPasswordEditText = view.findViewById(R.id.fragment_login_password_edit_text);
        mLoginButton = view.findViewById(R.id.fragment_login_button);
        mRegisterTextview = view.findViewById(R.id.fragment_login_register_text_view);
    }

    public boolean EMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

    public interface changeFragment {
        void changeFragment(boolean check);
    }
}
