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
		
		String[] gemColumnNames = new String[] {"Gem ID 1", "Gem ID 2", "Rank", "Value", "Inventory Slot"};
		gemTable = new JTable();
		gemTable.setModel(new DefaultTableModel(gemColumnNames, 300));
		
		// set gemID1 to uint2
		TableColumn gemID1Column = gemTable.getColumnModel().getColumn(0);
		JTextField gemID1TextField = new JTextField();
		AbstractDocument doc = (AbstractDocument) gemID1TextField.getDocument();
		doc.setDocumentFilter(gui.uint11);
		gemID1Column.setCellEditor(new DefaultCellEditor(gemID1TextField));
		
		// set gemID2 to uint2
		TableColumn gemID2Column = gemTable.getColumnModel().getColumn(1);
		JTextField gemID2TextField = new JTextField();
		doc = (AbstractDocument) gemID2TextField.getDocument();
		doc.setDocumentFilter(gui.uint12);
		gemID2Column.setCellEditor(new DefaultCellEditor(gemID2TextField));
		
		// set rank to uint1
		TableColumn gemRankColumn = gemTable.getColumnModel().getColumn(2);
		JTextField gemRankTextField = new JTextField();
		doc = (AbstractDocument) gemRankTextField.getDocument();
		doc.setDocumentFilter(gui.uint3);
		gemRankColumn.setCellEditor(new DefaultCellEditor(gemRankTextField));
		
		// set value to uint2
		TableColumn gemValueColumn = gemTable.getColumnModel().getColumn(3);
		JTextField gemValueTextField = new JTextField();
		doc = (AbstractDocument) gemValueTextField.getDocument();
		doc.setDocumentFilter(gui.uint11);
		gemValueColumn.setCellEditor(new DefaultCellEditor(gemValueTextField));
		
		// set gemInventorySlot to uint1
		TableColumn gemInventorySlotColumn = gemTable.getColumnModel().getColumn(4);
		JTextField gemInventorySlotTextField = new JTextField();
		doc = (AbstractDocument) gemInventorySlotTextField.getDocument();
		doc.setDocumentFilter(gui.uint8);
		gemInventorySlotColumn.setCellEditor(new DefaultCellEditor(gemInventorySlotTextField));
		
		gui.setArray(SaveField.gemArray, new ArrayField[] {ArrayField.gemID1, ArrayField.gemID2, ArrayField.gemRank, ArrayField.gemValue, ArrayField.gemInventorySlot}, gemTable);
		
		JScrollPane gemScrollPane = new JScrollPane(gemTable);
		this.add(gemScrollPane, "cell 0 1,growx,aligny top,spanx 2");
	}
	
}
