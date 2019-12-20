package com.example.digikala.model.attributesmodels;


import com.example.digikala.model.attributetermsmodels.AttributeTermsBody;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AttributeBody{
private List<AttributeTermsBody> mAttributeTermsBodies;

	public List<AttributeTermsBody> getAttributeTermsBodies() {
		return mAttributeTermsBodies;
	}

	public void setAttributeTermsBodies(List<AttributeTermsBody> attributeTermsBodies) {
		mAttributeTermsBodies = attributeTermsBodies;
	}

	@SerializedName("_links")
	private Links links;

	@SerializedName("name")
	private String name;

	@SerializedName("order_by")
	private String orderBy;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("slug")
	private String slug;

	@SerializedName("has_archives")
	private boolean hasArchives;

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOrderBy(String orderBy){
		this.orderBy = orderBy;
	}

	public String getOrderBy(){
		return orderBy;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setHasArchives(boolean hasArchives){
		this.hasArchives = hasArchives;
	}

	public boolean isHasArchives(){
		return hasArchives;
	}

	@Override
 	public String toString(){
		return 
			"AttributeBody{" + 
			"_links = '" + links + '\'' + 
			",name = '" + name + '\'' + 
			",order_by = '" + orderBy + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",slug = '" + slug + '\'' + 
			",has_archives = '" + hasArchives + '\'' + 
			"}";
		}
}