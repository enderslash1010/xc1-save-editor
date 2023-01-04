import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar = new JMenuBar();
	
	// Instance of crc16 class for checksum computation
	private crc16 c = new crc16();
	
	private JLabel currFile;
	private JLabel statusMessage;
	private JFormattedTextField THUMLevelTextField;

	public GUI(SaveFile saveFile) {		
		
		JFrame frame = new JFrame(); // top-level container, what every GUI element goes into
		JFileChooser fc = new JFileChooser(); 
		
		currFile = new JLabel("No File Selected");
		statusMessage = new JLabel("");
		
        frame.setTitle("Xenoblade Chronicles (Wii) Save Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(fc) == 0) {
		    		   String result = saveFile.setFileLocation(fc.getSelectedFile().getAbsolutePath());
		    		   if (!result.equals("")) { // Error with loading file
		    			   setStatusMessage(result);
		    			   return;
		    		   }
		    		   setCurrFile(fc.getSelectedFile().getAbsolutePath());
		    		   setStatusMessage(c.fixChecksums(saveFile, true));
	    		}
			}
        	
        });
        
        fileMenu.add(open);
        menuBar.add(fileMenu);
        
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setStatusMessage(c.fixChecksums(saveFile, true));
        	}
        });
        fileMenu.add(save);
        
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuBar);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        contentPanel.add(tabbedPane);
        
        JPanel THUMPanel = new JPanel();
        tabbedPane.addTab("THUM", null, THUMPanel, null);
        THUMPanel.setLayout(new BorderLayout(0, 0));
        
        JSplitPane splitPane = new JSplitPane();
        THUMPanel.add(splitPane, BorderLayout.NORTH);
        
        JLabel lblNewLabel = new JLabel("Level");
        splitPane.setLeftComponent(lblNewLabel);
        
        THUMLevelTextField = new JFormattedTextField();
        splitPane.setRightComponent(THUMLevelTextField);
        
        JPanel FLAGPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("FLAG", null, FLAGPanel, null);
        
        JPanel GAMEPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("GAME", null, GAMEPanel, null);
        
        JPanel TIMEPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("TIME", null, TIMEPanel, null);
        
        JPanel PCPMPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("PCPM", null, PCPMPanel, null);
        
        JPanel CAMDPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("CAMD", null, CAMDPanel, null);
        
        JPanel ITEMPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("ITEM", null, ITEMPanel, null);
        
        JPanel WTHRPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("WTHR", null, WTHRPanel, null);
        
        JPanel SNDSPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("SNDS", null, SNDSPanel, null);
        
        JPanel MINEPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("MINE", null, MINEPanel, null);
        
        JPanel TBOXPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("TBOX", null, TBOXPanel, null);
        
        JPanel OPTDPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("OPTD", null, OPTDPanel, null);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(BorderLayout.WEST, currFile);
        bottomPanel.add(BorderLayout.EAST, statusMessage);
        
        // Adding all the panels to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
        frame.getContentPane().add(BorderLayout.CENTER, contentPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        frame.setVisible(true);
        
	}
	
	public String getCurrFile() {
		return currFile.getText();
	}
	public void setCurrFile(String s) {
		currFile.setText(s);
	}
	public String getStatusMessage() {
		return statusMessage.getText();
	}
	public void setStatusMessage(String s) {
		statusMessage.setText(s);
	}
}
