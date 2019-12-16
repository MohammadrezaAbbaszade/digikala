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
import com.example.digikala.model.ordersModels.Billing;
import com.example.digikala.model.ordersModels.OrderBody;
import com.example.digikala.model.ordersModels.Shipping;
import com.example.digikala.viewmodels.LoginViewModel;
import com.google.gson.Gson;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        mLoginViewModel.getOrderBodyies().observe(this, new Observer<OrderBody>() {
            @Override
            public void onChanged(OrderBody orderBody) {
                if (orderBody.getStatus().equals("400")) {
                    mProgressBar.setVisibility(View.GONE);
                    mRegisterTextView.setVisibility(View.VISIBLE);
                    mSingleArrowImageView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "شما قبلا ثبت نام کرده اید!", Toast.LENGTH_SHORT).show();

                }else {
                    mProgressBar.setVisibility(View.GONE);
                    mRegisterTextView.setVisibility(View.VISIBLE);
                    mSingleArrowImageView.setVisibility(View.VISIBLE);
                    mRegisterButton.setEnabled(false);
                    Toast.makeText(getActivity(), "خرید شما با موفقیت ثبت شد!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = mFirstNameEditText.getText().toString();
                lastName = mLastNameEditText.getText().toString();
                phoneNumber = mPhoneNumberEditText.getText().toString();
                email = mEmailEditText.getText().toString();
                password = mPasswordEditText.getText().toString();
                if (!firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty()
                        && !email.isEmpty() && !password.isEmpty()) {
                    mRegisterTextView.setVisibility(View.GONE);
                    mSingleArrowImageView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    initOrderBodyForServer();
                }else {
                    Toast.makeText(getActivity(),"لطفا تمامی فیلدهارا پر کنید!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void init(View view) {
        mEmailEditText = view.findViewById(R.id.fragment_login_email_edit_text);
        mFirstNameEditText = view.findViewById(R.id.fragment_login_first_name_edit_text);
        mLastNameEditText = view.findViewById(R.id.fragment_last_name_edit_text);
        mPhoneNumberEditText = view.findViewById(R.id.fragment_login_phone_number_edit_text);
        mPasswordEditText = view.findViewById(R.id.fragment_login_password_edit_text);
        mRegisterButton = view.findViewById(R.id.fragment_login_register_button);
        mProgressBar = view.findViewById(R.id.fragment_login_progress_bar);
        mRegisterTextView = view.findViewById(R.id.fragment_login_register_text_view);
        mSingleArrowImageView = view.findViewById(R.id.fragment_login_image_view);
    }

    private void initOrderBodyForServer() {
        Gson gson=new Gson();
        Shipping shipping = new Shipping();
        Billing billing = new Billing();
        OrderBody orderBody = new OrderBody();
        shipping.setFirstName(firstName);
        shipping.setLastName(lastName);
        billing.setFirstName(firstName);
        billing.setLastName(lastName);
        billing.setEmail(email);
        orderBody.setBilling(billing);
        orderBody.setShipping(shipping);
        Log.d("gson",gson.toJson(orderBody));
        mLoginViewModel.setOrder(orderBody);
    }

}
