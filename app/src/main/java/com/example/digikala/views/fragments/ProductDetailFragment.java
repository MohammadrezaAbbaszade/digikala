package com.example.digikala.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import com.example.digikala.RecyclersViews.SliderAdaptor;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.viewmodels.ProductDetailViewModel;
import com.example.digikala.views.activities.ProductDetailActivity;
import com.example.digikala.views.activities.ShopBagFragmentActivity;
import com.example.digikala.views.changeFragment;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private static final String ID = "id";
    private int id;

    private ProgressBar mProgressBar;
    private CardView mCardView;
    private TextView mTextView;
    private RecyclerView mRelatedProductRecycler;
    private TextView mDiscriptionTextView;
    private TextView mRegularPriceTextView;
    private TextView mBudgetPriceTextView;
    private ProductAdaptor mProductAdaptor;
    private Button mBuyButton;
    private SliderView mSliderView;
    private SliderAdaptor mSliderAdaptor;
    private changeFragment mChangeFragment;
private ProductDetailViewModel mProductDetailViewModel;
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

        if (isNetworkConnected()) {
            id = getArguments().getInt(ID);
            mProductDetailViewModel= ViewModelProviders.of(this).get(ProductDetailViewModel.class);
            mProductDetailViewModel.loadSingleProduct(id);
        } else {
//            Intent intent = MainActivity.newIntent(getActivity(), 0);
//            Log.d("tag", "checkNetwork" + "0");
//            startActivity(intent);
            getActivity().finish();
            Log.d("tag", "finished");
        }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        init(view);
        mProgressBar.setVisibility(View.VISIBLE);
        mSliderView.setVisibility(View.GONE);
        mCardView.setVisibility(View.GONE);
        mRelatedProductRecycler.setVisibility(View.GONE);
        mProductDetailViewModel.getRelatedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                mRelatedProductRecycler.setVisibility(View.VISIBLE);
                setUpAdaptor(woocommerceBodies);
            }
        });
        mProductDetailViewModel.getProductById().observe(this, new Observer<WoocommerceBody>() {
            @Override
            public void onChanged(WoocommerceBody woocommerceBody) {
                if(woocommerceBody!=null) {
                    mProgressBar.setVisibility(View.GONE);
                    mSliderView.setVisibility(View.VISIBLE);
                    mCardView.setVisibility(View.VISIBLE);
                    PrepareViewPager(woocommerceBody);
                    PrepareRelatedProducts(woocommerceBody.getRelatedIds());
                    addProductToBag(woocommerceBody);
                }else
                {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }
    private void PrepareViewPager(WoocommerceBody woocommerceBody) {


        mSliderAdaptor = new SliderAdaptor(woocommerceBody, getActivity());
        mSliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
       mSliderView.setSliderAdapter(mSliderAdaptor);


        mTextView.setText(woocommerceBody.getName());
        mDiscriptionTextView.setText(woocommerceBody.getDescription());
        mRegularPriceTextView.setText(woocommerceBody.getPrice() + " " + "تومان");
        mBudgetPriceTextView.setText(woocommerceBody.getRegularPrice() + " " + "تومان");
    }

    private void addProductToBag(final WoocommerceBody woocommerceBody) {
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProductDetailViewModel.addToBag(woocommerceBody.getId());
                Intent intent = ShopBagFragmentActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
    }
    private void PrepareRelatedProducts(List<Integer> integers) {
        if(mProductAdaptor!=null)
            mRelatedProductRecycler.setVisibility(View.GONE);
       mProductDetailViewModel.loadRelatedProducts(integers);

    }

    private void init(View view) {
        mSliderView=view.findViewById(R.id.detail_fragment_imageSlider);
        mProgressBar = view.findViewById(R.id.detal_fragment_progress_bar);
        mCardView = view.findViewById(R.id.detail_fragment_card_view);
        mTextView = view.findViewById(R.id.detal_fragment_text_view);
        mDiscriptionTextView = view.findViewById(R.id.detal_fragment_discription_text_view);
        mRelatedProductRecycler = view.findViewById(R.id.detail_fragment_related_recycler);
        mBudgetPriceTextView = view.findViewById(R.id.detail_fragment_budget_price_textView);
        mRegularPriceTextView = view.findViewById(R.id.detail_fragment_regular_price_textView);
        mBuyButton = view.findViewById(R.id.detail_fragment_buy_button);
    }

    private void setUpAdaptor(List<WoocommerceBody> woocommerceBodies) {
        mProductAdaptor = new ProductAdaptor(woocommerceBodies);
        mRelatedProductRecycler.setAdapter(mProductAdaptor);
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
            mBudgetPriceTextView = itemView.findViewById(R.id.budget_price);
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
            mRegularPriceTextView.setText(woocommerceBody.getPrice() + " " + "تومان");
            mBudgetPriceTextView.setText(woocommerceBody.getRegularPrice() + " " + "تومان");
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_newest_products, parent, false);
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
