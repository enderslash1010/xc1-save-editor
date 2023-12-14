package View;

/**
 * A <code>TypeFilter</code> for integers
 * Used with a <code>JComponent</code> to only allow integer values of a specified size to be input
 * 
 * @author ender
 */
public class IntFilter extends TypeFilter {
	
	private int numBits;
	
	/**
	 * Default constructor
	 * Number of bits is set to 32 (4 bytes)
	 */
	public IntFilter() {
		this.numBits = 32;
	}
	
	/**
	 * @param numBytes the maximum number of bits allowed to be input
	 */
	public IntFilter(int numBits) {
		this.numBits = numBits;
	}
	
	@Override
	public boolean isType(String string) {
		if (string.equals("") || string.equals("-")) return true; // empty TextPane should be valid too, as well as a negative sign
		try {
			int i = Integer.parseInt(string);
			if (i < Math.pow(-2, this.numBits - 1)|| i > (Math.pow(2, this.numBits - 1) - 1)) return false;
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
