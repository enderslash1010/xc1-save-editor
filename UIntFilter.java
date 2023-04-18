public class UIntFilter extends TypeFilter {
	
	private int numBytes;
	
	public UIntFilter() {
		this.numBytes = 4; // default is 4 byte int
	}
	
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
