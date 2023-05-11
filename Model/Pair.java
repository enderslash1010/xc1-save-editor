package Model;
// Abstract class with children DataPair and ValuePair
public abstract class Pair<T extends Object> {

	private String name;
	private T data;
	
	public Pair(String name, T data) {
		this.name = name;
		this.data = data;
	}
	
	public String getName() { return this.name; }
	public T getData() { return this.data; }
	
	public String toString() { return "(" + this.getName() + ", " + this.getData() + ")"; }
	
	@Override
	public int hashCode() {
		return this.getName().hashCode() * this.getData().hashCode();
	}
	
}
