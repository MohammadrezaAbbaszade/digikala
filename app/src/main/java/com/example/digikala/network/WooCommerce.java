package com.example.digikala.network;

import android.util.Log;

import com.example.digikala.model.ImagesItem;
import com.example.digikala.model.WoocommerceBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WooCommerce {
    public static final String TAG = "FlickrFetcher";
    public static final String FLICKR_REST_PATH = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "%20ck_7c028a04c9faf616410b09e2ab90b1884c875d01";
    public static final String CONSUMER_SECRET = "cs_8c39f626780f01d135719f64214fd092b848f4aa";
    private WooCommerceCallback mCallback;

    public void setCallback(WooCommerceCallback callback) {
        mCallback = callback;
    }

    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    private WoocommerceService mWoocommerceApi = RetrofitInstance.getInstance(FLICKR_REST_PATH)
            .getRetrofit()
            .create(WoocommerceService.class);

//    public List<WoocommerceBody> productsRecentPhotos(String page) throws IOException {
//        mQueries.put("orderby", "date");
//        mQueries.put("page", page);
//        return getPhotoItems();
//    }
//
//    public List<WoocommerceBody> searchPhotos(String query) throws IOException {
//        mQueries.put("method", SEARCH_METHOD);
//        mQueries.put("text", query);
//
//        return getPhotoItems();
//    }

    public void productRecentPhotosAsync() {
        mQueries.put("orderby", "date");
//        mQueries.put("page", "2");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries);
        call.enqueue(getRetrofitCallback());
    }

//    public void searchPhotosAsync(String query) {
//        mQueries.put("method", SEARCH_METHOD);
//        mQueries.put("text", query);
//
//        Call<WoocommerceBody> call = mWoocommerceApi.getWooCommerceBody(mQueries);
//        call.enqueue(getRetrofitCallback());
//    }

    private Callback<List<WoocommerceBody>> getRetrofitCallback() {
        return new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    for (WoocommerceBody woocommerceBody : response.body()){
                        Log.e("TAG4" , woocommerceBody.getImages().get(0).getName() + " " +
                                "SRC " + woocommerceBody.getImages().get(0).getSrc());
                    }
                    List<ImagesItem> items = response.body().get(0).getImages();
                    mCallback.onRetrofitResponse(response.body());
                    Log.d("tag","onResponse"+items);
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {

                Log.d("tag","onFailure"+t.getMessage());
            }
        };
    }
    public interface WooCommerceCallback {
        void onRetrofitResponse(List<WoocommerceBody> items);
    }
}
