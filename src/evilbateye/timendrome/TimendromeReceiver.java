package evilbateye.timendrome;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimendromeReceiver extends BroadcastReceiver {
	public TimendromeReceiver() {
	}

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d(this.getClass().getSimpleName(), intent.getAction());
		
		if (intent.getAction() != "android.intent.action.BOOT_COMPLETED") return;
		
		TimendromeUtils.setNextAlarm(context, TimendromeUtils.nextPreciseMinute());
	}
}
