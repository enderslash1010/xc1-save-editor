package View;
import javax.swing.text.DocumentFilter;

/**
 * A <code>DocumentFilter</code> for Strings
 * Used with a <code>JComponent</code> to only allow String values of a specified size to be input
 * 
 * @author ender
 */
public class StringFilter extends DocumentFilter {
	
	private int size;
	
	public StringFilter(int size) {
		this.size = size;
	}
	
	/**
	 * Determines if the contents of the <code>JComponent</code> is a String with a size <= this.size
	 * @param string the text in the <code>JComponent</code>
	 * @return true if the text is a String of length <= size; otherwise, false
	 */
	public boolean isString(String string) {
		if (string.length() > this.size) return false;
		return true;
	}
	
}
