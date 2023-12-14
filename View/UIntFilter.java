package View;

/**
 * A <code>TypeFilter</code> for unsigned integers
 * Used with a <code>JComponent</code> to only allow uinteger values of a specified size to be input
 * 
 * @author ender
 */
public class UIntFilter extends TypeFilter {
	
	private int numBits;
	
	/**
	 * Default constructor, number of bytes is set to 4
	 */
	public UIntFilter() {
		this.numBits = 4;
	}
	
	/**
	 * @param numBytes the maximum number of bytes allowed to be input
	 */
	public UIntFilter(int numBits) {
		this.numBits = numBits;
	}
	
	@Override
	public boolean isType(String string) {
		if (string.equals("")) return true; // empty TextPane should be valid too
		try {
			int i = Integer.parseInt(string);
			if (i < 0|| i > (Math.pow(2, this.numBits) - 1)) return false;
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
