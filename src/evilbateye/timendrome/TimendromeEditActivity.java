package evilbateye.timendrome;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class TimendromeEditActivity extends Activity {

	TimendromeRegexItem item = null;
	
	CheckBox enabled = null;
	EditText regex = null;
	EditText name = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timendrome_edit);
		
		this.setTitle(R.string.title_activity_timendrome_edit_add);
		
		enabled = (CheckBox) this.findViewById(R.id.edit_activity_isenabled);
		name = (EditText) this.findViewById(R.id.edit_activity_name);		
		regex = (EditText) this.findViewById(R.id.edit_activity_regex);
		
		item = (TimendromeRegexItem) this.getIntent().getSerializableExtra(TimendromeUtils.EXTRA_ITEM);
		if (item == null) return;
		
		this.setTitle(R.string.title_activity_timendrome_edit);
		
		enabled.setChecked(item.isEnabled());
		regex.setText(item.regex());
		name.setText(item.name());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timendrome_edit, menu);
		return true;
	}
	
	public boolean regexItemSubmitClicked(MenuItem v) {
		if (item == null) item = new TimendromeRegexItem();
		
		item.setEnabled(enabled.isChecked());
		item.setName(name.getText().toString());
		item.setRegex(regex.getText().toString());
				
		this.getIntent().putExtra(TimendromeUtils.EXTRA_ITEM, item);
		
		this.setResult(RESULT_OK, this.getIntent());
		
		this.finish();
		
		return true;
	}
}
