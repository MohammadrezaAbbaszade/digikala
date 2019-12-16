package com.example.digikala.model.ordersModels;


import com.google.gson.annotations.SerializedName;


public class CollectionItem{

	@SerializedName("href")
	private String href;

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	@Override
 	public String toString(){
		return 
			"CollectionItem{" + 
			"href = '" + href + '\'' + 
			"}";
		}
}