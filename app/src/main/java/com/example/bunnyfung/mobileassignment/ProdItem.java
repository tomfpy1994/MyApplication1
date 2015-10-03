package com.example.bunnyfung.mobileassignment;

/**
 * Created by bunnyfung on 6/7/15.
 */
public class ProdItem {
    private String prodNo;
    private String prodName;

    public ProdItem(){
        this.prodNo = "";
        this.prodName = "";
    }

    public ProdItem(String prodNo, String prodName){
        this.prodNo = prodNo;
        this.prodName = prodName;
    }

    public String getProdNo(){return prodNo;}
    public String getProdName(){return prodName;}

}
