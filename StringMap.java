
public class StringMap extends ValueMap<String> {

	public StringMap(String name, String saveFileValue) {
		super(name, saveFileValue);
	}

	@Override
	public String getParameterizedClass() {
		return "String";
	}

}
