package Model;

/*
 *   abstract class Pointer
 *   
 *   Describes a location in a SaveFile, where a meaningful chunk of data is stored
 */
public abstract class Pointer {
	protected int start;
	protected int end;
	
	public int[] getLocation() {
		return new int[] {start, end};
	}
	
	// returns lengths of data in number of bytes
	public int size() {
		return end - start;
	}
}
