package com.example.digikala.model.categoriesmodel;


import com.google.gson.annotations.SerializedName;


public class CategoriesBody{

	@SerializedName("parent")
	private int parent;

	@SerializedName("image")
	private Object image;

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("_links")
	private Links links;

	@SerializedName("display")
	private String display;

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

	public void setParent(int parent){
		this.parent = parent;
	}

	public int getParent(){
		return parent;
	}

	public void setImage(Object image){
		this.image = image;
	}

	public Object getImage(){
		return image;
	}

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

	public void setDisplay(String display){
		this.display = display;
	}

	public String getDisplay(){
		return display;
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
			"CategoriesBody{" + 
			"parent = '" + parent + '\'' + 
			",image = '" + image + '\'' + 
			",menu_order = '" + menuOrder + '\'' + 
			",_links = '" + links + '\'' + 
			",display = '" + display + '\'' + 
			",name = '" + name + '\'' + 
			",count = '" + count + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}