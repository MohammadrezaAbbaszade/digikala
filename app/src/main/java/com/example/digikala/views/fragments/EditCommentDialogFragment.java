package com.example.digikala.views.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.reviewsmodels.ReviewBody;
import com.example.digikala.viewmodels.CommentsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditCommentDialogFragment extends DialogFragment {

    private static final String COMMENT = "comment";
    private static final String COMMENT_ID = "commentId";
    private final String COMMENT_OBJECT = "commentObject";
    private TextView mNameReviewer;
    private TextView mEmailReviewer;
    private SeekBar mRateReviewSeekBar;
    private TextInputEditText mReviewEdt;
    private MaterialButton mRegisterComment;
    private CommentsViewModel mCommentsViewModel;
    private ProgressBar mLoadingProgressBar;
    private int productId;
    private int commentId;
    private String comment;
    private static final String PRODUCT_ID = "productId";

    public static EditCommentDialogFragment newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        EditCommentDialogFragment fragment = new EditCommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static EditCommentDialogFragment newInstance(String comment, int productId,int commentId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        args.putInt(COMMENT_ID, commentId);
        args.putString(COMMENT, comment);
        EditCommentDialogFragment fragment = new EditCommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditCommentDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        productId = getArguments().getInt(PRODUCT_ID);
        Log.d("dcomm",String.valueOf(productId));
        comment = getArguments().getString(COMMENT);
        commentId=getArguments().getInt(COMMENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_comment, container, false);
        init(view);

        setDataToView(view);
        mRegisterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                mRegisterComment.setVisibility(View.GONE);
                int reviewSeekbarResult = mRateReviewSeekBar.getProgress();
                String reviewEditText = mReviewEdt.getText().toString();
                if (reviewEditText.isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا فیلد نظر را پر کنید!", Toast.LENGTH_SHORT).show();
                    mLoadingProgressBar.setVisibility(View.GONE);
                    mRegisterComment.setVisibility(View.VISIBLE);
                } else {
                    createNewCommentObject();
                }
            }
        });

        return view;
    }

    private void init(View view) {
        mNameReviewer = view.findViewById(R.id.name_reviewer);
        mEmailReviewer = view.findViewById(R.id.email_reviewer);
        mRateReviewSeekBar = view.findViewById(R.id.rate_review);
        mReviewEdt = view.findViewById(R.id.review_edt);
        mRegisterComment = view.findViewById(R.id.register_review);
        mLoadingProgressBar = view.findViewById(R.id.edit_comment_progress_bar);
    }

    private void setDataToView(View view) {
        mNameReviewer.setText(SharedPreferencesData.getCustomerName(getContext()));
        mEmailReviewer.setText(SharedPreferencesData.getCustomerEmail(getActivity()));
        if (comment != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mReviewEdt.setText(Html.fromHtml(comment, Html.FROM_HTML_MODE_COMPACT));
            } else {
                mReviewEdt.setText(Html.fromHtml(comment));
            }
        }

    }

    private void sendRresult() {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    private void createNewCommentObject() {
        ReviewBody reviewBody = new ReviewBody();
        if(comment!=null)
        {
            reviewBody.setId(commentId);
        }
        reviewBody.setReview(mReviewEdt.getText().toString());
        reviewBody.setReviewer(mNameReviewer.getText().toString());
        reviewBody.setRating(mRateReviewSeekBar.getProgress());
        reviewBody.setReviewerEmail(mEmailReviewer.getText().toString());
        reviewBody.setProductId(productId);
        reviewBody.setStatus("approved");
        reviewBody.setVerified(true);
        if (comment==null) {
            mCommentsViewModel.sendCustomerComment(reviewBody).observe(this, new Observer<ReviewBody>() {
                @Override
                public void onChanged(ReviewBody reviewBody) {
                    if (reviewBody.getId() != 0) {
                        mLoadingProgressBar.setVisibility(View.GONE);
                        mRegisterComment.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.publish_comment), Toast.LENGTH_LONG).show();
                        dismiss();
                        sendRresult();
                    }
                }
            });
        } else {
            mCommentsViewModel.updateComment(reviewBody).observe(this, new Observer<ReviewBody>() {
                @Override
                public void onChanged(ReviewBody reviewBody) {
                    if (reviewBody.getId() == 0) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.cant_update_comment), Toast.LENGTH_LONG).show();
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.update_comment), Toast.LENGTH_LONG).show();
                        dismiss();
                        sendRresult();
                    }
                }
            });

        }

    }
}
