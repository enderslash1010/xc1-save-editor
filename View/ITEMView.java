package View;
import javax.swing.DefaultCellEditor;
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
	private JTable gemTable;
	
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
		
		String[] gemColumnNames = new String[] {"Gem ID 1", "Gem ID 2", "Rank", "Value", "Inventory Slot", "Unknown 1", "Unknown 2", "Unknown 3"};
		gemTable = new JTable();
		gemTable.setModel(new DefaultTableModel(gemColumnNames, 300));
		
		// set gemID1 to uint11
		TableColumn gemID1Column = gemTable.getColumnModel().getColumn(0);
		JTextField gemID1TextField = new JTextField();
		AbstractDocument doc = (AbstractDocument) gemID1TextField.getDocument();
		doc.setDocumentFilter(gui.uint11);
		gemID1Column.setCellEditor(new DefaultCellEditor(gemID1TextField));
		
		// set gemID2 to uint12
		TableColumn gemID2Column = gemTable.getColumnModel().getColumn(1);
		JTextField gemID2TextField = new JTextField();
		doc = (AbstractDocument) gemID2TextField.getDocument();
		doc.setDocumentFilter(gui.uint12);
		gemID2Column.setCellEditor(new DefaultCellEditor(gemID2TextField));
		
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
		this.add(gemScrollPane, "cell 0 1,growx,aligny top,spanx 2");
	}
	
}
