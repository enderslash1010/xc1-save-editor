package Model;

/* 
 *   abstract class Pointer
 *   
 *   Describes a location in a SaveFile, where a meaningful chunk of data is stored
 */
public abstract class Pointer {
	protected int start;
	protected int end;
	
	protected byte startBit; // within the start byte, which bit is the MSB (default=7)
	protected byte endBit; // within the end byte, which bit is the LSB (default=0)
	
	/**
	 * @return an array with index 0 being the start, and index 1 being the end
	 */
	public int[] getLocation() {
		return new int[] {start, end};
	}
	
	/**
	 * @return the size, in bytes, of this <code>Pointer</code>
	 */
	public int size() {
		return end - start;
	}
}
