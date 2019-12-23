package com.example.digikala.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.views.fragments.SignUpFragment;

import Woo.Repository.Repository;

public class LoginActivity extends AppCompatActivity {
    private ImageView mArrowButton;
    private TextView mToolbarTextView;
    private ImageButton mToolbarBag;
    private TextView mCartBadge;
    private ImageButton mSearchImageButton;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mCartBadge != null) {
            mCartBadge.setText(Repository.getInstance().getBagsIds().size() + "");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mArrowButton = findViewById(R.id.login_toolbar_arrow);
        mToolbarTextView = findViewById(R.id.login_toolbar_text_view);
        mToolbarBag=findViewById(R.id.login_toolbar_shop);
        mSearchImageButton = findViewById(R.id.login_toolbar_search_view);
        mCartBadge=findViewById(R.id.login_toolbar_cart_badge);
        mCartBadge.setText(Repository.getInstance().getBagsIds().size() + "");
        mToolbarBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopBagFragmentActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
        mSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SearchActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.login_activity_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.login_activity_container, SignUpFragment.newInstance())
                    .commit();
        }

    }
}