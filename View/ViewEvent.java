package View;
import java.util.EventObject;

import Controller.ArrayField;
import Controller.SaveField;

/**
 *	class ViewEvent
 *
 *	represents an event that the view can broadcast to controller
 */
@SuppressWarnings("serial")
public class ViewEvent extends EventObject {

	private EventType type;

	private SaveField sf;

	private ArrayField af;

	private String fileLocation;

	private Integer index;

	private String value;

	public enum EventType {
		OPEN_FILE, // param is file location
		SAVE_FILE, // param null
		GET_DATA, // param is a SaveField
		SET_DATA, // param is "saveField:value"
		SET_ARRAY_DATA, // param is "saveField(array):index:arrayField:value"
		GET_ARRAY_DATA, // param is "saveField(array):index:arrayField"
	}

	/**
	 * ViewEvent constructor with param parameter (for event OPEN_FILE, SET_DATA)
	 * @param source the object that created this <code>ViewEvent</code>
	 * @param type the type of <code>ViewEvent</code> described by the constants of this class
	 * @param fileLocation the location of the file to be opened; or null if not applicable to the type
	 * @param sf the save field this event wants to see or modify; or null if not applicable to the type
	 * @param af the <code>Array</code> column name, used with an index to uniquely identify an <code>Array</code> element; or null if not applicable to the type
	 * @param index the <code>Array</code> index (or row value), used with an array field to uniquely identify an <code>Array</code> element; or null if not applicable to the type
	 * @param value the value to set a save field or <code>Array</code> element to; or null if not applicable to the type
	 */
	public ViewEvent(Object source, EventType type, String fileLocation, SaveField sf, ArrayField af, Integer index, String value) {
		super(source);
		
		// verify parameters based on the type
		switch (type) {
		case GET_ARRAY_DATA:
			if (sf == null || index == null || af == null) throw new IllegalArgumentException();
			break;
		case GET_DATA:
			if (sf == null) throw new IllegalArgumentException();
			break;
		case OPEN_FILE:
			if (fileLocation == null) throw new IllegalArgumentException();
			break;
		case SAVE_FILE:
			break;
		case SET_ARRAY_DATA:
			if (sf == null || index == null || af == null || value == null) throw new IllegalArgumentException();
			break;
		case SET_DATA:
			if (sf == null || value == null) throw new IllegalArgumentException();
			break;
		}
		
		this.type = type;
		this.fileLocation = fileLocation;
		this.sf = sf;
		this.af = af;
		this.index = index;
		this.value = value;
	}

	/**
	 * @return the type of this <code>ViewEvent</code> described by the constants of this class; or null if not applicable
	 */
	public EventType getType() { return this.type; }

	/**
	 * @return the save field this <code>ViewEvent</code> wants to get/set; or null if not applicable
	 */
	public SaveField getSaveField() { return this.sf; }

	/**
	 * @return the <code>Array</code> column name this <code>ViewEvent</code> is referring to; or null if not applicable
	 * Used in conjunction with an index to uniquely identify an <code>Array</code> element
	 */
	public ArrayField getArrayField() { return this.af; }

	/**
	 * @return the file location of the file to open, used only with EventType.OPEN_FILE
	 */
	public String getFileLocation() { return this.fileLocation; }

	/**
	 * @return the <code>Array</code> index (or row) this <code>ViewEvent</code> is referring to; or null if not applicable
	 * Used in conjunction with an ArrayField to uniquely identify an <code>Array</code> element
	 */
	public Integer getIndex() { return this.index; }

	/**
	 * @return the value to set a save field to; or null if not applicable
	 */
	public String getValue() { return this.value; }

	@Override
	public String toString() {		
		String result = "[" + this.type;
		if (fileLocation != null) result += ", fileLocation = " + fileLocation;
		if (sf != null) result += ", SaveField = " + sf;
		if (af != null) result += ", ArrayField = " + af;
		if (index != null) result += ", Index = " + index;
		if (value != null) result += ", Value = " + value;
		return result + "]";
	}

}
