package View;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;

import Controller.ArrayField;
import Controller.SaveField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ITEMView extends JPanel {
	
	GUI gui;
	
	private JTextField ITEMMoney;
	private JTable gemTable, weaponTable;
	
	public ITEMView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10[]10", "10[]10[]10[]"));
		
		JPanel ITEMMoneyPanel = new JPanel();
		ITEMMoneyPanel.setBorder(new TitledBorder(null, "Money", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(ITEMMoneyPanel, "cell 0 0,growx,aligny top");
		ITEMMoneyPanel.setLayout(new MigLayout("fillx", "10[]10", "5[]5"));

		ITEMMoney = new JTextField();
		ITEMMoneyPanel.add(ITEMMoney, "cell 0 0,growx,aligny top");
		ITEMMoney.setText("");
		ITEMMoney.setColumns(10);
		gui.setTextField(SaveField.money, ITEMMoney, gui.int32);
		
		// weapon array
		// TODO: decide weaponGemxValue by the gem at weaponGemxIndex in gemArray
		String[] weaponColumnNames = {"Weapon ID 1", "Weapon ID 2", "Number of Gem Slots", "Gem 1 Index", "Gem 2 Index", "Gem 3 Index", "Inventory Slot"};
		weaponTable = new JTable();
		weaponTable.setModel(new DefaultTableModel(weaponColumnNames, 150));
		
		// set weaponID1 to uint12
		TableColumn weaponID1Column = weaponTable.getColumnModel().getColumn(0);
		JTextField weaponID1TextField = new JTextField();
		AbstractDocument doc = (AbstractDocument) weaponID1TextField.getDocument();
		doc.setDocumentFilter(gui.uint12);
		weaponID1Column.setCellEditor(new DefaultCellEditor(weaponID1TextField));
		
		// set weaponID2 to uint11
		TableColumn weaponID2Column = weaponTable.getColumnModel().getColumn(1);
		JTextField weaponID2TextField = new JTextField();
		doc = (AbstractDocument) weaponID2TextField.getDocument();
		doc.setDocumentFilter(gui.uint11);
		weaponID2Column.setCellEditor(new DefaultCellEditor(weaponID2TextField));
		
		// set weaponNumGemSlots to uint8
		TableColumn weaponNumGemSlotsColumn = weaponTable.getColumnModel().getColumn(2);
		JTextField weaponNumGemSlotsTextField = new JTextField();
		doc = (AbstractDocument) weaponNumGemSlotsTextField.getDocument();
		doc.setDocumentFilter(gui.uint8);
		weaponNumGemSlotsColumn.setCellEditor(new DefaultCellEditor(weaponNumGemSlotsTextField));
		
		// set weaponGem1Index to uint16
		TableColumn weaponGem1IndexColumn = weaponTable.getColumnModel().getColumn(3);
		JTextField weaponGem1IndexTextField = new JTextField();
		doc = (AbstractDocument) weaponGem1IndexTextField.getDocument();
		doc.setDocumentFilter(gui.uint16);
		weaponGem1IndexColumn.setCellEditor(new DefaultCellEditor(weaponGem1IndexTextField));
		
		// set weaponGem2Index to uint16
		TableColumn weaponGem2IndexColumn = weaponTable.getColumnModel().getColumn(4);
		JTextField weaponGem2IndexTextField = new JTextField();
		doc = (AbstractDocument) weaponGem2IndexTextField.getDocument();
		doc.setDocumentFilter(gui.uint16);
		weaponGem2IndexColumn.setCellEditor(new DefaultCellEditor(weaponGem2IndexTextField));
		
		// set weaponGem3Index to uint16
		TableColumn weaponGem3IndexColumn = weaponTable.getColumnModel().getColumn(5);
		JTextField weaponGem3IndexTextField = new JTextField();
		doc = (AbstractDocument) weaponGem3IndexTextField.getDocument();
		doc.setDocumentFilter(gui.uint16);
		weaponGem3IndexColumn.setCellEditor(new DefaultCellEditor(weaponGem3IndexTextField));
		
		// set weaponInventorySlot to uint8
		TableColumn weaponInventorySlotColumn = weaponTable.getColumnModel().getColumn(6);
		JTextField weaponInventorySlotTextField = new JTextField();
		doc = (AbstractDocument) weaponInventorySlotTextField.getDocument();
		doc.setDocumentFilter(gui.uint8);
		weaponInventorySlotColumn.setCellEditor(new DefaultCellEditor(weaponInventorySlotTextField));
		
		gui.setArray(SaveField.weaponArray, new ArrayField[] {ArrayField.weaponID1, ArrayField.weaponID2, ArrayField.weaponNumGemSlots, ArrayField.weaponGem1Index, ArrayField.weaponGem2Index, ArrayField.weaponGem3Index, ArrayField.weaponInventorySlot}, weaponTable);
		
		JScrollPane weaponScrollPane = new JScrollPane(weaponTable);
		this.add(weaponScrollPane, "cell 0 1,growx,aligny top,spanx 2");
		
		
		String[] gemColumnNames = new String[] {"Gem ID (Name)", "Gem ID (Description)", "Rank", "Value", "Inventory Slot", "Unknown 1", "Unknown 2", "Unknown 3"};
		gemTable = new JTable();
		gemTable.setModel(new DefaultTableModel(gemColumnNames, 300));
		
		final String[] gemNames = {
				"None",
				"Strength Up",
				"Chill Defence",
				"Sleep Resist",
				"Slow Resist",
				"Bind Resist",
				"Buff Time Plus",
				"Weapon Power",
				"Strength Down",
				"Blaze Plus",
				"Blaze Attack",
				"Spike",
				"Revival HP Up",
				"Initial Tension",
				"Aggro Up",
				"EXP Up",
				"Weaken", // Unused
				"HP Up",
				"Poison Defence",
				"Spike Defence",
				"Paralysis Resist",
				"Debuff Resist",
				"Recovery Up",
				"Aura Heal",
				"Damage Heal",
				"Arts Heal",
				"HP Steal",
				"Unbeatable",
				"AP Up",
				"Aquatic Cloak",
				"Auto-Heal Up",
				"Terrain Defence",
				"HP Weaken", // Unused
				"Ether Up",
				"Double Attack",
				"Daze Resist",
				"Pierce Resist",
				"Daze Plus",
				"Phys Def Down",
				"Paralysis",
				"Lightning Attack",
				"Electric Plus",
				"Back Atk Plus",
				"First Attack Plus",
				"Daze Up",
				"Cast Quicken", // Unused
				"Tension Swing",
				"Daze Tension",
				"Ether Weaken", // Unused
				"Ether Def Up",
				"Blaze Defence",
				"Lock-On Resist",
				"Confuse Resist",
				"Critical Resist", // Unused
				"Ether Protect",
				"Slow",
				"Bind",
				"Ether Def Down",
				"Chill Plus",
				"Chill Attack",
				"Auto-Atk Stealth",
				"Arts Stealth",
				"Talent Boost",
				"Heat Sink",
				"Ether Smash", // Unused
				"Agility Up",
				"Topple Resist",
				"Good Footing",
				"Arts Seal Resist",
				"Accuracy Up", // Unused
				"Haste",
				"Topple Plus",
				"Bleed Attack",
				"Bleed Plus",
				"Topple Up",
				"Agility Down",
				"Break",
				"Quick Step",
				"Fall Defence",
				"Aerial Cloak",
				"Agility Weaken", // Unused
				"Muscle Up",
				"Attack Stability",
				"Attack Plus",
				"Critical Up",
				"Bleed Defence",
				"Divine Protect",
				"Physical Protect",
				"Night Vision",
				"Debuff Plus",
				"Armour Power", // Unused
				"Ether Down",
				"Poison Plus",
				"Poison Attack",
				"Aggro Down",
				"Earth Cloak",
				"Muscle Waste", // Unused
				"Unbeatable",
				"Impurity" // Unused
		};
		
		// set gemID1 to JComboBox
		TableColumn gemID1Column = gemTable.getColumnModel().getColumn(0);
		JComboBox<String> gemID1ComboBox = new JComboBox<String>(gemNames);
		gemID1Column.setCellEditor(new DefaultCellEditor(gemID1ComboBox));
		
		// set gemID2 to JComboBox
		TableColumn gemID2Column = gemTable.getColumnModel().getColumn(1);
		gemID2Column.setMinWidth(100);
		JComboBox<String> gemID2ComboBox = new JComboBox<String>(gemNames);
		gemID2Column.setCellEditor(new DefaultCellEditor(gemID2ComboBox));
		
		// set rank to uint3
		TableColumn gemRankColumn = gemTable.getColumnModel().getColumn(2);
		JTextField gemRankTextField = new JTextField();
		doc = (AbstractDocument) gemRankTextField.getDocument();
		doc.setDocumentFilter(gui.uint3);
		gemRankColumn.setCellEditor(new DefaultCellEditor(gemRankTextField));
		
		// set value to uint11
		TableColumn gemValueColumn = gemTable.getColumnModel().getColumn(3);
		JTextField gemValueTextField = new JTextField();
		doc = (AbstractDocument) gemValueTextField.getDocument();
		doc.setDocumentFilter(gui.uint11);
		gemValueColumn.setCellEditor(new DefaultCellEditor(gemValueTextField));
		
		// set gemInventorySlot to uint8
		TableColumn gemInventorySlotColumn = gemTable.getColumnModel().getColumn(4);
		JTextField gemInventorySlotTextField = new JTextField();
		doc = (AbstractDocument) gemInventorySlotTextField.getDocument();
		doc.setDocumentFilter(gui.uint8);
		gemInventorySlotColumn.setCellEditor(new DefaultCellEditor(gemInventorySlotTextField));
		
		// set gemUnk1 to uint5
		TableColumn gemUnk1Column = gemTable.getColumnModel().getColumn(5);
		JTextField gemUnk1TextField = new JTextField();
		doc = (AbstractDocument) gemUnk1TextField.getDocument();
		doc.setDocumentFilter(gui.uint5);
		gemUnk1Column.setCellEditor(new DefaultCellEditor(gemUnk1TextField));
		
		// set gemUnk2 to uint11
		TableColumn gemUnk2Column = gemTable.getColumnModel().getColumn(6);
		JTextField gemUnk2TextField = new JTextField();
		doc = (AbstractDocument) gemUnk2TextField.getDocument();
		doc.setDocumentFilter(gui.uint11);
		gemUnk2Column.setCellEditor(new DefaultCellEditor(gemUnk2TextField));
		
		// set gemUnk3 to uint7
		TableColumn gemUnk3Column = gemTable.getColumnModel().getColumn(7);
		JTextField gemUnk3TextField = new JTextField();
		doc = (AbstractDocument) gemUnk3TextField.getDocument();
		doc.setDocumentFilter(gui.uint7);
		gemUnk3Column.setCellEditor(new DefaultCellEditor(gemUnk3TextField));
		
		gui.setArray(SaveField.gemArray, new ArrayField[] {ArrayField.gemID1, ArrayField.gemID2, ArrayField.gemRank, ArrayField.gemValue, ArrayField.gemInventorySlot, ArrayField.gemUnk1, ArrayField.gemUnk2, ArrayField.gemUnk3}, gemTable);
		
		JScrollPane gemScrollPane = new JScrollPane(gemTable);
		this.add(gemScrollPane, "cell 0 2,growx,aligny top,spanx 2");
	}
	
}
