package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.digikala.R;
import com.example.digikala.fragments.MainFragment;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;
import com.example.digikala.network.WoocommerceService;

import java.util.List;

import Woo.C.Repository;

public class SplashActivity extends AppCompatActivity  implements WooCommerce.WooCommerceCallback{
    private Handler handler;
    private WooCommerce mWooCommerce=new WooCommerce();
    List<WoocommerceBody> mWoocommerceBodies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       mWooCommerce.setCallback(this);
        mWooCommerce.productPopularityAsync();
//        mWooCommerce.productRatedAsync();
//        mWooCommerce.productRecentPhotosAsync();
        handler = new Handler();



    }

    @Override
    public void onRetrofitResponse(List<WoocommerceBody> items) {
       Repository.getInstance().setWoocommerceBodies(items);
        Log.d("tag","onRetrofitResponse");
    }

    @Override
    public void checkNetwork(boolean check) {
        if(check) {
            Intent intent = MainActivity.newIntent(this, 1);
            Log.d("tag","checkNetwork");
            startActivity(intent);
            finish();
        }else
        {
            Intent intent = MainActivity.newIntent(this, 0);
            Log.d("tag","checkNetwork"+"0");
            startActivity(intent);
            finish();
        }
    }
}
