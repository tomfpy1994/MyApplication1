package com.example.bunnyfung.mobileassignment;

/**
 * Created by bunnyfung on 6/7/15.
 */
public class ComItem {
    private String comNo;
    private String comName;
    private String comTel;
    private String comAddr;

    public ComItem(){
        this.comNo = "";
        this.comName = "";
        this.comTel = "";
        this.comAddr = "";
    }

    public ComItem(String comNo, String comName, String comTel, String comAddr){
        this.comNo = comNo;
        this.comName = comName;
        this.comTel = comTel;
        this.comAddr = comAddr;
    }

    public String getComNo(){return comNo;}
    public String getComName(){return comName;}
    public String getComTel(){return comTel;}
    public String getComAddr(){return comAddr;}

}
