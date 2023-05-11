package Model;
public class Element {
	
	private DataType type;
	private int size;
	private String name;
	
	public Element(String name, int size, DataType type) {
		this.size = size;
		this.type = type;
		this.name = name;
	}
	
	public int size() {
		return this.size;
	}
	
	public DataType getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
}
