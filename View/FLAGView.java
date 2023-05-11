package View;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FLAGView extends JPanel {

	GUI gui;
	
	private JTextField FLAGScenarioNum;
	
	public FLAGView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[::200.00]20[]", "[]10[]"));
		
		JPanel FLAGScenarioPanel = new JPanel();
		FLAGScenarioPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		FLAGScenarioPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));
		this.add(FLAGScenarioPanel, "cell 0 0,growx");

		JLabel lblNewLabel_7 = new JLabel("Scenario Number:");
		FLAGScenarioPanel.add(lblNewLabel_7, "cell 0 0,alignx left");

		FLAGScenarioNum = new JTextField();
		FLAGScenarioNum.setColumns(4);
		gui.set("scenarioNum", FLAGScenarioNum, gui.uint2, gui.textIntFocusListener, false);
		FLAGScenarioPanel.add(FLAGScenarioNum, "cell 1 0,growx");
	}
	
}
