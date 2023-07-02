package View;

import javax.swing.JComponent;

// TODO: javadocs

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
	
	public void setSelected(boolean b) {
		bbg.setBoolean(b);
	}
	
}
