package View;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class GAMEView extends JPanel {

	GUI gui;
	
	public GAMEView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[]10[]", "[]10[]10[]"));
		
		JPanel GAMEMapPanel = new JPanel();
		GAMEMapPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(GAMEMapPanel, "cell 0 0,growx,aligny top");
		GAMEMapPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_8 = new JLabel("Map:");
		GAMEMapPanel.add(lblNewLabel_8, "cell 0 0,alignx left,aligny center");

		String[] GAMEMaps = {"Title Screen (ma0000)", "Colony 9 (ma0101)", "Tephra Cave (ma0201)", "Bionis' Leg (ma0301)", "Colony 6 (ma0401)",
				"Ether Mine (ma0402)", "Satorl Marsh (ma0501)", "Makna Forest (ma0601)", "Frontier Village (ma0701)", "Bionis' Shoulder (ma0801)",
				"High Entia Tomb (ma0901)", "Eryth Sea (ma1001)", "Alcamoth (ma1101)", "Prison Island (ma1201)", "Prison Island 2 (ma1202)",
				"Valak Mountain (ma1301)", "Sword Valley (ma1401)", "Galahad Fortress (ma1501)", "Fallen Arm (ma1601)", "Beta Fallen Arm (ma1602)",
				"Mechonis Field (ma1701)", "Agniratha (ma1901)", "Central Factory (ma2001)", "Bionis' Interior (ma2101)", "Memory Space (ma2201)",
				"Mechonis Core (ma2301)", "Junks (ma2401)", "Post-Game Colony 9 (ma0102)"};

		DefaultComboBoxModel<String> GAMEMapComboBoxModel = new DefaultComboBoxModel<String>(GAMEMaps);
		JComboBox<String> GAMEMapComboBox = new JComboBox<String>(GAMEMapComboBoxModel);
		gui.set("mapNum", GAMEMapComboBox, null, null, true);
		GAMEMapPanel.add(GAMEMapComboBox, "cell 1 0,growx,aligny top");

		DefaultComboBoxModel<String> GAMEMap2ComboBoxModel = new DefaultComboBoxModel<String>(GAMEMaps);

		JPanel GAMEMap2Panel = new JPanel();
		GAMEMap2Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(GAMEMap2Panel, "cell 1 0,growx,aligny top");
		GAMEMap2Panel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_9 = new JLabel("Map (for NPCs and music):");
		GAMEMap2Panel.add(lblNewLabel_9, "cell 0 0,alignx left,aligny center");
		JComboBox<String> GAMEMap2ComboBox = new JComboBox<String>(GAMEMap2ComboBoxModel);
		gui.set("mapNum2", GAMEMap2ComboBox, null, null, true);
		GAMEMap2Panel.add(GAMEMap2ComboBox, "cell 1 0,growx,aligny top");

		String[] pclist = new String[] {" ", "Shulk", "Reyn", "Fiora", "Dunban", "Sharla", "Riki", "Melia", "Seven", "Dickson", "Mumkhar", "Alvis", "Prologue Dunban", "Other Dunban"};

		DefaultComboBoxModel<String> GAMEPlayer1ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer2ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer3ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer4ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer5ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer6ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer7ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		JPanel GAMECurrentPlayersPanel = new JPanel();
		GAMECurrentPlayersPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(GAMECurrentPlayersPanel, "cell 0 1 2 1,growx,aligny top");
		GAMECurrentPlayersPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_10 = new JLabel("Current Players:");
		GAMECurrentPlayersPanel.add(lblNewLabel_10, "cell 0 0,alignx left,aligny center");
		JComboBox<String> GAMEPlayer1ComboBox = new JComboBox<String>(GAMEPlayer1ComboBoxModel);
		gui.set("player1", GAMEPlayer1ComboBox, null, null, true);
		GAMECurrentPlayersPanel.add(GAMEPlayer1ComboBox, "cell 1 0,growx,aligny top");
		JComboBox<String> GAMEPlayer2ComboBox = new JComboBox<String>(GAMEPlayer2ComboBoxModel);
		gui.set("player2", GAMEPlayer2ComboBox, null, null, true);
		GAMECurrentPlayersPanel.add(GAMEPlayer2ComboBox, "cell 2 0,growx,aligny top");
		JComboBox<String> GAMEPlayer3ComboBox = new JComboBox<String>(GAMEPlayer3ComboBoxModel);
		gui.set("player3", GAMEPlayer3ComboBox, null, null, true);
		GAMECurrentPlayersPanel.add(GAMEPlayer3ComboBox, "cell 3 0,growx,aligny top");

		JPanel GAMEReservePlayerPanel = new JPanel();
		GAMEReservePlayerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(GAMEReservePlayerPanel, "cell 0 2 2 1,growx,aligny top");
		GAMEReservePlayerPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_11 = new JLabel("Reserve Players:");
		GAMEReservePlayerPanel.add(lblNewLabel_11, "cell 0 0,alignx left,aligny center");
		JComboBox<String> GAMEPlayer4ComboBox = new JComboBox<String>(GAMEPlayer4ComboBoxModel);
		gui.set("player4", GAMEPlayer4ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer4ComboBox, "cell 1 0,growx,aligny top");
		JComboBox<String> GAMEPlayer5ComboBox = new JComboBox<String>(GAMEPlayer5ComboBoxModel);
		gui.set("player5", GAMEPlayer5ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer5ComboBox, "cell 2 0,growx,aligny top");
		JComboBox<String> GAMEPlayer6ComboBox = new JComboBox<String>(GAMEPlayer6ComboBoxModel);
		gui.set("player6", GAMEPlayer6ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer6ComboBox, "cell 3 0,growx,aligny top");
		JComboBox<String> GAMEPlayer7ComboBox = new JComboBox<String>(GAMEPlayer7ComboBoxModel);
		gui.set("player7", GAMEPlayer7ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer7ComboBox, "cell 4 0,growx,aligny top");
	}
	
}
