package evilbateye.timendrome;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public final class TimendromeUtils {
	public static final String EXTRA_MILLIS = "timeInMillis";
	public static final String PREFS_FILE_NAME = "timendrome.prefs";
	public static final String PREF_SAMEDIGITS = "same_digits";
	public static final String PREF_ENABLED = "enabled";
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
	
	@SuppressLint("NewApi")
	public static void setNextAlarm(Context context, long millis) {
				
		//Get intent.
		Intent i = new Intent(context, TimendromeService.class);
		
		//Save trigger time for intent service (Problem with desync).
		i.putExtra(TimendromeUtils.EXTRA_MILLIS, millis);
		
		//Get pending intent.
		PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		
		//Get alarm manager.
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		//Call intent service.
		if (Build.VERSION.SDK_INT < 19) { 
			am.set(AlarmManager.RTC_WAKEUP, millis, pi);
		} else {
			am.setExact(AlarmManager.RTC_WAKEUP, millis, pi);
		}
	}
}
