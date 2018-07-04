package com.ucustakvimi.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "flight_searches")
public class FlightSearch {

	@Id
	@GeneratedValue
	private Long id;

	private String fromCode;
	private String toCode;
	private String fromName;
	private String toName;
	private String date;
	private String ip;

	private Timestamp searchedOn;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private FlightUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getSearchedOn() {
		return searchedOn;
	}

	public void setSearchedOn(Timestamp searchedOn) {
		this.searchedOn = searchedOn;
	}

}
