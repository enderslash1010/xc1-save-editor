package View;
import java.util.EventObject;

/** TODO: javadocs
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

	public ViewEvent(Object source, int type, String param) {
		super(source);
		this.type = type;
		this.param = param;
	}
	
	public ViewEvent(Object source, int type) {
		super(source);
		this.type = type;
	}
	
	public int getType() { return this.type; }
	public String getParam() { return this.param; }
	
}
