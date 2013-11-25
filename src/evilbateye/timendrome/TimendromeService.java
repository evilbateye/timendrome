package evilbateye.timendrome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class TimendromeService extends IntentService {
	
	private List<TimendromeRegexItem> list = new ArrayList<TimendromeRegexItem>();
		
	public TimendromeService() {
		super("evilbateye.timendrome.TimendromeService");
	}

	@SuppressLint("NewApi")
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(this.getClass().getSimpleName(), "Service starting.");
		
		SharedPreferences prefs = this.getSharedPreferences(TimendromeUtils.PREFS_FILE_NAME, MODE_PRIVATE);
		
		if (!prefs.getBoolean(TimendromeUtils.PREF_ENABLED, true)) return;
		
		list = new TimendromeDB(this).selectAll();
		
		long millis = intent.getExtras().getLong(TimendromeUtils.EXTRA_MILLIS) ;
		
		String time = TimendromeUtils.timeString(millis);
		
		Log.d("service time", time);
				
		for (int i = 0; i < list.size();) {
			if (!time.matches(list.get(i).regex())) {
				list.remove(i);
			} else {
				Log.d("service matcher", list.get(i).regex());
				i++;
			}
		}
		
		Log.d("service list size", String.valueOf(list.size()));
		
		if (list.size() > 0) {
			Intent i = new Intent(this, TimendromeAlarmActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putParcelableArrayListExtra(TimendromeUtils.EXTRA_ITEM_ARRAY, (ArrayList<TimendromeRegexItem>) list);
			getApplication().startActivity(i);
		}
		
		TimendromeUtils.setNextAlarm(this, millis + 60 * 1000);
	}
}
