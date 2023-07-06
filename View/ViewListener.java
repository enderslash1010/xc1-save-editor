package View;

/**
 * An interface for classes that listen for each <code>ViewEvent</code>
 * @author ender
 */

public interface ViewListener {
	
	/**
	 * Handles a <code>ViewEvent</code> that comes from the View
	 * @param e
	 */
	public void viewEventOccurred(ViewEvent e);
}
