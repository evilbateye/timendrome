package evilbateye.timendrome;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import evilbateye.timendrome.R;


public class MainActivity extends ListActivity {
	
	private MenuItem actionEnable;
	
	private SharedPreferences prefs;
	
	private SharedPreferences.Editor editor;
	
	private TimendromeAdapter adapter;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
					
		prefs = this.getSharedPreferences(TimendromeUtils.PREFS_FILE_NAME, MODE_PRIVATE);
		editor = prefs.edit();
		
		TimendromeDB.openDB(this);
		
		adapter = new TimendromeAdapter(this);
		this.setListAdapter(adapter);
	}
	
	@Override
	public void onDestroy() {
		TimendromeDB.closeDB();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		actionEnable = menu.findItem(R.id.action_enable);
		actionEnable.setChecked(prefs.getBoolean(TimendromeUtils.PREF_ENABLED, true));
		
		return true;
	}
	
	public void onActionSettingsClicked(MenuItem item) {
		Log.d("MainActivity", "Settings clicked.");
	}
	
	public void onActionEnableClicked(MenuItem item) {
		item.setChecked(!item.isChecked());
				
		editor.putBoolean(TimendromeUtils.PREF_ENABLED, item.isChecked());
		editor.commit();
		
		if (item.isChecked()) TimendromeUtils.setNextAlarm(this, TimendromeUtils.nextPreciseMinute());
	}
	
	public void isEnabledCBClicked(View v) {
		CheckBox cb = (CheckBox) v;
		TimendromeRegexItem item = (TimendromeRegexItem) adapter.getItem((Integer) cb.getTag());
		item.setEnabled(cb.isChecked());
		TimendromeDB.update(item);
	}	
}
