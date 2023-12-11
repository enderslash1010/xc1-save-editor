package Model;

// Allows for fields in Arrays that don't change to be defined
public class NullElement extends Element {

	private int defaultVal;
	
	public NullElement(int size, int defaultVal) {
		super(null, size, null);
		this.defaultVal = defaultVal;
	}
	
	public int getDefaultVal() {
		return this.defaultVal;
	}

}
