import javax.swing.text.DocumentFilter;

public class StringFilter extends DocumentFilter {
	
	private int size;
	
	public StringFilter(int size) {
		this.size = size;
	}
	
	public boolean isString(String string) {
		if (string.length() > this.size) return false;
		return true;
	}
	
}
