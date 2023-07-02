package View;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PCPMView extends JPanel {

	GUI gui;
	
	private JTextField PCPMPlayer1x, PCPMPlayer1y, PCPMPlayer1z, PCPMPlayer2x, PCPMPlayer2y, PCPMPlayer2z, PCPMPlayer3x, PCPMPlayer3y, PCPMPlayer3z, PCPMPlayer1Angle, PCPMPlayer2Angle, PCPMPlayer3Angle;
	
	public PCPMView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10", "10[]10[]10[]10"));
		
		JPanel PCPMPlayer1Panel = new JPanel();
		PCPMPlayer1Panel.setBorder(new TitledBorder(null, "Player 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(PCPMPlayer1Panel, "cell 0 0,growx,aligny top");
		PCPMPlayer1Panel.setLayout(new MigLayout("fillx", "10[min!]10[]10[min!]10[]10[min!]10[]10[min!]10[]10", "[]"));

		JLabel lblNewLabel_12 = new JLabel("x:");
		PCPMPlayer1Panel.add(lblNewLabel_12, "cell 0 0,alignx left,aligny center");

		PCPMPlayer1x = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1x, "cell 1 0,growx,aligny top");
		PCPMPlayer1x.setColumns(10);
		gui.setTextField("p1x", PCPMPlayer1x, null);

		JLabel lblNewLabel_13 = new JLabel("y:");
		PCPMPlayer1Panel.add(lblNewLabel_13, "cell 2 0,alignx left,aligny center");

		PCPMPlayer1y = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1y, "cell 3 0,growx,aligny top");
		PCPMPlayer1y.setColumns(10);
		gui.setTextField("p1y", PCPMPlayer1y, null);

		JLabel lblNewLabel_14 = new JLabel("z:");
		PCPMPlayer1Panel.add(lblNewLabel_14, "cell 4 0,alignx left,aligny center");

		PCPMPlayer1z = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1z, "cell 5 0,growx,aligny top");
		PCPMPlayer1z.setColumns(10);
		gui.setTextField("p1z", PCPMPlayer1z, null);

		JLabel lblNewLabel_15 = new JLabel("Angle:");
		PCPMPlayer1Panel.add(lblNewLabel_15, "cell 6 0,alignx left,aligny center");

		PCPMPlayer1Angle = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1Angle, "cell 7 0,growx,aligny top");
		PCPMPlayer1Angle.setColumns(10);
		gui.setTextField("p1Angle", PCPMPlayer1Angle, null);

		JPanel PCPMPlayer2Panel = new JPanel();
		PCPMPlayer2Panel.setBorder(new TitledBorder(null, "Player 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(PCPMPlayer2Panel, "cell 0 1,growx,aligny top");
		PCPMPlayer2Panel.setLayout(new MigLayout("fillx", "10[min!]10[]10[min!]10[]10[min!]10[]10[min!]10[]10", "[]"));

		JLabel lblNewLabel_12_1 = new JLabel("x:");
		PCPMPlayer2Panel.add(lblNewLabel_12_1, "cell 0 0,alignx left,aligny center");

		PCPMPlayer2x = new JTextField();
		PCPMPlayer2x.setColumns(10);
		PCPMPlayer2Panel.add(PCPMPlayer2x, "cell 1 0,growx,aligny top");
		gui.setTextField("p2x", PCPMPlayer2x, null);

		JLabel lblNewLabel_13_1 = new JLabel("y:");
		PCPMPlayer2Panel.add(lblNewLabel_13_1, "cell 2 0,alignx left,aligny center");

		PCPMPlayer2y = new JTextField();
		PCPMPlayer2y.setColumns(10);
		PCPMPlayer2Panel.add(PCPMPlayer2y, "cell 3 0,growx,aligny top");
		gui.setTextField("p2y", PCPMPlayer2y, null);

		JLabel lblNewLabel_14_1 = new JLabel("z:");
		PCPMPlayer2Panel.add(lblNewLabel_14_1, "cell 4 0,alignx left,aligny center");

		PCPMPlayer2z = new JTextField();
		PCPMPlayer2z.setColumns(10);
		PCPMPlayer2Panel.add(PCPMPlayer2z, "cell 5 0,growx,aligny top");
		gui.setTextField("p2z", PCPMPlayer2z, null);

		JLabel lblNewLabel_15_1 = new JLabel("Angle:");
		PCPMPlayer2Panel.add(lblNewLabel_15_1, "cell 6 0,alignx left,aligny center");

		PCPMPlayer2Angle = new JTextField();
		PCPMPlayer2Panel.add(PCPMPlayer2Angle, "cell 7 0,growx,aligny top");
		PCPMPlayer2Angle.setColumns(10);
		gui.setTextField("p2Angle", PCPMPlayer2Angle, null);

		JPanel PCPMPlayer3Panel = new JPanel();
		PCPMPlayer3Panel.setBorder(new TitledBorder(null, "Player 3", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(PCPMPlayer3Panel, "cell 0 2,growx,aligny top");
		PCPMPlayer3Panel.setLayout(new MigLayout("fillx", "10[min!]10[]10[min!]10[]10[min!]10[]10[min!]10[]10", "[]"));

		JLabel lblNewLabel_12_1_1 = new JLabel("x:");
		PCPMPlayer3Panel.add(lblNewLabel_12_1_1, "cell 0 0,alignx left,aligny center");

		PCPMPlayer3x = new JTextField();
		PCPMPlayer3x.setColumns(10);
		PCPMPlayer3Panel.add(PCPMPlayer3x, "cell 1 0,growx,aligny top");
		gui.setTextField("p3x", PCPMPlayer3x, null);

		JLabel lblNewLabel_13_1_1 = new JLabel("y:");
		PCPMPlayer3Panel.add(lblNewLabel_13_1_1, "cell 2 0,alignx left,aligny center");

		PCPMPlayer3y = new JTextField();
		PCPMPlayer3y.setColumns(10);
		PCPMPlayer3Panel.add(PCPMPlayer3y, "cell 3 0,growx,aligny top");
		gui.setTextField("p3y", PCPMPlayer3y, null);

		JLabel lblNewLabel_14_1_1 = new JLabel("z:");
		PCPMPlayer3Panel.add(lblNewLabel_14_1_1, "cell 4 0,alignx left,aligny center");

		PCPMPlayer3z = new JTextField();
		PCPMPlayer3z.setColumns(10);
		PCPMPlayer3Panel.add(PCPMPlayer3z, "cell 5 0,growx,aligny top");
		gui.setTextField("p3z", PCPMPlayer3z, null);

		JLabel lblNewLabel_15_1_1 = new JLabel("Angle:");
		PCPMPlayer3Panel.add(lblNewLabel_15_1_1, "cell 6 0,alignx left,aligny center");

		PCPMPlayer3Angle = new JTextField();
		PCPMPlayer3Panel.add(PCPMPlayer3Angle, "cell 7 0,growx,aligny top");
		PCPMPlayer3Angle.setColumns(10);
		gui.setTextField("p3Angle", PCPMPlayer3Angle, null);
	}
	
}
