package com.example.digikala;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductRecyclerView extends RecyclerView.Adapter {
    private List<String> mToDoList;
    private Context mContext;
    private View view;

    public ProductRecyclerView(List<String> toDoList, Context context) {
        mToDoList = toDoList;
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
        productHolder.mTextView.setText(mToDoList.get(position));
    }


    @Override
    public int getItemCount() {
        return mToDoList.size();
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.list_newest_products_text_view);

        }
    }
}






