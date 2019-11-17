package com.example.digikala.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.digikala.R;
import com.example.digikala.activities.MainActivity;
import com.example.digikala.activities.SplashActivity;
import com.example.digikala.network.WooCommerce;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import Woo.Repository.Repository;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private static final String ID = "id";
    private int id;
    private WooCommerce mWooCommerce = new WooCommerce();
    private ViewPager mViewPager;
    private CircleIndicator mDotsIndicator;

    public static ProductDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ID, id);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(ID);
        InitProductsAsynceTask initProductsAsynceTask=new InitProductsAsynceTask();
        initProductsAsynceTask.execute();
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        init(view);

        return view;
    }

    private void PrepareViewPager() {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Repository.getInstance().getProductById().getImages().size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_view, null);
                ImageView imageView = view.findViewById(R.id.image);
                    Picasso.with(getActivity()).load(Repository.getInstance().getProductById().getImages().get(position).getSrc()).placeholder(R.drawable.digikala)
                            .into(imageView);

                container.addView(view);
                return view;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object view) {
                container.removeView((View) view);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return object == view;
            }

        });
        mDotsIndicator.setViewPager(mViewPager);
    }

    private void init(View view) {
        mViewPager = view.findViewById(R.id.detail_fragment_view_pager);
        mDotsIndicator = view.findViewById(R.id.detail_fragment_dots_indicator);


    }
    private class InitProductsAsynceTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Repository.getInstance().setProductById(mWooCommerce.getProductById(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Repository.getInstance().getProductById()!=null) {
                PrepareViewPager();
            }else {
                Intent intent = MainActivity.newIntent(getActivity(), 0);
                Log.d("tag", "checkNetwork" + "0");
                startActivity(intent);
                getActivity().finish();
            }
        }
    }
}
