package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.fragments.ListProductsFragment;

import Woo.Repository.Repository;

public class ListProductsActivity extends AppCompatActivity {
    private ImageView mArrowButton;
    private TextView mToolbarTextView;
    private ImageButton mToolbarBag;
    private TextView mCartBadge;
    private static final String STATE = "state";

    public static Intent newIntent(Context context, int state) {
        Intent intent = new Intent(context, ListProductsActivity.class);
        intent.putExtra(STATE, state);
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
        setContentView(R.layout.activity_list_products);
        int state = getIntent().getIntExtra(STATE, 0);
        mArrowButton = findViewById(R.id.list_product_toolbar_arrow);
        mToolbarTextView = findViewById(R.id.list_product_toolbar_text_view);
        mToolbarBag=findViewById(R.id.list_products_toolbar_shop);
        mCartBadge=findViewById(R.id.list_product_toolbar_cart_badge);
        mCartBadge.setText(Repository.getInstance().getBagsIds().size() + "");
        mToolbarBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopBagFragmentActivity.newIntent(ListProductsActivity.this);
                startActivity(intent);
            }
        });
        switch (state) {
            case 1:
                mToolbarTextView.setText("پرفروش ترین ها");
                break;
            case 2:
                mToolbarTextView.setText("پربازدیدترین ها");
                break;
            case 3:
                mToolbarTextView.setText("جدیدترین ها");
                break;
            default:
                mToolbarTextView.setText(SharedPreferencesData.getQuery(this));
        }


        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Log.d("tag", "onCreateViewLA");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.list_products_activity_container);
        if (fragment == null) {
            fm.beginTransaction().replace(R.id.list_products_activity_container, ListProductsFragment.newInstance(state))
                    .commit();
        }
    }
}
