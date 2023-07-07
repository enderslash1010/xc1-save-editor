package View;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.SaveField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class CAMDView extends JPanel {

	GUI gui;

	private JTextField CAMDVPos, CAMDHPos, CAMDDistance;

	public CAMDView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10", "[]10[grow]"));

		JPanel cameraPanel = new JPanel();
		cameraPanel.setBorder(new TitledBorder(null, "Camera", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(cameraPanel, "cell 0 0,grow");
		cameraPanel.setLayout(new MigLayout("", "10[grow]10[grow]10[grow]10", "[]"));

		JPanel cameraVPosPanel = new JPanel();
		cameraPanel.add(cameraVPosPanel, "cell 0 0,growx");
		cameraVPosPanel.setBorder(new TitledBorder(null, "Vertical Position", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraVPosPanel.setLayout(new MigLayout("fillx", "10[]10", "[]"));

		CAMDVPos = new JTextField();
		cameraVPosPanel.add(CAMDVPos, "cell 0 0,growx,aligny top");
		CAMDVPos.setColumns(10);
		gui.setTextField(SaveField.cameraPosVertical, CAMDVPos, null);

		JPanel cameraHPosPanel = new JPanel();
		cameraPanel.add(cameraHPosPanel, "cell 1 0,growx");
		cameraHPosPanel.setBorder(new TitledBorder(null, "Horizontal Position", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraHPosPanel.setLayout(new MigLayout("fillx", "10[]10", "[]"));

		CAMDHPos = new JTextField();
		cameraHPosPanel.add(CAMDHPos, "cell 0 0,growx,aligny top");
		CAMDHPos.setColumns(10);
		gui.setTextField(SaveField.cameraPosHorizontal, CAMDHPos, null);

		JPanel CAMDDistancePanel = new JPanel();
		cameraPanel.add(CAMDDistancePanel, "cell 2 0,growx");
		CAMDDistancePanel.setBorder(new TitledBorder(null, "Distance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CAMDDistancePanel.setLayout(new MigLayout("fillx", "10[]10", "[]"));

		CAMDDistance = new JTextField();
		CAMDDistancePanel.add(CAMDDistance, "cell 0 0,growx,aligny top");
		CAMDDistance.setColumns(10);
		gui.setTextField(SaveField.cameraDistance, CAMDDistance, null);
	}

}
