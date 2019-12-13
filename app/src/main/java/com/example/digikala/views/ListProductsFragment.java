package com.example.digikala.views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;
import com.example.digikala.viewmodels.ListProductsViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProductsFragment extends Fragment {
    private ListProductsViewModel mListProductViewModel;
    private static final String STATE = "state";
    public static final String CONSUMER_KEY = "%20ck_7c028a04c9faf616410b09e2ab90b1884c875d01";
    public static final String CONSUMER_SECRET = "cs_8c39f626780f01d135719f64214fd092b848f4aa";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    private List<WoocommerceBody> mNewProductList;
    private RecyclerView mListProductsRecycler;
    private TextView mTextView;
    private TextView mSortTextView;
    private ProgressBar mProgressBar;
    private TextView mSubSortTextView;
    private TextView mFilterTextView;
    private WooCommerce mWooCommerce = new WooCommerce();
    private int state;
    public static final int REQUEST_CODE_FOR_SORT_DIALOG_FRAGMENT = 0;
    public static final String SORT_DIALOG_FRAGMENT_TAG = "sortdialogfragmenttag";
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
//            Intent intent = MainActivity.newIntent(getActivity(), 0);
//            Log.d("tag", "checkNetwork" + "0");
//            startActivity(intent);
            getActivity().finish();
            Log.d("tag", "finished");
        }
        mListProductViewModel = ViewModelProviders.of(this).get(ListProductsViewModel.class);
        state = getArguments().getInt(STATE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_products, container, false);
        init(view);
        if (state == 1) {
            mSubSortTextView.setText(R.string.check_box_rated);
            SharedPreferencesData.setRadioGroupId(getActivity(), 2);
        } else if (state == 2) {
            mSubSortTextView.setText(R.string.check_box_selles);
            SharedPreferencesData.setRadioGroupId(getActivity(), 3);
        } else if (state == 3) {
            mSubSortTextView.setText(R.string.check_box_newest);
            SharedPreferencesData.setRadioGroupId(getActivity(), 1);
        } else {
            mSubSortTextView.setText(R.string.check_box_selles);
            SharedPreferencesData.setRadioGroupId(getActivity(), 0);
        }
        mSortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialogFragment sortDialogFragment = SortDialogFragment.newInstance(SharedPreferencesData.getRadioGroupId(getContext()));
                sortDialogFragment.setTargetFragment(ListProductsFragment.this, REQUEST_CODE_FOR_SORT_DIALOG_FRAGMENT);
                sortDialogFragment.show(getFragmentManager(), SORT_DIALOG_FRAGMENT_TAG);
            }
        });
        Log.d("tag", "onCreateViewL");
        initAdaptor(state);
        mListProductViewModel.getSearchedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null)
                    initAdaptor(4);
                else
                    mTextView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void initAdaptor(int state) {
        ProductAdaptor productAdaptor = new ProductAdaptor(state);
        mListProductsRecycler.setAdapter(productAdaptor);
    }

    private void initAdaptor(List<WoocommerceBody> woocommerceBodies) {
        ProductAdaptor productAdaptor = new ProductAdaptor(woocommerceBodies);
        mListProductsRecycler.setAdapter(productAdaptor);
    }

    private void init(View view) {
        mProgressBar = view.findViewById(R.id.list_products_fragment_progress_bar);
        mListProductsRecycler = view.findViewById(R.id.list_products_fragment_recycler);
        mTextView = view.findViewById(R.id.list_products_fragment_text_view);
        mSortTextView = view.findViewById(R.id.list_product_fragment_sort_text_view);
        mSubSortTextView = view.findViewById(R.id.list_product_fragment_sub_sort_text_view);
    }

    private void setSortTextView() {
        switch (SharedPreferencesData.getRadioGroupId(getActivity())) {
            case 1:
                mSubSortTextView.setText(R.string.check_box_newest);
                break;
            case 2:
                mSubSortTextView.setText(R.string.check_box_rated);
                break;
            case 3:
                mSubSortTextView.setText(R.string.check_box_selles);
                break;
            case 4:
                mSubSortTextView.setText(R.string.check_box_cost_high_low);
                break;
            case 5:
                mSubSortTextView.setText(R.string.check_box_cost_low_high);
                break;
            default: {
                Log.d("default", String.valueOf(SharedPreferencesData.getRadioGroupId(getActivity())));
                if (state == 1) {
                    mSubSortTextView.setText(R.string.check_box_rated);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 2);
                } else if (state == 2) {
                    mSubSortTextView.setText(R.string.check_box_selles);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 3);
                } else if (state == 3) {
                    mSubSortTextView.setText(R.string.check_box_newest);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 1);
                } else {
                    mSubSortTextView.setText(R.string.check_box_selles);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 0);
                }

            }
        }
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
                    if (!isNetworkConnected()) {
                        getActivity().finish();
                    } else {
                        Intent intent = ProductDetailActivity.newIntent(getActivity(), mWoocommerceBody.getId(), mWoocommerceBody.getName());
                        startActivity(intent);
                    }
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
                    mWoocommerceBodies = mListProductViewModel.getPopularProducts().getValue();
                    break;
                case 2:
                    mWoocommerceBodies = mListProductViewModel.getRatedProducts().getValue();
                    break;
                case 3:
                    mWoocommerceBodies = mListProductViewModel.getNewestProducts().getValue();
                    break;
                default:
                    mWoocommerceBodies = mListProductViewModel.getSearchedProducts().getValue();
            }
        }

        public ProductAdaptor(List<WoocommerceBody> woocommerceBodies) {
            mWoocommerceBodies = woocommerceBodies;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_FOR_SORT_DIALOG_FRAGMENT) {
            mListProductsRecycler.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            setSortTextView();
            requestForNewSoert();
        }

    }

    private void requestForNewSoert() {
        switch (SharedPreferencesData.getRadioGroupId(getActivity())) {
            case 1:
                mQueries.put("orderby", "date");
                mQueries.put("order", "desc");
                break;
            case 2:
                mQueries.put("orderby", "popularity");
                mQueries.put("order", "desc");
                break;
            case 3:
                mQueries.put("orderby", "rating");
                mQueries.put("order", "desc");
                break;
            case 4:
                mQueries.put("orderby", "price");
                mQueries.put("order", "desc");
                break;
            case 5:
                mQueries.put("orderby", "price");
                mQueries.put("order", "asc");
                break;
        }
        try {
            mWooCommerce.getSortedBaseProducts(mQueries);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        mListProductViewModel.getSortedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (woocommerceBodies != null) {
                    initAdaptor(woocommerceBodies);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
