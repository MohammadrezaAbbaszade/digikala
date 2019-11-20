package com.example.digikala.RecyclersViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikala.R;
import com.example.digikala.activities.ProductDetailActivity;
import com.example.digikala.model.WoocommerceBody;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularProductRecyclerViews extends RecyclerView.Adapter {
    private List<WoocommerceBody> productList;
    private Context mContext;
    private View view;

    public PopularProductRecyclerViews(List<WoocommerceBody> toDoList, Context context) {
        productList = toDoList;
        mContext = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.list_newest_products, parent, false);
      ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductHolder productHolder = (ProductHolder) holder;
        productHolder.bind(productList.get(position));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private TextView mRegularPriceTextView;
        private TextView mBudgetPriceTextView;
        private WoocommerceBody mWoocommerceBody;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.list_newest_products_text_view);
            mImageView = itemView.findViewById(R.id.list_newest_product_image_view);
            mRegularPriceTextView = itemView.findViewById(R.id.regular_price);
            mBudgetPriceTextView=itemView.findViewById(R.id.budget_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= ProductDetailActivity.newIntent(mContext,mWoocommerceBody.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        void bind(WoocommerceBody woocommerceBody) {
            mWoocommerceBody=woocommerceBody;
            if(!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
                mTextView.setText(woocommerceBody.getName());
            mRegularPriceTextView.setText(woocommerceBody.getPrice()+" "+"تومان");
            mBudgetPriceTextView.setText(woocommerceBody.getRegularPrice() + " " + "تومان");
            Picasso.with(mContext).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                    .into(mImageView);
        }
    }
}
