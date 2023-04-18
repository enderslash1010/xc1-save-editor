
public class IntegerMap extends ValueMap<Integer> {

	public IntegerMap(String name, Integer saveFileValue) {
		super(name, saveFileValue);
	}

	@Override
	public String getParameterizedClass() {
		return "Integer";
	}

}
