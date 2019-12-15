package com.example.digikala.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.example.digikala.R;
import com.example.digikala.model.WoocommerceBody;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    List<WoocommerceBody> mWoocommerceBodies;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       ProductAsyncTask productAsyncTask=new ProductAsyncTask();
       productAsyncTask.execute();
    }
    private class ProductAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Repository.getInstance().setCategoriesItems(Repository.getInstance().productCategoriesSync());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isNetworkConnected()){
            if(Repository.getInstance().getCategoriesItems()!=null)
            {
                Intent intent=MainActivity.newIntent(SplashActivity.this,1);
                startActivity(intent);
                finish();
            }
            }else
            {
                Intent intent=MainActivity.newIntent(SplashActivity.this,0);
                startActivity(intent);
                finish();
            }
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
