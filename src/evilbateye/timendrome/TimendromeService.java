package evilbateye.timendrome;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

public class TimendromeService extends IntentService {

	public TimendromeService(String name) {
		super(name);
	}
	
	public TimendromeService() {
		super("evilbateye.timendrome.TimendromeService");
	}

	@SuppressLint("NewApi")
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(this.getClass().getSimpleName(), "Service starting.");		
		
		//Get prefs.
		SharedPreferences prefs = this.getSharedPreferences(TimendromeUtils.PREFS_FILE_NAME, MODE_PRIVATE);
		boolean isSameDigits = prefs.getBoolean(TimendromeUtils.PREF_SAMEDIGITS, false);
		Log.d(this.getClass().getSimpleName(), String.valueOf(isSameDigits));
		
		//Set new trigger time.
		long millis = intent.getExtras().getLong(TimendromeUtils.EXTRA_MILLIS) + 60 * 1000;
		
		//Set new intent.
		Intent i = new Intent(this, TimendromeService.class);
		
		//Save new trigger time for the next intent service.
		i.putExtra(TimendromeUtils.EXTRA_MILLIS, millis);
		
		//Set new pending intent.
		PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		
		//Get alarm manager.
		AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		
		//Call this service again.
		if (Build.VERSION.SDK_INT < 19) { 
			am.set(AlarmManager.RTC_WAKEUP, millis, pi);
		} else {
			am.setExact(AlarmManager.RTC_WAKEUP, millis, pi);
		}
	}
}
