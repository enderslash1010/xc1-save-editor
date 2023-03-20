import java.util.HashMap;

// Child of Pair, associates String name with a Data object
public class DataPair extends Pair<Data> {

	public DataPair(String name, Data data) {
		super(name, data);
	}
	
	// Returns a HashMap of a DataPair array, for easy lookup of names
	public static HashMap<String, Data> toHashMap(DataPair[] arr) {
		HashMap<String, Data> result = new HashMap<String, Data>();
		for (int i = 0; i < arr.length; i++) {
			result.put(arr[i].getName(), arr[i].getData());
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		DataPair o = (DataPair) obj;
		return (this.getName().equals(o.getName()) && this.getData().equals(o.getData()));
	}

}