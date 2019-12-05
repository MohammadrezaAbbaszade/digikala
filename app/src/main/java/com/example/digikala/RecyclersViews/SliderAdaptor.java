package com.example.digikala.RecyclersViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.digikala.R;
import com.example.digikala.model.WoocommerceBody;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdaptor extends SliderViewAdapter<SliderAdaptor.SliderProductViewHolder>{
    private List<WoocommerceBody> productList;
    private Context mContext;

    public SliderAdaptor(List<WoocommerceBody> toDoList, Context context) {
        productList = toDoList;
        mContext = context;
    }

    @Override
    public SliderProductViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderProductViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_view ,
                parent ,false));
    }

    @Override
    public void onBindViewHolder(SliderProductViewHolder viewHolder, int position) {
        viewHolder.bind(productList.get(position));
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    public class SliderProductViewHolder extends SliderViewAdapter.ViewHolder {
        private ImageView imageViewBackground;
        public SliderProductViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image);
        }
        void bind(WoocommerceBody woocommerceBody) {

                Picasso.with(mContext).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                        .into(imageViewBackground);
        }


    }
}
