package com.example.digikala.model.categoriesmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Links{

	@SerializedName("self")
	private List<SelfItem> self;

	@SerializedName("collection")
	private List<CollectionItem> collection;

	@SerializedName("up")
	private List<UpItem> up;

	public void setSelf(List<SelfItem> self){
		this.self = self;
	}

	public List<SelfItem> getSelf(){
		return self;
	}

	public void setCollection(List<CollectionItem> collection){
		this.collection = collection;
	}

	public List<CollectionItem> getCollection(){
		return collection;
	}

	public void setUp(List<UpItem> up){
		this.up = up;
	}

	public List<UpItem> getUp(){
		return up;
	}

	@Override
 	public String toString(){
		return 
			"Links{" + 
			"self = '" + self + '\'' + 
			",collection = '" + collection + '\'' + 
			",up = '" + up + '\'' + 
			"}";
		}
}