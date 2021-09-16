package com.goldenrace.tickets.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {

	private Util() {
		super();
	}

	/**
	 * Converter a string date in Date object.
	 * 
	 * @param date String date to converter in format <b>yyyy-MM-dd</b>
	 * @return Wraper with the status.
	 */
	public static Wraper stringToDate(String date) {
		Wraper wraper = new Wraper();
		try {
			LocalDate localDate = LocalDate.parse(date);
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
			wraper.setValue(Date.from(zonedDateTime.toInstant()));
			wraper.setOk(true);
		} catch (Exception e) {
			wraper.setOk(false);
		}
		return wraper;
	}

	/**
	 * Converter Date object to string value in format <b>yyyy-MM-dd</b>.
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		if(date == null){
			return "";
		}
		LocalDateTime fec = LocalDateTime.ofInstant(new Date(date.getTime()).toInstant(), ZoneId.systemDefault());
		return fec.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

}
