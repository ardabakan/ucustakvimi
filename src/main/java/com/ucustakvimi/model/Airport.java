package com.ucustakvimi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "airports")
public class Airport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4588043488994675835L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name="CODE")
	public String code = "";
	
	@Column(name="NAME")
	public String name = "";

    @Column(name="CITY_CODE")
	public String cityCode = "";

    
    @Column(name="IATA_CODE")
	public String iataCode = "";
	
	
	@Column(name="CALENDAR_FROM_LIST")
	public Integer calendarFromList;
	
	
	@Column(name="CALENDAR_TO_LIST")
	public Integer calendarToList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public Integer getCalendarFromList() {
		return calendarFromList;
	}

	public void setCalendarFromList(Integer calendarFromList) {
		this.calendarFromList = calendarFromList;
	}

	public Integer getCalendarToList() {
		return calendarToList;
	}

	public void setCalendarToList(Integer calendarToList) {
		this.calendarToList = calendarToList;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	
	@Column(name="ENABLED")
	public Integer enabled;
	
}
