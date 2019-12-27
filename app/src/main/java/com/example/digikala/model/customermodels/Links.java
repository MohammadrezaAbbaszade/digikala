package com.example.digikala.model.customermodels;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Links{


	@SerializedName("collection")
	private List<CollectionItem> collection;


	public void setCollection(List<CollectionItem> collection){
		this.collection = collection;
	}

	public List<CollectionItem> getCollection(){
		return collection;
	}

	@Override
 	public String toString(){
		return 
			"Links{" +

			",collection = '" + collection + '\'' + 
			"}";
		}
}