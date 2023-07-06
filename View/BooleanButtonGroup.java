package View;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 *	Extends <code>ButtonGroup</code> to have it hold a boolean value
 *	Selecting the true button makes the BooleanButtonGroup true, and selecting the false button makes it false
 *	Needs exactly 2 buttons added to the button group
 *	Used when mapping two buttons to one boolean variable
 */

@SuppressWarnings("serial")
public class BooleanButtonGroup extends ButtonGroup {

	/**
	 * BooleanButtonGroup Constructor
	 * @param t the true button
	 * @param f the false button
	 */
	public BooleanButtonGroup(AbstractButton t, AbstractButton f) {
		super();
		super.add(f); // buttons.get(0); -> false button
		super.add(t); // buttons.get(1); -> true button
	}

	// can't add new buttons to this group
	@Override
	public void add(AbstractButton b) {
		throw new UnsupportedOperationException();
	}

	// can't remove buttons from this group
	@Override
	public void remove(AbstractButton b) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the value of this <code>BooleanButtonGroup</code>
	 * If the true button is selected, this method returns true; Similarly with the false button
	 * @return the value of this <code>BooleanButtonGroup</code>
	 */
	public boolean getBoolean() {
		AbstractButton f = super.buttons.get(0);
		AbstractButton t = super.buttons.get(1);

		if (f.isSelected()) {
			return false;
		}
		else if (t.isSelected()) {
			return true;
		}
		else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Sets the value of this <code>BooleanButtonGroup</code>
	 * @param b which button to select, or alternatively what value to set this <code>BooleanButtonGroup</code> to
	 */
	public void setBoolean(boolean b) {
		if (b) {
			AbstractButton t = super.buttons.get(1);
			t.setSelected(true);
		}
		else {
			AbstractButton f = super.buttons.get(0);
			f.setSelected(true);
		}
	}

	/**
	 * @return the button associated with false
	 */
	public AbstractButton getFalseButton() { return super.buttons.get(0); }
	
	/**
	 * @return the button associated with true
	 */
	public AbstractButton getTrueButton() { return super.buttons.get(1); }

}
