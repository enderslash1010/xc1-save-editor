package View;
public class IntFilter extends TypeFilter {
	
	private int numBytes;
	
	public IntFilter() {
		this.numBytes = 4; // default is 4 byte int
	}
	
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
