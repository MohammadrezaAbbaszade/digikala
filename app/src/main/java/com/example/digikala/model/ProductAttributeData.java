package com.example.digikala.model;

import java.util.List;

public class ProductAttributeData {
    private String search;
    private String order;
    private String orderby;
    private int page;
    private String  attribute;
    private List<Integer> attributeTerm;

    public ProductAttributeData(String attribute, List<Integer> attributeTerm) {
        this.attribute = attribute;
        this.attributeTerm = attributeTerm;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<Integer> getAttributeTerm() {
        return attributeTerm;
    }

    public void setAttributeTerm(List<Integer> attributeTerm) {
        this.attributeTerm = attributeTerm;
    }
}
