package com.ucustakvimi.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.id.jericho.lib.html.Element;
import au.id.jericho.lib.html.Segment;
import au.id.jericho.lib.html.Source;
import au.id.jericho.lib.html.StartTag;
import au.id.jericho.lib.html.Tag;

import com.ucustakvimi.repository.AirportRepository;

@Component
public class Flight implements Comparable<Flight> {

	private static final Logger logger = LoggerFactory.getLogger(Flight.class);

	@Autowired
	private static AirportRepository airportRepository;

	public static String ATLASJET_COOKIE = "";

	public static String ANADOLUJET_COOKIE = "";

	public static String THY_COOKIE = "";

	public static ArrayList THY_COOKIES = new ArrayList();

	public static ArrayList ONUR_COOKIES = new ArrayList();

	public static ArrayList SUNEXPRESS_COOKIES = new ArrayList();

	public static String SKY_COOKIE = "";

	public static String PEGASUS_COOKIE = "";

	public static String BRITISH_AIRWAYS_COOKIE = "";

	public static String USER_AGENT = "Mozilla/5.0 (Linux; Android 5.1.1; SM-G928X Build/LMY47X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36";

	public String flightNo = "";

	public String flightCode = "";

	public String fromAirportCode = "";

	public String fromAirportName = "";

	public String fromAirportCityCode = "";

	public String fromAirportCityName = "";

	public String toAirportCode = "";

	public String toAirportName = "";

	public String toAirportCityCode = "";

	public String toAirportCityName = "";

	public String departureTime = "";

	public String departureDate = "";

	public String arrivalTime = "";

	public String duration = "";

	public String airwaysCompanyCode = "";

	public String airwaysCompanyName = "";

	public String airwaysCompanyUrl = "";

	public String airwaysCompanyTelephone = "";

	public String feeAdult = "";

	public String feeInfant = "";

	public String feeBaby = "";

	public String feeService = "";

	public String feeTax = "";

	public String feeTotal = "";

	public int numberOfBabiesFlying = 0;

	public int numberOfInfantsFlying = 0;

	public int numberOfAdultsFlying = 0;

	public String buyLink = "";

	public String getBuyLink() {
		return buyLink;
	}

	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("" + "<pre>");
		sb.append("\nFlight Details : \n---------------");
		sb.append("\nFlight no : '");
		sb.append(getFlightNo());
		sb.append("'\nFlight code : '");
		sb.append(getFlightCode());
		sb.append("'\nAdult Fee : '");
		sb.append(getFeeAdult());
		sb.append("'\nDeparture Date : '");
		sb.append(getDepartureDate());
		sb.append("'\nDeparture Time : '");
		sb.append(getDepartureTime());
		sb.append("'\nCompany : '");
		sb.append(getAirwayName());
		sb.append("</pre>" + "");
		return sb.toString();
	}

	public static ArrayList<Flight> searchAllFlight(
			String[] airwaysCompaniesToSearch, String fromAirportCode,
			String toAirportCode, String flightDate) {

		ArrayList<Flight> allResults = new ArrayList<Flight>();

		ArrayList<Flight> tempResults = null;

		for (int i = 0; i < airwaysCompaniesToSearch.length; i++) {

			String company = airwaysCompaniesToSearch[i];

			MethodType methodType;
			MethodHandle methodHandle;
			MethodHandles.Lookup lookup = MethodHandles.lookup();

			try {
				methodType = MethodType.methodType(ArrayList.class,
						String.class, String.class, String.class, String.class);
				methodHandle = lookup.findStatic(Flight.class, "searchFlight"
						+ company, methodType);

				tempResults = (ArrayList) methodHandle.invokeExact(
						fromAirportCode, toAirportCode, flightDate, "tr");

				allResults.addAll(tempResults);

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return allResults;
	}

	public static ArrayList<Flight> searchFlightKHY(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		ArrayList<Flight> onurFlights = new ArrayList<Flight>();

		String fromAirportCodeKTHY = Airway.convertthyCityCode(fromAirportCode);
		String toAirportCodeKTHY = Airway.convertthyCityCode(toAirportCode);

		String url = "https://ww3.dotres.com/meridia?posid=YKYK&page=requestAirMessage_air&action=airRequest&realrequestAir=realrequestAir&departCity=##FROM_AIRPORT##&depDay=##DEP_DAY##&depMonth=##DEP_MONTH##&depTime=0000&returnCity=##TO_AIRPORT##&retDay=20&retMonth=AUG&retTime=0000&direction=onewaytravel&ADT=1&CHD=0&INF=0&STU=0&classService=CoachClass&flightType=1&actionType=semiFlex&x=15&y=9";

		String[] months = new String[] {

		"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
				"NOV", "DEC" };
		int monthIndex = Integer.parseInt(flightDate.substring(4, 6)) - 1;

		String month = months[monthIndex];

		url = StringUtils.replace(url, "##FROM_AIRPORT##", fromAirportCodeKTHY);
		url = StringUtils.replace(url, "##TO_AIRPORT##", toAirportCodeKTHY);
		url = StringUtils.replace(url, "##DEP_MONTH##", month);
		url = StringUtils.replace(url, "##DEP_DAY##",
				flightDate.substring(6, 8));

		String buyLinkUrl = url;

		try {

			Source source = new Source(new URL(url));

			source = new Source(source);

			List<?> segments = source.findAllElements(Tag.DIV);
			String tdContent = "";
			Segment segment = null;

			String feeAdult = "";
			String feeInfant = "";
			String feeBaby = "";

			Flight tempFlight = null;
			Airport tempFromAirport = null;
			Airport tempToAirport = null;

			for (Iterator<?> i = segments.iterator(); i.hasNext();) {

				segment = (Segment) i.next();
				tdContent = segment.toString();

				if (tdContent.contains("Yetişkin:")) {

					tempFlight = new Flight();

					tempFromAirport =  airportRepository.findByCode(fromAirportCode);
					tempToAirport = airportRepository.findByCode(toAirportCode);

					tempFlight.setFeeAdult(feeAdult);
					tempFlight.setFeeInfant(feeInfant);
					tempFlight.setFeeBaby(feeBaby);

					tempFlight.setFromAirportCode(fromAirportCode);
					tempFlight.setFromAirportName(tempFromAirport.getName());
					tempFlight.setToAirportCode(toAirportCode);
					tempFlight.setToAirportName(tempToAirport.getName());
					/*
					 * tempFlight.setDepartureTime(departureTime);
					 * tempFlight.setArrivalTime(arrivalTime);
					 * tempFlight.setFlightNo(flightNo);
					 * tempFlight.setFlightCode(flightCode);
					 */

					Airway a = Airway.getAirwaysByCode("KHY");

					tempFlight.setAirwayCode(a.getCode());
					tempFlight.setAirwayName(a.getName());
					tempFlight.setAirwayUrl(a.getUrl());
					tempFlight.setAirwayTelephone(a.getCallCenter());

					tempFlight.setDepartureDate(flightDate);

					tempFlight.setBuyLink(buyLinkUrl);

					onurFlights.add(tempFlight);
				}
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return onurFlights;

	}

	public static ArrayList<Flight> searchFlightOHY(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		// https://book.onurair.com/web/RezvEntry.xhtml --> Referer:
		// https://book.onurair.com/web/MobileRezvEntry.xhtml
		// https://book.onurair.com/web/RezvEntry.xhtml?tripType=ONE_WAY&language=tr&dateFormat=dd%2FMM%2Fyyyy&depPort=IST&arrPort=ADB&adult=1&child=0&infant=0&departureDate=18%2F11%2F2016

		// https://book.onurair.com/web/AvailabilityResults.xhtml?faces-redirect=true

		ArrayList<Flight> onurFlights = new ArrayList<Flight>();

		String fromAirportCodeOnur = Airway
				.convertOnurAirCityCode(fromAirportCode);
		String toAirportCodeOnur = Airway.convertOnurAirCityCode(toAirportCode);

		String flightDateConverted = flightDate.substring(6, 8) + "%2F"
				+ flightDate.substring(4, 6) + "%2F"
				+ flightDate.substring(0, 4);

		String url = "https://book.onurair.com/web/RezvEntry.xhtml?tripType=ONE_WAY&language=tr&dateFormat=dd%2FMM%2Fyyyy&depPort=##FROM_AIRPORT##&arrPort=##TO_AIRPORT##&adult=1&child=0&infant=0&departureDate=##DEPARTURE_DATE##";

		String buyLinkUrl = url;

		if (language.equals("en")) {

			buyLinkUrl = "https://book.onurair.com/web/RezvEntry.xhtml?tripType=ONE_WAY&language=en&dateFormat=dd%2FMM%2Fyyyy&depPort=##FROM_AIRPORT##&arrPort=##TO_AIRPORT##&adult=1&child=0&infant=0&departureDate=##DEPARTURE_DATE##";

		}

		buyLinkUrl = StringUtils.replace(buyLinkUrl, "##FROM_AIRPORT##",
				fromAirportCodeOnur);
		buyLinkUrl = StringUtils.replace(buyLinkUrl, "##TO_AIRPORT##",
				toAirportCodeOnur);
		buyLinkUrl = StringUtils.replace(buyLinkUrl, "##DEPARTURE_DATE##",
				flightDateConverted);

		try {

			String cevap1 = callOnur(
					"https://book.onurair.com/web/Mobile.xhtml", null, true);

			logResponse("ONUR1.html", cevap1);

			String cevap2 = callOnur(
					"https://book.onurair.com/web/MobileRezvEntry.xhtml",
					"https://book.onurair.com/web/Mobile.xhtml", true);

			logResponse("ONUR2.html", cevap2);

			String cevap = callOnur(buyLinkUrl,
					"https://book.onurair.com/web/MobileRezvEntry.xhtml", true);

			logResponse("ONUR3.html", cevap);

			/*
			 * cevap = callOnur(
			 * "https://book.onurair.com/web/AvailabilityResults.xhtml?faces-redirect=true"
			 * , buyLinkUrl, true);
			 * 
			 * logResponse("ONUR4.html", cevap);
			 */

			if (cevap.indexOf("<!-- segmentGroup begin		 -->") == -1) {
				return onurFlights;
			}

			cevap = cevap.substring(cevap
					.indexOf("<!-- segmentGroup begin		 -->"));

			/*
			 * String cevap = callOnur(
			 * "https://book.onurair.com/web/AvailabilityResults.xhtml?faces-redirect=true"
			 * , buyLinkUrl, true);
			 * 
			 * logResponse("ONUR3.html", cevap);
			 */

			Source source = new Source(new StringReader(cevap));

			// Source source = new Source(new URL(url));

			source = new Source(source);

			List<?> segments = source.findAllElements(Tag.DIV);
			String divContent = "";
			Segment segment = null;

			Flight tempFlight = null;
			Airport tempFromAirport = null;
			Airport tempToAirport = null;

			Airway tempAirways = null;

			for (Iterator<?> i = segments.iterator(); i.hasNext();) {

				segment = (Segment) i.next();
				divContent = segment.toString();

				if (divContent.indexOf("<div class=\"flight__row one\">") != -1) {

					String departureTime = extractByStartWord(divContent,
							"<span class=\"from-t from__hour\">", "</span>");

					departureTime = departureTime.replaceFirst(":", "");

					String arrivalTime = "";

					String flightNo = extractByStartWord(divContent,
							"book__icon\"></i>", "</span>").trim();
					String flightCode = flightNo;

					String feeAdult = "";

					feeAdult = extractByStartWord(divContent,
							"\">																	</div>																		",
							"TL");

					if (feeAdult.length() == 2) {

						feeAdult = "0" + feeAdult;

					}
					if (feeAdult.equals("")) {
						continue;
					}

					float lFee = Float.parseFloat(feeAdult);

					// servis ücreti
					if (lFee > 200) {

						feeAdult = (lFee + 20) + "";

					} else {

						feeAdult = (lFee + 15) + "";

					}

					flightCode = flightCode.replaceFirst(" ", "");

					tempFlight = new Flight();

					tempFromAirport = airportRepository.findByCode(fromAirportCode);
					tempToAirport = airportRepository.findByCode(toAirportCode);

					tempFlight.setFeeAdult(feeAdult);

					tempFlight.setFromAirportCode(fromAirportCode);
					tempFlight.setFromAirportName(tempFromAirport.getName());
					tempFlight.setToAirportCode(toAirportCode);
					tempFlight.setToAirportName(tempToAirport.getName());
					tempFlight.setDepartureTime(departureTime);
					tempFlight.setArrivalTime(arrivalTime);
					tempFlight.setFlightNo(flightNo);
					tempFlight.setFlightCode(flightCode);

					Airway a = Airway.getAirwaysByCode("OHY");

					tempFlight.setAirwayCode(a.getCode());
					tempFlight.setAirwayName(a.getName());
					tempFlight.setAirwayUrl(a.getUrl());
					tempFlight.setAirwayTelephone(a.getCallCenter());

					tempFlight.setDepartureDate(flightDate);

					tempFlight.setBuyLink(buyLinkUrl);

					onurFlights.add(tempFlight);
				}
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		onurFlights = new ArrayList<Flight>(new LinkedHashSet<Flight>(
				onurFlights));

		return onurFlights;

	}

	public static ArrayList<Flight> searchFlightSXS(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		callSunexpress("https://m.sunexpress.com/index.xhtml",
				"http://www.sunexpress.com/tr", true);

		// https://book.onurair.com/web/RezvEntry.xhtml --> Referer:
		// https://book.onurair.com/web/MobileRezvEntry.xhtml
		// https://book.onurair.com/web/RezvEntry.xhtml?tripType=ONE_WAY&language=tr&dateFormat=dd%2FMM%2Fyyyy&depPort=IST&arrPort=ADB&adult=1&child=0&infant=0&departureDate=18%2F11%2F2016

		// https://book.onurair.com/web/AvailabilityResults.xhtml?faces-redirect=true

		ArrayList<Flight> sunexpressFlights = new ArrayList<Flight>();

		String fromAirportCodeSunexpress = Airway
				.convertSunexpressAirCityCode(fromAirportCode);
		String toAirportCodeSunexpress = Airway
				.convertSunexpressAirCityCode(toAirportCode);

		String flightDateConverted = flightDate.substring(6, 8) + "%2F"
				+ flightDate.substring(4, 6) + "%2F"
				+ flightDate.substring(0, 4);

		String url = "https://sun.sunexpress.com.tr/web/RezvEntry.xhtml?TRIPTYPE=O&TRIPTYPERADIO=tripTypeO&DEPPORT=##FROM_AIRPORT##&RETDEPPORT&SECARRPORT=##TO_AIRPORT##&ARRPORT=##TO_AIRPORT##&RETARRPORT&from=##DEPARTURE_DATE##&DEPDATE=##DEPARTURE_DATE##&to=##DEPARTURE_DATE##&RETDATE=##DEPARTURE_DATE##&ADULT=1&CHILD=0&INFANT=0&SECARRCOMBO&source=btnSearch&LANGUAGE=TR";

		String buyLinkUrl = url;

		if (language.equals("en")) {

			buyLinkUrl = "https://sun.sunexpress.com.tr/web/RezvEntry.xhtml?TRIPTYPE=O&TRIPTYPERADIO=tripTypeO&DEPPORT=##FROM_AIRPORT##&RETDEPPORT&SECARRPORT=##TO_AIRPORT##&ARRPORT=##TO_AIRPORT##&RETARRPORT&from=##DEPARTURE_DATE##&DEPDATE=##DEPARTURE_DATE##&to=##DEPARTURE_DATE##&RETDATE=##DEPARTURE_DATE##&ADULT=1&CHILD=0&INFANT=0&SECARRCOMBO&source=btnSearch&LANGUAGE=EN";

		}

		buyLinkUrl = StringUtils.replace(buyLinkUrl, "##FROM_AIRPORT##",
				fromAirportCodeSunexpress);
		buyLinkUrl = StringUtils.replace(buyLinkUrl, "##TO_AIRPORT##",
				toAirportCodeSunexpress);
		buyLinkUrl = StringUtils.replace(buyLinkUrl, "##DEPARTURE_DATE##",
				flightDateConverted);

		try {

			String cevap = callSunexpress(buyLinkUrl,
					"http://www.sunexpress.com/tr", true);

			logResponse("SUNEXPRESS2.html", cevap);

			cevap = cevap
					.substring(cevap
							.indexOf("<ul style=\"margin: 0; padding-left: 15px; padding-top: 8px;\">"));

			/*
			 * String cevap = callOnur(
			 * "https://book.onurair.com/web/AvailabilityResults.xhtml?faces-redirect=true"
			 * , buyLinkUrl, true);
			 * 
			 * logResponse("ONUR3.html", cevap);
			 */

			Source source = new Source(new StringReader(cevap));

			List<Segment> ulSegments = source.findAllElements(Tag.UL);
			String ulContent = "";
			Segment ulSegment = null;

			Flight tempFlight = null;
			Airport tempFromAirport = null;
			Airport tempToAirport = null;

			for (Iterator<Segment> i = ulSegments.iterator(); i.hasNext();) {

				ulSegment = (Segment) i.next();
				ulContent = ulSegment.toString();

				if (ulContent.indexOf("Yok") == -1) {

					String departureTime = extractByStartWord(ulContent,
							"<span class=\"bundletime \">", "</span>");

					departureTime = departureTime.replaceFirst(":", "");

					String arrivalTime = "";

					String flightNo = extractByStartWord(ulContent,
							"book__icon\"></i>", "</span>").trim();
					String flightCode = flightNo;

					String feeAdult = "";

					feeAdult = extractByStartWord(ulContent,
							"\">																	</div>																		",
							"TL");

					if (feeAdult.length() == 2) {

						feeAdult = "0" + feeAdult;

					}

					float lFee = Float.parseFloat(feeAdult);
					// servis ücreti
					if (lFee > 200) {

						feeAdult = (lFee + 20) + "";

					} else {

						feeAdult = (lFee + 15) + "";

					}

					flightCode = flightCode.replaceFirst(" ", "");

					tempFlight = new Flight();

					tempFromAirport = airportRepository.findByCode(fromAirportCode);
					tempToAirport = airportRepository.findByCode(toAirportCode);

					tempFlight.setFeeAdult(feeAdult);

					tempFlight.setFromAirportCode(fromAirportCode);
					tempFlight.setFromAirportName(tempFromAirport.getName());
					tempFlight.setToAirportCode(toAirportCode);
					tempFlight.setToAirportName(tempToAirport.getName());
					tempFlight.setDepartureTime(departureTime);
					tempFlight.setArrivalTime(arrivalTime);
					tempFlight.setFlightNo(flightNo);
					tempFlight.setFlightCode(flightCode);

					Airway a = Airway.getAirwaysByCode("SXS");

					tempFlight.setAirwayCode(a.getCode());
					tempFlight.setAirwayName(a.getName());
					tempFlight.setAirwayUrl(a.getUrl());
					tempFlight.setAirwayTelephone(a.getCallCenter());

					tempFlight.setDepartureDate(flightDate);

					tempFlight.setBuyLink(buyLinkUrl);

					sunexpressFlights.add(tempFlight);
				}
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		sunexpressFlights = new ArrayList<Flight>(new LinkedHashSet<Flight>(
				sunexpressFlights));

		return sunexpressFlights;

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Flight> searchFlightPGT(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		ArrayList<Flight> pegasusFlights = new ArrayList<Flight>();

		Airport tempFromAirport = airportRepository.findByCode(fromAirportCode);
		Airport tempToAirport = airportRepository.findByCode(toAirportCode);

		String fromAirportIATACode = tempFromAirport.getIataCode();
		String toAirportIATACode = tempToAirport.getIataCode();

		// 20080523 --> 23/05/2008 --> 23%2F05%2F2008

		String flightDateConverted = flightDate.substring(6, 8) + "/"
				+ flightDate.substring(4, 6) + "/" + flightDate.substring(0, 4);

		String flightDateWithDots = flightDate.substring(6, 8) + "."
				+ flightDate.substring(4, 6) + "." + flightDate.substring(0, 4);

		String flightDateEncoded = URLEncoder.encode(flightDateConverted);

		// https://book.flypgs.com/Common/MemberRezvResults.jsp?activeLanguage=TR&DEPPORT=IST&ARRPORT=ADB&LBLDEPPORT=Istanbul-Atatürk&LBLARRPORT=Izmir&TRIPTYPE=O&DEPDATE=18/11/2016&RETDATE=18/11/2016&ADULT=1&CHILD=0&INFANT=0&STUDENT=0&SOLDIER=0&CURRENCY=TRY&LC=TR&FLEX&userId=6353698e4837a1fbbcd5689aa53f0201&resetErrors=T&clickedButton=btnSearch&DEPDATEO=3&RETDATEO=3&activeLanguage=TR

		String url = "https://book.flypgs.com/Common/MemberRezvResults.jsp?activeLanguage=TR&DEPPORT=##FROM_AIRPORT##&ARRPORT=##TO_AIRPORT##&LBLDEPPORT=Istanbul-Atatürk&LBLARRPORT=Izmir&TRIPTYPE=O&DEPDATE=##DEPARTURE_DATE##&RETDATE=##DEPARTURE_DATE##&ADULT=1&CHILD=0&INFANT=0&STUDENT=0&SOLDIER=0&CURRENCY=TRY&LC=TR&FLEX&userId=6353698e4837a1fbbcd5689aa53f0201&resetErrors=T&clickedButton=btnSearch&DEPDATEO=3&RETDATEO=3&activeLanguage=TR";

		if (language.equals("en")) {
			url = "https://book.flypgs.com/Common/MemberRezvResults.jsp?activeLanguage=EN&DEPPORT=##FROM_AIRPORT##&ARRPORT=##TO_AIRPORT##&LBLDEPPORT=Istanbul-Atatürk&LBLARRPORT=Izmir&TRIPTYPE=O&DEPDATE=##DEPARTURE_DATE##&RETDATE=##DEPARTURE_DATE##&ADULT=1&CHILD=0&INFANT=0&STUDENT=0&SOLDIER=0&CURRENCY=TRY&LC=TR&FLEX&userId=6353698e4837a1fbbcd5689aa53f0201&resetErrors=T&clickedButton=btnSearch&DEPDATEO=3&RETDATEO=3&activeLanguage=TR";
		}

		url = StringUtils.replace(url, "##FROM_AIRPORT##", fromAirportIATACode);
		url = StringUtils.replace(url, "##TO_AIRPORT##", toAirportIATACode);
		url = StringUtils.replace(url, "##DEPARTURE_DATE##", flightDateEncoded);

		String cevap = callPegasus(url, true);
		String feeAdult = "";

		String departureDate = "";

		Flight tempFlight = null;

		try {

			if (cevap.indexOf("<span class='flightPrice'>") == -1) {

				return pegasusFlights;

			}

			cevap = extractByStartWord(
					cevap,
					"<div style='display:none;' class='threePackageFlightDayContainerDEP' id='flightPrevDayContainerDEP'>",
					"<span class=\"text\">Para Birimini Değiştir</span>");

			logResponse("DENEME", cevap);

			Source src = new Source(new StringReader(cevap));

			ArrayList<?> a = new ArrayList<Object>(
					src.findAllElements(StartTag.DIV));

			for (int i = 0; i < a.size(); i++) {

				Element e = (Element) a.get(i);

				String content = e.toString();

				if (content.indexOf("flightInfo") == -1) {
					continue;
				}
				if (content.indexOf(flightDateWithDots) == -1) {
					continue;
				}

				/*
				 * <div class='item'> <script>var paymentItems332162_286712 =
				 * new Array();paymentItems332162_286712[0] = new
				 * paymentItem(29.99,29.99,10.0,29.99,29.99,29.99,'Fare','TRY');
				 * paymentItems332162_286712[1] = new
				 * paymentItem(7.0,7.0,0.0,0.0,0.0,0,'TAX','TRY');
				 * paymentItems332162_286712[2] = new
				 * paymentItem(15.0,15.0,0.0,15.0,15.0,0,'TAX','TRY');</script><
				 * span class='applyFilterRadio'> <input type='radio'
				 * onclick="flightInfo('PC120','G','GOW','04.08.2011','18:30','
				 * 04.08.2011','19:30','DEP','IST','SAW','IZM','ADB');
				 * applyFilter(paymentItems332162_286712,'DEP','TRY','SAW','T','
				 * TRY',this);" value='T#G#286712#GOW#332162#F'
				 * name='DEPSEGISN04/08/2011-1#-1'
				 * chk='DEPSEGISN04/08/2011-1#-1'></span> <span
				 * class='flightPrice'> 51.99<sup>TRY</sup></span> <span
				 * class='flightTicketType'><a title=' GOW '
				 * href="javascript:openInfoDlg('GOW')"
				 * class='flightTicketType'>KAMPANYA</a></span> <span
				 * class='flightTime'>KalkÄ±ÅŸ 18:30 VarÄ±ÅŸ 19:30</span> <div
				 * class='fltInfo'></div></div>
				 */

				int iFeeAdult = 0;
				int feeStartIndex = content
						.indexOf("<span class='flightPrice'>") + 26;
				int feeEndIndex = content.substring(feeStartIndex).indexOf(
						"<sup>")
						+ feeStartIndex;

				feeAdult = content.substring(feeStartIndex, feeEndIndex).trim();

				/*
				 * // 2 digits if (content.substring(feeEndIndex - 3,
				 * feeEndIndex) .indexOf(" ") != -1) { feeAdult =
				 * content.substring(feeEndIndex - 2, feeEndIndex); // 3 digits
				 * } else { feeAdult = content.substring(feeEndIndex - 3,
				 * feeEndIndex); }
				 */

				float fFeeAdult = Float.parseFloat(feeAdult + "99");

				iFeeAdult = (int) fFeeAdult;

				iFeeAdult += 10; // hizmet ucreti
				// iFeeAdult += 23;
				feeAdult = "" + iFeeAdult;

				// flightInfo('H9114'
				int flightNoStartIndex = content.indexOf("flightInfo('");
				String flightNo = content.substring(flightNoStartIndex + 12,
						flightNoStartIndex + 18);

				// flightInfo('PC4122','U','UOW2','06.08.2011','16:00','06.08.2011','17:05','DEP','IST','SAW','IZM','ADB')
				// flightInfo('PC2813','X','XOW','18.11.2016','13:10','18.11.2016','14:15','DEP','IST','IST','Istanbul-Atatürk','IZM','ADB','Izmir','#','','','1764578','TR','TR','DOM','PC','F','THIRD','Extra');
				int iDepartureTimeStartIndex = content.indexOf(":",
						flightNoStartIndex);
				String departureTime = content.substring(
						iDepartureTimeStartIndex - 2,
						iDepartureTimeStartIndex + 3);
				departureTime = departureTime.replaceAll(":", "");

				departureDate = content.substring(flightNoStartIndex + 31,
						flightNoStartIndex + 41);

				if (departureDate == null || departureDate.equals("")
						|| !departureDate.equals(flightDateWithDots)) {
					continue;
				}

				SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
				SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");

				try {

					Date date = formatter.parse(departureDate);

					departureDate = formatter2.format(date);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					continue;
				}

				tempFlight = new Flight();

				tempFlight.setFeeAdult(feeAdult);

				tempFlight.setFromAirportCode(fromAirportCode);
				tempFlight.setFromAirportName(tempFromAirport.getName());
				tempFlight.setToAirportCode(toAirportCode);
				tempFlight.setToAirportName(tempToAirport.getName());
				tempFlight.setDepartureTime(departureTime);
				// tempFlight.setArrivalTime(arrivalTime);
				tempFlight.setFlightNo(flightNo);
				tempFlight.setFlightCode(flightNo);

				Airway ac = Airway.getAirwaysByCode("PGT");

				tempFlight.setAirwayCode(ac.getCode());
				tempFlight.setAirwayName(ac.getName());
				tempFlight.setAirwayUrl(ac.getUrl());
				tempFlight.setAirwayTelephone(ac.getCallCenter());

				tempFlight.setDepartureDate(departureDate);

				tempFlight.setBuyLink(url);

				pegasusFlights.add(tempFlight);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		pegasusFlights = new ArrayList<Flight>(new LinkedHashSet<Flight>(
				pegasusFlights));

		return pegasusFlights;

	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub

		return (this.departureDate.hashCode() + this.departureTime.hashCode()
				+ this.feeAdult.hashCode() + this.flightCode.hashCode());
	}

	@Override
	public boolean equals(Object obj) {

		Flight fToCompare = (Flight) obj;

		return fToCompare.getDepartureDate().equals(getDepartureDate())
				&& fToCompare.getDepartureTime().equals(getDepartureTime())
				&& fToCompare.getFeeAdult().equals(getFeeAdult())
				&& fToCompare.getFlightCode().equals(getFlightCode());
	}

	public static ArrayList<Flight> searchFlightBritishAirways(
			String fromAirportCode, String toAirportCode, String flightDate,
			String language) {

		callBritishAirways("http://www.britishairways.com/travel/fx/public/en_gb");

		ArrayList<Flight> britishAirwaysFlights = new ArrayList<Flight>();

		String url = "http://www.britishairways.com/travel/fx/public/en_gb?eId=111002&requestedTX=False&pageid=&hostURL=http%3A%2F%2Fwww.britishairways.com&saleOption=FO&depCountry=TR&depCountryPkg=TR&from=IST&fromPkg=AYT&to=London%2C+United+Kingdom%2C+LHR%2C+Heathrow&packageTo=&ojDd=&ojGw=&ojCountry=&depDate=20%2F04%2F09&journeyType=OWFLT&cabin=M&restrictionType=LOWEST&ad=1&ch=0&inf=0&hotelGOTo=&checkInGO=DD%2FMM%2FYY&checkOutGO=DD%2FMM%2FYY&numNightsGO=&carGOTo=&pickUpGO=DD%2FMM%2FYY&pickUpTimeHr=12&pickUpTimeMin=00&dropOffGO=DD%2FMM%2FYY&dropOffTimeHr=12&dropOffTimeMin=00&expGOTo=&expFromGO=DD%2FMM%2FYY&expUntilGO=DD%2FMM%2FYY&expNumPaxGO=2&roomsRqd=0&flightHotelDate=Y&adcar=1&chcar=0&infcar=0&flightCarDate=Y&BAHPRESpecialOfferGroup=";

		Airport tempFromAirport = null;
		Airport tempToAirport = null;

		/*
		 * 
		 * Airport tempFromAirport = airportRepository.findByCode(fromAirportCode);
		 * Airport tempToAirport = airportRepository.findByCode(toAirportCode);
		 * 
		 * String fromAirportIATACode = tempFromAirport.getIataCode(); String
		 * toAirportIATACode = tempToAirport.getIataCode(); // 20080523 -->
		 * 23/05/2008 --> 23%2F05%2F2008
		 * 
		 * String flightDateConverted = flightDate.substring(6, 8) + "/" +
		 * flightDate.substring(4, 6) + "/" + flightDate.substring(0, 4);
		 * 
		 * String flightDateEncoded = URLEncoder.encode(flightDateConverted);
		 * 
		 * 
		 * 
		 * 
		 * String url =
		 * "https://book.flypgs.com/Common/MemberRezvResults.jsp?activeLanguage=TR&TRIPTYPE=O&ADULT=1&CHILD=0&INFANT=&DEPPORT=##FROM_AIRPORT##&ARRPORT=##TO_AIRPORT##&DEP_DATE_OPTS=3&RET_DATE_OPTS=3&DEPDATE=##DEPARTURE_DATE##&RETDATE=&clickedButton=btnSearch&resetErrors=T"
		 * ;
		 * 
		 * if (language.equals("en")) {
		 * 
		 * url =
		 * "https://book.flypgs.com/Common/MemberRezvResults.jsp?activeLanguage=EN&TRIPTYPE=O&ADULT=1&CHILD=0&INFANT=&DEPPORT=##FROM_AIRPORT##&ARRPORT=##TO_AIRPORT##&DEP_DATE_OPTS=3&RET_DATE_OPTS=3&DEPDATE=##DEPARTURE_DATE##&RETDATE=&clickedButton=btnSearch&resetErrors=T"
		 * ; }
		 * 
		 * url = StringUtils.replace(url, "##FROM_AIRPORT##",
		 * fromAirportIATACode); url = StringUtils.replace(url,
		 * "##TO_AIRPORT##", toAirportIATACode); url = StringUtils.replace(url,
		 * "##DEPARTURE_DATE##", flightDateEncoded);
		 */
		String cevap = callBritishAirways(url);

		// burada reklam var, geç

		int eidParameterIndex = cevap
				.indexOf("replaceURL+= '?eId=';	replaceURL+= '");
		// 6 chars
		String sEid = cevap.substring(eidParameterIndex + 36,
				eidParameterIndex + 42);

		int timestampParameterIndex = cevap
				.indexOf("replaceURL+= '&timestamp=");
		// 10 chars
		String sTimestamp = cevap.substring(timestampParameterIndex + 25,
				timestampParameterIndex + 35);

		cevap = callBritishAirways("http://www.britishairways.com/travel/fx/public/en_gb?eId="
				+ sEid + "&timestamp=" + sTimestamp);

		// System.out.println(cevap);

		int checkedIndex = cevap.indexOf("checked=\"checked\"");

		String feeAdult = "";
		String feeInfant = "";
		String feeBaby = "";

		String departureDate = "";

		Flight tempFlight = null;

		try {

			Source src = new Source(new StringReader(cevap));

			ArrayList<?> a = new ArrayList<Object>(
					src.findAllElements(StartTag.TR));

			for (int i = 0; i < a.size(); i++) {

				Element e = (Element) a.get(i);

				String content = e.toString();

				/*
				 * <tr class="lightRow" style="text-align:left;"><td>18.05.2008
				 * Paz
				 * </td><td>SAW</td><td>BJV</td><td>H9176</td><td>21:45</td><td
				 * >22:50</td><td>K</td><td align="right">79 TRY </td><td><span
				 * style="font-size:10px;"><a class="bodyLink"
				 * href="javascript:openInfoDlg('FIRSAT')"
				 * title="KOW">FIRSAT</a></span></td><td><input type="radio"
				 * name="DEPSEGISN17/05/2008-1#-1" value="T#K#98219#KOW#53101"
				 * onclick="javascript:if(!callInternal) CSF(this);
				 * if(callInternal||ie) applyFilter( 79.00, 32.00, 69.00, 32.00,
				 * 10.00,0.00,
				 * 'TRY','DEP',100,'#','#','F',this,'T');findSums();"/></td></
				 * tr>
				 */

				if (content.indexOf("table") == -1
						&& content.indexOf("TRY") != -1) {

					ArrayList<?> a2 = new ArrayList<Object>(
							e.findAllElements(StartTag.TD));

					Element eDepartureDate = (Element) a2.get(0);
					departureDate = eDepartureDate.extractText();

					// departureDate is 18.05.2008

					departureDate = departureDate.substring(6, 10)
							+ departureDate.substring(3, 5)
							+ departureDate.substring(0, 2);

					if (departureDate.equals(flightDate)) {

						Element eAdultFee = (Element) a2.get(7);
						String sFeeAdult = eAdultFee.extractText();

						StringBuffer sb = new StringBuffer();

						char[] c = sFeeAdult.toCharArray();

						for (int j = 0; j < c.length; j++) {
							char d = c[j];
							if (Character.isDigit(d)) {
								sb.append(d);
							}
						}

						feeAdult = sb.toString();

						Element eFlightNo = (Element) a2.get(3);
						String flightNo = eFlightNo.extractText();

						Element eDepartureTime = (Element) a2.get(4);
						String departureTime = eDepartureTime.extractText();
						departureTime = departureTime.replaceAll(":", "");

						tempFlight = new Flight();

						tempFlight.setFeeAdult(feeAdult);
						tempFlight.setFeeInfant(feeInfant);
						tempFlight.setFeeBaby(feeBaby);

						tempFlight.setFromAirportCode(fromAirportCode);
						tempFlight
								.setFromAirportName(tempFromAirport.getName());
						tempFlight.setToAirportCode(toAirportCode);
						tempFlight.setToAirportName(tempToAirport.getName());
						tempFlight.setDepartureTime(departureTime);
						// tempFlight.setArrivalTime(arrivalTime);
						tempFlight.setFlightNo(flightNo);
						tempFlight.setFlightCode(flightNo);

						Airway ac = Airway.getAirwaysByCode("PGT");

						tempFlight.setAirwayCode(ac.getCode());
						tempFlight.setAirwayName(ac.getName());
						tempFlight.setAirwayUrl(ac.getUrl());
						tempFlight.setAirwayTelephone(ac.getCallCenter());
						tempFlight.setDepartureDate(flightDate);

						tempFlight.setBuyLink(url);

						britishAirwaysFlights.add(tempFlight);

					}

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return britishAirwaysFlights;

	}

	public static String extractByStartWord(String extractFrom,
			String startWith, String endWith) {

		String result = "";

		int indexStart = extractFrom.indexOf(startWith);

		if (indexStart != -1) {

			int indexEnd = extractFrom.indexOf(endWith,
					indexStart + startWith.length());

			if (indexEnd != -1) {

				result = extractFrom.substring(indexStart + startWith.length(),
						indexEnd);

			}

		}

		return result;

	}

	@SuppressWarnings({ "deprecation", "unused", "unchecked", "rawtypes" })
	public static ArrayList searchFlightKKK(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		ArrayList atlasFlights = new ArrayList();

		Airport tempFromAirport = airportRepository.findByCode(fromAirportCode);
		Airport tempToAirport = airportRepository.findByCode(toAirportCode);

		String fromAirportIATACode = tempFromAirport.getIataCode();
		String toAirportIATACode = tempToAirport.getIataCode();

		// 20080523 --> 23/05/2008 --> 23%2F05%2F2008

		String flightDateConverted = flightDate.substring(6, 8) + "/"
				+ flightDate.substring(4, 6) + "/" + flightDate.substring(0, 4);

		String flightDateEncoded = URLEncoder.encode(flightDateConverted);

		// first call to get a session and cookie
		/*
		 * callAtlasjet("https://online.atlasglb.com/AtlasOnline/availability.kk"
		 * , true, "http://www.atlasglb.com/");
		 */
		callAtlasjet("https://online.atlasglb.com/AtlasOnline/sessionref.jsp",
				false, "http://www.atlasglb.com/");

		String url = "https://online.atlasglb.com/AtlasOnline/availability.kk?from_input=&from=##FROM_AIRPORT##&to_input=&to=##TO_AIRPORT##&lang=TR&direction=1&depdate=##DEPARTURE_DATE##&retdate=08%2F06%2F2011&adult=1&yp=0&chd=0&inf=0&sc=0&stu=0&tsk=0&refid=REFERERDELETE&paramstatus=1&openjaw&bannerSize=200x200";

		// IST AYT 28%2F04%2F2009 şeklinde

		url = StringUtils.replace(url, "##FROM_AIRPORT##", fromAirportIATACode);
		url = StringUtils.replace(url, "##TO_AIRPORT##", toAirportIATACode);
		url = StringUtils.replace(url, "##DEPARTURE_DATE##", flightDateEncoded);

		String buyLinkUrl = url;

		// System.out.println(buyLinkUrl);

		String cevap = callAtlasjet(url, true, "https://www.atlasglb.com/tr");

		cevap = StringUtils.strip(cevap);

		Source source = new Source(cevap);

		List segments = source.findAllElements(Tag.TR);

		String trContent = "";
		Segment segment = null;
		Flight tempFlight = null;

		String tempDepartureTime = "";
		String tempFlightCode = "";

		for (Iterator iterator = segments.iterator(); iterator.hasNext();) {

			segment = (Segment) iterator.next();

			trContent = segment.getSourceText();

			int indexATLJET = trContent.indexOf("<tr class=\"darkLine\">");

			// biletler içeren tr buldun
			if (indexATLJET != -1) {

				Source sourceTD = new Source(trContent);

				List segmentsTD = sourceTD.findAllElements(Tag.TD);

				Segment segmentTD = null;
				String tdContent = null;

				String departure = "";
				String fee = "";
				String flightNo = "";

				Segment segmentDeparture = (Segment) segmentsTD.get(0);
				Segment segmentFlightNo = (Segment) segmentsTD.get(4);
				Segment segmentFee = (Segment) segmentsTD.get(5);

				String contentDeparture = segmentDeparture.getSourceText();
				String contentFee = segmentFee.getSourceText();
				String contentFlightNo = segmentFlightNo.getSourceText();

				departure = extractByStartWord(contentDeparture,
						"<td style=\";background:#e6e6e6;\">", "</td>").trim();
				departure = departure.replaceFirst(":", "");

				fee = extractByStartWord(contentFee, "data-flight=", "</label>")
						.trim();
				fee = extractByStartWord(fee, "\">", "TL").trim();

				flightNo = extractByStartWord(contentFlightNo,
						"<td  style=\";background:#e6e6e6;\"  title=\"\" >",
						"</td>").trim();

				if (flightNo.equals("")) {
					continue;
				}

				tempFlight = new Flight();

				if (fee.trim().equals("")) {
					continue;
				}

				double d = Double.parseDouble(fee);
				int iFeeFullAdult = (int) d;

				tempFlight.setFeeAdult("" + iFeeFullAdult);

				tempFlight.setFromAirportCode(fromAirportCode);
				tempFlight.setFromAirportName(tempFromAirport.getName());
				tempFlight.setToAirportCode(toAirportCode);
				tempFlight.setToAirportName(tempToAirport.getName());
				tempFlight.setDepartureTime(departure);
				// tempFlight.setArrivalTime(arrivalTime);
				tempFlight.setFlightNo(flightNo);
				tempFlight.setFlightCode(flightNo);

				Airway a = Airway.getAirwaysByCode("KKK");

				tempFlight.setAirwayCode(a.getCode());
				tempFlight.setAirwayName(a.getName());
				tempFlight.setAirwayUrl(a.getUrl());
				tempFlight.setAirwayTelephone(a.getCallCenter());

				tempFlight.setDepartureDate(flightDate);

				tempFlight.setBuyLink(buyLinkUrl);

				atlasFlights.add(tempFlight);

			}

		}

		return atlasFlights;

	}

	public ArrayList<Flight> searchFlightSXS2(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		ArrayList<Flight> skyFlights = new ArrayList<Flight>();

		// call order
		// get cookie --> search -----------------> show results
		// start.tk --> validateSchedulePage.tk --> availability.tk

		// first call to get a session and cookie
		callSKY("https://www.skyairlines.net/flight_search.sky");

		Airport tempFromAirport = airportRepository.findByCode(fromAirportCode);
		Airport tempToAirport = airportRepository.findByCode(toAirportCode);

		String fromAirportIATACode = tempFromAirport.getIataCode();
		String toAirportIATACode = tempToAirport.getIataCode();

		// 20080523 --> 23.05.2008 --> 23%2F05%2F2008

		String flightDateConverted = flightDate.substring(6, 8) + "."
				+ flightDate.substring(4, 6) + "." + flightDate.substring(0, 4);

		String url1 = "https://www.skyairlines.net/flight_search.sky?clickedButton=btnSearch&segmentType=1&activateLanguage=TR&activeLanguage=TR&IS_MAIN=true&TRIPTYPE=O&DEPPORT=##FROM_AIRPORT##&ARRPORT=##TO_AIRPORT##&DEPDATE=##DEPARTURE_DATE##&RETDATE=##DEPARTURE_DATE##&ADULT=1&CHILD=&INFANT=&CABINCLASS=Y";

		url1 = StringUtils.replace(url1, "##FROM_AIRPORT##",
				fromAirportIATACode);
		url1 = StringUtils.replace(url1, "##TO_AIRPORT##", toAirportIATACode);
		url1 = StringUtils.replace(url1, "##DEPARTURE_DATE##",
				flightDateConverted);

		callSKY(url1);

		String url2 = "https://skyacente.crane.aero/Common/MemberRezvResults.jsp?clickedButton=btnSearch&segmentType=1&activateLanguage=TR&activeLanguage=TR&IS_MAIN=true&TRIPTYPE=O&DEPPORT=##FROM_AIRPORT##&ARRPORT=##TO_AIRPORT##&DEPDATE=##DEPARTURE_DATE##&RETDATE=##DEPARTURE_DATE##&ADULT=1&CHILD=&INFANT=&CABINCLASS=Y";

		url2 = StringUtils.replace(url2, "##FROM_AIRPORT##",
				fromAirportIATACode);
		url2 = StringUtils.replace(url2, "##TO_AIRPORT##", toAirportIATACode);
		url2 = StringUtils.replace(url2, "##DEPARTURE_DATE##",
				flightDateConverted);

		String cevap = callSKY(url2);

		String feeAdult = "";
		String feeInfant = "";
		String feeBaby = "";

		Flight tempFlight = null;

		try {

			String tempFlightNo = "ZY";
			String tempFlightTime = "";

			Source src = new Source(new StringReader(cevap));

			ArrayList<?> a = new ArrayList<Object>(
					src.findAllElements(StartTag.TD));

			for (int i = 0; i < a.size(); i++) {

				Element e = (Element) a.get(i);

				String content = e.toString();

				// <input type="radio" name="DEPSEGISN05/02/2011-1#-1"
				// value="T#G#2308#GOW#395#F" time="16:20:00"
				// onclick="javascript:if(!callInternal) CSF(this);
				// if(callInternal||ie) applyFilterForIbe( 59.00, 20.00, 50.00,
				// 20.00, 15.00,
				// 0.00,'TRY','DEP',100,'1.0#0.6172839506172839#0.44712720769058795#','TRY#USD#EUR#','T',this,'T','TRY','Y');"/>

				if (content.indexOf("onclick=\"javascript:if(!callInternal)") != -1) {

					tempFlight = new Flight();

					// 35 tl hizmet/vergi ekle

					int indexFeeStart = content.indexOf("applyFilterForIbe(") + 18;
					int indexFeeEnd = content.indexOf(",", indexFeeStart);
					feeAdult = content.substring(indexFeeStart, indexFeeEnd);
					feeAdult = feeAdult.trim();
					feeAdult = StringUtils.replace(feeAdult, " ", "");
					feeAdult = StringUtils.replace(feeAdult, ".00", "");
					feeAdult = "" + (Integer.parseInt(feeAdult) + 35);
					tempFlight.setFeeAdult(feeAdult);

					int indexFlightTimeStart = content.indexOf("time=\"") + 6;
					int indexFlightTimeEnd = content.indexOf("\"",
							indexFlightTimeStart);
					tempFlightTime = content.substring(indexFlightTimeStart,
							indexFlightTimeEnd);
					tempFlightTime = tempFlightTime.trim();
					tempFlightTime = StringUtils.replaceOnce(tempFlightTime,
							":00", "");
					tempFlightTime = StringUtils.replace(tempFlightTime, ":",
							"");

					tempFlight.setFromAirportCode(fromAirportCode);
					tempFlight.setFromAirportName(tempFromAirport.getName());
					tempFlight.setToAirportCode(toAirportCode);
					tempFlight.setToAirportName(tempToAirport.getName());

					tempFlight.setFlightNo(tempFlightNo);
					tempFlight.setFlightCode(tempFlightNo);
					tempFlight.setDepartureTime(tempFlightTime);

					Airway ac = Airway.getAirwaysByCode("SXS");

					tempFlight.setAirwayCode(ac.getCode());
					tempFlight.setAirwayName(ac.getName());
					tempFlight.setAirwayUrl(ac.getUrl());
					tempFlight.setAirwayTelephone(ac.getCallCenter());

					tempFlight.setDepartureDate(flightDate);

					tempFlight.setBuyLink(url2);

					skyFlights.add(tempFlight);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return skyFlights;

	}

	public static String callOnur(String urlString, String referer,
			boolean setCookies) {

		StringBuffer sbURLContent = new StringBuffer("");

		// Create a trust manager that does not validate certificate
		// chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			URL url = new URL(urlString);
			URLConnection uc = url.openConnection();
			uc.setConnectTimeout(100000);
			uc.setReadTimeout(100000);
			uc.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");

			if (referer != null) {
				uc.setRequestProperty("Referer", referer);
			}

			for (Iterator iter = ONUR_COOKIES.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				uc.setRequestProperty("Cookie", element);
			}

			String headerName = null;

			ONUR_COOKIES = new ArrayList();

			if (setCookies) {

				for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

					if (headerName.equals("Set-Cookie")) {

						String cookie = uc.getHeaderField(i);
						ONUR_COOKIES.add(cookie);
					}
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));

			String buffer = "";

			while (buffer != null) {
				try {

					buffer = br.readLine();
					sbURLContent.append(buffer);

				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Onurair baglanamadi", e);
		}

		return sbURLContent.toString();

	}

	public static String callSunexpress(String urlString, String referer,
			boolean setCookies) {

		StringBuffer sbURLContent = new StringBuffer("");

		// Create a trust manager that does not validate certificate
		// chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			URL url = new URL(urlString);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", USER_AGENT);

			if (referer != null) {
				uc.setRequestProperty("Referer", referer);
			}

			for (Iterator iter = SUNEXPRESS_COOKIES.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				uc.setRequestProperty("Cookie", element);
			}

			String headerName = null;

			SUNEXPRESS_COOKIES = new ArrayList();

			if (setCookies) {

				for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

					if (headerName.equals("Set-Cookie")) {

						String cookie = uc.getHeaderField(i);
						SUNEXPRESS_COOKIES.add(cookie);
					}
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));

			String buffer = "";

			while (buffer != null) {
				try {

					buffer = br.readLine();
					sbURLContent.append(buffer);

				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sbURLContent.toString();

	}

	public static String callTHY(String urlString, String referer) {

		StringBuffer sbURLContent = new StringBuffer("");

		try {

			URL url = new URL(urlString);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", USER_AGENT);

			if (referer == null) {
				uc.setRequestProperty("Referer",
						"http://www.turkishairlines.com/tr-tr/");
			} else {
				uc.setRequestProperty("Referer", referer);
			}

			for (Iterator iter = THY_COOKIES.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				uc.setRequestProperty("Cookie", element);
			}

			String headerName = null;

			THY_COOKIES = new ArrayList();

			for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

				if (headerName.equals("Set-Cookie")) {

					String cookie = uc.getHeaderField(i);
					THY_COOKIES.add(cookie);
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));

			String buffer = "";

			while (buffer != null) {
				try {

					buffer = br.readLine();
					sbURLContent.append(buffer);

				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sbURLContent.toString();

	}

	public static String callAnadoluJet(String urlString) {

		StringBuffer sbURLContent = new StringBuffer("");

		try {

			URL url = new URL(urlString);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", USER_AGENT);

			uc.setRequestProperty("Referer",
					"http://www.anadolujet.com/aj-TR/index.aspx");

			uc.setRequestProperty("Cookie", ANADOLUJET_COOKIE);

			String headerName = null;

			for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

				if (headerName.equals("Set-Cookie")) {

					String cookie = uc.getHeaderField(i);
					ANADOLUJET_COOKIE = cookie;
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));

			String buffer = "";

			while (buffer != null) {
				try {

					buffer = br.readLine();
					sbURLContent.append(buffer);

				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sbURLContent.toString();

	}

	public static String callSKY(String urlString) {

		StringBuffer sbURLContent = new StringBuffer("");

		try {

			URL url = new URL(urlString);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", USER_AGENT);

			uc.setRequestProperty("Referer",
					"http://www.skyairlines.net/ic_hatlar/default.sky");

			uc.setRequestProperty("Cookie", SKY_COOKIE);

			String headerName = null;

			for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

				if (headerName.equals("Set-Cookie")) {

					String cookie = uc.getHeaderField(i);
					SKY_COOKIE = cookie;
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));

			String buffer = "";

			while (buffer != null) {
				try {

					buffer = br.readLine();
					sbURLContent.append(buffer);

				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sbURLContent.toString();

	}

	public static String callBritishAirways(String urlString) {

		StringBuffer sbURLContent = new StringBuffer("");

		try {

			URL url = new URL(urlString);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", USER_AGENT);

			uc.setRequestProperty("Referer",
					"http://www.britishairways.com/travel/fx/public/en_gb");

			uc.setRequestProperty("Cookie", BRITISH_AIRWAYS_COOKIE);

			String headerName = null;

			for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

				if (headerName.equals("Set-Cookie")) {

					String cookie = uc.getHeaderField(i);
					BRITISH_AIRWAYS_COOKIE = cookie;
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));

			String buffer = "";

			while (buffer != null) {
				try {

					buffer = br.readLine();
					sbURLContent.append(buffer);

				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sbURLContent.toString();

	}

	public static String callPegasus(String urlString, boolean logresponse) {

		StringBuffer sbURLContent = new StringBuffer("");

		try {

			// Create a trust manager that does not validate certificate
			// chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}
			} };

			// Install the all-trusting trust manager
			try {
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Now you can access an https URL without having the
			// certificate in the truststore
			try {

				URL url = new URL(urlString);
				URLConnection uc = url.openConnection();

				uc.setRequestProperty("User-Agent", USER_AGENT);

				uc.setRequestProperty("Referer", "http://www.flypgs.com/");

				uc.setRequestProperty("Cookie", PEGASUS_COOKIE);

				String headerName = null;

				for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

					if (headerName.equals("Set-Cookie")) {

						String cookie = uc.getHeaderField(i);
						// System.out.println(cookie);
						PEGASUS_COOKIE = cookie;
					}
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						uc.getInputStream()));

				String buffer = "";

				while (buffer != null) {
					try {

						buffer = br.readLine();
						sbURLContent.append(buffer);

					} catch (IOException ioe) {
						ioe.printStackTrace();
						break;
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		if (logresponse) {
			logResponse("PEGASUS" + System.currentTimeMillis() + ".html",
					sbURLContent.toString());
		}

		return sbURLContent.toString();

	}

	@Value("${log.flights}")
	private static String logFlights;

	@Value("${log.folder}")
	private static String logFolder;

	public static void logResponse(String filename, String logcontent) {

		if (logFlights != null && logFlights.trim().equals("true")) {

			try {

				String logFileName = logFolder + filename;

				File file = new File(logFileName);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(logcontent);
				bw.close();

				// System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static String callAtlasjet(String urlString, boolean logFile,
			String referer) {

		StringBuffer sbURLContent = new StringBuffer("");

		try {

			if (urlString.startsWith("https://")) { // https

				// Create a trust manager that does not validate certificate
				// chains
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(
							java.security.cert.X509Certificate[] certs,
							String authType) {
					}

					public void checkServerTrusted(
							java.security.cert.X509Certificate[] certs,
							String authType) {
					}
				} };

				// Install the all-trusting trust manager
				try {
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, trustAllCerts,
							new java.security.SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc
							.getSocketFactory());
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Now you can access an https URL without having the
				// certificate in the truststore
				try {

					URL url = new URL(urlString);
					URLConnection uc = url.openConnection();
					uc.setRequestProperty("User-Agent", USER_AGENT);

					uc.setRequestProperty("Referer", referer);

					uc.setRequestProperty("Cookie", ATLASJET_COOKIE

					);

					String headerName = null;

					for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

						if (headerName.equals("Set-Cookie")) {

							String cookie = uc.getHeaderField(i);
							// System.out.println(cookie);
							ATLASJET_COOKIE = cookie;
						}
					}

					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream()));

					String buffer = "";

					while (buffer != null) {
						try {

							buffer = br.readLine();
							sbURLContent.append(buffer);

						} catch (IOException ioe) {
							ioe.printStackTrace();
							break;
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

			} else { // http

				long startTime = System.currentTimeMillis();

				URL u = new URL(urlString);
				URLConnection uc = u.openConnection();
				uc.setConnectTimeout(10000);

				uc.setRequestProperty("User-Agent", USER_AGENT);

				uc.setRequestProperty("Referer", referer);

				uc.setRequestProperty("Cookie", ATLASJET_COOKIE);

				String inputLine;

				BufferedReader br = new BufferedReader(new InputStreamReader(
						uc.getInputStream()));

				String buffer = "";

				while (buffer != null) {
					try {

						buffer = br.readLine();
						sbURLContent.append(buffer);

					} catch (IOException ioe) {
						ioe.printStackTrace();
						break;
					}
				}

				String headerName = null;

				for (int i = 1; (headerName = uc.getHeaderFieldKey(i)) != null; i++) {

					if (headerName.equals("Set-Cookie")) {

						String cookie = uc.getHeaderField(i);
						// System.out.println(cookie);
						if (cookie != null && cookie.startsWith("JSESSI")) {
							ATLASJET_COOKIE = cookie;
						}
					}
				}

				long endTime = System.currentTimeMillis();

			}
		} catch (Exception e) {

			e.printStackTrace();

		}

		if (logFile) {

			logResponse("ATLAS" + System.currentTimeMillis() + ".html",
					sbURLContent.toString());
		}

		return sbURLContent.toString();

	}

	public static BigDecimal calculateTotalFee(String airwaysCompanyCode,
			int numberOfBabiesFlying, int numberOfInfantsFlying,
			int numberOfAdultsFlying) {

		BigDecimal bdTotalFee = new BigDecimal("0");

		return bdTotalFee;

	}

	public String getAirwayCode() {
		return airwaysCompanyCode;
	}

	public void setAirwayCode(String airwaysCompanyCode) {
		this.airwaysCompanyCode = airwaysCompanyCode;
	}

	public String getAirwayName() {
		return airwaysCompanyName;
	}

	public void setAirwayName(String airwaysCompanyName) {
		this.airwaysCompanyName = airwaysCompanyName;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String dateTime) {
		this.departureTime = dateTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFeeAdult() {
		return feeAdult;
	}

	public void setFeeAdult(String feeAdult) {
		this.feeAdult = feeAdult;
	}

	public String getFeeBaby() {
		return feeBaby;
	}

	public void setFeeBaby(String feeBaby) {
		this.feeBaby = feeBaby;
	}

	public String getFeeInfant() {
		return feeInfant;
	}

	public void setFeeInfant(String feeInfant) {
		this.feeInfant = feeInfant;
	}

	public String getFeeService() {
		return feeService;
	}

	public void setFeeService(String feeService) {
		this.feeService = feeService;
	}

	public String getFeeTax() {
		return feeTax;
	}

	public void setFeeTax(String feeTax) {
		this.feeTax = feeTax;
	}

	public String getFeeTotal() {
		return feeTotal;
	}

	public void setFeeTotal(String feeTotal) {
		this.feeTotal = feeTotal;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFromAirportCityCode() {
		return fromAirportCityCode;
	}

	public void setFromAirportCityCode(String fromAirportCityCode) {
		this.fromAirportCityCode = fromAirportCityCode;
	}

	public String getFromAirportCityName() {
		return fromAirportCityName;
	}

	public void setFromAirportCityName(String fromAirportCityName) {
		this.fromAirportCityName = fromAirportCityName;
	}

	public String getFromAirportCode() {
		return fromAirportCode;
	}

	public void setFromAirportCode(String fromAirportCode) {
		this.fromAirportCode = fromAirportCode;
	}

	public String getFromAirportName() {
		return fromAirportName;
	}

	public void setFromAirportName(String fromAirportName) {
		this.fromAirportName = fromAirportName;
	}

	public int getNumberOfAdultsFlying() {
		return numberOfAdultsFlying;
	}

	public void setNumberOfAdultsFlying(int numberOfAdultsFlying) {
		this.numberOfAdultsFlying = numberOfAdultsFlying;
	}

	public int getNumberOfBabiesFlying() {
		return numberOfBabiesFlying;
	}

	public void setNumberOfBabiesFlying(int numberOfBabiesFlying) {
		this.numberOfBabiesFlying = numberOfBabiesFlying;
	}

	public int getNumberOfInfantsFlying() {
		return numberOfInfantsFlying;
	}

	public void setNumberOfInfantsFlying(int numberOfInfantsFlying) {
		this.numberOfInfantsFlying = numberOfInfantsFlying;
	}

	public String getToAirportCityCode() {
		return toAirportCityCode;
	}

	public void setToAirportCityCode(String toAirportCityCode) {
		this.toAirportCityCode = toAirportCityCode;
	}

	public String getToAirportCityName() {
		return toAirportCityName;
	}

	public void setToAirportCityName(String toAirportCityName) {
		this.toAirportCityName = toAirportCityName;
	}

	public String getToAirportCode() {
		return toAirportCode;
	}

	public void setToAirportCode(String toAirportCode) {
		this.toAirportCode = toAirportCode;
	}

	public String getToAirportName() {
		return toAirportName;
	}

	public void setToAirportName(String toAirportName) {
		this.toAirportName = toAirportName;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getAirwayUrl() {
		return airwaysCompanyUrl;
	}

	public void setAirwayUrl(String airwaysCompanyUrl) {
		this.airwaysCompanyUrl = airwaysCompanyUrl;
	}

	public String getAirwayTelephone() {
		return airwaysCompanyTelephone;
	}

	public void setAirwayTelephone(String airwaysCompanyTelephone) {
		this.airwaysCompanyTelephone = airwaysCompanyTelephone;
	}

	public static ArrayList<Flight> searchFlightTHY(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		ArrayList<Flight> thyFlights = new ArrayList<Flight>();

		// call order
		// get cookie --> search -----------------> show results
		// start.tk --> validateSchedulePage.tk --> availability.tk

		// http://www.turkishairlines.com/tr-tr/?lang=tr&tripType=oneway&from=IST%2CIST&departureDate=06.11.2012&to=ADB%2CIZM&adult=1&child=0&infant=0&senior=0&student=0&typeOfClass=Y&flexPriceType=fareDriven

		// first call to get a session and cookie
		String cevap1 = callTHY("http://www.turkishairlines.com/tr-tr/", null);
		logResponse("THY1_" + System.currentTimeMillis() + " .html", cevap1);

		Airport tempFromAirport = airportRepository.findByCode(fromAirportCode);
		Airport tempToAirport = airportRepository.findByCode(toAirportCode);

		String fromAirportIATACode = tempFromAirport.getIataCode();
		String toAirportIATACode = tempToAirport.getIataCode();

		// 20080523 --> 23.05.2008 --> 23%2F05%2F2008

		String flightDateConverted = flightDate.substring(6, 8) + "."
				+ flightDate.substring(4, 6) + "." + flightDate.substring(0, 4);

		// example get url :
		// https://online.turkishairlines.com/internet-booking/validateSchedulePage.tk?lang=tr&from=IST,IST&to=ADB,IZM&departureDate=5.2.2013&returnDate=5.2.2013&tripType=oneway&adult=1&child=0&infant=0&senior=0&student=0&view=external&flexPriceType=fareDriven&nereden=%C4%B0stanbul%20Atat%C3%BCrk%20Havaliman%C4%B1&nereye=%C4%B0zmir%20Adnan%20Menderes%20Havaliman%C4%B1&typeOfClass=Y&GACookie=null&__utma=230785920.511803039.1359197246.1359411428.1359412250.3&__utmb=230785920.7.9.1359413184550&__utmc=230785920&__utmx=-&__utmz=230785920.1359197246.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)&__utmv=230785920.|1=DU=tr-tr=1^2=LG=visitor=1&__utmk=161607039
		String searchUrl = "https://online.turkishairlines.com/internet-booking/validateSchedulePage.tk?lang=tr&hdnNereden=&hdnNereye=&view=external&from=##FROM_AIRPORT##&to=##TO_AIRPORT##&departureDate=##DEPARTURE_DATE##&returnDate=##DEPARTURE_DATE##&flexPriceType=fareDriven&tripType=oneway&lsttypeOfClass=Y&typeOfClass=Y&adult=1&child=0&infant=0&senior=0&student=0&devam=Devam";

		if (language.equals("en")) {
			searchUrl = "https://online.turkishairlines.com/internet-booking/validateSchedulePage.tk?lang=en&hdnNereden=&hdnNereye=&view=external&from=##FROM_AIRPORT##&to=##TO_AIRPORT##&departureDate=##DEPARTURE_DATE##&returnDate=##DEPARTURE_DATE##&flexPriceType=fareDriven&tripType=oneway&lsttypeOfClass=Y&typeOfClass=Y&adult=1&child=0&infant=0&senior=0&student=0&devam=Devam";
		}

		searchUrl = StringUtils.replace(
				searchUrl,
				"##FROM_AIRPORT##",
				fromAirportIATACode + "%2C"
						+ Airway.convertthyCityCode(fromAirportCode));

		if (toAirportCode.equals("LTCH") || toAirportCode.equals("LTAQ")) {
			searchUrl = StringUtils.replace(searchUrl, "##TO_AIRPORT##",
					Airway.convertthyCityCode(toAirportCode) + "%2C"
							+ toAirportIATACode);
		} else {
			searchUrl = StringUtils.replace(
					searchUrl,
					"##TO_AIRPORT##",
					toAirportIATACode + "%2C"
							+ Airway.convertthyCityCode(toAirportCode));
		}

		searchUrl = StringUtils.replace(searchUrl, "##DEPARTURE_DATE##",
				flightDateConverted);

		String cevap2 = callTHY(searchUrl, null);

		logResponse("THY2_" + System.currentTimeMillis() + " .html", cevap2);

		String cevap3 = callTHY(
				"https://online.turkishairlines.com/internet-booking/availability.tk;"
						+ cevap2.substring(cevap2.lastIndexOf("jsessionid="),
								cevap2.lastIndexOf("</a>")), searchUrl);

		logResponse("THY3_" + System.currentTimeMillis() + " .html", cevap3);

		String feeAdult = "";

		Flight tempFlight = null;

		try {

			String tempFlightNo = "";
			String tempFlightTime = "";

			Source src = new Source(new StringReader(cevap3));

			List<Segment> segmentsTR = new ArrayList<Segment>(
					src.findAllElements(Tag.TR));

			for (int i = 0; i < segmentsTR.size(); i++) {

				Segment segmentTR = (Segment) segmentsTR.get(i);

				String contentTR = segmentTR.getSourceText();

				// we found a TR with flight number and at least one fee with
				// TRY
				if (contentTR.indexOf("data-cell-value=\"TK") != -1
						&& contentTR
								.indexOf("<span class='currency'>TRY</span>") != -1) {

				} else {
					// no flight, step to next table
					continue;

				}

				// we found a flight time info containing TD (if
				// departureTimeFound)

				tempFlightTime = extractByStartWord(contentTR,
						"<time class=\"hour\" datetime=\"\">", "</time>");

				tempFlightTime = tempFlightTime.replaceFirst(":", "").trim();

				tempFlightNo = extractByStartWord(contentTR,
						"<th class=\"flight-number\" data-cell-value=\"", ">");

				tempFlightNo = StringUtils.replace(tempFlightNo, "\"", "");

				feeAdult = extractByStartWord(contentTR,
						"<span class='price'> <span class='number'>", "</span>");
				String fullExpression = "<span class='number'>dolu";

				// cheapest is full so proceed to next fee
				if (feeAdult.indexOf("dolu") != -1) {
					feeAdult = extractByStartWord(
							contentTR.substring(contentTR
									.indexOf(fullExpression)
									+ fullExpression.length()),
							"<span class='price'> <span class='number'>",
							"</span>");
				}
				// middle class is also full, proceed business
				if (feeAdult.indexOf("dolu") != -1) {
					feeAdult = extractByStartWord(contentTR.substring(contentTR
							.lastIndexOf(fullExpression)),
							"<span class='price'> <span class='number'>",
							"</span>");
				}

				// all three classes are full, so no seats! proceed to next row
				if (feeAdult.indexOf("dolu") != -1) {

					continue;
				}

				tempFlight = new Flight();

				// 35 tl hizmet/vergi ekle

				feeAdult = "" + (Float.parseFloat(feeAdult + ".99") + 10);
				tempFlight.setFeeAdult(feeAdult);

				tempFlight.setFromAirportCode(fromAirportCode);
				tempFlight.setFromAirportName(tempFromAirport.getName());
				tempFlight.setToAirportCode(toAirportCode);
				tempFlight.setToAirportName(tempToAirport.getName());

				tempFlight.setFlightNo(tempFlightNo);
				tempFlight.setFlightCode(tempFlightNo);
				tempFlight.setDepartureTime(tempFlightTime);

				Airway a = Airway.getAirwaysByCode("THY");

				tempFlight.setAirwayCode(a.getCode());
				tempFlight.setAirwayName(a.getName());
				tempFlight.setAirwayUrl(a.getUrl());

				tempFlight.setDepartureDate(flightDate);

				tempFlight.setBuyLink(searchUrl);

				tempFlight.setAirwayTelephone(a.getCallCenter());

				thyFlights.add(tempFlight);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		thyFlights = new ArrayList<Flight>(
				new LinkedHashSet<Flight>(thyFlights));

		return thyFlights;

	}

	public static ArrayList<Flight> searchFlightAJA(String fromAirportCode,
			String toAirportCode, String flightDate, String language) {

		ArrayList<Flight> anadolujetFlights = new ArrayList<Flight>();

		// call order
		// get cookie --> search -----------------> show results
		// start.tk --> validateSchedulePage.tk --> availability.tk

		// http://www.turkishairlines.com/tr-tr/?lang=tr&tripType=oneway&from=IST%2CIST&departureDate=06.11.2012&to=ADB%2CIZM&adult=1&child=0&infant=0&senior=0&student=0&typeOfClass=Y&flexPriceType=fareDriven

		// first call to get a session and cookie
		String cevap1 = callTHY("http://www.anadolujet.com/aj-tr/", null);
		logResponse("ANADOLUJET1.html", cevap1);

		Airport tempFromAirport = airportRepository.findByCode(fromAirportCode);
		Airport tempToAirport = airportRepository.findByCode(toAirportCode);

		String fromAirportIATACode = tempFromAirport.getIataCode();
		String toAirportIATACode = tempToAirport.getIataCode();

		// 20080523 --> 23.05.2008 --> 23%2F05%2F2008

		String flightDateConverted = flightDate.substring(6, 8) + "."
				+ flightDate.substring(4, 6) + "." + flightDate.substring(0, 4);

		// example get url :
		// https://online.turkishairlines.com/internet-booking/validateSchedulePage.tk?lang=tr&from=SAW,IST&to=ADB,IZM&departureDate=18.11.2016&returnDate=25.11.2016&tripType=oneway&adult=1&child=0&infant=0&senior=0&jetgenc=0&teacher=0&officer=0&typeOfClass=*AJ&view=external&flexPriceType=fareDriven&airline=anadolujet&nereden=%C4%B0stanbul%20Sabiha%20G%C3%B6k%C3%A7en&nereye=%C4%B0zmir
		String searchUrl = "https://online.turkishairlines.com/internet-booking/validateSchedulePage.tk?lang=tr&from=##FROM_AIRPORT##&to=##TO_AIRPORT##&departureDate=##DEPARTURE_DATE##&returnDate=##DEPARTURE_DATE##&tripType=oneway&adult=1&child=0&infant=0&senior=0&jetgenc=0&teacher=0&officer=0&typeOfClass=*AJ&view=external&flexPriceType=fareDriven&airline=anadolujet&nereden=%C4%B0stanbul%20Sabiha%20G%C3%B6k%C3%A7en&nereye=%C4%B0zmir";

		if (language.equals("en")) {
			searchUrl = "https://online.turkishairlines.com/internet-booking/validateSchedulePage.tk?lang=en&from=##FROM_AIRPORT##&to=##TO_AIRPORT##&departureDate=##DEPARTURE_DATE##&returnDate=##DEPARTURE_DATE##&tripType=oneway&adult=1&child=0&infant=0&senior=0&jetgenc=0&teacher=0&officer=0&typeOfClass=*AJ&view=external&flexPriceType=fareDriven&airline=anadolujet&nereden=%C4%B0stanbul%20Sabiha%20G%C3%B6k%C3%A7en&nereye=%C4%B0zmir";
		}

		searchUrl = StringUtils.replace(searchUrl, "##FROM_AIRPORT##",

		Airway.convertAnadolujetCityCode(fromAirportIATACode));

		if (toAirportCode.equals("LTCH") || toAirportCode.equals("LTAQ")) {
			searchUrl = StringUtils.replace(searchUrl, "##TO_AIRPORT##",
					Airway.convertAnadolujetCityCode(toAirportCode) + "%2C"
							+ toAirportIATACode);
		} else {
			searchUrl = StringUtils.replace(searchUrl, "##TO_AIRPORT##",
					Airway.convertAnadolujetCityCode(toAirportIATACode));
		}

		searchUrl = StringUtils.replace(searchUrl, "##DEPARTURE_DATE##",
				flightDateConverted);

		String cevap2 = callTHY(searchUrl, null);

		logResponse("ANADOLUJET2.html", cevap2);

		String cevap3 = callTHY(
				"https://online.turkishairlines.com/internet-booking/availability.tk;"
						+ cevap2.substring(cevap2.lastIndexOf("jsessionid="),
								cevap2.lastIndexOf("</a>")), searchUrl);

		logResponse("ANADOLUJET3.html", cevap3);

		String feeAdult = "";

		Flight tempFlight = null;

		try {

			String tempFlightNo = "";
			String tempFlightTime = "";

			Source src = new Source(new StringReader(cevap3));

			List<Segment> segmentsTR = new ArrayList<Segment>(
					src.findAllElements(Tag.TR));

			for (int i = 0; i < segmentsTR.size(); i++) {

				Segment segmentTR = (Segment) segmentsTR.get(i);

				String contentTR = segmentTR.getSourceText();

				// we found a TR with flight number and at least one fee with
				// TRY
				if (contentTR.indexOf("data-cell-value=\"TK") != -1
						&& contentTR
								.indexOf("<span class='currency'>TRY</span>") != -1) {

					tempFlightNo = contentTR.substring(contentTR.indexOf("TK"),
							contentTR.indexOf("TK") + 6);

				} else {
					// no flight, step to next table
					continue;

				}

				// we found a flight time info containing TD (if
				// departureTimeFound)

				tempFlightTime = extractByStartWord(contentTR,
						"<time class=\"hour\" datetime=\"\">", "</time>");

				tempFlightTime = tempFlightTime.replaceFirst(":", "");

				tempFlightNo = extractByStartWord(contentTR,
						"<th class=\"flight-number\" data-cell-value=\"", ">");

				tempFlightNo = StringUtils.replace(tempFlightNo, "\"", "");

				feeAdult = extractByStartWord(contentTR,
						"<span class='price'> <span class='number'>", "</span>");
				String fullExpression = "<span class='number'>dolu";

				// cheapest is full so proceed to next fee
				if (feeAdult.indexOf("dolu") != -1) {
					feeAdult = extractByStartWord(
							contentTR.substring(contentTR
									.indexOf(fullExpression)
									+ fullExpression.length()),
							"<span class='price'> <span class='number'>",
							"</span>");
				}
				// middle class is also full, proceed business
				if (feeAdult.indexOf("dolu") != -1) {
					feeAdult = extractByStartWord(contentTR.substring(contentTR
							.lastIndexOf(fullExpression)),
							"<span class='price'> <span class='number'>",
							"</span>");
				}

				// all three classes are full, so no seats! proceed to next row
				if (feeAdult.indexOf("dolu") != -1) {

					continue;
				}

				tempFlight = new Flight();

				// 35 tl hizmet/vergi ekle

				feeAdult = "" + (Float.parseFloat(feeAdult + ".99") + 10);
				tempFlight.setFeeAdult(feeAdult);

				tempFlight.setFromAirportCode(fromAirportCode);
				tempFlight.setFromAirportName(tempFromAirport.getName());
				tempFlight.setToAirportCode(toAirportCode);
				tempFlight.setToAirportName(tempToAirport.getName());

				tempFlight.setFlightNo(tempFlightNo);
				tempFlight.setFlightCode(tempFlightNo);
				tempFlight.setDepartureTime(tempFlightTime);

				Airway a = Airway.getAirwaysByCode("AJA");

				tempFlight.setAirwayCode(a.getCode());
				tempFlight.setAirwayName(a.getName());
				tempFlight.setAirwayUrl(a.getUrl());
				tempFlight.setAirwayTelephone(a.getCallCenter());

				tempFlight.setDepartureDate(flightDate);

				tempFlight.setBuyLink(searchUrl);

				anadolujetFlights.add(tempFlight);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		anadolujetFlights = new ArrayList<Flight>(new LinkedHashSet<Flight>(
				anadolujetFlights));

		return anadolujetFlights;

	}

	@Override
	public int compareTo(Flight f) {

		float f1 = Float.parseFloat(getFeeAdult());
		float f2 = Float.parseFloat(f.getFeeAdult());

		return (int) (f1 - f2);

	}
}
