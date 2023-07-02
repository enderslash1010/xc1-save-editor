package Model;
/*  TODO: javadocs
 *   class Array
 * 
 *                Element
 *  |----------------------------------------|
 *                                        Entry
 *  |-----------------------------------------------------------------------------------------|
 * 
   [[(mineCooldown, Int at [0x240f0, 0x240f2)), (numHarvests, Int at [0x240f2, 0x240f3)), ...,], 
    [(mineCooldown, Int at [0x240f6, 0x240f8)), (numHarvests, Int at [0x240f8, 0x240f9)), ...,],
    [(mineCooldown, Int at [0x240fc, 0x240fe)), (numHarvests, Int at [0x240fe, 0x240ff)), ...,]]
    
    This class stores the location/description of array structured data in the save file, 
    but doesn't store the actual values (SaveFile class can retrieve these values from an Array object)
 */
public class Array extends Pointer {
	
	private Element[] entryOutline;
	private int entrySize; // Size of individual entry in bytes (use getEntryOutlineLength() for number of elements in one entry)
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
	
	public int getNumEntries() { return this.numEntries; }
	public int getRowLength() { return this.numEntries; } // alias for numEntries
	
	public int getEntryOutlineLength() { return this.entryOutline.length; }
	public int getColLength() { return this.entryOutline.length; } // alias for entryOutline length
	
	/*
	 *  returns a String array of all internal column names
	 */
	public String[] getColNames() {
		String[] result = new String[entryOutline.length];
		for (int i = 0; i < entryOutline.length; i++) {
			result[i] = entryOutline[i].getName();
		}
		return result;
	}
	
	private int getColElementIndex(String name) { // returns index of Element that has String name, or -1 if there is none
		for (int i = 0; i < entryOutline.length; i++) {
			if (entryOutline[i].getName().equals(name)) return i;
		}
		return -1;
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
	
	// returns a Data object for the nth index and corresponding column name (colName)
	public Data get(int n, String colName) {
		int start = this.start + (entrySize*n); // start of index
		int end = start + entryOutline[0].size();
		
		// determine which index in entryOutline corresponds with colName
		int colIndex = getColElementIndex(colName);
		
		// calculate the start and end
		for (int i = 1; i <= colIndex; i++) {
			start = end;
			end = start + entryOutline[i].size();
		}
		
		Data result = new Data(start, end, entryOutline[colIndex].getType());
		return result;
	}
	
	// returns DataPair array that pairs names with data for nth index
	public DataPair[] getPairArray(int n) {
		DataPair[] result = new DataPair[getEntryOutlineLength()];
		Data[] array = get(n);
		for (int i = 0; i < array.length; i++) {
			result[i] = new DataPair(entryOutline[i].getName(), array[i]);
		}
		return result;
	}
	
	// returns an array of HashMaps for each entry in array
	public DataPair[][] getPairArrays() {
		DataPair[][] result = new DataPair[this.numEntries][getEntryOutlineLength()];
		for (int i = 0; i < this.numEntries; i++) {
			result[i] = getPairArray(i); 
		}
		return result;
	}
	
	public String toString() {
		String result = "";
		DataPair[][] pairArr = this.getPairArrays();
		for (int i = 0; i < pairArr.length; i++) {
			result += "----- element " + i + " -----\n";
			for (int j = 0; j < pairArr[i].length; j++) {
				result += pairArr[i][j] + "\n";
			}
		}
		return result;
	}
	
}
