package com.example.digikala.network;


import com.example.digikala.model.attributesmodels.AttributeBody;
import com.example.digikala.model.attributetermsmodels.AttributeTermsBody;
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
    Call<List<WoocommerceBody>> getWooCommerceBody(@QueryMap Map<String, String> queries,@Query("page") int page);
    @GET("products")
    Call<List<WoocommerceBody>> getSpecialProducts(@QueryMap Map<String, String> queries,@Query("tags") int especialTag);
    @GET("products/categories")
    Call<List<CategoriesBody>> getCategories(@QueryMap Map<String, String> queries);

    @GET("products/{id}")
    Call<WoocommerceBody> getProductById(@Path("id") String productId,@QueryMap Map<String, String> queries);

    @GET("products")
    Call<List<WoocommerceBody>> getReleatedProducts(@QueryMap Map<String, String> queries,@Query("include") String...releateds);
    @GET("products")
    Call<List<WoocommerceBody>> getSortedBaseProducts(@QueryMap Map<String, String> queries);

    @GET("products")
    Call<List<WoocommerceBody>> searchProducts(@QueryMap Map<String, String> queries);

    @POST("orders")
     Call<OrderBody> setOrder(@QueryMap Map<String, String> queries,@Body OrderBody orderBody);

    @GET("products/attributes")
    Call<List<AttributeBody>> getProductAttributes(@QueryMap Map<String, String> queries);

    @GET("products/attributes/{id}/terms")
    Call<List<AttributeTermsBody>> getAttributesTerms(@Path("id") int attrId,@QueryMap Map<String, String> queries);
    @GET("products")
    Call<List<WoocommerceBody>> getsortedAndFilteredProductsList
            (@QueryMap Map<String, String> queries,@Query("order") String order ,
             @Query("orderby") String orderBy , @Query("page") int page
                    , @Query("search") String search , @Query("attribute") String attribute , @Query("attribute_term") List<Integer> attributeTerm);
}
