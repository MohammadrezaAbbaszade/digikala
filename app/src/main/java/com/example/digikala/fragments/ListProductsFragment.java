package com.example.digikala.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.activities.MainActivity;
import com.example.digikala.activities.ProductDetailActivity;
import com.example.digikala.model.CategoriesItem;
import com.example.digikala.model.WoocommerceBody;
import com.squareup.picasso.Picasso;

import java.util.List;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProductsFragment extends Fragment {
    private static final String STATE = "state";
    private RecyclerView mListProductsRecycler;
    private int state;
    private changeFragment mChangeFragment;
    public static ListProductsFragment newInstance(int state) {

        Bundle args = new Bundle();
        args.putInt(STATE, state);
        ListProductsFragment fragment = new ListProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()) {
            Intent intent = MainActivity.newIntent(getActivity(), 0);
            Log.d("tag", "checkNetwork" + "0");
            startActivity(intent);
            getActivity().finish();
            Log.d("tag","finished");
        }
        state=getArguments().getInt(STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_products, container, false);
        init(view);
        Log.d("tag","onCreateViewL");
        ProductAdaptor productAdaptor = new ProductAdaptor(state);
        mListProductsRecycler.setAdapter(productAdaptor);
        return view;
    }

    private void init(View view) {
        mListProductsRecycler = view.findViewById(R.id.list_products_fragment_recycler);

    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mBudgetTextView;
        private ImageView mImageView;
        private TextView mRegularPriceTextView;
        private WoocommerceBody mWoocommerceBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.list_products_text_view);
            mImageView = itemView.findViewById(R.id.list_products_image_view);
            mRegularPriceTextView = itemView.findViewById(R.id.list_products_regular_price);
            mBudgetTextView = itemView.findViewById(R.id.list_products_budget_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ProductDetailActivity.newIntent(getActivity(), mWoocommerceBody.getId());
                    getActivity().startActivity(intent);
                }
            });

        }

        void bind(WoocommerceBody woocommerceBody) {
            mWoocommerceBody = woocommerceBody;
            if (!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
                mNameTextView.setText(woocommerceBody.getName());
            mRegularPriceTextView.setText(woocommerceBody.getPrice() + "" + "تومان");
            mBudgetTextView.setText(woocommerceBody.getRegularPrice() + "تومان");
            Picasso.with(getActivity()).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                    .into(mImageView);
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<WoocommerceBody> mWoocommerceBodies;

        public ProductAdaptor(int state) {
            switch (state) {
                case 0:
                    break;
                case 1:
                    mWoocommerceBodies = Repository.getInstance().getPopularProducts();
                    break;
                case 2:
                    mWoocommerceBodies = Repository.getInstance().getRatedProducts();
                    break;
                default:
                    mWoocommerceBodies = Repository.getInstance().getNewestProducts();
            }
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.products_linear_items, parent, false);
            ProductHolder productHolder = new ProductHolder(view);
            return productHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            holder.bind(mWoocommerceBodies.get(position));

        }

        @Override
        public int getItemCount() {
            return mWoocommerceBodies.size();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
