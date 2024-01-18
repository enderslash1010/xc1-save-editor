package Model;

import java.util.HashMap;

import Controller.ArrayField;

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
	private HashMap<ArrayField, Integer> colNames;
	
	public Array(int start, int end, Element[] entryOutline) {
		this.start = start;
		this.end = end;
		this.entryOutline = entryOutline;
		
		// Determine the size of each entry from entryOutline, and create colNames arrays
		
		// This variable will be in bytes, but for calculating the entry size we need to first count bits and then convert that to bytes
		// This is because each array entry in the save file is byte aligned, but the entry's elements may not be (because of bitfields)
		this.entrySize = 0;
		
		this.colNames = new HashMap<ArrayField, Integer>();
		for (int i = 0; i < this.entryOutline.length; i++) {
			this.entrySize += entryOutline[i].size();
			
			if (!(entryOutline[i] instanceof StaticElement)) {
				this.colNames.put(entryOutline[i].getName(), i);
			}
		}
		if (this.entrySize % 8 != 0) System.out.println("Warning: Array entry not byte aligned");
		this.entrySize /= 8;
		
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
	public ArrayField[] getColNames() {
		return this.colNames.keySet().toArray(new ArrayField[0]);
	}
	
	/**
	 * Gets the index of the column with the specified name
	 * @param name the internal name of a column in this Array to get the index of
	 * @return the index of the column that has the String name, or -1 if there is none
	 */
	private int getColNameIndex(ArrayField name) {
		Integer index = this.colNames.get(name);
		try {
			return index;
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	/**
	 * Gets a <code>Data</code> object for the nth index and the specified column index
	 * @param n the index in the Array
	 * @param colIndex the column index in the Array
	 * @return a <code>Data</code> object at the specified location; null if n is out of bounds
	 */
	public Data get(int n, int colIndex) {
		if (n >= this.numEntries) return null;
		int start = this.start + (entrySize*n);
		
		// calculate number of bits from start of index to colIndex
		int numBits = 0;
		for (int i = 0; i < colIndex; i++) {
			numBits += entryOutline[i].size();
		}
		
		// Calculate start, end, startBit, and endBit
		byte startBit = (byte) (7 - (numBits % 8));
		
		start += (numBits / 8);
		int end = start + 1;
		
		byte endBit = startBit;
		for (int i = 0; i < entryOutline[colIndex].size()-1; i++) {
			if (--endBit == -1) {
				end++;
				endBit = 7;
			}
		}
		
		Data result = new Data(start, startBit, end, endBit, entryOutline[colIndex].getType());
		return result;
	}
	
	/**
	 * 	Gets a <code>Data</code> object for the nth index and specified column name
	 *  @param n the index in the Array
	 *  @param colName a column name of this Array
	 *  @return a <code>Data</code> object at the specified location
	 */
	public Data get(int n, ArrayField colName) {		
		// determine which index in entryOutline corresponds with colName
		int colIndex = getColNameIndex(colName);
		if (colIndex == -1) return null;
		
		return get(n, colIndex); // Call more general function
	}
	
	/**
	 * Gets all the <code>Data</code> objects associated with all StaticElements at index n
	 * @param n the index in the Array
	 * @return an array of <code>Data</code> objects representing all the StaticElements at index n
	 */
	public Data[] getStatics(int n) {
		Data[] result = new Data[entryOutline.length - colNames.size()];
		for (int i = 0, currIndex = 0; i < entryOutline.length; i++) {
			if (entryOutline[i] instanceof StaticElement) {
				result[currIndex++] = get(n, i);
			}
		}
		return result;
	}
	
	/**
	 * Gets all the <code>Data</code> objects associated with all StaticElements at index n, and pairs them with their default value
	 * @param n the index in the Array
	 * @return a HashMap associating <code>Data</code> objects of StaticElements to their default value
	 */
	public HashMap<Data, Integer> getStaticValues(int n) {
		HashMap<Data, Integer> result = new HashMap<Data, Integer>();
		for (int i = 0; i < entryOutline.length; i++) {
			if (entryOutline[i] instanceof StaticElement) {
				result.put(get(n, i), ((StaticElement) entryOutline[i]).getStaticVal());
			}
		}
		return result;
	}
}
