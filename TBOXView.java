import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TBOXView extends JPanel {

	GUI gui;
	
	public TBOXView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("", "[]", "[]"));
	}
	
}
