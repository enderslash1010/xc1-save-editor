package Model;

import java.util.HashMap;

/*  
 *  Array
 *  
 *  Layout of an Array:
 * 
 *                Element
 *  |----------------------------------------|
 *                                        Entry
 *  |-----------------------------------------------------------------------------------------|
 * 																									  _
 *  [[(mineCooldown, Int at [0x240f0, 0x240f2)), (numHarvests, Int at [0x240f2, 0x240f3)), ...,], 0   |
 *   [(mineCooldown, Int at [0x240f6, 0x240f8)), (numHarvests, Int at [0x240f8, 0x240f9)), ...,], 1   |  Index
 *   [(mineCooldown, Int at [0x240fc, 0x240fe)), (numHarvests, Int at [0x240fe, 0x240ff)), ...,]] 2   |
 *   																							      -
 *   This class stores the location/description of array structured data in the save file, 
 *   but doesn't store the actual values (SaveFile class can retrieve these values from an Array object)
 */
public class Array extends Pointer {
	
	private Element[] entryOutline;
	private int entrySize; // Size of individual entry in bytes (use getEntryOutlineLength() for number of elements in one entry)
	private int numEntries; // how many entries are in array
	private HashMap<String, Integer> colNames;
	
	public Array(int start, int end, Element[] entryOutline) {
		this.start = start;
		this.end = end;
		this.entryOutline = entryOutline;
		
		// Determine the size of each entry from entryOutline, and create colNames arrays
		this.entrySize = 0;
		this.colNames = new HashMap<String, Integer>();
		for (int i = 0; i < this.entryOutline.length; i++) {
			this.entrySize += entryOutline[i].size();
			this.colNames.put(entryOutline[i].getName(), i);
		}
		
		// Determine how many entries
		this.numEntries = this.size() / this.entrySize;
	}
	 
	/**
	 * Gets the number of entries in this Array, the same as the row length
	 * @return the number of entries (also the row length) in this Array
	 */
	public int getNumEntries() { return this.numEntries; }
	public int getRowLength() { return this.numEntries; } // alias for numEntries
	
	/**
	 * Gets the entry outline length of this Array, the same as the column length
	 * @return the column length (also the entry outline length)
	 */
	public int getEntryOutlineLength() { return this.entryOutline.length; }
	public int getColLength() { return this.entryOutline.length; } // alias for entryOutline length
	
	/**
	 * Gets an un-ordered array of the column names in this Array
	 * @return a String array consisting of the column names
	 */
	public String[] getColNames() {
		return this.colNames.keySet().toArray(new String[0]);
	}
	
	/**
	 * Gets the index of the column with the specified name
	 * @param name the internal name of a column in this Array to get the index of
	 * @return the index of the column that has the String name, or -1 if there is none
	 */
	private int getColNameIndex(String name) {
		Integer index = this.colNames.get(name);
		try {
			return index;
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	/**
	 * 	Gets a <code>Data</code> object for the nth index and specified column name
	 *  @param n the index in the Array
	 *  @param colName a column name of this Array
	 *  @return a <code>Data</code> object at the specified location
	 */
	public Data get(int n, String colName) {
		int start = this.start + (entrySize*n); // start of index
		int end = start + entryOutline[0].size();
		
		// determine which index in entryOutline corresponds with colName
		int colIndex = getColNameIndex(colName);
		
		// calculate the start and end
		for (int i = 1; i <= colIndex; i++) {
			start = end;
			end = start + entryOutline[i].size();
		}
		
		Data result = new Data(start, end, entryOutline[colIndex].getType());
		return result;
	}
	
}
