package com.example.digikala.views.fragments;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikala.R;
import com.example.digikala.model.ProductAttributeData;
import com.example.digikala.model.attributesmodels.AttributeBody;
import com.example.digikala.model.attributetermsmodels.AttributeTermsBody;
import com.example.digikala.viewmodels.FilterViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {
    private int index;
    private AttributeAdaptor mAttributeAdaptor;
    private AttributTermseAdaptor mAttributTermseAdaptor;
    private RecyclerView mAttributeRecycler;
    private RecyclerView mAttributeTermsRecycler;
    private FilterViewModel mFilterViewModel;
    private List<AttributeBody> mAttributeBodies;
    private List<AttributeTermsBody> mColorsAttributeTermsBodies;
    private List<AttributeTermsBody> mSizessAttributeTermsBodies;
    private Button mFilterButton;
    private ProductAttributeData mProductAttributeData;
    private List<Integer> mAttributesTermsIds = new ArrayList<>();

    public static FilterFragment newInstance() {

        Bundle args = new Bundle();

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
        mFilterViewModel.loadAttributes();
        mFilterViewModel.loadColorsAttributeTerms(3);
        mFilterViewModel.loadSizessAttributeTerms(4);
    }

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filte, container, false);
        init(view);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("event", "eventSended");
                if (index == 0) {
                    EventBus.getDefault().postSticky(new ProductAttributeData(mAttributeBodies.get(0).getSlug(), mAttributesTermsIds));
                    Log.e("event", mAttributeBodies.get(0).getSlug()+""+mAttributesTermsIds.toString());
                } else {
                    EventBus.getDefault().postSticky(new ProductAttributeData(mAttributeBodies.get(1).getSlug(), mAttributesTermsIds));
                }
                getActivity().finish();
            }
        });
        mAttributeTermsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAttributeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFilterViewModel.getAttributes().observe(this, new Observer<List<AttributeBody>>() {
            @Override
            public void onChanged(List<AttributeBody> attributeBodies) {
                mAttributeBodies = attributeBodies;
                Log.d("attribute", String.valueOf(attributeBodies.size()));
                initAttributeAdapter(attributeBodies);
            }
        });
        mFilterViewModel.getColorsAttributeTerms().observe(this, new Observer<List<AttributeTermsBody>>() {
            @Override
            public void onChanged(List<AttributeTermsBody> attributeTermsBodies) {
                mColorsAttributeTermsBodies = attributeTermsBodies;
                Log.d("attribute", String.valueOf(attributeTermsBodies.size()));
            }
        });
        mFilterViewModel.getSizessAttributeTerms().observe(this, new Observer<List<AttributeTermsBody>>() {
            @Override
            public void onChanged(List<AttributeTermsBody> attributeTermsBodies) {
                mSizessAttributeTermsBodies = attributeTermsBodies;
                Log.d("attribute", String.valueOf(attributeTermsBodies.size()));
            }
        });
        return view;
    }

    private void init(View view) {
        mAttributeRecycler = view.findViewById(R.id.fragment_filter_attribute_recycler_view);
        mAttributeTermsRecycler = view.findViewById(R.id.fragment_filter_attributeterms_recycler_view);
        mFilterButton = view.findViewById(R.id.fragment_filter_btn);
    }

    private void initAttributeAdapter(List<AttributeBody> attributeBodies) {
        mAttributeAdaptor = new AttributeAdaptor(attributeBodies);
        mAttributeRecycler.setAdapter(mAttributeAdaptor);
    }

    private void initAttributeTermsAdapter(List<AttributeTermsBody> attributeTermsBodies) {
        mAttributTermseAdaptor = new AttributTermseAdaptor(attributeTermsBodies);
        mAttributeTermsRecycler.setAdapter(mAttributTermseAdaptor);
    }

    private class AttributeHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private AttributeBody mAttributeBody;

        public AttributeHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.attribute_item_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = getIndex(mAttributeBody.getId());
                    mTextView.setBackgroundColor(getResources().getColor(R.color.white));
                    mTextView.setTextColor(getResources().getColor(R.color.black));
                    if (index == 0) {
                        initAttributeTermsAdapter(mColorsAttributeTermsBodies);
                    } else {
                        initAttributeTermsAdapter(mSizessAttributeTermsBodies);
                    }

                }
            });
        }

        void bind(AttributeBody attributeBody) {
            mAttributeBody = attributeBody;
            mTextView.setText(attributeBody.getName());
        }

    }

    private class AttributeAdaptor extends RecyclerView.Adapter<AttributeHolder> {
        private List<AttributeBody> mAttributeBodies;


        public AttributeAdaptor(List<AttributeBody> attributeBodies) {
            mAttributeBodies = attributeBodies;
        }

        @NonNull
        @Override
        public AttributeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_attributes_items, parent, false);
            AttributeHolder attributeHolder = new AttributeHolder(view);
            return attributeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AttributeHolder holder, int position) {
            holder.bind(mAttributeBodies.get(position));

        }

        @Override
        public int getItemCount() {
            return mAttributeBodies.size();
        }
    }


    private class AttributTermseHolder extends RecyclerView.ViewHolder {
        private AttributeTermsBody mAttributeTermsBody;
        private CheckBox mCheckBox;

        public AttributTermseHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.attribute_term_item_check_box);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mAttributesTermsIds.add(mAttributeTermsBody.getId());
                    } else {
                        if (mAttributesTermsIds.contains(mAttributeTermsBody.getId()))
                            mAttributesTermsIds.remove(mAttributeTermsBody.getId());
                    }

                }
            });
        }

        void bind(AttributeTermsBody attributeTermsBody) {
            mAttributeTermsBody = attributeTermsBody;
            mCheckBox.setText(attributeTermsBody.getName());
        }

    }

    private class AttributTermseAdaptor extends RecyclerView.Adapter<AttributTermseHolder> {
        private List<AttributeTermsBody> mAttributeTermsBodies;


        public AttributTermseAdaptor(List<AttributeTermsBody> attributeTermsBodies) {
            mAttributeTermsBodies = attributeTermsBodies;
        }

        @NonNull
        @Override
        public AttributTermseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_attributes_terms_items, parent, false);
            AttributTermseHolder attributTermseHolder = new AttributTermseHolder(view);
            return attributTermseHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AttributTermseHolder holder, int position) {
            holder.bind(mAttributeTermsBodies.get(position));

        }

        @Override
        public int getItemCount() {
            return mAttributeTermsBodies.size();
        }
    }

    private int getIndex(int id) {
        for (int i = 0; i < mAttributeBodies.size(); i++) {
            if (mAttributeBodies.get(i).getId() == id)
                return i;
        }
        return 0;
    }

}
