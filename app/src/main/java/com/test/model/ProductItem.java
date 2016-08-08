package com.test.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by pyeddula on 8/6/16.
 */
public class ProductItem implements Serializable{

    @Expose
    private String productId;

    /**
     *
     * @return
     *     The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     *     The productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }


    @Expose
    private String productName;

    /**
     *
     * @return
     *     The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     *     The productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }


    @Expose
    private String shortDescription;

    /**
     *
     * @return
     *     The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     *
     * @param shortDescription
     *     The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    @Expose
    private String longDescription;

    /**
     *
     * @return
     *     The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     *
     * @param longDescription
     *     The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }


    @Expose
    private String price;

    /**
     *
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }


    @Expose
    private String productImage;

    /**
     *
     * @return
     *     The productImage
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     *
     * @param productImage
     *     The productImage
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }


    @Expose
    private float reviewRating;

    /**
     *
     * @return
     *     The reviewRating
     */
    public float getReviewRating() {
        return  reviewRating;
    }

    /**
     *
     * @param reviewRating
     *     The reviewRating
     */
    public void setReviewRating(float reviewRating) {
        this.reviewRating = reviewRating;
    }


    @Expose
    private int reviewCount;

    /**
     *
     * @return
     *     The reviewCount
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     *
     * @param reviewCount
     *     The reviewCount
     */
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }


    @Expose
    private boolean inStock;

    /**
     *
     * @return
     *     The inStock
     */
    public boolean getInStock() {
        return inStock;
    }

    /**
     *
     * @param inStock
     *     The inStock
     */
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

}
