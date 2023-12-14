package View;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.SaveField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class WTHRView extends JPanel {

	GUI gui;
	private JTextField WTHRRerollCountdown;
	private JComboBox<String> WTHRForeground;
	private JComboBox<String> WTHRBackground;
	private JTextField WTHRUnknown1;
	private JTextField WTHRUnknown2;

	String[][] weatherValues = new String[][] {
		{"Clear"}, // Title Screen (ma0000)
		{"Clear"}, // Colony 9 (ma0101)
		{"Clear", "Rain", "Storm"}, // Tephra Cave (ma0201)
		{"Clear", "Rain", "Storm"}, // Bionis' Leg (ma0301)
		{"Clear", "Rain", "Storm"}, // Colony 6 (ma0401)
		{"Clear"}, // Ether Mine (ma0402)
		{"Clear", "Dense Fog"}, // Satorl Marsh (ma0501)
		{"Clear", "Heatwave", "Rain"}, // Makna Forest (ma0601)
		{"Clear"}, // Frontier Village (ma0701)
		{"Clear", "Rain", "Storm"}, // Bionis' Shoulder (ma0801)
		{"Clear"}, // High Entia Tomb (ma0901)
		{"Clear", "Storm", "Shooting Stars"}, // Eryth Sea (ma1001)
		{"Clear", "Storm", "Shooting Stars"}, // Alcamoth (ma1101)
		{"Clear", "Storm", "Shooting Stars"}, // Prison Island (ma1201)
		{"Clear"}, // Fallen Prison Island (ma1202)
		{"Clear", "Snow", "Blizzard"}, // Valak Mountain (ma1301)
		{"Clear", "Sandstorm"}, // Sword Valley (ma1401)
		{"Clear"}, // Galahad Fortress (ma1501)
		{"Clear", "Rain", "Storm"}, // Fallen Arm (ma1601)
		{"Clear", "Rain", "Storm"}, // Beta Fallen Arm (ma1602)
		{"Clear"}, // Mechonis Field (ma1701)
		{"Clear"}, // Agniratha (ma1901)
		{"Clear"}, // Central Factory (ma2001)
		{"Clear", "Slumber"}, // Bionis' Interior (ma2101)
		{"Clear"}, // Memory Space (ma2201)
		{"Rebirth"}, // Mechonis Core (ma2301)
		{"Clear"}, // Junks (ma2401)
		{"Clear"} // Post-Game Colony 9 (ma0102)
	};

	public WTHRView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[]10[]10[]10[]10", "[]10[]10[]"));

		JPanel WTHRRerollPanel = new JPanel();
		WTHRRerollPanel.setBorder(new TitledBorder(null, "Reroll Countdown", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(WTHRRerollPanel, "cell 0 0,grow");
		WTHRRerollPanel.setLayout(new MigLayout("fill", "10[]10", "5[]5"));

		WTHRRerollCountdown = new JTextField();
		WTHRRerollPanel.add(WTHRRerollCountdown, "cell 0 0,growx");
		WTHRRerollCountdown.setColumns(10);
		gui.setTextField(SaveField.weatherReroll, WTHRRerollCountdown, null);

		JPanel WTHRMapPanel = new JPanel();
		WTHRMapPanel.setBorder(new TitledBorder(null, "Map", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(WTHRMapPanel, "cell 1 0,grow");
		WTHRMapPanel.setLayout(new MigLayout("fill", "10[]10", "5[]5"));

		JComboBox<String> WTHRMap = new JComboBox<String>(gui.Maps);
		WTHRMap.setToolTipText("This map associated with the weather doesn't seem to do anything");
		WTHRMapPanel.add(WTHRMap, "cell 0 0,growx");
		gui.setComboBox(SaveField.weatherMap, WTHRMap);

		JPanel WTHRForegroundPanel = new JPanel();
		WTHRForegroundPanel.setBorder(new TitledBorder(null, "Foreground Weather", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(WTHRForegroundPanel, "cell 2 0,grow");
		WTHRForegroundPanel.setLayout(new MigLayout("fill", "10[]10", "5[]5"));

		WTHRForeground = new JComboBox<String>(weatherValues[0]);
		WTHRForegroundPanel.add(WTHRForeground, "cell 0 0,growx");
		WTHRForeground.setToolTipText("Selection based on Map in the \"Game\" tab");
		gui.setComboBox(SaveField.foregroundWeather, WTHRForeground);

		JPanel WTHRBackgroundPanel = new JPanel();
		WTHRBackgroundPanel.setBorder(new TitledBorder(null, "Background Weather", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(WTHRBackgroundPanel, "cell 3 0,grow");
		WTHRBackgroundPanel.setLayout(new MigLayout("fill", "10[]10", "5[]5"));

		WTHRBackground = new JComboBox<String>(weatherValues[0]);
		WTHRBackgroundPanel.add(WTHRBackground, "cell 0 0,growx");
		WTHRBackground.setToolTipText("Selection based on Map in the \"Game\" tab");
		gui.setComboBox(SaveField.backgroundWeather, WTHRBackground);

		JPanel WTHRWeatherPanel_1 = new JPanel();
		WTHRWeatherPanel_1.setBorder(new TitledBorder(null, "Unknown 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(WTHRWeatherPanel_1, "cell 0 1,grow");
		WTHRWeatherPanel_1.setLayout(new MigLayout("fill", "10[]10", "5[]5"));

		WTHRUnknown1 = new JTextField();
		WTHRWeatherPanel_1.add(WTHRUnknown1, "cell 0 0,growx");
		WTHRUnknown1.setColumns(10);
		gui.setTextField(SaveField.weatherUnknown1, WTHRUnknown1, gui.uint16);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Unknown 2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel, "cell 1 1,grow");
		panel.setLayout(new MigLayout("fill", "10[]10", "5[]5"));

		WTHRUnknown2 = new JTextField();
		panel.add(WTHRUnknown2, "cell 0 0,growx");
		WTHRUnknown2.setColumns(10);
		gui.setTextField(SaveField.weatherUnknown2, WTHRUnknown2, gui.uint16);
	}
	
	public void setWeatherOptions(int index) {
		WTHRForeground.setModel(new DefaultComboBoxModel<String>(weatherValues[index]));
		WTHRBackground.setModel(new DefaultComboBoxModel<String>(weatherValues[index]));
		
		// Ensures weather selection from previously selected map is reset, so the new selection reflects what's in the save file model
		WTHRForeground.setSelectedIndex(0);
		WTHRBackground.setSelectedIndex(0);
	}

}
