package com.example.digikala.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.customermodels.Billing;
import com.example.digikala.model.customermodels.CustomerBody;
import com.example.digikala.model.customermodels.Shipping;
import com.example.digikala.model.ordersModels.OrderBody;
import com.example.digikala.viewmodels.LoginViewModel;
import com.example.digikala.views.activities.MainActivity;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private FrameLayout mRegisterButton;
    private EditText mPhoneNumberEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private LoginViewModel mLoginViewModel;
    private ProgressBar mProgressBar;
    private TextView mRegisterTextView;
    private ImageView mSingleArrowImageView;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init(view);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = mFirstNameEditText.getText().toString().trim();
                lastName = mLastNameEditText.getText().toString().trim();
                phoneNumber = mPhoneNumberEditText.getText().toString().trim();
                email = mEmailEditText.getText().toString().trim();
                password = mPasswordEditText.getText().toString().trim();
                if (!EMailValidation(email)){
                    Toast.makeText(getActivity() , getString(R.string.wrong_email) , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getActivity(), getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty() && lastName.isEmpty() && password.isEmpty() && firstName.isEmpty()
                        && lastName.isEmpty()){
                    Toast.makeText(getActivity(), getString(R.string.fill_all_field), Toast.LENGTH_SHORT).show();
                    return;
                }
                    mRegisterTextView.setVisibility(View.GONE);
                    mSingleArrowImageView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    initOrderBodyForServer();
            }
        });


        return view;
    }

    private void init(View view) {
        mEmailEditText = view.findViewById(R.id.fragment_login_email_edit_text);
        mFirstNameEditText = view.findViewById(R.id.fragment_sign_up_first_name_edit_text);
        mLastNameEditText = view.findViewById(R.id.fragment_sign_up_last_name_edit_text);
        mPhoneNumberEditText = view.findViewById(R.id.fragment_sign_up_phone_number_edit_text);
        mPasswordEditText = view.findViewById(R.id.fragment_login_password_edit_text);
        mRegisterButton = view.findViewById(R.id.fragment_sign_up_register_button);
        mProgressBar = view.findViewById(R.id.fragment_sign_up_progress_bar);
        mRegisterTextView = view.findViewById(R.id.fragment_login_register_text_view);
        mSingleArrowImageView = view.findViewById(R.id.fragment_sign_up_image_view);
    }

    private void initOrderBodyForServer() {
        CustomerBody customerBody = new CustomerBody();
        customerBody.setEmail(email);
        customerBody.setUsername(firstName);
        customerBody.setFirstName(firstName);
        customerBody.setLastName(lastName);
        customerBody.setBilling(new Billing(firstName,lastName,email,phoneNumber));
        customerBody.setShipping(new Shipping(firstName , lastName));
        mLoginViewModel.registerCustomer(customerBody).observe(this, new Observer<CustomerBody>() {
            @Override
            public void onChanged(CustomerBody customerBody) {
                if (customerBody.getUsername()!=null){
                    SharedPreferencesData.setCustomerEmail(getContext(), customerBody.getEmail());
                    SharedPreferencesData.setCustomerName(getContext(), customerBody.getFirstName());
                    SharedPreferencesData.setCustomerLogedIn(getContext(), true);
                    Toast.makeText(getActivity() , getString(R.string.register_successfull)  ,
                            Toast.LENGTH_SHORT).show();
                    startActivity(MainActivity.newIntent(getActivity(),1));
                    getActivity().finish();
                }else if (customerBody.getError() !=null){
                    SharedPreferencesData.setCustomerLogedIn(getContext(), false);
                    Toast.makeText(getActivity() , getString(R.string.problem_occur) + customerBody
                            .getError().getMessage() , Toast.LENGTH_SHORT).show();
                }else if (customerBody.getCode()==400){
                    SharedPreferencesData.setCustomerLogedIn(getContext(), false);
                    Toast.makeText(getActivity() , getString(R.string.repeat_email) ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
}
