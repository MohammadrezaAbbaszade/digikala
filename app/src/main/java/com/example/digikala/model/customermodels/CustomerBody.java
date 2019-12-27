package com.example.digikala.model.customermodels;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CustomerBody {
    private Throwable error;
    private int code;
    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;

    @SerializedName("role")
    private String role;

    @SerializedName("_links")
    private Links links;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("billing")
    private Billing billing;

    @SerializedName("date_modified")
    private String dateModified;

    @SerializedName("shipping")
    private Shipping shipping;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("meta_data")
    private List<Object> metaData;

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("is_paying_customer")
    private boolean isPayingCustomer;

    @SerializedName("username")
    private String username;

    public CustomerBody(Throwable error) {
        this.error = error;
    }

    public CustomerBody(int code) {
        this.code = code;
    }
    public CustomerBody() {

    }
    public int getCode() {
        return code;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Links getLinks() {
        return links;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setMetaData(List<Object> metaData) {
        this.metaData = metaData;
    }

    public List<Object> getMetaData() {
        return metaData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIsPayingCustomer(boolean isPayingCustomer) {
        this.isPayingCustomer = isPayingCustomer;
    }

    public boolean isIsPayingCustomer() {
        return isPayingCustomer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return
                "CustomerBody{" +
                        "date_modified_gmt = '" + dateModifiedGmt + '\'' +
                        ",role = '" + role + '\'' +
                        ",_links = '" + links + '\'' +
                        ",date_created = '" + dateCreated + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",date_created_gmt = '" + dateCreatedGmt + '\'' +
                        ",billing = '" + billing + '\'' +
                        ",date_modified = '" + dateModified + '\'' +
                        ",shipping = '" + shipping + '\'' +
                        ",avatar_url = '" + avatarUrl + '\'' +
                        ",meta_data = '" + metaData + '\'' +
                        ",id = '" + id + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        ",is_paying_customer = '" + isPayingCustomer + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }


}