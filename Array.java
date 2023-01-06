import java.util.HashMap;

public class Array extends Pointer {
	
	private Element[] entryOutline;
	private int entrySize; // Size of individual entry
	private int numEntries; // how many entries are in array
	
	public Array(int start, int end, Element[] entryOutline) {
		this.start = start;
		this.end = end;
		this.entryOutline = entryOutline;
		
		// Determine the size of each entry from entryOutline
		this.entrySize = 0;
		for (int i = 0; i < this.entryOutline.length; i++) {
			this.entrySize += entryOutline[i].size();
		}
		
		// Determine how many entries
		this.numEntries = this.size() / this.entrySize;
	}
	
	// returns a Data array for the nth index
	public Data[] get(int n) {
		Data[] result = new Data[entryOutline.length];
		int currAddr = this.start + (entrySize*n);
		
		for (int i = 0; i < result.length; i++) {
			int start = currAddr;
			int end = currAddr + entryOutline[i].size();
			DataType type = entryOutline[i].getType();
			result[i] = new Data(start, end, type);
			currAddr = end;
		}
		return result;
	}
	
	// returns a multidimensional Data array to describe entire array
	public Data[][] getArray() {
		Data[][] result = new Data[numEntries][entryOutline.length];
		for (int i = 0; i < numEntries; i++) {
			result[i] = get(i);
		}
		return result;
	}
	
	// returns HashMap that pairs names with data for nth index
	public HashMap<String, Pointer> getHashMap(int n) {
		HashMap<String, Pointer> result = new HashMap<String, Pointer>();
		Data[] array = get(n);
		for (int i = 0; i < array.length; i++) {
			result.put(entryOutline[i].getName(), array[i]);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked") // getHashMap(int n) will always return type HashMap<String, Pointer>
	public HashMap<String, Pointer>[] getHashMap() {
		HashMap<String, Pointer>[] result = new HashMap[this.numEntries];
		for (int i = 0; i < this.numEntries; i++) {
			result[i] = getHashMap(i);
		}
		return result;
	}
	
}
