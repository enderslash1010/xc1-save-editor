import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class WTHRView extends JPanel {

	GUI gui;
	
	public WTHRView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("", "[]", "[]"));
	}
	
}
