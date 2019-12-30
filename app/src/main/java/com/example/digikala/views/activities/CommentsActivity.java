package com.example.digikala.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.digikala.R;
import com.example.digikala.views.fragments.CommentsFragment;

public class CommentsActivity extends AppCompatActivity {


    private static final String PRODUCT_ID = "productsId";

    public static Intent newIntent(Context context, int productId) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        int productId = getIntent().getIntExtra(PRODUCT_ID,0);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.comment_container);
        if (fragment == null) {
            fm.beginTransaction().replace(R.id.comment_container, CommentsFragment.newInstance(productId))
                    .commit();
        }
    }
}
