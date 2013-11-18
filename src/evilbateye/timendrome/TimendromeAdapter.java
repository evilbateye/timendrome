package evilbateye.timendrome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
	public View getView(int pos, View view, ViewGroup viewGroup) {
		if (view == null) view = LayoutInflater.from(this.context).inflate(R.layout.timendrome_regex_item, null);
		
		TimendromeRegexItem item = (TimendromeRegexItem) this.getItem(pos);
		CheckBox isEnabledCB = (CheckBox) view.findViewById(R.id.regex_item_isenabled);
		isEnabledCB.setChecked(item.isEnabled());
		isEnabledCB.setTag(pos);
		
		TextView nameTW = (TextView) view.findViewById(R.id.regex_item_name);
		nameTW.setText(item.name());
		
		TextView regexTW = (TextView) view.findViewById(R.id.regex_item_regex);
		regexTW.setText(item.regex());
		
		return view;
	}

}
