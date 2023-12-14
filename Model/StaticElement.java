package Model;

/**
 *	<code>StaticElement</code>
 *
 *	Represents an Element in an Array that doesn't change value (has a static value)
 */
public class StaticElement extends Element {

	private int staticVal;
	
	/**
	 * StaticElement constructor
	 * @param size the size in bits of the <code>StaticElement</code>
	 * @param staticVal the static value of this <code>StaticElement</code>
	 */
	public StaticElement(int size, int staticVal) {
		super(null, size, DataType.Int);
		this.staticVal = staticVal;
	}
	
	/**
	 * Gets the static value of this <code>StaticElement</code>
	 * @return the static value
	 */
	public int getStaticVal() {
		return this.staticVal;
	}

}
