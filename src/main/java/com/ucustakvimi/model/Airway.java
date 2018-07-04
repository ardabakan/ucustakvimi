package com.ucustakvimi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ucustakvimi.repository.AirwaysRepository;

@Entity
@Table(name = "airways")
public class Airway implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(Airway.class);

	@Autowired
	private static AirwaysRepository airwaysRepository;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3235287813123087248L;

	public static final char SEARCH_STATUS_AVAILABLE = '1';

	public static final char SEARCH_STATUS_NOT_AVAILABLE = '0';

	public static HashMap<String, String> onurAirCitiesConversionMap = new HashMap<String, String>();

	public static HashMap<String, String> sunexpressCitiesConversionMap = new HashMap<String, String>();

	public static HashMap<String, String> thyCitiesConversionMap = new HashMap<String, String>();

	public static HashMap<String, Airway> airwaysCompaniesHash = new HashMap<String, Airway>();

	public static List<Airway> getAirwaysCompaniesList() {
		return airwaysCompaniesList;
	}

	public static ArrayList<Airway> getAirwaysCompaniesSearchAvailableList() {

		ArrayList<Airway> results = new ArrayList<Airway>();

		for (Iterator iterator = getAirwaysCompaniesList().iterator(); iterator
				.hasNext();) {
			Airway a = (Airway) iterator.next();
			if (a.getSearchStatus() == (SEARCH_STATUS_AVAILABLE)) {
				results.add(a);
			}

		}

		return results;

	}

	public static void setAirwaysCompaniesList(
			ArrayList<Airway> airwaysCompaniesList) {
		Airway.airwaysCompaniesList = airwaysCompaniesList;
	}

	public static List<Airway> airwaysCompaniesList = new ArrayList<Airway>();

	static {

		fillTHYAirportConversionMap();
		fillOnurAirAirportConversionMap();
		fillSunexpressAirportConversionMap();
		//fillAirwaysCompaniesList();

	}

	public static void fillAirwaysCompaniesList() {

		try {

			airwaysCompaniesList = airwaysRepository.findAllByOrderByIdAsc();

			Airway tempAirway = null;

			for (int i = 0; i < airwaysCompaniesList.size(); i++) {

				tempAirway = (Airway) airwaysCompaniesList.get(i);

				airwaysCompaniesHash.put(tempAirway.getCode(), tempAirway);

			}

		} catch (Exception e) {
			logger.error("Airports can not be loaded from db");
			e.printStackTrace();
		}

	}

	public static String convertAnadolujetCityCode(String originalAirportCode) {

		if (originalAirportCode.equals("ESB")) {
			originalAirportCode = (originalAirportCode + "%2C" + "ANK");
		} else if (originalAirportCode.equals("BJV")) {
			originalAirportCode = (originalAirportCode + "%2C" + "BXN");
		} else if (originalAirportCode.equals("YEI")) {
			originalAirportCode = (originalAirportCode + "%2C" + "BTZ");
		} else if (originalAirportCode.equals("SAW")) {
			originalAirportCode = (originalAirportCode + "%2C" + "IST");
		} else if (originalAirportCode.equals("ADB")) {
			originalAirportCode = (originalAirportCode + "%2C" + "IZM");
		} else if (originalAirportCode.equals("SZF")) {
			originalAirportCode = (originalAirportCode + "%2C" + "SSX");
		} else if (originalAirportCode.equals("GNY")) {
			originalAirportCode = (originalAirportCode + "%2C" + "SFQ");
		} else if (originalAirportCode.equals("USQ")) {
			originalAirportCode = (originalAirportCode + "%2C" + "ADF");
		} else {
			originalAirportCode = (originalAirportCode + "," + originalAirportCode);
		}
		/*
		 * 'ESB','ANK' 'BJV','BXN' 'YEI','BTZ' 'SAW','IST' 'ADB','IZM'
		 * 'SZF','SSX' 'GNY','SFQ' 'USQ','ADF'
		 */

		return originalAirportCode;

	}

	public static String convertthyCityCode(String originalAirportCode) {

		Object o = thyCitiesConversionMap.get(originalAirportCode);

		if (o != null) {

			return o.toString();

		} else {

			return null;
		}
	}

	public static String convertSunexpressAirCityCode(String originalAirportCode) {

		Object o = sunexpressCitiesConversionMap.get(originalAirportCode);

		if (o != null) {

			return o.toString();

		} else {

			return null;
		}
	}

	public static String convertOnurAirCityCode(String originalAirportCode) {

		Object o = onurAirCitiesConversionMap.get(originalAirportCode);

		if (o != null) {

			return o.toString();

		} else {

			return null;
		}
	}

	public static Airway getAirwaysByCode(String airwaysCompanyCode) {

		return airwaysCompaniesHash.get(airwaysCompanyCode);

	}

	public static String getAirwayNameByCode(String airwaysCompanyCode) {

		Object o = airwaysCompaniesHash.get(airwaysCompanyCode);

		if (o != null) {

			Airway ac = (Airway) Airway.airwaysCompaniesHash
					.get(airwaysCompanyCode);

			return ac.getName();

		} else {

			return "-";

		}

	}

	public static void fillOnurAirAirportConversionMap() {

		onurAirCitiesConversionMap.put("LTAF", "ADA");
		onurAirCitiesConversionMap.put("LTAC", "ESB");
		onurAirCitiesConversionMap.put("LTAI", "AYT");
		onurAirCitiesConversionMap.put("LTFE", "BJV");
		onurAirCitiesConversionMap.put("LTBS", "DLM");
		onurAirCitiesConversionMap.put("LTCC", "DIY");
		onurAirCitiesConversionMap.put("LTAJ", "GZT");
		onurAirCitiesConversionMap.put("LTBA", "IST");
		onurAirCitiesConversionMap.put("LTBJ", "ADB");
		onurAirCitiesConversionMap.put("LTAU", "ASR");
		onurAirCitiesConversionMap.put("LTAT", "MLX");
		onurAirCitiesConversionMap.put("LTAQ", "SZF");
		onurAirCitiesConversionMap.put("LTCG", "TZX");

	}

	public static void fillSunexpressAirportConversionMap() {

		sunexpressCitiesConversionMap.put("LTAF", "ADA");
		sunexpressCitiesConversionMap.put("LTAC", "ESB");
		sunexpressCitiesConversionMap.put("LTAI", "AYT");
		sunexpressCitiesConversionMap.put("LTFE", "BJV");
		sunexpressCitiesConversionMap.put("LTBS", "DLM");
		sunexpressCitiesConversionMap.put("LTCC", "DIY");
		sunexpressCitiesConversionMap.put("LTAJ", "GZT");
		sunexpressCitiesConversionMap.put("LTBA", "IST");
		sunexpressCitiesConversionMap.put("LTFJ", "SAW");
		sunexpressCitiesConversionMap.put("LTBJ", "ADB");
		sunexpressCitiesConversionMap.put("LTAU", "ASR");
		sunexpressCitiesConversionMap.put("LTAT", "MLX");
		sunexpressCitiesConversionMap.put("LTAQ", "SZF");
		sunexpressCitiesConversionMap.put("LTCG", "TZX");

	}

	public static void fillTHYAirportConversionMap() {

		thyCitiesConversionMap.put("LTAC", "ANK");
		thyCitiesConversionMap.put("LTAF", "ADA");
		thyCitiesConversionMap.put("LTAJ", "GZT");
		thyCitiesConversionMap.put("LTAK", "HTY");
		thyCitiesConversionMap.put("LTAI", "AYT");
		thyCitiesConversionMap.put("LTAP", "MZH");
		thyCitiesConversionMap.put("LTAQ", "SZF");
		thyCitiesConversionMap.put("LTAN", "KYA");
		thyCitiesConversionMap.put("LTAY", "DNZ");

		thyCitiesConversionMap.put("LTAR", "VAS");
		thyCitiesConversionMap.put("LTAT", "MLX");
		thyCitiesConversionMap.put("LTAU", "ASR");

		thyCitiesConversionMap.put("LTBA", "IST");
		thyCitiesConversionMap.put("LTBJ", "IZM");
		thyCitiesConversionMap.put("LTBS", "DLM");
		thyCitiesConversionMap.put("LTBY", "AOE");

		thyCitiesConversionMap.put("LTCA", "EZS");
		thyCitiesConversionMap.put("LTCC", "DIY");
		thyCitiesConversionMap.put("LTCD", "ERC");
		thyCitiesConversionMap.put("LTCE", "ERZ");
		thyCitiesConversionMap.put("LTCF", "KSY");
		thyCitiesConversionMap.put("LTCG", "TZX");
		thyCitiesConversionMap.put("LTCH", "GNY");
		thyCitiesConversionMap.put("LTCI", "VAN");
		thyCitiesConversionMap.put("LTCJ", "BAL");
		thyCitiesConversionMap.put("LTCK", "MSR");
		thyCitiesConversionMap.put("LTCM", "SIC");
		thyCitiesConversionMap.put("LTCN", "KCM");
		thyCitiesConversionMap.put("LTCO", "AJI");
		thyCitiesConversionMap.put("LTCP", "ADF");
		thyCitiesConversionMap.put("LTCR", "MQM");

		thyCitiesConversionMap.put("LTFE", "BXN");

		thyCitiesConversionMap.put("LCEN", "ECN");

	}

	@Id
	@GeneratedValue
	public Long id;

	public String name;
	public String code;
	public String url;
	public String callCenter;
	public char searchStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCallCenter() {
		return callCenter;
	}

	public void setCallCenter(String callCenter) {
		this.callCenter = callCenter;
	}

	public char getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(char searchStatus) {
		this.searchStatus = searchStatus;
	}

}
