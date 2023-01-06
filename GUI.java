import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar = new JMenuBar();
	
	private JLabel currFile;
	private JLabel statusMessage;
	
	private SaveFile saveFile = null;
	private JFormattedTextField THUMLevel;

	public GUI() {		
		
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
        			try {
        				String fileLocation = fc.getSelectedFile().getAbsolutePath();
        				saveFile = new SaveFile(fileLocation);
            			setCurrFile(fileLocation);
            			setStatusMessage("Successfully loaded file");
        			}
        			catch (Exception ex) {
        				setStatusMessage("Error with loading file");
        			}
        		}
        	}

        });

        fileMenu.add(open);
        menuBar.add(fileMenu);
        
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// TODO: save changes made in editor to actual file
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
        GridBagLayout gbl_THUMPanel = new GridBagLayout();
        gbl_THUMPanel.columnWidths = new int[]{0, 0, 0};
        gbl_THUMPanel.rowHeights = new int[]{0, 0};
        gbl_THUMPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_THUMPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        THUMPanel.setLayout(gbl_THUMPanel);
        
        JLabel lblNewLabel = new JLabel("Level");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        THUMPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        THUMLevel = new JFormattedTextField();
        GridBagConstraints gbc_THUMLevel = new GridBagConstraints();
        gbc_THUMLevel.fill = GridBagConstraints.HORIZONTAL;
        gbc_THUMLevel.gridx = 1;
        gbc_THUMLevel.gridy = 0;
        THUMPanel.add(THUMLevel, gbc_THUMLevel);
        
        JPanel FLAGPanel = new JPanel();
        tabbedPane.addTab("FLAG", null, FLAGPanel, null);
        GridBagLayout gbl_FLAGPanel = new GridBagLayout();
        gbl_FLAGPanel.columnWidths = new int[]{0};
        gbl_FLAGPanel.rowHeights = new int[]{0};
        gbl_FLAGPanel.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_FLAGPanel.rowWeights = new double[]{Double.MIN_VALUE};
        FLAGPanel.setLayout(gbl_FLAGPanel);
        
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
	public String getTHUMLevelText() {
		return THUMLevel.getText();
	}
	public void setTHUMLevelText(String text) {
		THUMLevel.setText(text);
	}
}
