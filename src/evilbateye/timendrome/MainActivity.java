package evilbateye.timendrome;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends Activity {
			
	private CheckBox sameDigitsCB;
	private CheckBox enabledCB;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Get checkboxes.
		sameDigitsCB = (CheckBox) this.findViewById(R.id.SameDigitsCheckBox);
		enabledCB = (CheckBox) this.findViewById(R.id.enableCheckBox);
		
		//Get prefs.
		SharedPreferences prefs = this.getSharedPreferences(TimendromeUtils.PREFS_FILE_NAME, MODE_PRIVATE);
		editor = prefs.edit();
		
		//Set checkboxes.
		sameDigitsCB.setChecked(prefs.getBoolean(TimendromeUtils.PREF_SAMEDIGITS, false));
		enabledCB.setChecked(prefs.getBoolean(TimendromeUtils.PREF_ENABLED, true));
		
		Log.d(this.getClass().getSimpleName(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString());
		
		MediaPlayer mp = new MediaPlayer();
		
		try {
			mp.setVolume(1.0f, 1.0f);
			mp.setDataSource(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
			mp.setAudioStreamType(AudioManager.STREAM_ALARM);
			mp.setLooping(true);
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
			mp.release();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void saveButtonClicked(View v) {
		//Toast.makeText(v.getContext(), "cb:" + cb.isChecked(), Toast.LENGTH_LONG).show();
		
		//Save prefs.
		editor.putBoolean(TimendromeUtils.PREF_SAMEDIGITS, sameDigitsCB.isChecked());
		editor.putBoolean(TimendromeUtils.PREF_ENABLED, enabledCB.isChecked());
		editor.commit();
		
		if (enabledCB.isChecked()) {
			TimendromeUtils.setNextAlarm(this, TimendromeUtils.nextPreciseMinute());
		}
		
		//End activity.
		this.finish();
	}
	
	public void cancelButtonClicked(View v) {
		//Toast.makeText(v.getContext(), "THIS IS A TEST!", Toast.LENGTH_LONG).show();
		
		//End activity.
		this.finish();
	}

}
