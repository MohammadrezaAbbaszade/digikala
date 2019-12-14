package com.example.digikala.views.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.viewmodels.ShopBagViewModel;
import com.example.digikala.views.activities.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopBagFragment extends Fragment {
    private List<WoocommerceBody> mRelatedProducts = new ArrayList<>();
    private RecyclerView mRelatedProductRecycler;
    private ProductAdaptor mProductAdaptor;
    private TextView mShopFinalSumTextView;
    private ProgressBar mProgressBar;
    private ShopBagViewModel mShopBagViewModel;
private Repository mRepository;
    public static ShopBagFragment newInstance() {

        Bundle args = new Bundle();
        ShopBagFragment fragment = new ShopBagFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShopBagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()) {
            getActivity().finish();
            Log.d("tag", "finished");
        }else
        {
            mRepository=Repository.getInstance();
            mShopBagViewModel= ViewModelProviders.of(this).get(ShopBagViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_bag, container, false);
        mRelatedProductRecycler = view.findViewById(R.id.shop_bag_recycler_view);
        mShopFinalSumTextView = view.findViewById(R.id.shop_final_sum_text_view);
        mProgressBar=view.findViewById(R.id.shop_bag_fragment_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        if(mProductAdaptor!=null)
        mRelatedProductRecycler.setVisibility(View.GONE);
        mRelatedProductRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShopBagViewModel.getRelatedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
               mRelatedProducts=woocommerceBodies;
               setUpAdaptor();
                if(mProductAdaptor!=null)
                    mRelatedProductRecycler.setVisibility(View.VISIBLE);
               mProgressBar.setVisibility(View.GONE);
            }
        });
        PrepareRelatedProducts();
        Log.d("tag", "dialogOnCreatView");
        return view;
    }

    private void PrepareRelatedProducts() {
        List<String> ids = new ArrayList<>();
        ids.add("0");
        ids.addAll(Repository.getInstance().getBagsIds());
        Log.d("tag", "PrepareRelatedProducts" + " " + ids.toString());
        mShopBagViewModel.loadRelatedProducts(ids);
    }

    private void calculateProductsPrice() {
        double finalValue = 0;
        for (WoocommerceBody woocommerceBody : mRelatedProducts) {
            double price = Double.valueOf(woocommerceBody.getPrice());
            finalValue += price;
        }

        mShopFinalSumTextView.setText(getString(R.string.price_format, finalValue + " "));
    }

    private void setUpAdaptor() {
        Log.d("tag", "setUpAdaptor");
        mProductAdaptor = new ProductAdaptor(mRelatedProducts);
        mRelatedProductRecycler.setAdapter(mProductAdaptor);
        calculateProductsPrice();
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private Button mDeleteBbutton;
        private TextView mRegularPriceTextView;
        private TextView mBudgetPriceTextView;
        private WoocommerceBody mWoocommerceBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mDeleteBbutton = itemView.findViewById(R.id.delete_product_bag_btn);
            mRegularPriceTextView = itemView.findViewById(R.id.final_price_text_view);
            mTextView = itemView.findViewById(R.id.product_shop_bag_name_text_view);
            mImageView = itemView.findViewById(R.id.product_shop_bag_image);
            mBudgetPriceTextView = itemView.findViewById(R.id.full_price_text_view);
            mDeleteBbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowMsgDialog(getActivity(), mWoocommerceBody);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ProductDetailActivity.newIntent(getActivity(), mWoocommerceBody.getId(), mWoocommerceBody.getName());
                    startActivity(intent);
                }
            });

        }

        void bind(WoocommerceBody woocommerceBody) {
            mWoocommerceBody = woocommerceBody;
            if (!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
                mTextView.setText(woocommerceBody.getName());
            mBudgetPriceTextView.setText(woocommerceBody.getRegularPrice() + " " + "تومان");
            mRegularPriceTextView.setText(woocommerceBody.getPrice() + " " + "تومان");
            Picasso.with(getActivity()).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                    .into(mImageView);
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<WoocommerceBody> mWoocommerceBodies;

        public ProductAdaptor(List<WoocommerceBody> woocommerceBodies) {
            mWoocommerceBodies = woocommerceBodies;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.shop_bag_list_item, parent, false);
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

    public void ShowMsgDialog(Context self, final WoocommerceBody woocommerceBody) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(self);
        dlgAlert.setMessage("Are you Sure?");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.d("tag", Integer.toString(woocommerceBody.getId()));
                Repository.getInstance().deleteBag(Integer.toString(woocommerceBody.getId()));
                mRelatedProducts.remove(woocommerceBody);
                setUpAdaptor();

            }
        });
        dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dlgAlert.show();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
