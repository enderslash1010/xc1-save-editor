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
public class TBOXView extends JPanel {

	GUI gui;

	JTable TBOXTable;
	private JTextField numBoxes;

	public TBOXView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10", "10[100::359][]"));

		String[] columnNames = new String[] {"Map", "Rank", "x", "y", "z", "Angle (Radians)", "Drop Table"};
		TBOXTable = new JTable();
		TBOXTable.setModel(new DefaultTableModel(
				columnNames,
				21 // number of rows
				));
		
		// set boxMapID column to JComboBox
		TableColumn mapIDColumn = TBOXTable.getColumnModel().getColumn(0);
		JComboBox<String> mapIDComboBox = new JComboBox<String>(gui.Maps);
		mapIDColumn.setCellEditor(new DefaultCellEditor(mapIDComboBox));
		
		// set boxRank column to JComboBox
		TableColumn boxRankColumn = TBOXTable.getColumnModel().getColumn(1);
		JComboBox<String> boxRankComboBox = new JComboBox<String>(new String[] {"Unspecified", "Normal", "Rare", "Super"});
		boxRankColumn.setCellEditor(new DefaultCellEditor(boxRankComboBox));
		
		// document filter for drop table
		TableColumn dropColumn = TBOXTable.getColumnModel().getColumn(6);
		JTextField dropTextField = new JTextField();
		AbstractDocument doc = (AbstractDocument) dropTextField.getDocument();
		doc.setDocumentFilter(gui.uint16);
		dropColumn.setCellEditor(new DefaultCellEditor(dropTextField));

		gui.setArray(SaveField.boxArray, new ArrayField[] {ArrayField.boxMapID, ArrayField.boxRank, ArrayField.xBox, ArrayField.yBox, ArrayField.zBox, ArrayField.boxAngle, ArrayField.boxDropTable}, TBOXTable);

		JScrollPane scrollPane = new JScrollPane(TBOXTable);
		this.add(scrollPane, "cell 0 0,growx,aligny top");
		
		JPanel numBoxesPanel = new JPanel();
		numBoxesPanel.setBorder(new TitledBorder(null, "Number of Boxes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(numBoxesPanel, "cell 0 1,alignx left,aligny top");
		numBoxesPanel.setLayout(new MigLayout("", "10[]10", "[]"));
		
		numBoxes = new JTextField();
		numBoxesPanel.add(numBoxes, "cell 0 0,alignx left,aligny top");
		numBoxes.setColumns(10);
		gui.setTextField(SaveField.numBoxes, numBoxes, gui.uint8);
	}

}
