package com.example.digikala.RecyclersViews;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.reviewsmodels.ReviewBody;
import com.example.digikala.views.fragments.CommentsFragment;
import com.example.digikala.views.fragments.EditCommentDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CommentsRcyclerView extends RecyclerView.Adapter {
    private Context mContext;
    private List<ReviewBody> mReviewBodies;

    public CommentsRcyclerView(Context context, List<ReviewBody> reviewBodies) {
        mContext = context;
        mReviewBodies = reviewBodies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comments_items, parent, false);
        CommentHolder commentHolder = new CommentHolder(view);
        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentHolder commentHolder = (CommentHolder) holder;
        commentHolder.bind(mReviewBodies.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviewBodies.size();
    }

    private class CommentHolder extends RecyclerView.ViewHolder {
        private TextView mNameReviewer;
        private TextView mDateReview;
        private TextView mReview;
        private TextView mRatingComment;
        private ImageView mEditComment;
        private ImageView mDeleteCommnet;
        private ReviewBody mReviewBody;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            mNameReviewer=itemView.findViewById(R.id.name_reviewer);
            mDateReview=itemView.findViewById(R.id.date_review);
            mReview=itemView.findViewById(R.id.review_txt);
            mRatingComment=itemView.findViewById(R.id.rating_comment);
            mEditComment=itemView.findViewById(R.id.edit_comment);
            mDeleteCommnet=itemView.findViewById(R.id.delete_comment);
            mEditComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new ReviewBody(mReviewBody.getReview(),mReviewBody.getId()));
                }
            });
        }

        void bind(ReviewBody reviewBody) {
            mReviewBody = reviewBody;
            mNameReviewer.setText(reviewBody.getReviewer());
            mDateReview.setText(reviewBody.getDateCreated());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mReview.setText(Html.fromHtml(reviewBody.getReview(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                mReview.setText(Html.fromHtml(reviewBody.getReview()));
            }
            String ratingCommentTxt = "امتیاز: " + reviewBody.getRating() + " از 5";
            mRatingComment.setText(ratingCommentTxt);
            if (SharedPreferencesData.checkCustomerLogedIn(mContext)){
                if (SharedPreferencesData.getCustomerEmail(mContext).equals(reviewBody.getReviewerEmail())){
                    mEditComment.setVisibility(View.VISIBLE);
                    mDeleteCommnet.setVisibility(View.VISIBLE);
                }else {
                    mEditComment.setVisibility(View.INVISIBLE);
                    mDeleteCommnet.setVisibility(View.INVISIBLE);
                }
            }



        }
    }
}
