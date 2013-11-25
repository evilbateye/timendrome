package evilbateye.timendrome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimendromeAdapter extends BaseAdapter{
	
	private Context context = null;
	
	private List<TimendromeRegexItem> list = new ArrayList<TimendromeRegexItem>();
	
	public TimendromeAdapter(Context context) {
		this.context = context;
		list = TimendromeDB.selectAll();
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
		
		TextView nameTW = (TextView) convertView.findViewById(R.id.regex_item_name);
		nameTW.setText(item.name());
				
		TextView regexTW = (TextView) convertView.findViewById(R.id.regex_item_regex);
		regexTW.setText(item.regex());
		
		ImageButton edit = (ImageButton) convertView.findViewById(R.id.regex_item_button_edit);
		edit.setTag(pos);
		
		ImageButton delete = (ImageButton) convertView.findViewById(R.id.regex_item_button_delete);
		delete.setTag(pos);
		
		return convertView;
	}
	
	public void delete(int pos) {
		//TimendromeRegexItem item = (TimendromeRegexItem) this.getItem(pos);
		TimendromeDB.delete(list.get(pos));
		list.remove(pos);
	}
	
	public void update(int pos, TimendromeRegexItem item) {
		TimendromeDB.update(item);
		list.set(pos, item);
	}
	
	public void add(TimendromeRegexItem item) {
		TimendromeDB.insert(item);
		list.add(item);
	}
}
