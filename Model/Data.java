package Model;
/* 
 *   class Data
 *   
 *   Builds upon Pointer class to add a data type to the specified location in a SaveFile
 */
public class Data extends Pointer {

	private DataType type;
	
	/**
	 * Data constructor
	 * @param start the start location of the data described, inclusive
	 * @param end the end location of the data described, exclusive
	 * @param type the data type 
	 */
	public Data(int start, int end, DataType type) {
		this.start = start;
		this.end = end;
		this.type = type;
	}
	
	/**
	 * Gets the data type of this <code>Data</code>
	 * @return the <code>DataType</code> of this object
	 */
	public DataType getType() {
		return type;
	}
	
	public String toString() {
		return this.type.name() + " at [0x" + Integer.toHexString(this.start) + ", 0x" + Integer.toHexString(this.end) + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		Data o = (Data) obj;
		return (this.type == o.type && this.start == o.start && this.end == o.end);
	}
	
	@Override
	public int hashCode() {
		return this.start * this.end;
	}

}
