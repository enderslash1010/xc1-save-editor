
public class FloatFilter extends TypeFilter {

	@Override
	public boolean isType(String string) {
		if (string.equals("") || string.equals("-")) return true; // empty TextPane should be valid too, as well as a negative sign
		try {
			Float.parseFloat(string);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

}
