package evilbateye.timendrome;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;

public class TimendromeAlarmActivity extends Activity {
	private MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timendrome_alarm);
		
		
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
	}
		
	public void OkButtonClicked(View v) {
		mp.stop();
		this.finish();
	}
	

	@Override
	public void onDestroy() {
		mp.release();
		super.onDestroy();
	}

}
