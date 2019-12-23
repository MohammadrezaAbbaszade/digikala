package com.example.digikala.model.productsModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class WoocommerceBody{

	@SerializedName("upsell_ids")
	private List<Object> upsellIds;

	@SerializedName("featured")
	private boolean featured;

	@SerializedName("purchasable")
	private boolean purchasable;

	@SerializedName("grouped_products")
	private List<Object> groupedProducts;

	@SerializedName("tax_status")
	private String taxStatus;

	@SerializedName("catalog_visibility")
	private String catalogVisibility;

	@SerializedName("type")
	private String type;

	@SerializedName("external_url")
	private String externalUrl;

	@SerializedName("price")
	private String price;



	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("slug")
	private String slug;

	@SerializedName("date_on_sale_from")
	private Object dateOnSaleFrom;

	@SerializedName("shipping_required")
	private boolean shippingRequired;

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("images")
	private List<ImagesItem> images;

	@SerializedName("stock_status")
	private String stockStatus;

	@SerializedName("price_html")
	private String priceHtml;

	@SerializedName("download_expiry")
	private int downloadExpiry;

	@SerializedName("backordered")
	private boolean backordered;

	@SerializedName("weight")
	private String weight;

	@SerializedName("rating_count")
	private int ratingCount;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("date_on_sale_to")
	private Object dateOnSaleTo;

	@SerializedName("sold_individually")
	private boolean soldIndividually;

	@SerializedName("backorders")
	private String backorders;

	@SerializedName("shipping_taxable")
	private boolean shippingTaxable;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("download_limit")
	private int downloadLimit;

	@SerializedName("name")
	private String name;

	@SerializedName("shipping_class")
	private String shippingClass;

	@SerializedName("button_text")
	private String buttonText;

	@SerializedName("permalink")
	private String permalink;

	@SerializedName("status")
	private String status;

	@SerializedName("cross_sell_ids")
	private List<Object> crossSellIds;

	@SerializedName("short_description")
	private String shortDescription;

	@SerializedName("virtual")
	private boolean virtual;

	@SerializedName("downloadable")
	private boolean downloadable;

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("description")
	private String description;

	@SerializedName("date_on_sale_to_gmt")
	private Object dateOnSaleToGmt;

	@SerializedName("date_on_sale_from_gmt")
	private Object dateOnSaleFromGmt;

	@SerializedName("regular_price")
	private String regularPrice;

	@SerializedName("backorders_allowed")
	private boolean backordersAllowed;

	@SerializedName("downloads")
	private List<Object> downloads;

	@SerializedName("reviews_allowed")
	private boolean reviewsAllowed;

	@SerializedName("variations")
	private List<Object> variations;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("total_sales")
	private int totalSales;

	@SerializedName("on_sale")
	private boolean onSale;

	@SerializedName("manage_stock")
	private boolean manageStock;

	@SerializedName("default_attributes")
	private List<Object> defaultAttributes;

	@SerializedName("purchase_note")
	private String purchaseNote;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("tax_class")
	private String taxClass;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("average_rating")
	private String averageRating;

	@SerializedName("stock_quantity")
	private Object stockQuantity;

	@SerializedName("sale_price")
	private String salePrice;

	@SerializedName("shipping_class_id")
	private int shippingClassId;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("related_ids")
	private List<Integer> relatedIds;

	@SerializedName("attributes")
	private List<Object> attributes;

	@SerializedName("dimensions")
	private Dimensions dimensions;

	public void setUpsellIds(List<Object> upsellIds){
		this.upsellIds = upsellIds;
	}

	public List<Object> getUpsellIds(){
		return upsellIds;
	}

	public void setFeatured(boolean featured){
		this.featured = featured;
	}

	public boolean isFeatured(){
		return featured;
	}

	public void setPurchasable(boolean purchasable){
		this.purchasable = purchasable;
	}

	public boolean isPurchasable(){
		return purchasable;
	}

	public void setGroupedProducts(List<Object> groupedProducts){
		this.groupedProducts = groupedProducts;
	}

	public List<Object> getGroupedProducts(){
		return groupedProducts;
	}

	public void setTaxStatus(String taxStatus){
		this.taxStatus = taxStatus;
	}

	public String getTaxStatus(){
		return taxStatus;
	}

	public void setCatalogVisibility(String catalogVisibility){
		this.catalogVisibility = catalogVisibility;
	}

	public String getCatalogVisibility(){
		return catalogVisibility;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setExternalUrl(String externalUrl){
		this.externalUrl = externalUrl;
	}

	public String getExternalUrl(){
		return externalUrl;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}



	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setDateOnSaleFrom(Object dateOnSaleFrom){
		this.dateOnSaleFrom = dateOnSaleFrom;
	}

	public Object getDateOnSaleFrom(){
		return dateOnSaleFrom;
	}

	public void setShippingRequired(boolean shippingRequired){
		this.shippingRequired = shippingRequired;
	}

	public boolean isShippingRequired(){
		return shippingRequired;
	}

	public void setDateModifiedGmt(String dateModifiedGmt){
		this.dateModifiedGmt = dateModifiedGmt;
	}

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public void setImages(List<ImagesItem> images){
		this.images = images;
	}

	public List<ImagesItem> getImages(){
		return images;
	}

	public void setStockStatus(String stockStatus){
		this.stockStatus = stockStatus;
	}

	public String getStockStatus(){
		return stockStatus;
	}

	public void setPriceHtml(String priceHtml){
		this.priceHtml = priceHtml;
	}

	public String getPriceHtml(){
		return priceHtml;
	}

	public void setDownloadExpiry(int downloadExpiry){
		this.downloadExpiry = downloadExpiry;
	}

	public int getDownloadExpiry(){
		return downloadExpiry;
	}

	public void setBackordered(boolean backordered){
		this.backordered = backordered;
	}

	public boolean isBackordered(){
		return backordered;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
	}

	public void setRatingCount(int ratingCount){
		this.ratingCount = ratingCount;
	}

	public int getRatingCount(){
		return ratingCount;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public void setDateOnSaleTo(Object dateOnSaleTo){
		this.dateOnSaleTo = dateOnSaleTo;
	}

	public Object getDateOnSaleTo(){
		return dateOnSaleTo;
	}

	public void setSoldIndividually(boolean soldIndividually){
		this.soldIndividually = soldIndividually;
	}

	public boolean isSoldIndividually(){
		return soldIndividually;
	}

	public void setBackorders(String backorders){
		this.backorders = backorders;
	}

	public String getBackorders(){
		return backorders;
	}

	public void setShippingTaxable(boolean shippingTaxable){
		this.shippingTaxable = shippingTaxable;
	}

	public boolean isShippingTaxable(){
		return shippingTaxable;
	}

	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	public int getParentId(){
		return parentId;
	}

	public void setDownloadLimit(int downloadLimit){
		this.downloadLimit = downloadLimit;
	}

	public int getDownloadLimit(){
		return downloadLimit;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setShippingClass(String shippingClass){
		this.shippingClass = shippingClass;
	}

	public String getShippingClass(){
		return shippingClass;
	}

	public void setButtonText(String buttonText){
		this.buttonText = buttonText;
	}

	public String getButtonText(){
		return buttonText;
	}

	public void setPermalink(String permalink){
		this.permalink = permalink;
	}

	public String getPermalink(){
		return permalink;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setCrossSellIds(List<Object> crossSellIds){
		this.crossSellIds = crossSellIds;
	}

	public List<Object> getCrossSellIds(){
		return crossSellIds;
	}

	public void setShortDescription(String shortDescription){
		this.shortDescription = shortDescription;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public void setVirtual(boolean virtual){
		this.virtual = virtual;
	}

	public boolean isVirtual(){
		return virtual;
	}

	public void setDownloadable(boolean downloadable){
		this.downloadable = downloadable;
	}

	public boolean isDownloadable(){
		return downloadable;
	}

	public void setMenuOrder(int menuOrder){
		this.menuOrder = menuOrder;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setDateOnSaleToGmt(Object dateOnSaleToGmt){
		this.dateOnSaleToGmt = dateOnSaleToGmt;
	}

	public Object getDateOnSaleToGmt(){
		return dateOnSaleToGmt;
	}

	public void setDateOnSaleFromGmt(Object dateOnSaleFromGmt){
		this.dateOnSaleFromGmt = dateOnSaleFromGmt;
	}

	public Object getDateOnSaleFromGmt(){
		return dateOnSaleFromGmt;
	}

	public void setRegularPrice(String regularPrice){
		this.regularPrice = regularPrice;
	}

	public String getRegularPrice(){
		return regularPrice;
	}

	public void setBackordersAllowed(boolean backordersAllowed){
		this.backordersAllowed = backordersAllowed;
	}

	public boolean isBackordersAllowed(){
		return backordersAllowed;
	}

	public void setDownloads(List<Object> downloads){
		this.downloads = downloads;
	}

	public List<Object> getDownloads(){
		return downloads;
	}

	public void setReviewsAllowed(boolean reviewsAllowed){
		this.reviewsAllowed = reviewsAllowed;
	}

	public boolean isReviewsAllowed(){
		return reviewsAllowed;
	}

	public void setVariations(List<Object> variations){
		this.variations = variations;
	}

	public List<Object> getVariations(){
		return variations;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public void setTotalSales(int totalSales){
		this.totalSales = totalSales;
	}

	public int getTotalSales(){
		return totalSales;
	}

	public void setOnSale(boolean onSale){
		this.onSale = onSale;
	}

	public boolean isOnSale(){
		return onSale;
	}

	public void setManageStock(boolean manageStock){
		this.manageStock = manageStock;
	}

	public boolean isManageStock(){
		return manageStock;
	}

	public void setDefaultAttributes(List<Object> defaultAttributes){
		this.defaultAttributes = defaultAttributes;
	}

	public List<Object> getDefaultAttributes(){
		return defaultAttributes;
	}

	public void setPurchaseNote(String purchaseNote){
		this.purchaseNote = purchaseNote;
	}

	public String getPurchaseNote(){
		return purchaseNote;
	}

	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public void setTaxClass(String taxClass){
		this.taxClass = taxClass;
	}

	public String getTaxClass(){
		return taxClass;
	}

	public void setDateCreatedGmt(String dateCreatedGmt){
		this.dateCreatedGmt = dateCreatedGmt;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public void setAverageRating(String averageRating){
		this.averageRating = averageRating;
	}

	public String getAverageRating(){
		return averageRating;
	}

	public void setStockQuantity(Object stockQuantity){
		this.stockQuantity = stockQuantity;
	}

	public Object getStockQuantity(){
		return stockQuantity;
	}

	public void setSalePrice(String salePrice){
		this.salePrice = salePrice;
	}

	public String getSalePrice(){
		return salePrice;
	}

	public void setShippingClassId(int shippingClassId){
		this.shippingClassId = shippingClassId;
	}

	public int getShippingClassId(){
		return shippingClassId;
	}

	public void setDateModified(String dateModified){
		this.dateModified = dateModified;
	}

	public String getDateModified(){
		return dateModified;
	}

	public void setRelatedIds(List<Integer> relatedIds){
		this.relatedIds = relatedIds;
	}

	public List<Integer> getRelatedIds(){
		return relatedIds;
	}

	public void setAttributes(List<Object> attributes){
		this.attributes = attributes;
	}

	public List<Object> getAttributes(){
		return attributes;
	}

	public void setDimensions(Dimensions dimensions){
		this.dimensions = dimensions;
	}

	public Dimensions getDimensions(){
		return dimensions;
	}

	@Override
 	public String toString(){
		return 
			"WoocommerceBody{" + 
			"upsell_ids = '" + upsellIds + '\'' + 
			",featured = '" + featured + '\'' + 
			",purchasable = '" + purchasable + '\'' + 
			",grouped_products = '" + groupedProducts + '\'' +
			",tax_status = '" + taxStatus + '\'' + 
			",catalog_visibility = '" + catalogVisibility + '\'' + 
			",type = '" + type + '\'' + 
			",external_url = '" + externalUrl + '\'' + 
			",price = '" + price + '\'' +
			",id = '" + id + '\'' + 
			",sku = '" + sku + '\'' + 
			",slug = '" + slug + '\'' + 
			",date_on_sale_from = '" + dateOnSaleFrom + '\'' + 
			",shipping_required = '" + shippingRequired + '\'' + 
			",date_modified_gmt = '" + dateModifiedGmt + '\'' + 
			",images = '" + images + '\'' + 
			",stock_status = '" + stockStatus + '\'' + 
			",price_html = '" + priceHtml + '\'' + 
			",download_expiry = '" + downloadExpiry + '\'' + 
			",backordered = '" + backordered + '\'' + 
			",weight = '" + weight + '\'' + 
			",rating_count = '" + ratingCount + '\'' + 
			",tags = '" + tags + '\'' + 
			",date_on_sale_to = '" + dateOnSaleTo + '\'' + 
			",sold_individually = '" + soldIndividually + '\'' + 
			",backorders = '" + backorders + '\'' + 
			",shipping_taxable = '" + shippingTaxable + '\'' + 
			",parent_id = '" + parentId + '\'' + 
			",download_limit = '" + downloadLimit + '\'' + 
			",name = '" + name + '\'' + 
			",shipping_class = '" + shippingClass + '\'' + 
			",button_text = '" + buttonText + '\'' + 
			",permalink = '" + permalink + '\'' + 
			",status = '" + status + '\'' + 
			",cross_sell_ids = '" + crossSellIds + '\'' + 
			",short_description = '" + shortDescription + '\'' + 
			",virtual = '" + virtual + '\'' + 
			",downloadable = '" + downloadable + '\'' + 
			",menu_order = '" + menuOrder + '\'' + 
			",description = '" + description + '\'' + 
			",date_on_sale_to_gmt = '" + dateOnSaleToGmt + '\'' + 
			",date_on_sale_from_gmt = '" + dateOnSaleFromGmt + '\'' + 
			",regular_price = '" + regularPrice + '\'' + 
			",backorders_allowed = '" + backordersAllowed + '\'' + 
			",downloads = '" + downloads + '\'' + 
			",reviews_allowed = '" + reviewsAllowed + '\'' + 
			",variations = '" + variations + '\'' + 
			",categories = '" + categories + '\'' + 
			",total_sales = '" + totalSales + '\'' + 
			",on_sale = '" + onSale + '\'' + 
			",manage_stock = '" + manageStock + '\'' + 
			",default_attributes = '" + defaultAttributes + '\'' + 
			",purchase_note = '" + purchaseNote + '\'' + 
			",date_created = '" + dateCreated + '\'' + 
			",tax_class = '" + taxClass + '\'' + 
			",date_created_gmt = '" + dateCreatedGmt + '\'' + 
			",average_rating = '" + averageRating + '\'' + 
			",stock_quantity = '" + stockQuantity + '\'' + 
			",sale_price = '" + salePrice + '\'' + 
			",shipping_class_id = '" + shippingClassId + '\'' + 
			",date_modified = '" + dateModified + '\'' + 
			",related_ids = '" + relatedIds + '\'' + 
			",attributes = '" + attributes + '\'' + 
			",dimensions = '" + dimensions + '\'' + 
			"}";
		}
}