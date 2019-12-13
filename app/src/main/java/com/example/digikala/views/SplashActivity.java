package com.example.digikala.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.digikala.R;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    private WooCommerce mWooCommerce = new WooCommerce();
    List<WoocommerceBody> mWoocommerceBodies;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = MainActivity.newIntent(SplashActivity.this,1);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 5000);
    }

}
