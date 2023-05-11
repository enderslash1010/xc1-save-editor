package View;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SNDSView extends JPanel {

	GUI gui;
	
	public SNDSView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("", "[]", "[]"));
	}
	
}
