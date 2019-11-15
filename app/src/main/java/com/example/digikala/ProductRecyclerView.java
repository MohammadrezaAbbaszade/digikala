package com.example.digikala;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikala.model.ImagesItem;
import com.example.digikala.model.WoocommerceBody;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductRecyclerView extends RecyclerView.Adapter {
    private List<WoocommerceBody> productList;
    private Context mContext;
    private View view;

    public ProductRecyclerView(List<WoocommerceBody> toDoList, Context context) {
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

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.list_newest_products_text_view);
            mImageView = itemView.findViewById(R.id.list_newest_product_image_view);
            mRegularPriceTextView=itemView.findViewById(R.id.regular_price);
        }

        void bind(WoocommerceBody woocommerceBody) {
            if(!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
            mTextView.setText(woocommerceBody.getName());
            mRegularPriceTextView.setText(woocommerceBody.getPrice()+""+"تومان");
            Picasso.with(mContext).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                    .into(mImageView);
        }
    }
}






