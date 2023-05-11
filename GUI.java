import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

import com.google.common.collect.HashBiMap;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;

/*
 *  class GUI
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

	/*
	 *   Contains mappings for names to gui components
	 *   
	 *   Used to 'talk' with the controller/know what component the controller is 'talking' about,
	 */
	private HashBiMap<String, JComponent> componentMap = HashBiMap.create(new HashMap<String, JComponent>());

	/*
	 *   Filters for different types of data
	 */
	public IntFilter int4 = new IntFilter(4);
	public UIntFilter uint1 = new UIntFilter(1);
	public UIntFilter uint2 = new UIntFilter(2);
	public UIntFilter uint4 = new UIntFilter(4);

	/*
	 *   FocusListeners for different types of data
	 *   Needed to disallow intermediary strings (like a negative sign) to be kept in a text field upon saving to model
	 */

	public FocusListener textIntFocusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) { // do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			// make sure text isn't empty or "-"
			JTextComponent src = (JTextComponent) e.getSource();
			if (src.getText().equals("") || src.getText().equals("-")) {
				// revert text back to what's in the save file
				fireViewEvent(ViewEvent.GET_DATA, componentMap.inverse().get(src));
			}
			else {
				fireViewEvent(ViewEvent.SET_DATA, componentMap.inverse().get(src) + ":" + getValue(src));
			}
		}

	};
	public FocusListener textFloatFocusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) { // do nothing
		}

		// ensures value in text field is a valid float when focus is lost (so the user can input Infinity, NaN, etc.)
		@Override
		public void focusLost(FocusEvent e) {
			JTextComponent src = (JTextComponent) e.getSource();
			String text = src.getText();

			try {
				float f;
				if (text.length() > 2 && text.substring(0, 2).equals("0x")) { // check which format
					f = Float.intBitsToFloat(Integer.parseInt(text.substring(2), 16));
				}
				else {
					f = Float.parseFloat(text);
				}
				fireViewEvent(ViewEvent.SET_DATA, componentMap.inverse().get(src) + ":" + f);
			}
			catch (NumberFormatException ex) {
				fireViewEvent(ViewEvent.GET_DATA, componentMap.inverse().get(src));
			}

		}

	};
	public FocusListener textStringFocusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) { // do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextComponent src = (JTextComponent) e.getSource();
			fireViewEvent(ViewEvent.SET_DATA, componentMap.inverse().get(src) + ":" + getValue(src));
		}

	};

	/*
	 *   MigLayout layout for panels that hold a label and one other component (like JTextField)
	 *   These types of panels are contained within the panels of the JTabbedPane
	 */
	public MigLayout getDefaultLayout() {
		return new MigLayout(
				"fillx", // Layout Constraints
				"10[min!]10[]10", // Column constraints
				"5[]5"); // Row constraints
	}

	// TODO: write tool tips for fields that need more explanation
	// TODO: convert panels in JTabbedPane to MigLayout
	public GUI(SaveFileController sfc) {

		this.viewListener = sfc;

		JFrame frame = new JFrame(); // top-level container, what every GUI element goes into
		JFileChooser fc = new JFileChooser();

		frame.setTitle("Xenoblade Chronicles (Wii) Save Editor");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1050, 600);

		JMenu fileMenu = new JMenu("File");

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION) {
					String fileLocation = fc.getSelectedFile().getAbsolutePath();
					fireViewEvent(ViewEvent.OPEN_FILE, fileLocation);
					setCurrFile(fileLocation);
					setEnabled(true); // enable fields when a file is opened
					showMessage("Sucessfully Opened File");
				}
			}

		});

		fileMenu.add(open);
		menuBar.add(fileMenu);

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(ViewEvent.SAVE_FILE);
			}
		});
		fileMenu.add(save);

		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(menuBar);

		JPanel contentPanel = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		contentPanel.add(tabbedPane);

		/*
		 *   Save file partition views for each tab in JTabbedPane
		 */
		JPanel THUMPanel = new THUMView(this);
		tabbedPane.addTab("THUM", null, THUMPanel, null);

		JPanel FLAGPanel = new FLAGView(this);
		tabbedPane.addTab("FLAG", null, FLAGPanel, null);

		JPanel GAMEPanel = new GAMEView(this);
		tabbedPane.addTab("GAME", null, GAMEPanel, null);

		JPanel TIMEPanel = new TIMEView(this);
		tabbedPane.addTab("TIME", null, TIMEPanel, null);
		
		JPanel PCPMPanel = new PCPMView(this);
		tabbedPane.addTab("PCPM", null, PCPMPanel, null);
		
		JPanel CAMDPanel = new CAMDView(this);
		tabbedPane.addTab("CAMD", null, CAMDPanel, null);
		
		JPanel ITEMPanel = new ITEMView(this);
		tabbedPane.addTab("ITEM", null, ITEMPanel, null);

		JPanel WTHRPanel = new WTHRView(this);
		tabbedPane.addTab("WTHR", null, WTHRPanel, null);

		JPanel SNDSPanel = new SNDSView(this);
		tabbedPane.addTab("SNDS", null, SNDSPanel, null);

		JPanel MINEPanel = new MINEView(this);
		tabbedPane.addTab("MINE", null, MINEPanel, null);

		JPanel TBOXPanel = new TBOXView(this);
		tabbedPane.addTab("TBOX", null, TBOXPanel, null);

		JPanel OPTDPanel = new OPTDView(this);
		tabbedPane.addTab("OPTD", null, OPTDPanel, null);

		
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

	private void setCurrFile(String s) {
		currFile.setText(s);
	}

	/*
	 * 	sets JComponent component with specified name to specified data (that should come from a SaveFile)
	 */
	public void setValue(String name, Object data) {
		JComponent jc = componentMap.get(name);
		if (jc == null) return; // invalid name

		String[] temp = (jc.getClass().toString()).split("\\.");
		String instance = temp[temp.length-1];
		switch(instance) { // TODO: add more component types here as needed
		case "JTextField":
			((JTextField) jc).setText(data.toString());
			break;
		case "JCheckBox": // assumes a checkbox item is associated with a boolean var
			if ((boolean) data) {
				((JCheckBox) jc).setSelected(true);
			}
			else {
				((JCheckBox) jc).setSelected(false);
			}
			break;
		case "JComboBox":
			for (int i = 0; i < ((JComboBox<?>) jc).getItemCount(); i++) {
				String curr = ((JComboBox<?>) jc).getItemAt(i).toString();
				if (data.equals(curr)) {
					((JComboBox<?>) jc).setSelectedIndex(i);
					break;
				}
			}
			break;
		case "JTable":
			// TODO: load array into table somehow
			break;
		}
	}

	/*
	 *   gets String representation of the value of provided JComponent (i.e. text in a JTextField)
	 */
	private String getValue(JComponent jc) {
		String[] temp = (jc.getClass().toString()).split("\\.");
		String instance = temp[temp.length-1];

		switch (instance) {
		case "JTextField":
			return ((JTextField) jc).getText();
		case "JCheckBox":
			return ((JCheckBox) jc).isSelected() ? "true" : "false"; // assumes a checkbox item is associated with a boolean var
		case "JComboBox":
			return ((JComboBox<?>) jc).getSelectedItem().toString();
		}

		return null;
	}

	// enables/disables all user input boxes
	public void setEnabled(boolean b) {
		for (String key : componentMap.keySet()) {
			componentMap.get(key).setEnabled(b);
		}
	}

	/*
	 *   associates a name string with a JComponent, and adds a DocumentFilter, FocusListener, and actionListener if specified
	 *   
	 *   TODO: can delete param actionListener if all JComboBox, JCheckBox, etc. only use it
	 */
	public void set(String name, JComponent jc, DocumentFilter df, FocusListener fl, boolean actionListener) {
		componentMap.put(name, jc);
		componentMap.inverse().put(jc, name);
		if (df != null && jc instanceof JTextComponent) {
			AbstractDocument doc = (AbstractDocument) ((JTextComponent) jc).getDocument();
			doc.setDocumentFilter(df); // set document filter
		}
		if (fl != null) jc.addFocusListener(fl);
		if (actionListener) {
			// TODO: make these into switch statements
			if (jc instanceof JComboBox) {
				((JComboBox<?>) jc).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						fireViewEvent(ViewEvent.SET_DATA, name + ":" + ((JComboBox<?>) jc).getSelectedItem());
					}

				});
			}
			else if (jc instanceof JCheckBox) {
				((JCheckBox) jc).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						fireViewEvent(ViewEvent.SET_DATA, name + ":" + (((JCheckBox) jc).isSelected() ? "true" : "false"));
					}

				});
			}
			// TODO: add more classes that use actionlisteners if needed
		}
	}

	public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

	private void fireViewEvent(int type, String param) { 
		this.viewListener.viewEventOccurred(new ViewEvent(this, type, param));
	}
	private void fireViewEvent(int type) {
		this.viewListener.viewEventOccurred(new ViewEvent(this, type));
	}

}
