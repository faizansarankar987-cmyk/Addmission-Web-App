package com.isees.dtos;



	public class ReceiptResponse {

	    private String receiptNo;
	    private String studentName;
	    private String email;
	    private String course;
	    private double paidAmount;

	    // getters & setters
	    public String getReceiptNo() { return receiptNo; }
	    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo; }

	    public String getStudentName() { return studentName; }
	    public void setStudentName(String studentName) { this.studentName = studentName; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getCourse() { return course; }
	    public void setCourse(String course) { this.course = course; }

	    public double getPaidAmount() { return paidAmount; }
	    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
	}


