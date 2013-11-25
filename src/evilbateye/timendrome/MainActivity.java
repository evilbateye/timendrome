package evilbateye.timendrome;

import java.util.List;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
		
		//TEST
		TimendromeRegexItem item = new TimendromeRegexItem();
		
		item.setName("All");
		item.setRegex("(\\d)\\1*");
		TimendromeDB.insert(item);
		
		item.setName("Palindromes");
		item.setRegex("(\\d)(\\d)\\1\\2");
		TimendromeDB.insert(item);
		
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
	
	/* Defined in timendrome_regex_item.xml as android:onClick function */
	public void regexItemEnabledClicked(View v) {
		CheckBox cb = (CheckBox) v;
		TimendromeRegexItem item = (TimendromeRegexItem) adapter.getItem((Integer) cb.getTag());
		item.setEnabled(cb.isChecked());
		TimendromeDB.update(item);
	}
	
	public void regexItemEditClicked(View v) {
		int pos = (Integer) v.getTag();
		
		TimendromeRegexItem item = (TimendromeRegexItem) adapter.getItem(pos);
		
		Intent i = new Intent(this, TimendromeEditActivity.class);
		i.putExtra(TimendromeUtils.EXTRA_ITEM, item);
		i.putExtra(TimendromeUtils.EXTRA_ITEM_POS, pos);
		
		this.startActivityForResult(i, TimendromeUtils.REQUEST_CODE_EDIT);
	}
	
	public boolean regexItemAddClicked(MenuItem v) {
				
		Intent i = new Intent(this, TimendromeEditActivity.class);
				
		this.startActivityForResult(i, TimendromeUtils.REQUEST_CODE_ADD);
		
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("result", String.valueOf(resultCode));
		Log.d("request", String.valueOf(requestCode));
		switch (requestCode) {
			case TimendromeUtils.REQUEST_CODE_EDIT: {
				
				if (resultCode == RESULT_OK) {
					TimendromeRegexItem item = (TimendromeRegexItem) data.getSerializableExtra(TimendromeUtils.EXTRA_ITEM);
					int pos = data.getIntExtra(TimendromeUtils.EXTRA_ITEM_POS, -1);
					adapter.update(pos, item);
					adapter.notifyDataSetChanged();
				}
				break;
			}
			
			case TimendromeUtils.REQUEST_CODE_ADD: {
				
				if (resultCode == RESULT_OK) {
					TimendromeRegexItem item = (TimendromeRegexItem) data.getSerializableExtra(TimendromeUtils.EXTRA_ITEM);
					adapter.add(item);
					adapter.notifyDataSetChanged();
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void regexItemDeleteClicked(View v) {
		adapter.delete((Integer) v.getTag());
		adapter.notifyDataSetChanged();
	}
		
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Log.d("pos:", String.valueOf(position));
		Log.d("row id:", String.valueOf(id));
		
		super.onListItemClick(l, v, position, id);
	}
}
