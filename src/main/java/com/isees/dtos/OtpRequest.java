package com.isees.dtos;



import lombok.Data;

@Data

public class OtpRequest {
private String to;
public String getTo() {
	return to;
}
public void setTo(String to) {
	this.to = to;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
private String subject;
@Override
public String toString() {
	return "OtpRequest [to=" + to + ", subject=" + subject + "]";
}
public OtpRequest() {
	super();
	// TODO Auto-generated constructor stub
}



}
