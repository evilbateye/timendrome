package evilbateye.timendrome;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import evilbateye.timendrome.R;


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
