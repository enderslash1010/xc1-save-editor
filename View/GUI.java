package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import com.google.common.collect.HashBiMap;

import Controller.ArrayField;
import Controller.SaveField;
import Controller.SaveFileController;
import View.ViewEvent.EventType;

/**
 *  GUI
 *  
 *  main window for editor that contains:
 *  	1)	JMenu for user options (Open, Save, etc.)
 *    	2)  JTabbedPane to separate save partitions, and Views for each partition in the tabs
 *    	3)	JLabel for current file
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private ViewListener viewListener;

	private JMenuBar menuBar = new JMenuBar();

	private JLabel currFile;

	private THUMView THUMPanel;
	private FLAGView FLAGPanel;
	private GAMEView GAMEPanel;
	private TIMEView TIMEPanel;
	private PCPMView PCPMPanel;
	private CAMDView CAMDPanel;
	private ITEMView ITEMPanel;
	private WTHRView WTHRPanel;
	//private SNDSView SNDSPanel;
	private MINEView MINEPanel;
	private TBOXView TBOXPanel;
	private OPTDView OPTDPanel;

	/**
	 *   Contains mappings for names to GUI components
	 *   Used to 'talk' with the controller/know what component the controller is 'talking' about
	 *   
	 *   (Internal component name -> JComponent)
	 */
	private HashMap<SaveField, JComponent> componentMap = new HashMap<SaveField, JComponent>();

	/**
	 *  Maps array names to mappings of user-facing column names to internal column names
	 *  
	 *  (Array Name -> (User-facing column name -> Internal column name))
	 */ 
	private HashMap<SaveField, HashBiMap<String, ArrayField>> columnMap = new HashMap<SaveField, HashBiMap<String, ArrayField>>();

	/**
	 *   User-facing Map names
	 */
	public final String[] Maps = {"Title Screen (ma0000)", "Colony 9 (ma0101)", "Tephra Cave (ma0201)", "Bionis' Leg (ma0301)", "Colony 6 (ma0401)",
			"Ether Mine (ma0402)", "Satorl Marsh (ma0501)", "Makna Forest (ma0601)", "Frontier Village (ma0701)", "Bionis' Shoulder (ma0801)",
			"High Entia Tomb (ma0901)", "Eryth Sea (ma1001)", "Alcamoth (ma1101)", "Prison Island (ma1201)", "Fallen Prison Island (ma1202)",
			"Valak Mountain (ma1301)", "Sword Valley (ma1401)", "Galahad Fortress (ma1501)", "Fallen Arm (ma1601)", "Beta Fallen Arm (ma1602)",
			"Mechonis Field (ma1701)", "Agniratha (ma1901)", "Central Factory (ma2001)", "Bionis' Interior (ma2101)", "Memory Space (ma2201)",
			"Mechonis Core (ma2301)", "Junks (ma2401)", "Post-Game Colony 9 (ma0102)"};

	/**
	 *   Integer filters of varying sizes
	 */
	public IntFilter int4 = new IntFilter(4);
	public UIntFilter uint1 = new UIntFilter(1);
	public UIntFilter uint2 = new UIntFilter(2);
	public UIntFilter uint4 = new UIntFilter(4);

	/**
	 * GUI Constructor
	 * Initiates all GUI components and Views
	 * 
	 * @param sfc the <code>SaveFileController</code> that created the GUI
	 */
	public GUI(SaveFileController sfc) {

		this.viewListener = sfc;

		JFrame frame = new JFrame(); // top-level container, what every GUI element goes into
		JFileChooser fc = new JFileChooser();

		frame.setTitle("Xenoblade Chronicles (Wii) Save Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1050, 600);
		frame.setMinimumSize(new Dimension(1100, 500));

		JMenu fileMenu = new JMenu("File");

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION) {
					String fileLocation = fc.getSelectedFile().getAbsolutePath();
					fireViewEvent(ViewEvent.EventType.OPEN_FILE, fileLocation);
				}
			}

		});

		fileMenu.add(open);
		menuBar.add(fileMenu);

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(ViewEvent.EventType.SAVE_FILE, null);
			}
		});
		fileMenu.add(save);

		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(menuBar);

		JPanel contentPanel = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		contentPanel.add(tabbedPane);

		// Save file partition views for each tab in JTabbedPane
		THUMPanel = new THUMView(this);
		tabbedPane.addTab("Save Thumnail", null, THUMPanel, null);

		FLAGPanel = new FLAGView(this);
		tabbedPane.addTab("Flag", null, FLAGPanel, null);

		GAMEPanel = new GAMEView(this);
		tabbedPane.addTab("Game", null, GAMEPanel, null);

		TIMEPanel = new TIMEView(this);
		tabbedPane.addTab("Time", null, TIMEPanel, null);

		PCPMPanel = new PCPMView(this);
		tabbedPane.addTab("Position", null, PCPMPanel, null);

		CAMDPanel = new CAMDView(this);
		tabbedPane.addTab("Camera", null, CAMDPanel, null);

		ITEMPanel = new ITEMView(this);
		tabbedPane.addTab("Items", null, ITEMPanel, null);

		WTHRPanel = new WTHRView(this);
		tabbedPane.addTab("Weather", null, WTHRPanel, null);

//		SNDSPanel = new SNDSView(this);
//		tabbedPane.addTab("SNDS", null, SNDSPanel, null);

		MINEPanel = new MINEView(this);
		tabbedPane.addTab("Ether Mines", null, MINEPanel, null);

		TBOXPanel = new TBOXView(this);
		tabbedPane.addTab("Treasure Boxes", null, TBOXPanel, null);

		OPTDPanel = new OPTDView(this);
		tabbedPane.addTab("Options", null, OPTDPanel, null);


		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		currFile = new JLabel("No File Selected");
		currFile.setHorizontalAlignment(SwingConstants.LEFT);
		bottomPanel.add(currFile);

		// Adding all the panels to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
		frame.getContentPane().add(BorderLayout.CENTER, contentPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		frame.setVisible(true);

		// Starts with null saveFile, so disable user input
		setEnabled(false);
	}

	/**
	 *  Sets the text of the currFile <code>JComponent<code>, which shows which file is currently open
	 * 
	 *  @param s the location of current file shown in the bottom right of the UI
	 */
	public void setCurrFile(String s) {
		currFile.setText(s);
	}

	/**
	 * 	sets <code>JComponent</code> component with specified name (that should come from SaveFile.DataMap) to specified value
	 * 
	 *  @param name the internal name of field
	 *  @param value the value to load into the corresponding JComponenet
	 */
	public void setValue(SaveField name, Object value) {
		JComponent jc = componentMap.get(name);

		if (jc == null) return; // invalid name, or name not mapped to component

		String[] temp = (jc.getClass().toString()).split("\\.");
		String instance = temp[temp.length-1];

		switch (instance) { 
		case "JTextField":
			((JTextField) jc).setText(value.toString());
			break;
		case "JCheckBox": // assumes a checkbox item is associated with a boolean variable
			((JCheckBox) jc).setSelected((boolean) value);
			break;
		case "JComboBox":
			for (int i = 0; i < ((JComboBox<?>) jc).getItemCount(); i++) {
				String curr = ((JComboBox<?>) jc).getItemAt(i).toString();
				if (value.equals(curr)) {
					((JComboBox<?>) jc).setSelectedIndex(i);
					break;
				}
			}
			break;
		case "JBooleanButtonGroup": // assumes value is type boolean
			((JBooleanButtonGroup) jc).setSelected((boolean) value);
			break;
		case "JSlider":
			((JSlider) jc).setValue((int) value);
			break;
		}
	}

	public String getValue(SaveField name) {
		JComponent jc = componentMap.get(name);

		if (jc instanceof JTextField) return ((JTextField) jc).getText();
		else if (jc instanceof JCheckBox) return ((JCheckBox) jc).isSelected() + "";
		else if (jc instanceof JComboBox<?>) return ((JComboBox<?>) jc).getSelectedItem().toString();
		else if (jc instanceof JBooleanButtonGroup) return ((JBooleanButtonGroup) jc).isSelected() + "";
		else if (jc instanceof JSlider) return ((JSlider) jc).getValue() + "";
		else return null;
	}

	/**
	 * Sets <code>JTable</code> cell for Array name at index and colName to the specified value
	 * The index and column name uniquely identify an <code>Array</code> element
	 * 
	 * @param arrName the name of Array from SaveFile.DataMap
	 * @param index the row (or index) of the Array
	 * @param internalColName the internal column name to specify column in the Array
	 * @param value what value to put into the Array at the specified index/internal column name
	 */
	public void setArrayValue(SaveField arrName, int index, ArrayField internalColName, Object value) {
		// get JTable from name
		JTable table = (JTable) componentMap.get(arrName);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// determine row
		int row = index;

		// determine col
		int col = -1;
		String externalColName = (columnMap.get(arrName)).inverse().get(internalColName);
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			if (tableModel.getColumnName(i).equals(externalColName)) {
				col = i;
			}
		}

		// if col == -1, then that internal column is not associated with a column in the GUI, so don't set anything
		if (col == -1) return;

		// set table cell to data
		tableModel.setValueAt(value, row, col);
	}

	/**
	 *  enables/disables all user input boxes
	 *  @param b whether to enable all the JComponents mapped to a field
	 */
	public void setEnabled(boolean b) {
		for (SaveField key : componentMap.keySet()) {
			componentMap.get(key).setEnabled(b);
		}
	}

	/**
	 *  associates a field name with a <code>JTextField</code> by adding the association to componentMap, and adds a <code>DocumentFilter</code> if needed
	 *  
	 *  @param name the name of the field from SaveFile.DataMap
	 *  @param tf the <code>JTextField</code> to associate with name
	 *  @param df the DocumentFilter to use with the JTextField; use null to specify the default DocumentFilter
	 */
	public void setTextField(SaveField name, JTextField tf, DocumentFilter df) {
		componentMap.put(name, tf);

		if (df != null) {
			AbstractDocument doc = (AbstractDocument) tf.getDocument();
			doc.setDocumentFilter(df); // set document filter
		}

		tf.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) { // do nothing
			}

			@Override
			public void focusLost(FocusEvent e) {
				fireViewEvent(ViewEvent.EventType.SET_DATA, name, tf.getText());
			}
			
		});
	}

	/**
	 *  associates a field name with a <code>JCheckBox</code> by adding the association to componentMap
	 * 
	 *  @param name the name of the field (comes from SaveFile.DataMap)
	 *  @param cb the <code>JCheckBox</code> to associate with name
	 */
	public void setCheckBox(SaveField name, JCheckBox cb) {
		componentMap.put(name, cb);

		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(ViewEvent.EventType.SET_DATA, name, (cb.isSelected() ? "true" : "false"));
			}

		});
	}

	/**
	 *  associates a field name with a <code>JComboBox</code> by adding the association to componentMap
	 * 
	 *  @param name the name of the field (comes from SaveFile.DataMap)
	 *  @param cb the <code>JComboBox</code> to associate with name
	 */
	public void setComboBox(SaveField name, JComboBox<?> cb) {
		componentMap.put(name, cb);

		if (name == SaveField.mapNum) { // mapNum determines weather options
			cb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireViewEvent(ViewEvent.EventType.SET_DATA, name, cb.getSelectedItem());
					int mapIndex = Arrays.asList(Maps).indexOf(cb.getSelectedItem());
					WTHRPanel.setWeatherOptions(mapIndex);
				}

			});
		}
		else {

			cb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireViewEvent(ViewEvent.EventType.SET_DATA, name, cb.getSelectedItem());
				}

			});
		}
	}

	/**
	 *  associates a field name with a <code>JSlider</code> by adding the association to componentMap
	 * 
	 *  @param name the name of the field (comes from SaveFile.DataMap)
	 *  @param s the <code>JSlider</code> to associate with name
	 */
	public void setSlider(SaveField name, JSlider s) {
		componentMap.put(name, s);

		s.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (!s.getValueIsAdjusting()) fireViewEvent(ViewEvent.EventType.SET_DATA, name, s.getValue());;
			}

		});
	}

	/**
	 *  associates a field name with a <code>BooleanButtonGroup</code> by adding the association to componentMap
	 * 
	 *  @param name the name of the field (comes from SaveFile.DataMap)
	 *  @param cb the <code>BooleanButtonGroup</code> to associate with name
	 */
	public void setBooleanButtonGroups(SaveField name, BooleanButtonGroup bbg) {
		componentMap.put(name, new JBooleanButtonGroup(bbg));
		bbg.getTrueButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(ViewEvent.EventType.SET_DATA, name, "true");
			}

		});
		bbg.getFalseButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(ViewEvent.EventType.SET_DATA, name, "false");
			}

		});
	}

	/**
	 *  associates an array name with a <code>JTable</code>, and adds column mappings so the JTable can show columns in a different order
	 * 
	 * 	@param arrName the name of array (from SaveFile.DataMap)
	 * 	@param columnMaps the array of column names (from SaveFile.DataMap) in order they should appear in JTable
	 * 	@param jc the JTable object to associate with name
	 */
	public void setArray(SaveField arrName, ArrayField[] columnMaps, JTable jc) {
		if (jc.getColumnCount() != columnMaps.length) return;

		componentMap.put(arrName, jc);

		// map column names
		for (int i = 0; i < columnMaps.length; i++) {
			HashBiMap<String, ArrayField> map;
			if (columnMap.containsKey(arrName)) { // add to existing HashBiMap
				map = columnMap.get(arrName);
			}
			else { // create new HashBiMap
				map = HashBiMap.create();
			}
			map.put(jc.getColumnName(i), columnMaps[i]);
			columnMap.put(arrName, map);
		}

		jc.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int col = e.getColumn();

				// column number determines the dataName, row number determines the index
				if (jc.getModel().getValueAt(row, col) == null) return;
				fireViewEvent(ViewEvent.EventType.SET_ARRAY_DATA, arrName, columnMaps[col], row, jc.getModel().getValueAt(row, col).toString());
			}

		});
	}

	/**
	 *  Shows a pop-up message with a custom message
	 *  @param msg the message shown in the pop-up message
	 */
	public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

	/**
	 *  fires a <code>ViewEvent</code> for <code>SaveFileController<code> to handle
	 * 
	 *  @param type the type of <code>ViewEvent</code>
	 *  @param param the parameters for the <code>ViewEvent</code>
	 */

	private void fireViewEvent(EventType type, String fileLocation) { 
		this.viewListener.viewEventOccurred(new ViewEvent(this, type, fileLocation, null, null, null, null));
	}
	private void fireViewEvent(EventType type, SaveField sf, ArrayField af, Integer index, String value) { 
		this.viewListener.viewEventOccurred(new ViewEvent(this, type, null, sf, af, index, value));
	}
	private void fireViewEvent(EventType type, SaveField sf, String value) { 
		this.viewListener.viewEventOccurred(new ViewEvent(this, type, null, sf, null, null, value));
	}
	private void fireViewEvent(EventType type, SaveField sf, Object value) { 
		this.viewListener.viewEventOccurred(new ViewEvent(this, type, null, sf, null, null, value.toString()));
	}

}
