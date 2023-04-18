
public abstract class ValueMap<T> {
	private String name;
	private T saveFileValue;
	
	public ValueMap(String name, T saveFileValue) {
		this.name = name;
		this.saveFileValue = saveFileValue;
	}
	
	public String getName() { return this.name; }
	public T getSaveFileValue() { return this.saveFileValue; }
	public abstract String getParameterizedClass();
}
