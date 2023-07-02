package View;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GAMEView extends JPanel {

	GUI gui;
	
	public GAMEView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10[]", "10[]10[]10[]"));
		
		String[] GAMEMaps = {"Title Screen (ma0000)", "Colony 9 (ma0101)", "Tephra Cave (ma0201)", "Bionis' Leg (ma0301)", "Colony 6 (ma0401)",
				"Ether Mine (ma0402)", "Satorl Marsh (ma0501)", "Makna Forest (ma0601)", "Frontier Village (ma0701)", "Bionis' Shoulder (ma0801)",
				"High Entia Tomb (ma0901)", "Eryth Sea (ma1001)", "Alcamoth (ma1101)", "Prison Island (ma1201)", "Prison Island 2 (ma1202)",
				"Valak Mountain (ma1301)", "Sword Valley (ma1401)", "Galahad Fortress (ma1501)", "Fallen Arm (ma1601)", "Beta Fallen Arm (ma1602)",
				"Mechonis Field (ma1701)", "Agniratha (ma1901)", "Central Factory (ma2001)", "Bionis' Interior (ma2101)", "Memory Space (ma2201)",
				"Mechonis Core (ma2301)", "Junks (ma2401)", "Post-Game Colony 9 (ma0102)"};
		
		JPanel GAMEMapPanel = new JPanel();
		GAMEMapPanel.setBorder(new TitledBorder(null, "Map", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(GAMEMapPanel, "cell 0 0,growx,aligny top");
		GAMEMapPanel.setLayout(new MigLayout("fillx", "10[]10", "10[]10"));

		DefaultComboBoxModel<String> GAMEMapComboBoxModel = new DefaultComboBoxModel<String>(GAMEMaps);
		JComboBox<String> GAMEMapComboBox = new JComboBox<String>(GAMEMapComboBoxModel);
		gui.setComboBox("mapNum", GAMEMapComboBox);
		GAMEMapPanel.add(GAMEMapComboBox, "cell 0 0,growx,aligny top");

		DefaultComboBoxModel<String> GAMEMap2ComboBoxModel = new DefaultComboBoxModel<String>(GAMEMaps);

		JPanel GAMEMap2Panel = new JPanel();
		GAMEMap2Panel.setBorder(new TitledBorder(null, "Map (for NPCs and music)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(GAMEMap2Panel, "cell 1 0,grow");
		GAMEMap2Panel.setLayout(new MigLayout("fillx", "10[]10", "10[]10"));
		JComboBox<String> GAMEMap2ComboBox = new JComboBox<String>(GAMEMap2ComboBoxModel);
		gui.setComboBox("mapNum2", GAMEMap2ComboBox);
		GAMEMap2Panel.add(GAMEMap2ComboBox, "cell 0 0,growx,aligny top");

		String[] pclist = new String[] {" ", "Shulk", "Reyn", "Fiora", "Dunban", "Sharla", "Riki", "Melia", "Seven", "Dickson", "Mumkhar", "Alvis", "Prologue Dunban", "Other Dunban"};

		DefaultComboBoxModel<String> GAMEPlayer1ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer2ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer3ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer4ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer5ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer6ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> GAMEPlayer7ComboBoxModel = new DefaultComboBoxModel<String>(pclist);

		JPanel GAMECurrentPlayersPanel = new JPanel();
		GAMECurrentPlayersPanel.setBorder(new TitledBorder(null, "Current Players", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(GAMECurrentPlayersPanel, "cell 0 1 2 1,growx,aligny top");
		GAMECurrentPlayersPanel.setLayout(new MigLayout("fillx", "10[]10[]10", "10[]10"));
		JComboBox<String> GAMEPlayer1ComboBox = new JComboBox<String>(GAMEPlayer1ComboBoxModel);
		gui.setComboBox("player1", GAMEPlayer1ComboBox);
		GAMECurrentPlayersPanel.add(GAMEPlayer1ComboBox, "cell 0 0,growx,aligny top");
		JComboBox<String> GAMEPlayer2ComboBox = new JComboBox<String>(GAMEPlayer2ComboBoxModel);
		gui.setComboBox("player2", GAMEPlayer2ComboBox);
		GAMECurrentPlayersPanel.add(GAMEPlayer2ComboBox, "cell 1 0,growx,aligny top");
		JComboBox<String> GAMEPlayer3ComboBox = new JComboBox<String>(GAMEPlayer3ComboBoxModel);
		gui.setComboBox("player3", GAMEPlayer3ComboBox);
		GAMECurrentPlayersPanel.add(GAMEPlayer3ComboBox, "cell 3 0,growx,aligny top");

		JPanel GAMEReservePlayerPanel = new JPanel();
		GAMEReservePlayerPanel.setBorder(new TitledBorder(null, "Reserve Players", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(GAMEReservePlayerPanel, "cell 0 2 2 1,growx,aligny top");
		GAMEReservePlayerPanel.setLayout(new MigLayout("fillx", "10[]10[]10", "10[]10"));
		JComboBox<String> GAMEPlayer4ComboBox = new JComboBox<String>(GAMEPlayer4ComboBoxModel);
		gui.setComboBox("player4", GAMEPlayer4ComboBox);
		GAMEReservePlayerPanel.add(GAMEPlayer4ComboBox, "cell 0 0,growx,aligny top");
		JComboBox<String> GAMEPlayer5ComboBox = new JComboBox<String>(GAMEPlayer5ComboBoxModel);
		gui.setComboBox("player5", GAMEPlayer5ComboBox);
		GAMEReservePlayerPanel.add(GAMEPlayer5ComboBox, "cell 1 0,growx,aligny top");
		JComboBox<String> GAMEPlayer6ComboBox = new JComboBox<String>(GAMEPlayer6ComboBoxModel);
		gui.setComboBox("player6", GAMEPlayer6ComboBox);
		GAMEReservePlayerPanel.add(GAMEPlayer6ComboBox, "cell 3 0,growx,aligny top");
		JComboBox<String> GAMEPlayer7ComboBox = new JComboBox<String>(GAMEPlayer7ComboBoxModel);
		gui.setComboBox("player7", GAMEPlayer7ComboBox);
		GAMEReservePlayerPanel.add(GAMEPlayer7ComboBox, "cell 4 0,growx,aligny top");
	}
	
}
