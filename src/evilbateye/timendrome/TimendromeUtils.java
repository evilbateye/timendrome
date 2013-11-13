package evilbateye.timendrome;

import java.util.Calendar;
import java.util.GregorianCalendar;

public final class TimendromeUtils {
	public static final String EXTRA_MILLIS = "timeInMillis";
	public static final String PREFS_FILE_NAME = "timendrome.prefs";
	public static final String PREF_SAMEDIGITS = "same_digits";
	public static long nextPreciseMinute() {
		
		//Get calendar.
		GregorianCalendar gc = new GregorianCalendar();
		int milli = gc.get(Calendar.MILLISECOND);
		int sec = gc.get(Calendar.SECOND);
		
		//Set to trigger precisely at the next minute.
		gc.add(Calendar.MILLISECOND, -milli);
		gc.add(Calendar.SECOND, -sec);
		gc.add(Calendar.MINUTE, 1);
		
		return gc.getTimeInMillis();
	}
}
