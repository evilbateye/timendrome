package evilbateye.timendrome;

public class TimendromeRegexItem {
	
	private long id = -1;
	private boolean isEnabled = true;
	private String name = "";
	private String regex = "";
	
	public TimendromeRegexItem() {}
	
	public void setName(String name) { this.name = name; }
	public String name() { return this.name; }
	
	public void setRegex(String regex) { this.regex = regex; } 
	public String regex() { return this.regex; }
	
	public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
	public boolean isEnabled() { return this.isEnabled; }
	
	public void setId(long id) { this.id = id; }
	public long id() { return this.id; }
}
