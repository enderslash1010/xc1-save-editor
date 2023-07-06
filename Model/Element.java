package Model;

/**
 * An Element describes a column in an <code>Array</code>
 * Used when creating an <code>Array</code> to describe what data each entry consists of
 * @author ender
 */
public class Element {
	
	private DataType type;
	private int size;
	private String name;
	
	/**
	 * Element Constructor
	 * @param name the name of the field from SaveFile.DataMap
	 * @param size the number of bytes this field takes up in the save file
	 * @param type the data type of this field
	 */
	public Element(String name, int size, DataType type) {
		this.size = size;
		this.type = type;
		this.name = name;
	}
	
	/**
	 * @return the size, in bytes, of this object
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * @return the data type of this object
	 */
	public DataType getType() {
		return this.type;
	}
	
	/**
	 * @return the field name of this object (from SaveFile.DataMap)
	 */
	public String getName() {
		return this.name;
	}
}
