package evilbateye.timendrome;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimendromeAdapter extends BaseAdapter {
	
	private ListActivity context = null;
	private TimendromeDB db = null;
	private List<TimendromeRegexItem> list;
	
	public TimendromeAdapter(ListActivity context) {
		this.context = context;
				
		db = new TimendromeDB(context);
		list = db.selectAll();
	}
	
	public TimendromeAdapter(ListActivity context, List<TimendromeRegexItem> list) {
		this.context = context;
		this.list = list;
		db = new TimendromeDB(context);
	}
	
	@Override
	public int getCount() {	return list.size();	}

	@Override
	public Object getItem(int pos) { return list.get(pos); }

	@Override
	public long getItemId(int pos) { return pos; }

	@Override
	public View getView(int pos, View convertView, ViewGroup viewGroup) {
		if (convertView == null) convertView = LayoutInflater.from(this.context).inflate(R.layout.timendrome_regex_item, null);
		
		TimendromeRegexItem item = (TimendromeRegexItem) this.getItem(pos);
		CheckBox isEnabledCB = (CheckBox) convertView.findViewById(R.id.regex_item_isenabled);
		isEnabledCB.setChecked(item.isEnabled());
		isEnabledCB.setTag(pos);
		isEnabledCB.setOnClickListener(listener);
		
		TextView nameTW = (TextView) convertView.findViewById(R.id.regex_item_name);
		nameTW.setText(item.name());
				
		TextView regexTW = (TextView) convertView.findViewById(R.id.regex_item_regex);
		regexTW.setText(item.regex());
		
		ImageButton edit = (ImageButton) convertView.findViewById(R.id.regex_item_button_edit);
		edit.setTag(pos);
		edit.setOnClickListener(listener);
		
		ImageButton delete = (ImageButton) convertView.findViewById(R.id.regex_item_button_delete);
		delete.setTag(pos);
		delete.setOnClickListener(listener);
		
		return convertView;
	}
	
	public void delete(int pos) {
		//TimendromeRegexItem item = (TimendromeRegexItem) this.getItem(pos);
		db.delete(list.get(pos));
		list.remove(pos);
	}
	
	public void update(int pos, TimendromeRegexItem item) {
		db.update(item);
		list.set(pos, item);
	}
	
	public void add(TimendromeRegexItem item) {
		db.insert(item);
		list.add(item);
	}
	
	public void regexItemEnabledClicked(View v) {
		CheckBox cb = (CheckBox) v;
		int pos = (Integer) cb.getTag();
		
		TimendromeRegexItem item = (TimendromeRegexItem) this.getItem(pos);
		item.setEnabled(cb.isChecked());
		
		this.update(pos, item);
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int pos = (Integer) v.getTag();
			
			switch (v.getId()) {
				case R.id.regex_item_isenabled: {
									
					CheckBox cb = (CheckBox) v;					
					
					TimendromeRegexItem item = (TimendromeRegexItem) getItem(pos);
					
					item.setEnabled(cb.isChecked());
					
					update(pos, item);
					
					break;
				}
				
				case R.id.regex_item_button_edit: {
					
					TimendromeRegexItem item = (TimendromeRegexItem) getItem(pos);
					
					Intent i = new Intent(context, TimendromeEditActivity.class);
					i.putExtra(TimendromeUtils.EXTRA_ITEM, item);
					i.putExtra(TimendromeUtils.EXTRA_ITEM_POS, pos);
					
					context.startActivityForResult(i, TimendromeUtils.REQUEST_CODE_EDIT);
					
					break;
				}
				
				case R.id.regex_item_button_delete : {
					
					delete((Integer) v.getTag());
					notifyDataSetChanged();
					
					break;
				}
			}
		}
	};
}
