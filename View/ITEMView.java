package View;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ITEMView extends JPanel {
	
	GUI gui;
	
	private JTextField ITEMMoney;
	
	public ITEMView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[::200.00]10[]", "[]"));
		
		JPanel ITEMMoneyPanel = new JPanel();
		ITEMMoneyPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(ITEMMoneyPanel, "cell 0 0,growx,aligny top");
		ITEMMoneyPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_23 = new JLabel("Money:");
		ITEMMoneyPanel.add(lblNewLabel_23, "cell 0 0,alignx left,aligny center");

		ITEMMoney = new JTextField();
		ITEMMoneyPanel.add(ITEMMoney, "cell 1 0,growx,aligny top");
		ITEMMoney.setText("");
		ITEMMoney.setColumns(10);
		gui.set("money", ITEMMoney, gui.int4, gui.textIntFocusListener, false);
	}
	
}
