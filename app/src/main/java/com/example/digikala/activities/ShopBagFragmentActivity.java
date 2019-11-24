package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.digikala.R;
import com.example.digikala.fragments.ShopBagFragment;

public class ShopBagFragmentActivity extends AppCompatActivity {
    private static final String PRODUCT_ID = "id";
    private static final String STATE = "state";
    public static Intent newIntent(Context context,int productId, int state) {
        Intent intent = new Intent(context, ShopBagFragmentActivity.class);
        intent.putExtra(STATE, state);
        intent.putExtra(PRODUCT_ID,productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_bag_fragment);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.shop_bag_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.shop_bag_container, ShopBagFragment.newInstance(getIntent().getIntExtra(PRODUCT_ID,0),getIntent().getIntExtra(STATE,0)))
            .commit();
        }
    }
}
