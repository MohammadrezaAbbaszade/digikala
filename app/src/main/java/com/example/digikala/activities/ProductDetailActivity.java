package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.fragments.ProductDetailFragment;

import Woo.Repository.Repository;

public class ProductDetailActivity extends AppCompatActivity {
    private static final String NAME_OF_PRODUCT = "nameOfProduct";
    private ImageButton mArrowButton;
    private ImageButton mToolbarBag;
    private TextView mToolbarTextView;
    private TextView mCartBadge;
    private String nameOfProduct;
    private static final String ID = "id";

    public static Intent newIntent(Context context, int id,String nameOfProduct) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(NAME_OF_PRODUCT,nameOfProduct);
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
        setContentView(R.layout.activity_product_detail);
        mArrowButton=findViewById(R.id.detail_fragment_toolbar_arrow);
        mToolbarTextView=findViewById(R.id.detail_fragment_toolbar_text_view);
        mCartBadge=findViewById(R.id.detail_fragment_cart_badge);
        mToolbarBag=findViewById(R.id.detail_fragment_toolbar_shop);
        mCartBadge.setText(Repository.getInstance().getBagsIds().size() + "");
        mToolbarBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopBagFragmentActivity.newIntent(ProductDetailActivity.this);
                startActivity(intent);
            }
        });
        nameOfProduct=getIntent().getStringExtra(NAME_OF_PRODUCT);
        mToolbarTextView.setText(nameOfProduct);
        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int id = getIntent().getIntExtra(ID, 0);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.detail_activity_container);
        if (fragment == null) {
            fm.beginTransaction().replace(R.id.detail_activity_container, ProductDetailFragment.newInstance(id))
                    .commit();
        }

    }
}
