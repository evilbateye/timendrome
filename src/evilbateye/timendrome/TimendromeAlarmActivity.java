package evilbateye.timendrome;

import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;

public class TimendromeAlarmActivity extends ListActivity {
	
	private MediaPlayer mp;
	
	private List<TimendromeRegexItem> list;
	
	private TimendromeAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timendrome_alarm);
		
		list = this.getIntent().getParcelableArrayListExtra(TimendromeUtils.EXTRA_ITEM_ARRAY);
		
		Log.d("alarmActivity list size", String.valueOf(list.size()));
		
		mp = MediaPlayer.create(this, R.raw.sillyexplosion);
		if (mp != null) { 
			try {
				mp.setVolume(1.0f, 1.0f);
				//mp.setDataSource(getApplicationContext(), R.raw.happycat);
				//mp.setAudioStreamType(AudioManager.STREAM_ALARM);
				mp.setLooping(true);
				//mp.prepare();
				mp.start();
			} catch (Exception e) {
				e.printStackTrace();
				mp.release();
			}
		}
		
		adapter = new TimendromeAdapter(this, list);
		this.setListAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timendrome_alarm, menu);
		return true;
	}
	
	public boolean OkButtonClicked(MenuItem item) {
		mp.stop();
		Intent i = new Intent(TimendromeUtils.ACTION_RELOAD);
		LocalBroadcastManager.getInstance(this).sendBroadcast(i);
		this.finish();
		return true;
	}	

	@Override
	public void onDestroy() {
		mp.release();
		super.onDestroy();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
			case TimendromeUtils.REQUEST_CODE_EDIT: {
				
				if (resultCode == RESULT_OK) {
					TimendromeRegexItem item = (TimendromeRegexItem) data.getParcelableExtra(TimendromeUtils.EXTRA_ITEM);
					int pos = data.getIntExtra(TimendromeUtils.EXTRA_ITEM_POS, -1);
					if (pos > -1) {
						adapter.update(pos, item);
					}
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
