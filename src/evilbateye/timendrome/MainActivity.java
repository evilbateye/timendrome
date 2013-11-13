package evilbateye.timendrome;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends Activity {
			
	private CheckBox cb;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Get checkbox.
		cb = (CheckBox) this.findViewById(R.id.SameDigitsCheckBox);
		
		//Get prefs.
		SharedPreferences prefs = this.getSharedPreferences(TimendromeUtils.PREFS_FILE_NAME, MODE_PRIVATE);
		editor = prefs.edit();
		cb.setChecked(prefs.getBoolean(TimendromeUtils.PREF_SAMEDIGITS, false));
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
		boolean isSameDigits = cb.isChecked();
		editor.putBoolean(TimendromeUtils.PREF_SAMEDIGITS, isSameDigits);
		editor.commit();
		
		//End activity.
		this.finish();
	}
	
	public void cancelButtonClicked(View v) {
		//Toast.makeText(v.getContext(), "THIS IS A TEST!", Toast.LENGTH_LONG).show();
		
		//End activity.
		this.finish();
	}

}
