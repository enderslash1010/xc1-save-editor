package Model;
/* TODO: javadocs
 *   class Data
 *   
 *   Builds upon Pointer class to add a data type to the specified location in a SaveFile
 */
public class Data extends Pointer {

	private DataType type;
	
	public Data(int start, int end, DataType type) {
		this.start = start;
		this.end = end;
		this.type = type;
	}
	
	public DataType getType() {
		return type;
	}
	
	public String toString() {
		return this.type.name() + " at [0x" + Integer.toHexString(this.start) + ", 0x" + Integer.toHexString(this.end) + ")";
	}
	
	// Need to override equals() and hashCode() for Tests to work correctly
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
