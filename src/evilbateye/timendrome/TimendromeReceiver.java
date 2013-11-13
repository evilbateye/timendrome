package evilbateye.timendrome;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class TimendromeReceiver extends BroadcastReceiver {
	public TimendromeReceiver() {
	}

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d(this.getClass().getSimpleName(), intent.getAction());
		
		if (intent.getAction() == "android.intent.action.BOOT_COMPLETED") {
			
			long millis = TimendromeUtils.nextPreciseMinute();
			
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
}
