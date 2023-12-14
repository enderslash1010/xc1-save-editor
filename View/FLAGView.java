package View;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.SaveField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FLAGView extends JPanel {

	GUI gui;
	
	private JTextField FLAGScenarioNum;
	
	public FLAGView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("", "10[]10[]", "10[]10[]"));
		
		JPanel FLAGScenarioPanel = new JPanel();
		FLAGScenarioPanel.setBorder(new TitledBorder(null, "Scenario Number", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		FLAGScenarioPanel.setLayout(new MigLayout("fillx", "10[]10", "5[]5"));
		this.add(FLAGScenarioPanel, "cell 0 0,growx");

		FLAGScenarioNum = new JTextField();
		FLAGScenarioNum.setColumns(10);
		gui.setTextField(SaveField.scenarioNum, FLAGScenarioNum, gui.uint16);
		FLAGScenarioPanel.add(FLAGScenarioNum, "cell 0 0,growx");
	}
	
}
