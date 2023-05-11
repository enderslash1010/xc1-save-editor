package Model;
import java.util.EventObject;

/**
 *	class ModelEvent
 *
 *	represents an event that the model can broadcast to controller
 */
public class ModelEvent extends EventObject {
	
	private int type;
	private String param;
	
	private static final long serialVersionUID = 1L;
	
	public static final int SET_DATA = 0; // param contains name of data changed

	public ModelEvent(Object source, int type, String param) {
		super(source);
		this.type = type;
		this.param = param;
	}
	
	public ModelEvent(Object source, int type) {
		super(source);
		this.type = type;
	}
	
	public int getType() { return this.type; }
	public String getParam() { return this.param; }
	
}
