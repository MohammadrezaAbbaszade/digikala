package com.example.digikala.model.attributetermsmodels;


import com.google.gson.annotations.SerializedName;


public class AttributeTermsBody{

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("_links")
	private Links links;

	@SerializedName("name")
	private String name;

	@SerializedName("count")
	private int count;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	public void setMenuOrder(int menuOrder){
		this.menuOrder = menuOrder;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

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

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"AttributeTermsBody{" + 
			"menu_order = '" + menuOrder + '\'' + 
			",_links = '" + links + '\'' + 
			",name = '" + name + '\'' + 
			",count = '" + count + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}