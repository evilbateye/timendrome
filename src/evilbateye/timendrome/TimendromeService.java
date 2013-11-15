package evilbateye.timendrome;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
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
		
		
		SharedPreferences prefs = this.getSharedPreferences(TimendromeUtils.PREFS_FILE_NAME, MODE_PRIVATE);
		
		if (!prefs.getBoolean(TimendromeUtils.PREF_ENABLED, true)) return;
		
		if (prefs.getBoolean(TimendromeUtils.PREF_SAMEDIGITS, false)) {
			Intent i = new Intent(this, TimendromeAlarmActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplication().startActivity(i);
		}
				
		//Set new trigger time.
		long millis = intent.getExtras().getLong(TimendromeUtils.EXTRA_MILLIS) + 60 * 1000;
		TimendromeUtils.setNextAlarm(this, millis);
	}
}
