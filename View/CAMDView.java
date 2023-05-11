package View;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class CAMDView extends JPanel {

	GUI gui;
	
	private JTextField CAMDVPos, CAMDHPos, CAMDDistance;
	
	public CAMDView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10", "10[]10"));
		
		JPanel cameraVPosPanel = new JPanel();
		cameraVPosPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(cameraVPosPanel, "cell 0 0,growx,aligny top");
		cameraVPosPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_20 = new JLabel("Camera Vertical Position:");
		cameraVPosPanel.add(lblNewLabel_20, "cell 0 0,alignx left,aligny center");

		CAMDVPos = new JTextField();
		cameraVPosPanel.add(CAMDVPos, "cell 1 0,growx,aligny top");
		CAMDVPos.setColumns(10);
		gui.set("cameraPosVertical", CAMDVPos, null, gui.textFloatFocusListener, false);

		JPanel cameraHPosPanel = new JPanel();
		cameraHPosPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(cameraHPosPanel, "cell 1 0,growx,aligny top");
		cameraHPosPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_21 = new JLabel("Camera Horizontal Position:");
		cameraHPosPanel.add(lblNewLabel_21, "cell 0 0,alignx left,aligny center");

		CAMDHPos = new JTextField();
		cameraHPosPanel.add(CAMDHPos, "cell 1 0,growx,aligny top");
		CAMDHPos.setColumns(10);
		gui.set("cameraPosHorizontal", CAMDHPos, null, gui.textFloatFocusListener, false);

		JPanel CAMDDistancePanel = new JPanel();
		CAMDDistancePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(CAMDDistancePanel, "cell 2 0,growx,aligny top");
		CAMDDistancePanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_22 = new JLabel("Camera Distance:");
		CAMDDistancePanel.add(lblNewLabel_22, "cell 0 0,alignx left,aligny center");

		CAMDDistance = new JTextField();
		CAMDDistancePanel.add(CAMDDistance, "cell 1 0,growx,aligny top");
		CAMDDistance.setColumns(10);
		gui.set("cameraDistance", CAMDDistance, null, gui.textFloatFocusListener, false);
	}
	
}
