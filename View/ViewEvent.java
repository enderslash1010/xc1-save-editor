package View;
import java.util.EventObject;

/**
 *	class ViewEvent
 *
 *	represents an event that the view can broadcast to controller
 */
public class ViewEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	
	// describes type of ViewEvent, using one of the defined constants
	private int type;
	
	// contains passed data from the View that the Controller needs to handle the specific event
	private String param;
	
	/*
	 *   Constants
	 */
	public static final int OPEN_FILE = 0; // param contains file location of selected file to be opened

	public static final int SAVE_FILE = 1; // param null
	
	public static final int GET_DATA = 2; // param contains name of data to get

	public static final int SET_DATA = 3; // param is in form "dataName:dataValue" to set dataName to dataValue

	public static final int SET_ARRAY_DATA = 4; // param is in form "arrName:index:colName:value" to set arrName[index][colName] = value

	public static final int GET_ARRAY_DATA = 5; // param is in form "arrName:index:colName"

	/**
	 * ViewEvent constructor with param parameter
	 * @param source the object that created this <code>ViewEvent</code>
	 * @param type the type of <code>ViewEvent</code> described by the constants of this class
	 * @param param the parameters, if any, in the form "param1:param2:param3:...:lastparam"
	 */
	public ViewEvent(Object source, int type, String param) {
		super(source);
		this.type = type;
		this.param = param;
	}
	
	/**
	 * ViewEvent constructor without the param parameter
	 * @param source the object that created this <code>ViewEvent</code>
	 * @param type the type of <code>ViewEvent</code> described by the constants of this class
	 */
	public ViewEvent(Object source, int type) {
		super(source);
		this.type = type;
	}
	
	/**
	 * @return the type of this <code>ViewEvent</code> described by the constants of this class
	 */
	public int getType() { return this.type; }
	
	/**
	 * @return the param String associated with this <code>ViewEvent</code>, in the form "param1:param2:param3:...:lastparam"
	 */
	public String getParam() { return this.param; }
	
}
