package com.xinnet.xa.core.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class DateUtil extends DateUtils {

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_HOUR_PATTERN = "yyyyMMddHH";

	public static final FastDateFormat DATE_HOUR_FORMAT = FastDateFormat
			.getInstance(DATE_HOUR_PATTERN);
	public static final FastDateFormat DATE_TIME_FORMAT = FastDateFormat
			.getInstance(DATE_TIME_PATTERN);
	public static final String dateRegex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|"
			+ "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|"
			+ "[2468][048]|[3579][26])00))-02-29)";

	public static boolean isDate(String date) {
		Pattern p = Pattern.compile(dateRegex);
		Matcher m = p.matcher(date);
		return m.matches();
	}

	public static String formatDate(Date date) {
		return DateFormatUtils.ISO_DATE_FORMAT.format(date);
	}

	public static String formatDateTime(Date date) {
		return DATE_TIME_FORMAT.format(date);
	}

	public static String formatDateHour(Date date) {
		return DATE_HOUR_FORMAT.format(date);
	}

	public static Date parseDate(String source) throws ParseException {
		return parseDate(source,
				new String[] { DateFormatUtils.ISO_DATE_FORMAT.getPattern() });
	}

	//
	public static Date parseDateTime(String source) throws ParseException {
		return parseDate(source, new String[] { DATE_TIME_PATTERN });
	}

	public static Date parseDateDateHour(String source) throws ParseException {
		return parseDate(source, new String[] { DATE_HOUR_PATTERN });
	}

	public static long timeDifferenceDay(Date date1, Date date2) {
		return timeDifference(date1, date2, Calendar.DATE);
	}

	public static long timeDifferenceHour(Date date1, Date date2) {
		return timeDifference(date1, date2, Calendar.HOUR);
	}

	public static long timeDifferenceMinute(Date date1, Date date2) {
		return timeDifference(date1, date2, Calendar.MINUTE);
	}

	public static long timeDifferenceSecond(Date date1, Date date2) {
		return timeDifference(date1, date2, Calendar.SECOND);
	}

	private static long timeDifference(Date date1, Date date2, int type) {
		long diff = date1.getTime() - date2.getTime();
		switch (type) {
		case Calendar.SECOND:
			diff = diff / MILLIS_PER_SECOND;
			break;
		case Calendar.MINUTE:
			diff = diff / MILLIS_PER_MINUTE;
			break;
		case Calendar.HOUR:
			diff = diff / MILLIS_PER_HOUR;
			break;
		default:
			diff = diff / MILLIS_PER_DAY;
			break;
		}
		return diff;
	}

	public static String addDay(Date date, int amount) {
		return formatDate(addDays(date, amount));
	}

	public static String addHoursString(Date date, int amount) {
		return formatDateHour(addHours(date, amount));
	}

	public static String addHoursString(String hour, int amount)
			throws ParseException {
		Date date = parseDateDateHour(hour);
		return formatDateHour(addHours(date, amount));
	}

	public static void main(String[] args) throws ParseException {
		String s = "2014121200";
		String e = "2014121223";
		System.out.println(timeDifferenceHour(parseDateDateHour(e), parseDateDateHour(s)));
	}

}
