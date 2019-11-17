package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.digikala.R;
import com.example.digikala.fragments.ProductDetailFragment;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String ID = "id";

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(ID,id);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        int id=getIntent().getIntExtra(ID,0);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.detail_activity_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.detail_activity_container, ProductDetailFragment.newInstance(id))
                    .commit();
        }

    }
}
