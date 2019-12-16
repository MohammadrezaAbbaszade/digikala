package com.example.digikala.network;


import com.example.digikala.model.ordersModels.OrderBody;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WoocommerceService {

    @GET("products")
    Call<List<WoocommerceBody>> getWooCommerceBody(@QueryMap Map<String, String> queries);

    @GET("products/categories")
    Call<List<CategoriesBody>> getCategories(@QueryMap Map<String, String> queries);

    @GET("products/{id}")
    Call<WoocommerceBody> getProductById(@Path("id") String productId,@QueryMap Map<String, String> queries);

    @GET("products")
    Call<List<WoocommerceBody>> getReleatedProducts(@QueryMap Map<String, String> queries,@Query("include") String...releateds);
    @GET("products")
    Call<List<WoocommerceBody>> getSortedBaseProducts(@QueryMap Map<String, String> queries);

    @GET("products")
    Call<List<WoocommerceBody>> searchProducts(@Query("search") String productName,@QueryMap Map<String, String> queries);

    @POST("orders")
    Call<OrderBody> createCustomer(@Body OrderBody orderBody);
}
