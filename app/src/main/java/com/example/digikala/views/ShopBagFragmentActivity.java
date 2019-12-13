package com.example.digikala.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.digikala.R;

public class ShopBagFragmentActivity extends AppCompatActivity {
    private static final String PRODUCT_ID = "id";
    private static final String STATE = "state";
    private ImageButton mCloseImageButton;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ShopBagFragmentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_bag_fragment);
        mCloseImageButton=findViewById(R.id.shop_bag_close_button);
        mCloseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.shop_bag_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.shop_bag_container, ShopBagFragment.newInstance())
                    .commit();

        }
    }
}
