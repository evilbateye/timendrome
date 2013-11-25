package evilbateye.timendrome;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class TimendromeRegexItem implements Parcelable {		
	
	private long id = -1;
	private boolean isEnabled = true;
	private String name = "";
	private String regex = "";
	
	public TimendromeRegexItem() {}
	public TimendromeRegexItem(Parcel in) {
		id = in.readLong();
		
		boolean bool[] = new boolean[1];
		in.readBooleanArray(bool);
		isEnabled = bool[0];
		
		name = in.readString();
		regex = in.readString();
	}
	
	public void setName(String name) { this.name = name; }
	public String name() { return this.name; }
	
	public void setRegex(String regex) { this.regex = regex; } 
	public String regex() { return this.regex; }
	
	public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
	public boolean isEnabled() { return this.isEnabled; }
	
	public void setId(long id) { this.id = id; }
	public long id() { return this.id; }

	@Override
	public int describeContents() {	return 0; }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		
		boolean[] bool = new boolean[1];
		bool[0] = isEnabled;
		dest.writeBooleanArray(bool);
		
		dest.writeString(name);
		dest.writeString(regex);
	}
	
	public static final Parcelable.Creator<TimendromeRegexItem> CREATOR = new Parcelable.Creator<TimendromeRegexItem>() {

		@Override
		public TimendromeRegexItem createFromParcel(Parcel source) {
			return new TimendromeRegexItem(source);
		}

		@Override
		public TimendromeRegexItem[] newArray(int size) {
			return new TimendromeRegexItem[size];
		}
	};
}
