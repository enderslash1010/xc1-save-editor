import java.util.HashMap;

// Child of Pair, associates String name with a value
public class ValuePair extends Pair<Object> {

	public ValuePair(String name, Object data) {
		super(name, data);
	}
	
	// Returns a HashMap of a ValuePair array, for easy lookup of names
	public static HashMap<String, Object> toHashMap(ValuePair[] arr) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < arr.length; i++) {
			result.put(arr[i].getName(), arr[i].getData());
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		ValuePair o = (ValuePair) obj;
		return (this.getName().equals(o.getName()) && this.getData().equals(o.getData()));
	}

}