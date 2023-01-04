
public class Data {
	private int start, end;
	private DataType type;
	
	public Data(int start, int end, DataType type) {
		this.start = start;
		this.end = end;
		this.type = type;
	}
	
	public DataType getType() {
		return type;
	}
	
	public int[] getLocation() {
		return new int[] {start, end};
	}
}
