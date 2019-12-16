package com.example.digikala.model.productsModels;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShoppingBag {
    @Id(autoincrement = true)
    private Long mId;
    private String mProductId;
    private String mName;
    @Generated(hash = 537859356)
    public ShoppingBag(Long mId, String mProductId, String mName) {
        this.mId = mId;
        this.mProductId = mProductId;
        this.mName = mName;
    }
    @Generated(hash = 1113443636)
    public ShoppingBag() {
    }
    public Long getMId() {
        return this.mId;
    }
    public void setMId(Long mId) {
        this.mId = mId;
    }
    public String getMProductId() {
        return this.mProductId;
    }
    public void setMProductId(String mProductId) {
        this.mProductId = mProductId;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }

    
}
