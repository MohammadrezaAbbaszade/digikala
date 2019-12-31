package com.example.digikala.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.views.fragments.MainFragment;
import com.example.digikala.views.fragments.NoNetworkFragment;
import com.example.digikala.views.changeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import Woo.Repository.Repository;

public class MainActivity extends AppCompatActivity implements changeFragment {
    private static final String STATE = "state";
    private static final String WOOCOMEERCE_BODY = "woocommercebody";
    private DrawerLayout mDrawerLayout;
    private FrameLayout mToolbar;
    private TextView mcart_badge;
    private TextView mBagTextView;
    private ImageView mToolbarUserImageView;
    private NavigationView mNavigationView;
    private ImageButton mImageButton;
    private ImageButton mSearchImageButton;
    private ImageButton mToolbarBag;
    private ImageView mLoginHeaderImageView;
    private TextView mHeaderCustomerNameTextView;
    private ImageView mHeaderUserIcon;
    private LinearLayout mHeaderLogin;
    int state;
    List<WoocommerceBody> woocommerceBodies;
    FragmentManager fm = getSupportFragmentManager();

    public static Intent newIntent(Context context, int state) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(STATE, state);
        return intent;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mcart_badge != null) {
            mcart_badge.setText(Repository.getInstance().getBagsIds().size() + "");
            initBagTextview();
        }
        if(SharedPreferencesData.checkCustomerLogedIn(this))
        {
            if(mHeaderCustomerNameTextView!=null&&mHeaderUserIcon!=null&&mLoginHeaderImageView!=null)
            {

                mToolbarUserImageView.setVisibility(View.VISIBLE);
                mLoginHeaderImageView.setVisibility(View.GONE);
                mHeaderLogin.setVisibility(View.VISIBLE);
                mHeaderCustomerNameTextView.setText(SharedPreferencesData.getCustomerName(this));
            }
        }else
        {
            if(mHeaderCustomerNameTextView!=null&&mHeaderUserIcon!=null&&mLoginHeaderImageView!=null) {
                mToolbarUserImageView.setVisibility(View.GONE);
                mLoginHeaderImageView.setVisibility(View.VISIBLE);
                mHeaderLogin.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = getIntent().getIntExtra(STATE, 0);
        mDrawerLayout = findViewById(R.id.acvity_drawer);
        mNavigationView = findViewById(R.id.navigation);
        mToolbar = findViewById(R.id.toolbar);
        mcart_badge = findViewById(R.id.cart_badge);
        mToolbarBag = findViewById(R.id.toolbar_shop);
        mSearchImageButton = findViewById(R.id.search_view_button);
        mLoginHeaderImageView = findViewById(R.id.main_toolbal_login_image_view);
        mToolbarUserImageView=findViewById(R.id.toolbar_user_image_view);
        mHeaderLogin=findViewById(R.id.header_linear);

//        setSupportActionBar(mToolbar);

        initBagTextview();
        mSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SearchActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        mcart_badge.setText(Repository.getInstance().getBagsIds().size() + "");
        mToolbarBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopBagFragmentActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        mImageButton = findViewById(R.id.toolbar_burger);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
        View header = mNavigationView.getHeaderView(0);
        mLoginHeaderImageView = header.findViewById(R.id.main_toolbal_login_image_view);
        mHeaderCustomerNameTextView=header.findViewById(R.id.main_header_number_text_view);
        mHeaderUserIcon=header.findViewById(R.id.main_header_user_image_view);
        mHeaderLogin=header.findViewById(R.id.header_linear);
        mHeaderLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.newIntent(MainActivity.this,1);
                startActivity(intent);
            }
        });
        if (state == 1) {
            fm.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.VISIBLE);
            Log.d("tag", "mainActivity" + "1");
        } else {
            fm.beginTransaction().replace(R.id.fragment_container, NoNetworkFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.GONE);
            Log.d("tag", "mainActivity" + "0");
        }
        mHeaderCustomerNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=CustomerInfoActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        mToolbarUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=CustomerInfoActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.d("nav", "onNavigationItemSelected");
                switch (menuItem.getItemId()) {
                    case R.id.newest_menu:
                        Log.d("tag", "newest_menu");
                        Intent intent = ListProductsActivity.newIntent(MainActivity.this, 3);
                        startActivity(intent);
                        break;
                    case R.id.most_seen__menu:
                        Log.d("tag", "most_seen__menu");
                        Intent intent2 = ListProductsActivity.newIntent(MainActivity.this, 2);
                        startActivity(intent2);
                        break;
                    case R.id.most_sales__menu:
                        Log.d("tag", "most_seen__menu");
                        Intent intent3 = ListProductsActivity.newIntent(MainActivity.this, 1);
                        startActivity(intent3);
                        break;
                    case R.id.home_menu:
                        break;
                    case R.id.product_list__menu:
                        Intent intent4 = CategoriesViewPagerActivity.newIntent(MainActivity.this);
                        startActivity(intent4);
                        break;
                    case R.id.sale_bag__menu:
                        Intent intent5 = ShopBagFragmentActivity.newIntent(MainActivity.this);
                        startActivity(intent5);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initBagTextview() {
        mBagTextView = (TextView) (mNavigationView.getMenu().findItem(R.id.sale_bag__menu).getActionView());
        mBagTextView.setGravity(Gravity.CENTER_VERTICAL);
        mBagTextView.setTextColor(getResources().getColor(R.color.black));
        mBagTextView.setTextSize(16);
        mBagTextView.setText(Repository.getInstance().getBagsIds().size() + "");

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void changeFragment(boolean check) {
        if (check == false) {
            fm.beginTransaction().replace(R.id.fragment_container, NoNetworkFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.GONE);
            mNavigationView.cancelDragAndDrop();
        } else {
            fm.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.VISIBLE);
            mNavigationView.setVisibility(View.VISIBLE);
        }
    }
}
