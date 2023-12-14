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
		
		this.startBit = 7;
		this.endBit = 0;
	}
	
	/**
	 * Data constructor for use with bitfields
	 * @param start the start location of the data described, inclusive
	 * @param end the end location of the data described, exclusive
	 * @param type the data type 
	 * @param startBit the MSB in the start byte
	 * @param endBit the LSB in the end-1 byte
	 */
	public Data(int start, byte startBit, int end, byte endBit, DataType type) {
		this.start = start;
		this.end = end;
		this.type = type;
		this.startBit = startBit;
		this.endBit = endBit;
	}
	
	/**
	 * Gets the data type of this <code>Data</code>
	 * @return the <code>DataType</code> of this object
	 */
	public DataType getType() {
		return type;
	}
	
	public String toString() {
		return this.type.name() + " at [0x" + Integer.toHexString(this.start) + (this.startBit == 7 ? "" : (" at bit " + this.startBit)) 
				+ ", 0x" + Integer.toHexString(this.end) + (this.endBit == 0 ? "" : (" at bit " + this.endBit)) + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		Data o = (Data) obj;
		return (this.type == o.type && this.start == o.start && this.end == o.end && this.startBit == o.startBit && this.endBit == o.endBit);
	}
	
	@Override
	public int hashCode() {
		return (this.start * this.end);
	}

}
