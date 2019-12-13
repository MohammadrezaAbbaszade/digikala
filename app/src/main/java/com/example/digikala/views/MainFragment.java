package com.example.digikala.views;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.digikala.RecyclersViews.ProductsRecyclerView;
import com.example.digikala.R;
import com.example.digikala.RecyclersViews.SliderAdaptor;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;
import com.example.digikala.viewmodels.MainViewModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import Woo.Repository.Repository;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mRatedRecyclerView;
    private RecyclerView mPopularRecyclerView;
    private ProductAdaptor mProductAdaptor;
    private RecyclerView mRecentRecyclerView;
    private ProductsRecyclerView mNewestProductAdaptor;
    private ProductsRecyclerView mPopularProductAdaptor;
    private ProductsRecyclerView mRatedRecyclerAdaptor;
    private SliderView mSliderView;
    private changeFragment mChangeFragment;
    private SliderAdaptor mSliderAdaptor;
    private MainViewModel mMainViewModel;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.loadNewestProducts();
        mMainViewModel.loadPopularProducts();
        mMainViewModel.loadRatedProducts();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isNetworkConnected()) {
            mChangeFragment.changeFragment(false);
        }
        Log.d("tag", "onResume");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof changeFragment) {
            mChangeFragment = (changeFragment) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeFragment = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        mMainViewModel.getPopularProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                initSliderView(woocommerceBodies);
                updateAdaptor(woocommerceBodies,1);
            }
        });
        mMainViewModel.getNewestProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                updateAdaptor(woocommerceBodies,0);
            }
        });
        mMainViewModel.getRatedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                updateAdaptor(woocommerceBodies,2);
            }
        });




        return view;
    }

    private void initSliderView(List<WoocommerceBody> woocommerceBodies) {
        mSliderAdaptor = new SliderAdaptor(woocommerceBodies, getActivity());
        mSliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        mSliderView.setSliderAdapter(mSliderAdaptor);
    }

    private void init(View view) {
        mSliderView = view.findViewById(R.id.imageSlider);
        mCategoryRecyclerView = view.findViewById(R.id.fragment_main_recycler);
        mRecentRecyclerView = view.findViewById(R.id.fragment_main_newest_product_recycler);
        mPopularRecyclerView = view.findViewById(R.id.fragment_main_popular_product_recycler);
        mRatedRecyclerView = view.findViewById(R.id.fragment_main_rated_product_recycler);
    }


    public void updateAdaptor(List<WoocommerceBody> woocommerceBodies,int state) {

        switch (state)
        {
            case 0:
                mNewestProductAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
            case 1:
                mPopularProductAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
            case 2:
                mRatedRecyclerAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
        }

//        mProductAdaptor = new ProductAdaptor(Repository.getInstance().getFilteredCategoriesItems());
        mCategoryRecyclerView.setAdapter(mProductAdaptor);
        mRecentRecyclerView.setAdapter(mNewestProductAdaptor);
        mPopularRecyclerView.setAdapter(mPopularProductAdaptor);
        mRatedRecyclerView.setAdapter(mRatedRecyclerAdaptor);
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private Button mButton;
        private CategoriesBody mCategoriesBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.product_items);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = CategoriesViewPagerActivity.newIntent(getActivity(), mCategoriesBody.getId());
                    startActivity(intent);
                }
            });

        }

        void bind(CategoriesBody categoriesItem) {
            mCategoriesBody = categoriesItem;
            mButton.setText(categoriesItem.getName());
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<CategoriesBody> mCategoriesItems;

        public ProductAdaptor(List<CategoriesBody> categoriesItems) {
            mCategoriesItems = categoriesItems;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_items_product, parent, false);
            ProductHolder productHolder = new ProductHolder(view);
            return productHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            holder.bind(mCategoriesItems.get(position));

        }

        @Override
        public int getItemCount() {
            return mCategoriesItems.size();
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}