package com.example.digikala.notificationutils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.network.RetrofitInstance;
import com.example.digikala.network.WoocommerceService;
import com.example.digikala.views.activities.MainActivity;
import com.example.digikala.views.activities.ProductDetailActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;

public class NotificationWorker extends Worker {
    public static final int REQUEST_CODE_NOTIFICATION = 1;
    public static final String FLICKR_REST_PATH = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
    public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    private final int NOTOFICATION_ID = 0;
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    private WoocommerceService mWoocommerceApi = RetrofitInstance.getInstance(FLICKR_REST_PATH)
            .getRetrofit()
            .create(WoocommerceService.class);
    private List<WoocommerceBody> mNewestProducts;
    private Repository mRepository;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("work2565", "enteredReq");
        int lastId = SharedPreferencesData.getLastProductsId(getApplicationContext());
        try {
            mNewestProducts = checkNewestProducts();
        } catch (IOException e) {
            Log.e("work2565", e.getMessage());
        }
        if (mNewestProducts.get(0).getId() != lastId) {
            Log.d("work2565", "enteredFirstIf");
            SendNotification();
        } else {
            Log.d("work2565", "enteredElse");
            SharedPreferencesData.setLastProductId(getApplicationContext(), mNewestProducts.get(0).getId());
        }
        return Result.success();
    }

    public void SendNotification(){
        {
            Intent activityIntent = ProductDetailActivity.newIntent(getApplicationContext(), mNewestProducts.get(0).getId(),
                    mNewestProducts.get(0).getName());
            PendingIntent pi = PendingIntent.getActivity(
                    getApplicationContext(),
                    REQUEST_CODE_NOTIFICATION,
                    activityIntent,
                    0);

            String channelId = getApplicationContext().getString(R.string.channel_id);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setContentTitle(getApplicationContext().getString(R.string.new_products_title))
                    .setContentText(mNewestProducts.get(0).getName() + "اضافه شد")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
            managerCompat.notify(NOTOFICATION_ID, notification);
        }

    }
    public List<WoocommerceBody> checkNewestProducts() throws IOException {
        mQueries.put("orderby", "date");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries, 1);
        return call.execute().body();
    }
}