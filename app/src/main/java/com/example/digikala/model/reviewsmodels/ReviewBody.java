package com.example.digikala.model.reviewsmodels;


import com.google.gson.annotations.SerializedName;


public class ReviewBody{

	@SerializedName("reviewer_avatar_urls")
	private ReviewerAvatarUrls reviewerAvatarUrls;

	@SerializedName("_links")
	private Links links;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("review")
	private String review;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("rating")
	private int rating;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("id")
	private int id;

	@SerializedName("reviewer")
	private String reviewer;

	@SerializedName("reviewer_email")
	private String reviewerEmail;

	@SerializedName("status")
	private String status;

	public void setReviewerAvatarUrls(ReviewerAvatarUrls reviewerAvatarUrls){
		this.reviewerAvatarUrls = reviewerAvatarUrls;
	}

	public ReviewerAvatarUrls getReviewerAvatarUrls(){
		return reviewerAvatarUrls;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public void setReview(String review){
		this.review = review;
	}

	public String getReview(){
		return review;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setRating(int rating){
		this.rating = rating;
	}

	public int getRating(){
		return rating;
	}

	public void setVerified(boolean verified){
		this.verified = verified;
	}

	public boolean isVerified(){
		return verified;
	}

	public void setDateCreatedGmt(String dateCreatedGmt){
		this.dateCreatedGmt = dateCreatedGmt;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setReviewer(String reviewer){
		this.reviewer = reviewer;
	}

	public String getReviewer(){
		return reviewer;
	}

	public void setReviewerEmail(String reviewerEmail){
		this.reviewerEmail = reviewerEmail;
	}

	public String getReviewerEmail(){
		return reviewerEmail;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ReviewBody{" + 
			"reviewer_avatar_urls = '" + reviewerAvatarUrls + '\'' + 
			",_links = '" + links + '\'' + 
			",date_created = '" + dateCreated + '\'' + 
			",review = '" + review + '\'' + 
			",product_id = '" + productId + '\'' + 
			",rating = '" + rating + '\'' + 
			",verified = '" + verified + '\'' + 
			",date_created_gmt = '" + dateCreatedGmt + '\'' + 
			",id = '" + id + '\'' + 
			",reviewer = '" + reviewer + '\'' + 
			",reviewer_email = '" + reviewerEmail + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}