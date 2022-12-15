import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	JFrame frame = new JFrame();
	JFileChooser fc = new JFileChooser();
	JTextArea text = new JTextArea();
	
	JLabel leftLabel = new JLabel("No File Selected");
	JLabel rightLabel = new JLabel("");
	
	JMenuBar menuBar = new JMenuBar();
	
	JSplitPane splitPane;
	
	JList<String> sections;
	JPanel[] sectionPanels = new JPanel[crc16.sectionNames.length];
	int currSectionPanel = 0;
	
	crc16 c;

	public GUI() {		
        frame.setTitle("Xenoblade Chronicles (Wii) Save Modifier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        text.setEditable(false);
        
        // section panel views
        for (int i = 0; i < sectionPanels.length; i++) { // init panels
        	sectionPanels[i] = new JPanel(new BorderLayout());
        	JLabel sectionLabel = new JLabel(crc16.sectionNames[i]);
        	sectionPanels[i].add(sectionLabel);
        }
        
        // section list
        sections = new JList<String>(crc16.sectionNames);
        sections.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sections.addListSelectionListener(new CustomListSelectionListener());
        //sections.setSelectedIndex(currSectionPanel);
        
        // menu
        CustomActionListener handler = new CustomActionListener();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(handler);
        JMenuItem computeAgain = new JMenuItem("Fix Checksums");
        computeAgain.addActionListener(handler);
        
        fileMenu.add(open);
        fileMenu.add(computeAgain);
        menuBar.add(fileMenu);
        
        // panels
        JPanel north = new JPanel(new BorderLayout());
        north.add(menuBar);
      
        JPanel middle = new JPanel(new BorderLayout());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sections, sectionPanels[currSectionPanel]);
        middle.add(splitPane);
        
        JPanel south = new JPanel(new BorderLayout());
        south.add(BorderLayout.WEST, leftLabel);
        south.add(BorderLayout.EAST, rightLabel);
        
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, north);
        frame.getContentPane().add(BorderLayout.CENTER, middle);
        frame.getContentPane().add(BorderLayout.SOUTH, south);
        frame.setVisible(true);
        
	}
	
	class CustomActionListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    
	    	switch (((JMenuItem)e.getSource()).getText()) {
	    		case "Open": 
	    			if (fc.showOpenDialog(fc) == 0) {
		    		   clearText();
		    		   c.setFileLocation(fc.getSelectedFile().getAbsolutePath());
		    		   leftLabel.setText(fc.getSelectedFile().getAbsolutePath());
		    		   String result = c.computeCRC16();
		    		   if (result.length() > 0) { // checksums modified
		    			   rightLabel.setText("Sections Fixed: " + result.substring(0, result.length() - 3));
		    		   }
		    		   else { // savefile was not modified
		    			   rightLabel.setText("Sections Already Fixed");
		    		   }
	    			}
	    			break;
	    		case "Fix Checksums":
	    			clearText();
	    			String result = c.computeCRC16();
		    		if (result.length() > 0) { // checksums modified
		    			rightLabel.setText("Sections Fixed: " + result.substring(0, result.length() - 3));
		    		}
		    		else { // savefile was not modified
		    			rightLabel.setText("Sections Already Fixed");
		    		}
	    			break;
	    	}
	    }
	}
	
	class CustomListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			//splitPane.remove(sectionPanels[currSectionPanel]);
			splitPane.add(sectionPanels[arg0.getFirstIndex()]);
		}
		
	}
	
	public void setText(String s) {
		text.setText(s);
	}
	
	public void addText(String s) {
		text.setText(text.getText() + s);
	}
	
	public void clearText() {
		text.setText("");
	}
	
	
	public void addcrc16(crc16 c) {
		this.c = c;
	}
	
}
