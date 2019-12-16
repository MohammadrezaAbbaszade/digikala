package com.example.digikala.model.ordersModels;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class OrderBody{

	@SerializedName("discount_total")
	private String discountTotal;

	@SerializedName("order_key")
	private String orderKey;

	@SerializedName("prices_include_tax")
	private boolean pricesIncludeTax;

	@SerializedName("_links")
	private Links links;

	@SerializedName("customer_note")
	private String customerNote;

	@SerializedName("line_items")
	private List<Object> lineItems;

	@SerializedName("coupon_lines")
	private List<Object> couponLines;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("refunds")
	private List<Object> refunds;

	@SerializedName("number")
	private String number;

	@SerializedName("total")
	private String total;

	@SerializedName("shipping")
	private Shipping shipping;

	@SerializedName("date_paid_gmt")
	private Object datePaidGmt;

	@SerializedName("tax_lines")
	private List<Object> taxLines;

	@SerializedName("date_paid")
	private Object datePaid;

	@SerializedName("customer_user_agent")
	private String customerUserAgent;

	@SerializedName("payment_method_title")
	private String paymentMethodTitle;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("date_completed")
	private Object dateCompleted;

	@SerializedName("currency")
	private String currency;

	@SerializedName("id")
	private int id;

	@SerializedName("date_completed_gmt")
	private Object dateCompletedGmt;

	@SerializedName("payment_method")
	private String paymentMethod;

	@SerializedName("shipping_tax")
	private String shippingTax;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("cart_hash")
	private String cartHash;

	@SerializedName("shipping_total")
	private String shippingTotal;

	@SerializedName("cart_tax")
	private String cartTax;

	@SerializedName("created_via")
	private String createdVia;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("discount_tax")
	private String discountTax;

	@SerializedName("total_tax")
	private String totalTax;

	@SerializedName("version")
	private String version;

	@SerializedName("customer_ip_address")
	private String customerIpAddress;

	@SerializedName("shipping_lines")
	private List<Object> shippingLines;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("fee_lines")
	private List<Object> feeLines;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("status")
	private String status;

	public void setDiscountTotal(String discountTotal){
		this.discountTotal = discountTotal;
	}

	public String getDiscountTotal(){
		return discountTotal;
	}

	public void setOrderKey(String orderKey){
		this.orderKey = orderKey;
	}

	public String getOrderKey(){
		return orderKey;
	}

	public void setPricesIncludeTax(boolean pricesIncludeTax){
		this.pricesIncludeTax = pricesIncludeTax;
	}

	public boolean isPricesIncludeTax(){
		return pricesIncludeTax;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setCustomerNote(String customerNote){
		this.customerNote = customerNote;
	}

	public String getCustomerNote(){
		return customerNote;
	}

	public void setLineItems(List<Object> lineItems){
		this.lineItems = lineItems;
	}

	public List<Object> getLineItems(){
		return lineItems;
	}

	public void setCouponLines(List<Object> couponLines){
		this.couponLines = couponLines;
	}

	public List<Object> getCouponLines(){
		return couponLines;
	}

	public void setBilling(Billing billing){
		this.billing = billing;
	}

	public Billing getBilling(){
		return billing;
	}

	public void setRefunds(List<Object> refunds){
		this.refunds = refunds;
	}

	public List<Object> getRefunds(){
		return refunds;
	}

	public void setNumber(String number){
		this.number = number;
	}

	public String getNumber(){
		return number;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setShipping(Shipping shipping){
		this.shipping = shipping;
	}

	public Shipping getShipping(){
		return shipping;
	}

	public void setDatePaidGmt(Object datePaidGmt){
		this.datePaidGmt = datePaidGmt;
	}

	public Object getDatePaidGmt(){
		return datePaidGmt;
	}

	public void setTaxLines(List<Object> taxLines){
		this.taxLines = taxLines;
	}

	public List<Object> getTaxLines(){
		return taxLines;
	}

	public void setDatePaid(Object datePaid){
		this.datePaid = datePaid;
	}

	public Object getDatePaid(){
		return datePaid;
	}

	public void setCustomerUserAgent(String customerUserAgent){
		this.customerUserAgent = customerUserAgent;
	}

	public String getCustomerUserAgent(){
		return customerUserAgent;
	}

	public void setPaymentMethodTitle(String paymentMethodTitle){
		this.paymentMethodTitle = paymentMethodTitle;
	}

	public String getPaymentMethodTitle(){
		return paymentMethodTitle;
	}

	public void setMetaData(List<Object> metaData){
		this.metaData = metaData;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public void setDateCompleted(Object dateCompleted){
		this.dateCompleted = dateCompleted;
	}

	public Object getDateCompleted(){
		return dateCompleted;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDateCompletedGmt(Object dateCompletedGmt){
		this.dateCompletedGmt = dateCompletedGmt;
	}

	public Object getDateCompletedGmt(){
		return dateCompletedGmt;
	}

	public void setPaymentMethod(String paymentMethod){
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public void setShippingTax(String shippingTax){
		this.shippingTax = shippingTax;
	}

	public String getShippingTax(){
		return shippingTax;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setDateModifiedGmt(String dateModifiedGmt){
		this.dateModifiedGmt = dateModifiedGmt;
	}

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public void setCartHash(String cartHash){
		this.cartHash = cartHash;
	}

	public String getCartHash(){
		return cartHash;
	}

	public void setShippingTotal(String shippingTotal){
		this.shippingTotal = shippingTotal;
	}

	public String getShippingTotal(){
		return shippingTotal;
	}

	public void setCartTax(String cartTax){
		this.cartTax = cartTax;
	}

	public String getCartTax(){
		return cartTax;
	}

	public void setCreatedVia(String createdVia){
		this.createdVia = createdVia;
	}

	public String getCreatedVia(){
		return createdVia;
	}

	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public void setDateCreatedGmt(String dateCreatedGmt){
		this.dateCreatedGmt = dateCreatedGmt;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public void setDiscountTax(String discountTax){
		this.discountTax = discountTax;
	}

	public String getDiscountTax(){
		return discountTax;
	}

	public void setTotalTax(String totalTax){
		this.totalTax = totalTax;
	}

	public String getTotalTax(){
		return totalTax;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}

	public void setCustomerIpAddress(String customerIpAddress){
		this.customerIpAddress = customerIpAddress;
	}

	public String getCustomerIpAddress(){
		return customerIpAddress;
	}

	public void setShippingLines(List<Object> shippingLines){
		this.shippingLines = shippingLines;
	}

	public List<Object> getShippingLines(){
		return shippingLines;
	}

	public void setDateModified(String dateModified){
		this.dateModified = dateModified;
	}

	public String getDateModified(){
		return dateModified;
	}

	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	public int getParentId(){
		return parentId;
	}

	public void setFeeLines(List<Object> feeLines){
		this.feeLines = feeLines;
	}

	public List<Object> getFeeLines(){
		return feeLines;
	}

	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}

	public int getCustomerId(){
		return customerId;
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
			"OrderBody{" + 
			"discount_total = '" + discountTotal + '\'' + 
			",order_key = '" + orderKey + '\'' + 
			",prices_include_tax = '" + pricesIncludeTax + '\'' + 
			",_links = '" + links + '\'' + 
			",customer_note = '" + customerNote + '\'' + 
			",line_items = '" + lineItems + '\'' + 
			",coupon_lines = '" + couponLines + '\'' + 
			",billing = '" + billing + '\'' + 
			",refunds = '" + refunds + '\'' + 
			",number = '" + number + '\'' + 
			",total = '" + total + '\'' + 
			",shipping = '" + shipping + '\'' + 
			",date_paid_gmt = '" + datePaidGmt + '\'' + 
			",tax_lines = '" + taxLines + '\'' + 
			",date_paid = '" + datePaid + '\'' + 
			",customer_user_agent = '" + customerUserAgent + '\'' + 
			",payment_method_title = '" + paymentMethodTitle + '\'' + 
			",meta_data = '" + metaData + '\'' + 
			",date_completed = '" + dateCompleted + '\'' + 
			",currency = '" + currency + '\'' + 
			",id = '" + id + '\'' + 
			",date_completed_gmt = '" + dateCompletedGmt + '\'' + 
			",payment_method = '" + paymentMethod + '\'' + 
			",shipping_tax = '" + shippingTax + '\'' + 
			",transaction_id = '" + transactionId + '\'' + 
			",date_modified_gmt = '" + dateModifiedGmt + '\'' + 
			",cart_hash = '" + cartHash + '\'' + 
			",shipping_total = '" + shippingTotal + '\'' + 
			",cart_tax = '" + cartTax + '\'' + 
			",created_via = '" + createdVia + '\'' + 
			",date_created = '" + dateCreated + '\'' + 
			",date_created_gmt = '" + dateCreatedGmt + '\'' + 
			",discount_tax = '" + discountTax + '\'' + 
			",total_tax = '" + totalTax + '\'' + 
			",version = '" + version + '\'' + 
			",customer_ip_address = '" + customerIpAddress + '\'' + 
			",shipping_lines = '" + shippingLines + '\'' + 
			",date_modified = '" + dateModified + '\'' + 
			",parent_id = '" + parentId + '\'' + 
			",fee_lines = '" + feeLines + '\'' + 
			",customer_id = '" + customerId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}