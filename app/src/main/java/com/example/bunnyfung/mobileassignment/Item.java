package com.example.bunnyfung.mobileassignment;

/**
 * Created by bunnyfung on 4/7/15.
 */
public class Item {
    private int jobNo;
    private String requestDate;
    private String jobStatus;
    private String jobProblem;

    public Item(){
        this.jobNo = 0;
        this.requestDate = "";
        this.jobStatus = "";
        this.jobProblem = "";
    }

    public Item(int jobNo, String requestDate, String jobStatus, String jobProblem){
        this.jobNo = jobNo;
        this.requestDate = requestDate;
        this.jobStatus = jobStatus;
        this.jobProblem = jobProblem;
    }

    public int getJobNo(){return jobNo;}
    public String getRequestDate(){return requestDate;}
    public String getJobStatus(){return jobStatus;}
    public String getJobProblem(){return jobProblem;}
}
