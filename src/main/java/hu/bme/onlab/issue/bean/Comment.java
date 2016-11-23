package hu.bme.onlab.issue.bean;

import java.util.Calendar;

public class Comment {
	private String message;
	private Calendar timeStamp;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Calendar getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}
}
