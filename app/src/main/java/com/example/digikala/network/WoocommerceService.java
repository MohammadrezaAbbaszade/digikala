package com.example.digikala.network;


import com.example.digikala.model.attributesmodels.AttributeBody;
import com.example.digikala.model.attributetermsmodels.AttributeTermsBody;
import com.example.digikala.model.customermodels.CustomerBody;
import com.example.digikala.model.ordersModels.OrderBody;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;
import com.example.digikala.model.reviewsmodels.ReviewBody;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<WoocommerceBody>> searchProducts(@QueryMap Map<String, String> queries, @Query("page") int page);

    @POST("orders")
     Call<OrderBody> setOrder(@QueryMap Map<String, String> queries,@Body OrderBody orderBody);

    @GET("products/attributes")
    Call<List<AttributeBody>> getProductAttributes(@QueryMap Map<String, String> queries);

    @GET("products/attributes/{id}/terms")
    Call<List<AttributeTermsBody>> getAttributesTerms(@Path("id") int attrId,@QueryMap Map<String, String> queries);
    @GET("products")
    Call<List<WoocommerceBody>> getsortedAndFilteredProductsList(@QueryMap Map<String, String> queries, @Query("page") int page);
    @GET("products/reviews")
    Call<List<ReviewBody>> getProductComment (@QueryMap Map<String, String> queries,@Query("product") int productId);
    @POST("products/reviews")
    Call<ReviewBody> sendCustomerComment(@QueryMap Map<String, String> queries,@Body ReviewBody reviewBody);
    @DELETE("products/reviews/{id}")
    Call<ReviewBody> deleteCustomerComment(@Path("id") int id,@QueryMap Map<String, String> queries);
    @POST("customers")
    Call<CustomerBody> registerCustomer(@QueryMap Map<String, String> queries,@Body CustomerBody customerBody);
    @GET("customers")
    Call<List<CustomerBody>> getCustomer(@QueryMap Map<String, String> queries,@Query("email") String email);
    @PUT("customers/{id}")
    Call<CustomerBody> updateCustomer(@Path("id")int customerId,@QueryMap Map<String, String> queries , @Body CustomerBody customerBody);
    @PUT("products/reviews/{id}")
    Call<ReviewBody> updateComment(@Path("id") int id,@QueryMap Map<String, String> queries , @Body ReviewBody reviewBody);
}
