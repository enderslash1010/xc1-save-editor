package View;

/**
 * A <code>TypeFilter</code> for integers
 * Used with a <code>JComponent</code> to only allow integer values of a specified size to be input
 * 
 * @author ender
 */
public class IntFilter extends TypeFilter {
	
	private int numBytes;
	
	/**
	 * Default constructor, number of bytes is set to 4
	 */
	public IntFilter() {
		this.numBytes = 4;
	}
	
	/**
	 * @param numBytes the maximum number of bytes allowed to be input
	 */
	public IntFilter(int numBytes) {
		this.numBytes = numBytes;
	}
	
	@Override
	public boolean isType(String string) {
		if (string.equals("") || string.equals("-")) return true; // empty TextPane should be valid too, as well as a negative sign
		try {
			int i = Integer.parseInt(string);
			// Int1: [-2^7, 2^7 - 1]
			// Int2: [-2^15, 2^15 - 1]
			// Int4: [-2^31, 2^31 - 1]
			if (i < Math.pow(-2, this.numBytes*8 - 1)|| i > (Math.pow(2, this.numBytes*8 - 1) - 1)) return false;
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
