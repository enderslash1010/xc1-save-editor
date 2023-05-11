import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class OPTDView extends JPanel {

	GUI gui;
	
	public OPTDView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("", "[]", "[]"));
	}
	
}
