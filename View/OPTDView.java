package View;
import java.awt.Color;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import Controller.SaveField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class OPTDView extends JPanel {

	GUI gui;
	// TODO: connect components using gui.set()
	public OPTDView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fill", "[]", "[]"));

		// create custom labels
		Dictionary<Integer, JLabel> brightnessTable = new Hashtable<Integer, JLabel>() {{
			put(8, new JLabel("Dark"));
			put(10, new JLabel("Normal"));
			put(12, new JLabel("Bright"));
			put(14, new JLabel("Brighter"));
			put(16, new JLabel("Brightest"));
		}};
		// create custom labels
		Dictionary<Integer, JLabel> axisSpeedTable = new Hashtable<Integer, JLabel>() {{
			put(0, new JLabel("Fastest"));
			put(1, new JLabel("Fast"));
			put(2, new JLabel("Normal"));
			put(3, new JLabel("Slow"));
			put(4, new JLabel("Slowest"));
		}};
		// create custom labels
		Dictionary<Integer, JLabel> povTable = new Hashtable<Integer, JLabel>() {{
			put(0, new JLabel("High"));
			put(1, new JLabel("Normal"));
			put(2, new JLabel("Low"));
		}};

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane, "cell 0 0 3 1,grow");

		JPanel viewPort = new JPanel();
		scrollPane.setViewportView(viewPort);
		viewPort.setLayout(new MigLayout("", "[]10[grow]", "[]10[]10[]10[]"));

		JPanel voicePanel = new JPanel();
		viewPort.add(voicePanel, "cell 0 0,growy");
		voicePanel.setBorder(new TitledBorder(null, "Voices", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		voicePanel.setLayout(new MigLayout("fill", "20[]5[]15", "[]"));

		JRadioButton englishRadio = new JRadioButton("English");
		voicePanel.add(englishRadio, "cell 0 0");

		JRadioButton japaneseRadio = new JRadioButton("Japanese");
		voicePanel.add(japaneseRadio, "cell 1 0");

		BooleanButtonGroup voiceRadioGroup = new BooleanButtonGroup(japaneseRadio, englishRadio);

		JPanel gammaPanel = new JPanel();
		viewPort.add(gammaPanel, "cell 1 0,grow");
		gammaPanel.setBorder(new TitledBorder(null, "Gamma", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		gammaPanel.setLayout(new MigLayout("fill", "[min!]10[grow]", "[]"));

		JLabel lblNewLabel_1 = new JLabel("Brightness:");
		gammaPanel.add(lblNewLabel_1, "cell 0 0,alignx trailing");

		JSlider brightnessSlider = new JSlider();
		brightnessSlider.setSnapToTicks(true);
		brightnessSlider.setMinorTickSpacing(2);
		brightnessSlider.setLabelTable(brightnessTable);

		brightnessSlider.setMaximum(16);
		brightnessSlider.setMinimum(8);
		brightnessSlider.setMajorTickSpacing(2);
		brightnessSlider.setValue(8);
		brightnessSlider.setPaintTicks(true);
		brightnessSlider.setPaintLabels(true);
		gammaPanel.add(brightnessSlider, "cell 1 0,growx");
		gui.setSlider(SaveField.gamma, brightnessSlider);

		JPanel helpPanel = new JPanel();
		viewPort.add(helpPanel, "cell 0 1 2 1,growx");
		helpPanel.setBorder(new TitledBorder(null, "Help", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		helpPanel.setLayout(new MigLayout("fillx", "[min!]10[]10[]10[]10[]10[][]", "[grow]10"));

		JCheckBox displayControls = new JCheckBox("Display Controls");
		helpPanel.add(displayControls, "cell 1 0");
		gui.setCheckBox(SaveField.showControls, displayControls);

		JCheckBox artDescriptions = new JCheckBox("Show Art Descriptions");
		helpPanel.add(artDescriptions, "cell 2 0");
		gui.setCheckBox(SaveField.showArtDescriptions, artDescriptions);

		JCheckBox enemyIcons = new JCheckBox("Show Enemy Icons");
		helpPanel.add(enemyIcons, "cell 3 0");
		gui.setCheckBox(SaveField.showEnemyIcons, enemyIcons);

		JCheckBox buffDebuffInfo = new JCheckBox("Buff/Debuff Info");
		helpPanel.add(buffDebuffInfo, "cell 4 0");
		gui.setCheckBox(SaveField.showBuffDebuffInfoEveryTime, buffDebuffInfo);

		JCheckBox buffDebuffIndicator = new JCheckBox("Buff/Debuff Indicator");
		helpPanel.add(buffDebuffIndicator, "cell 5 0");
		gui.setCheckBox(SaveField.showBuffDebuffIndicator, buffDebuffIndicator);

		JCheckBox destinationMarker = new JCheckBox("Show Destination Marker");
		helpPanel.add(destinationMarker, "cell 6 0");
		gui.setCheckBox(SaveField.showDestinationMarker, destinationMarker);

		JPanel cameraPanel = new JPanel();
		viewPort.add(cameraPanel, "cell 0 2 2 1,growx");
		cameraPanel.setBorder(new TitledBorder(null, "Camera", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraPanel.setLayout(new MigLayout("", "10[grow]10[grow]10", "[grow]3[grow]3[grow]"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "X-Axis", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraPanel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "15[]15[grow]20[]15", "10[]10"));

		JLabel lblNewLabel_3 = new JLabel("Speed:");
		panel_1.add(lblNewLabel_3, "cell 0 0");

		JSlider xAxisSlider = new JSlider();
		xAxisSlider.setLabelTable(axisSpeedTable);
		xAxisSlider.setPaintTicks(true);
		xAxisSlider.setPaintLabels(true);
		xAxisSlider.setMaximum(4);
		xAxisSlider.setValue(0);
		xAxisSlider.setMajorTickSpacing(1);
		panel_1.add(xAxisSlider, "cell 1 0,growx");
		gui.setSlider(SaveField.xAxisSpeed, xAxisSlider);

		JCheckBox invertedXAxis = new JCheckBox("Inverted");
		panel_1.add(invertedXAxis, "cell 2 0");
		gui.setCheckBox(SaveField.nonInvertedXAxis, invertedXAxis);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Y-Axis", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraPanel.add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "15[]15[grow]20[]15", "10[]10"));

		JLabel lblNewLabel_2 = new JLabel("Speed:");
		panel.add(lblNewLabel_2, "cell 0 0,alignx left,aligny center");

		JSlider yAxisSlider = new JSlider();
		yAxisSlider.setPaintLabels(true);
		yAxisSlider.setLabelTable(axisSpeedTable);

		yAxisSlider.setMajorTickSpacing(1);
		yAxisSlider.setPaintTicks(true);
		yAxisSlider.setValue(0);
		panel.add(yAxisSlider, "cell 1 0,growx,aligny top");
		yAxisSlider.setMaximum(4);
		gui.setSlider(SaveField.yAxisSpeed, yAxisSlider);

		JCheckBox invertedYAxis = new JCheckBox("Inverted");
		panel.add(invertedYAxis, "cell 2 0");
		gui.setCheckBox(SaveField.nonInvertedYAxis, invertedYAxis);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Zoom Speed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraPanel.add(panel_2, "cell 0 1,grow");
		panel_2.setLayout(new MigLayout("", "15[grow]15", "10[]10"));

		JSlider zoomSlider = new JSlider();
		zoomSlider.setLabelTable(axisSpeedTable);
		zoomSlider.setMajorTickSpacing(1);
		zoomSlider.setValue(0);
		zoomSlider.setMaximum(4);
		zoomSlider.setPaintTicks(true);
		zoomSlider.setPaintLabels(true);
		panel_2.add(zoomSlider, "cell 0 0,growx");
		gui.setSlider(SaveField.zoomSpeed, zoomSlider);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Point of View", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cameraPanel.add(panel_3, "cell 1 1,grow");
		panel_3.setLayout(new MigLayout("", "15[grow]15", "10[]10"));

		JSlider povSlider = new JSlider();
		povSlider.setLabelTable(povTable);

		povSlider.setMajorTickSpacing(1);
		povSlider.setMaximum(2);
		povSlider.setValue(0);
		povSlider.setPaintTicks(true);
		povSlider.setPaintLabels(true);
		panel_3.add(povSlider, "cell 0 0,growx");
		gui.setSlider(SaveField.pointOfView, povSlider);

		JPanel panel_4 = new JPanel();
		cameraPanel.add(panel_4, "cell 0 2,grow");
		panel_4.setLayout(new MigLayout("fillx", "[min!]30[grow]", "[]"));

		JCheckBox angleCorrection = new JCheckBox("Angle Correction");
		panel_4.add(angleCorrection, "cell 0 0");
		gui.setCheckBox(SaveField.angleCorrection, angleCorrection);

		JCheckBox battleCamera = new JCheckBox("Battle Camera");
		panel_4.add(battleCamera, "cell 1 0");
		gui.setCheckBox(SaveField.battleCamera, battleCamera);

		JPanel panel_5 = new JPanel();
		viewPort.add(panel_5, "cell 0 3,growx");
		panel_5.setBorder(new TitledBorder(null, "Minimap", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		JCheckBox chckbxDisplayMinimap = new JCheckBox("Display Minimap");
		panel_5.add(chckbxDisplayMinimap, "cell 0 0,alignx left,aligny top");
		gui.setCheckBox(SaveField.minimap, chckbxDisplayMinimap);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "Rotation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.add(panel_7, "cell 0 1,growx,aligny top");
		panel_7.setLayout(new MigLayout("fillx", "15[]10[]15", "[]"));

		JRadioButton fixedRadio = new JRadioButton("Fixed");
		panel_7.add(fixedRadio, "cell 0 0");

		JRadioButton rotateRadio = new JRadioButton("Rotate");
		panel_7.add(rotateRadio, "cell 1 0");

		BooleanButtonGroup rotationRadioGroup = new BooleanButtonGroup(rotateRadio, fixedRadio);

		JPanel panel_6 = new JPanel();
		viewPort.add(panel_6, "cell 1 3,growx");
		panel_6.setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setLayout(new MigLayout("", "[grow]15[]15[grow]", "[grow]"));

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Event Scrolling", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_8, "cell 0 0,grow");
		panel_8.setLayout(new MigLayout("", "15[grow]10[grow]15", "[]"));

		JRadioButton manualRadio = new JRadioButton("Manual");
		panel_8.add(manualRadio, "cell 0 0");

		JRadioButton autoRadio = new JRadioButton("Automatic");
		panel_8.add(autoRadio, "cell 1 0");

		BooleanButtonGroup eventRadioGroup = new BooleanButtonGroup(autoRadio, manualRadio);

		JCheckBox subtitles = new JCheckBox("Show Subtitles");
		panel_6.add(subtitles, "cell 1 0");
		gui.setCheckBox(SaveField.showSubtitles, subtitles);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(null, "Dialogue Text Speed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(panel_9, "cell 2 0,grow");
		panel_9.setLayout(new MigLayout("", "15[grow]10[grow]15", "[]"));

		JRadioButton normalTextSpeedRadio = new JRadioButton("Normal");
		panel_9.add(normalTextSpeedRadio, "cell 0 0");

		JRadioButton fastTextSpeedRadio = new JRadioButton("Fast");
		panel_9.add(fastTextSpeedRadio, "cell 1 0");

		BooleanButtonGroup textSpeedRadioGroup = new BooleanButtonGroup(fastTextSpeedRadio, normalTextSpeedRadio);

		gui.setBooleanButtonGroups(SaveField.jpVoice, voiceRadioGroup);
		gui.setBooleanButtonGroups(SaveField.rotate, rotationRadioGroup);
		gui.setBooleanButtonGroups(SaveField.autoEventScrolling, eventRadioGroup);
		gui.setBooleanButtonGroups(SaveField.fastDialogueText, textSpeedRadioGroup);

	}

}
