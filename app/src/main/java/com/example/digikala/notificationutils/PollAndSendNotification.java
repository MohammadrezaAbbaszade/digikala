package com.example.digikala.notificationutils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.network.RetrofitInstance;
import com.example.digikala.network.WoocommerceService;
import com.example.digikala.views.activities.MainActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollAndSendNotification {
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
    private Context mContext;

    public PollAndSendNotification(Context context) {
        mContext = context;
    }

    public void PollAndSendNotification() {
        int lastId = SharedPreferencesData.getLastProductsId(mContext);
        try {
            mNewestProducts = checkNewestProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mNewestProducts.get(0).getId()!=lastId)
        {
            Intent activityIntent = MainActivity.newIntent(mContext,1);
            PendingIntent pi = PendingIntent.getActivity(
                    mContext,
                    REQUEST_CODE_NOTIFICATION,
                    activityIntent,
                    0);

            String channelId = mContext.getString(R.string.channel_id);
            Notification notification = new NotificationCompat.Builder(mContext, channelId)
                    .setContentTitle(mContext.getString(R.string.new_products_title))
                    .setContentText(mNewestProducts.get(0).getName()+"اضافه شد")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mContext);
            managerCompat.notify(NOTOFICATION_ID, notification);

        }else {
            SharedPreferencesData.setLastProductId(mContext,mNewestProducts.get(0).getId());
        }
    }

    public List<WoocommerceBody> checkNewestProducts() throws IOException {
        mQueries.put("orderby", "date");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries, 1);
        return call.execute().body();
    }
}
