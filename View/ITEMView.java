package View;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.SaveField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ITEMView extends JPanel {
	
	GUI gui;
	
	private JTextField ITEMMoney;
	
	public ITEMView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[]10[]10[]10[]10[]", "[]10[]10[]"));
		
		JPanel ITEMMoneyPanel = new JPanel();
		ITEMMoneyPanel.setBorder(new TitledBorder(null, "Money", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(ITEMMoneyPanel, "cell 0 0,growx,aligny top");
		ITEMMoneyPanel.setLayout(new MigLayout("fillx", "10[]10", "5[]5"));

		ITEMMoney = new JTextField();
		ITEMMoneyPanel.add(ITEMMoney, "cell 0 0,growx,aligny top");
		ITEMMoney.setText("");
		ITEMMoney.setColumns(10);
		gui.setTextField(SaveField.money, ITEMMoney, gui.int4);
	}
	
}
