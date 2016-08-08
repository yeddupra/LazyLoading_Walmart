package com.test.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by pyeddula on 8/6/16.
 */
public class ProductItemsList {

    @Expose
    private int totalProducts=0;

    /**
     *
     * @return
     *     The totalProducts
     */
    public int getTotalProducts() {
        return totalProducts;
    }

    /**
     *
     * @param totalProducts
     *     The totalProducts
     */
    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    @Expose
    private int pageNumber;

    /**
     *
     * @return
     *     The pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     *
     * @param pageNumber
     *     The pageNumber
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Expose
    private int pageSize;

    /**
     *
     * @return
     *     The pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     *
     * @param pageSize
     *     The pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Expose
    private int status;

    /**
     *
     * @return
     *     The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(int status) {
        this.status = status;
    }




    @Expose
    private String kind;

    /**
     *
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.etag = kind;
    }



    @Expose
    private String etag;

    /**
     *
     * @return
     *     The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     *     The etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }


    @Expose
    ArrayList<ProductItem> products = new ArrayList<ProductItem> ();

    /**
     *
     * @return
     *     The products
     */
    public ArrayList<ProductItem> getProducts() {
        return products;
    }

    /**
     *
     * @param products
     *     The products
     */
    public void setProducts(ArrayList<ProductItem> products ) {
        this.products = products;
    }
}
