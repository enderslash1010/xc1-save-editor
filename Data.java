
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
	
	// returns size in bytes
	public int size() {
		return this.end - this.start;
	}

}
