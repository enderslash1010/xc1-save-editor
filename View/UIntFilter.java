package View;

/**
 * A <code>TypeFilter</code> for unsigned integers
 * Used with a <code>JComponent</code> to only allow uinteger values of a specified size to be input
 * 
 * @author ender
 */
public class UIntFilter extends TypeFilter {
	
	private int numBytes;
	
	/**
	 * Default constructor, number of bytes is set to 4
	 */
	public UIntFilter() {
		this.numBytes = 4;
	}
	
	/**
	 * @param numBytes the maximum number of bytes allowed to be input
	 */
	public UIntFilter(int numBytes) {
		this.numBytes = numBytes;
	}
	
	@Override
	public boolean isType(String string) {
		if (string.equals("")) return true; // empty TextPane should be valid too
		try {
			int i = Integer.parseInt(string);
			// Int1: [0, 2^8 - 1]
			// Int2: [0, 2^16 - 1]
			// Int4: [0, 2^32 - 1]
			if (i < 0|| i > (Math.pow(2, this.numBytes*8) - 1)) return false;
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
