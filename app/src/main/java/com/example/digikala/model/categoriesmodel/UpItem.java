package com.example.digikala.model.categoriesmodel;


import com.google.gson.annotations.SerializedName;


public class UpItem{

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
			"UpItem{" + 
			"href = '" + href + '\'' + 
			"}";
		}
}