package evilbateye.timendrome;

import java.io.Serializable;

public class TimendromeRegexItem implements Serializable {		
	private static final long serialVersionUID = 7138720956322967520L;
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
