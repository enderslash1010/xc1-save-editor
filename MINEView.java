import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MINEView extends JPanel {

	GUI gui;
	
	private JTable MINETable;
	
	public MINEView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fill", "[]", "[]"));
		
		MINETable = new JTable();
		MINETable.setModel(new DefaultTableModel(
				new Object[] {"Map", "Mine ID", "Number of Harvests", "Cooldown"},
				151 // number of rows
				));

		JScrollPane scrollPane = new JScrollPane(MINETable);
		this.add(scrollPane, "cell 0 0,grow");
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
}
