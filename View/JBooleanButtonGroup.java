package View;

import javax.swing.JComponent;

/**
 * A <code>JComponent</code> container for a <code>BooleanButtonGroup</code>
 * 
 * @author ender
 */
@SuppressWarnings("serial")
public class JBooleanButtonGroup extends JComponent {

	private BooleanButtonGroup bbg;
	
	public JBooleanButtonGroup(BooleanButtonGroup bbg) {
		this.bbg = bbg;
	}
	
	@Override
	public void setEnabled(boolean b) {
		bbg.getTrueButton().setEnabled(b);
		bbg.getFalseButton().setEnabled(b);
	}
	
	/**
	 * Sets the value of the <code>BooleanButtonGroup</code>
	 * @param b determines which button is selected (either the true or false button)
	 */
	public void setSelected(boolean b) {
		bbg.setBoolean(b);
	}
	
	public boolean isSelected() { return bbg.getBoolean(); }
	
}
