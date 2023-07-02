package View;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MINEView extends JPanel {

	GUI gui;
	
	private JTable MINETable;
	
	public MINEView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fill", "[]", "[]"));
		
		String[] columnNames = new String[] {"Map", "Mine ID", "Number of Harvests", "Cooldown"};
		MINETable = new JTable();
		MINETable.setModel(new DefaultTableModel(
				columnNames,
				150 // number of rows
				));
		
		// TODO: put this (document filters) in GUI's setArray if it can improve readability
		
		// set mapID column to JComboBox
		TableColumn mapIDColumn = MINETable.getColumnModel().getColumn(0); 
		JComboBox<String> mapIDComboBox = new JComboBox<String>(gui.Maps);
		mapIDColumn.setCellEditor(new DefaultCellEditor(mapIDComboBox));
		
		// set mineID column to uint1
		TableColumn mineIDColumn = MINETable.getColumnModel().getColumn(1); 
		JTextField mineIDTextField = new JTextField();
		AbstractDocument doc = (AbstractDocument) (mineIDTextField).getDocument();
		doc.setDocumentFilter(gui.uint1); // set document filter
		mineIDColumn.setCellEditor(new DefaultCellEditor(mineIDTextField));
		
		// set numHarvests to uint1
		TableColumn numHarvestsColumn = MINETable.getColumnModel().getColumn(2);
		JTextField numHarvestsTextField = new JTextField();
		doc = (AbstractDocument) numHarvestsTextField.getDocument();
		doc.setDocumentFilter(gui.uint1);
		numHarvestsColumn.setCellEditor(new DefaultCellEditor(numHarvestsTextField));
		
		// set mineCooldown to uint2
		TableColumn cooldownColumn = MINETable.getColumnModel().getColumn(3);
		JTextField cooldownTextField = new JTextField();
		doc = (AbstractDocument) cooldownTextField.getDocument();
		doc.setDocumentFilter(gui.uint2);
		cooldownColumn.setCellEditor(new DefaultCellEditor(cooldownTextField));
		
		gui.setArray("mineArray", new String[] {"mapID", "minelistID", "numHarvests", "mineCooldown"}, MINETable);

		JScrollPane scrollPane = new JScrollPane(MINETable);
		this.add(scrollPane, "cell 0 0,grow");
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
}
