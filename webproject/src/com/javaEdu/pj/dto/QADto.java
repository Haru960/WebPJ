package com.javaEdu.pj.dto;

import java.sql.Timestamp;

public class QADto {
	int qId;
	String id;
	String qTitle;
	String qContent;
	Timestamp qDate;
	int aId;
	int qHit;
	
	public QADto() {
		
	}
	
	public QADto(int qId, String id, String qTitle, String qContent, Timestamp qDate, int aId, int qHit) {
		this.qId = qId;
		this.id = id;
		this.qTitle = qTitle;
		this.qContent = qContent;
		this.qDate = qDate;
		this.aId = aId;
		this.qHit = qHit;
	}
	
	public QADto(int qId, String id, String aTitle, String aContent, Timestamp aDate) {
		this.qId = qId;
		this.id = id;
		this.qTitle = aTitle;
		this.qContent = aContent;
		this.qDate = aDate;
	}
	
	public int getqId() {
		return qId;
	}
	public void setqId(int qId) {
		this.qId = qId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getqTitle() {
		return qTitle;
	}
	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}
	public String getqContent() {
		return qContent;
	}
	public void setqContent(String qContent) {
		this.qContent = qContent;
	}
	public Timestamp getqDate() {
		return qDate;
	}
	public void setqDate(Timestamp qDate) {
		this.qDate = qDate;
	}
	public int getaId() {
		return aId;
	}
	public void setaId(int aId) {
		this.aId = aId;
	}

	public int getqHit() {
		return qHit;
	}

	public void setqHit(int qHit) {
		this.qHit = qHit;
	}
	
}
