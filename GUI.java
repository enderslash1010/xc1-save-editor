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

/*
 *  class GUI
 *  
 *  main window for editor that contains:
 *  	1)	JMenu for user options (Open, Save, etc.)
 *    	2)  JTabbedPane to separate save partitions
 *    	3)	Two JLabels for current file and status messages
 *    
 *  TODO: make the above actually true; make separate Views for each partition (THUMView, FLAGView, etc.)
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
	 *   Declaration of all gui fields
	 */
	private JTextField THUMLevel, THUMName, THUMPlayTimeHours, THUMPlayTimeMinutes, THUMSaveTimeDay, THUMSaveTimeMonth, THUMSaveTimeYear, THUMSaveTimeHour, THUMSaveTimeMinute;
	private JTextField FLAGScenarioNum;
	private JTextField PCPMPlayer1x, PCPMPlayer1y, PCPMPlayer1z, PCPMPlayer2x, PCPMPlayer2y, PCPMPlayer2z, PCPMPlayer3x, PCPMPlayer3y, PCPMPlayer3z, PCPMPlayer1Angle, PCPMPlayer2Angle, PCPMPlayer3Angle;
	private JTextField TIMEPlayTime, TIMEDayCounter, TIMEDayTime, TIMEYearCounter;
	private JTextField CAMDVPos, CAMDHPos, CAMDDistance;
	private JTextField ITEMMoney;
	private JTable MINETable;

	/*
	 *   FocusListeners for different types of data
	 *   Needed to disallow intermediary strings (like a negative sign) to be kept in a text field upon saving to model
	 */
	
	private FocusListener textIntFocusListener = new FocusListener() {

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
	private FocusListener textFloatFocusListener = new FocusListener() {

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
	private FocusListener textStringFocusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) { // do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextComponent src = (JTextComponent) e.getSource();
			fireViewEvent(ViewEvent.SET_DATA, componentMap.inverse().get(src) + ":" + getValue(src));
		}
		
	};

	// TODO: write tool tips for fields that need more explanation
	// TODO: convert panels in JTabbedPane to MigLayout
	public GUI(SaveFileController sfc) {
		
		this.viewListener = sfc;

		IntFilter int4 = new IntFilter(4);
		
		UIntFilter uint1 = new UIntFilter(1);
		UIntFilter uint2 = new UIntFilter(2);
		UIntFilter uint4 = new UIntFilter(4);

		JFrame frame = new JFrame(); // top-level container, what every GUI element goes into
		JFileChooser fc = new JFileChooser();

		frame.setTitle("Xenoblade Chronicles (Wii) Save Editor");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1055, 600);

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

		JPanel THUMPanel = new JPanel();
		tabbedPane.addTab("THUM", null, THUMPanel, null);
		FlowLayout fl_THUMPanel = new FlowLayout(FlowLayout.LEFT, 10, 10);
		THUMPanel.setLayout(fl_THUMPanel);

		JPanel THUMLevelPanel = new JPanel();
		THUMLevelPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMLevelPanel);

		JLabel lblNewLabel = new JLabel("Level:");
		THUMLevelPanel.add(lblNewLabel);

		THUMLevel = new JTextField();
		THUMLevel.setColumns(4);
		set("level", THUMLevel, uint2, textIntFocusListener, false);
		THUMLevelPanel.add(THUMLevel);

		JPanel THUMNamePanel = new JPanel();
		THUMNamePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMNamePanel);
		THUMNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Name Text:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		THUMNamePanel.add(lblNewLabel_1);

		THUMName = new JTextField();
		THUMName.setColumns(32);
		set("name", THUMName, new StringFilter(32), textStringFocusListener, false);
		THUMNamePanel.add(THUMName);

		JPanel THUMPlayTimePanel = new JPanel();
		THUMPlayTimePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMPlayTimePanel);

		JLabel lblNewLabel_2 = new JLabel("Play Time:");
		THUMPlayTimePanel.add(lblNewLabel_2);

		THUMPlayTimeHours = new JTextField();
		THUMPlayTimeHours.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMPlayTimeHours.setColumns(4);
		set("playTimeHours", THUMPlayTimeHours, uint2, textIntFocusListener, false);
		THUMPlayTimePanel.add(THUMPlayTimeHours);

		JLabel lblNewLabel_3 = new JLabel(":");
		THUMPlayTimePanel.add(lblNewLabel_3);

		THUMPlayTimeMinutes = new JTextField();
		THUMPlayTimeMinutes.setColumns(4);
		set("playTimeMins", THUMPlayTimeMinutes, uint2, textIntFocusListener, false);
		THUMPlayTimePanel.add(THUMPlayTimeMinutes);


		JPanel THUMSaveTimePanel = new JPanel();
		THUMSaveTimePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMSaveTimePanel);
		THUMSaveTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_4 = new JLabel("Save Time:");
		THUMSaveTimePanel.add(lblNewLabel_4);

		THUMSaveTimeDay = new JTextField();
		THUMSaveTimeDay.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeDay.setColumns(3);
		set("saveTimeDay", THUMSaveTimeDay, uint1, textIntFocusListener, false);
		THUMSaveTimePanel.add(THUMSaveTimeDay);

		JLabel lblNewLabel_5 = new JLabel(" / ");
		THUMSaveTimePanel.add(lblNewLabel_5);

		THUMSaveTimeMonth = new JTextField();
		THUMSaveTimeMonth.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeMonth.setColumns(3);
		set("saveTimeMonth", THUMSaveTimeMonth, uint2, textIntFocusListener, false);
		THUMSaveTimePanel.add(THUMSaveTimeMonth);

		JLabel lblNewLabel_5_1 = new JLabel(" / ");
		THUMSaveTimePanel.add(lblNewLabel_5_1);

		THUMSaveTimeYear = new JTextField();
		THUMSaveTimeYear.setColumns(5);
		set("saveTimeYear", THUMSaveTimeYear, uint2, textIntFocusListener, false);
		THUMSaveTimePanel.add(THUMSaveTimeYear);

		JLabel lblNewLabel_5_2 = new JLabel("     ");
		THUMSaveTimePanel.add(lblNewLabel_5_2);

		THUMSaveTimeHour = new JTextField();
		THUMSaveTimeHour.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeHour.setColumns(3);
		set("saveTimeHour", THUMSaveTimeHour, uint1, textIntFocusListener, false);
		THUMSaveTimePanel.add(THUMSaveTimeHour);

		JLabel lblNewLabel_5_3 = new JLabel(":");
		THUMSaveTimePanel.add(lblNewLabel_5_3);

		THUMSaveTimeMinute = new JTextField();
		THUMSaveTimeMinute.setColumns(3);
		set("saveTimeMinute", THUMSaveTimeMinute, uint1, textIntFocusListener, false);
		THUMSaveTimePanel.add(THUMSaveTimeMinute);

		JPanel THUMNGPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) THUMNGPanel.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setVgap(3);
		THUMNGPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMNGPanel);

		JCheckBox THUMNgPlusCheckBox = new JCheckBox("New Game+");
		set("ng+Flag", THUMNgPlusCheckBox, null, null, true);
		THUMNGPanel.add(THUMNgPlusCheckBox);

		JCheckBox THUMSystemSaveCheckBox = new JCheckBox("System Save");
		set("systemSaveFlag", THUMSystemSaveCheckBox, null, null, true);
		THUMNGPanel.add(THUMSystemSaveCheckBox);
		
		JPanel THUMPicSlotPanel = new JPanel();
		THUMPicSlotPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMPicSlotPanel);
		
		JLabel lblNewLabel_6 = new JLabel("Picture Slots:");
		THUMPicSlotPanel.add(lblNewLabel_6);
		
		String[] pclist = new String[] {" ", "Shulk", "Reyn", "Fiora", "Dunban", "Sharla", "Riki", "Melia", "Seven", "Dickson", "Mumkhar", "Alvis", "Prologue Dunban", "Other Dunban"};
		
		// We need these DefaultComboBoxModels because WindowBuilder doesn't like putting array (pclist) directly in JComboBox initialization for some reason
		DefaultComboBoxModel<String> THUMPicSlot1Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot1 = new JComboBox<String>(THUMPicSlot1Model);
		set("picSlot1", THUMPicSlot1, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot1);
		
		DefaultComboBoxModel<String> THUMPicSlot2Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot2 = new JComboBox<String>(THUMPicSlot2Model);
		set("picSlot2", THUMPicSlot2, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot2);
		
		DefaultComboBoxModel<String> THUMPicSlot3Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot3 = new JComboBox<String>(THUMPicSlot3Model);
		set("picSlot3", THUMPicSlot3, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot3);
		
		DefaultComboBoxModel<String> THUMPicSlot4Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot4 = new JComboBox<String>(THUMPicSlot4Model);
		set("picSlot4", THUMPicSlot4, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot4);
		
		DefaultComboBoxModel<String> THUMPicSlot5Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot5 = new JComboBox<String>(THUMPicSlot5Model);
		set("picSlot5", THUMPicSlot5, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot5);
		
		DefaultComboBoxModel<String> THUMPicSlot6Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot6 = new JComboBox<String>(THUMPicSlot6Model);
		set("picSlot6", THUMPicSlot6, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot6);
		
		DefaultComboBoxModel<String> THUMPicSlot7Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot7 = new JComboBox<String>(THUMPicSlot7Model);
		set("picSlot7", THUMPicSlot7, null, null, true);
		THUMPicSlotPanel.add(THUMPicSlot7);

		JPanel FLAGPanel = new JPanel();
		tabbedPane.addTab("FLAG", null, FLAGPanel, null);
		FLAGPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		JPanel FLAGScenarioPanel = new JPanel();
		FLAGScenarioPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		FLAGPanel.add(FLAGScenarioPanel);
		
		JLabel lblNewLabel_7 = new JLabel("Scenario Number:");
		FLAGScenarioPanel.add(lblNewLabel_7);
		
		FLAGScenarioNum = new JTextField();
		FLAGScenarioNum.setColumns(4);
		set("scenarioNum", FLAGScenarioNum, uint2, textIntFocusListener, false);
		FLAGScenarioPanel.add(FLAGScenarioNum);

		JPanel GAMEPanel = new JPanel();
		tabbedPane.addTab("GAME", null, GAMEPanel, null);
		GAMEPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		JPanel GAMEMapPanel = new JPanel();
		GAMEMapPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMEMapPanel);
		
		JLabel lblNewLabel_8 = new JLabel("Map:");
		GAMEMapPanel.add(lblNewLabel_8);
		
		String[] GAMEMaps = {"Title Screen (ma0000)", "Colony 9 (ma0101)", "Tephra Cave (ma0201)", "Bionis' Leg (ma0301)", "Colony 6 (ma0401)",
				"Ether Mine (ma0402)", "Satorl Marsh (ma0501)", "Makna Forest (ma0601)", "Frontier Village (ma0701)", "Bionis' Shoulder (ma0801)",
				"High Entia Tomb (ma0901)", "Eryth Sea (ma1001)", "Alcamoth (ma1101)", "Prison Island (ma1201)", "Prison Island 2 (ma1202)",
				"Valak Mountain (ma1301)", "Sword Valley (ma1401)", "Galahad Fortress (ma1501)", "Fallen Arm (ma1601)", "Beta Fallen Arm (ma1602)",
				"Mechonis Field (ma1701)", "Agniratha (ma1901)", "Central Factory (ma2001)", "Bionis' Interior (ma2101)", "Memory Space (ma2201)",
				"Mechonis Core (ma2301)", "Junks (ma2401)", "Post-Game Colony 9 (ma0102)"};
		
		DefaultComboBoxModel<String> GAMEMapComboBoxModel = new DefaultComboBoxModel<String>(GAMEMaps);
		JComboBox<String> GAMEMapComboBox = new JComboBox<String>(GAMEMapComboBoxModel);
		set("mapNum", GAMEMapComboBox, null, null, true);
		GAMEMapPanel.add(GAMEMapComboBox);
		
		JPanel GAMEMap2Panel = new JPanel();
		GAMEMap2Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMEMap2Panel);
		
		JLabel lblNewLabel_9 = new JLabel("Map (for NPCs and music):");
		GAMEMap2Panel.add(lblNewLabel_9);
		
		DefaultComboBoxModel<String> GAMEMap2ComboBoxModel = new DefaultComboBoxModel<String>(GAMEMaps);
		JComboBox<String> GAMEMap2ComboBox = new JComboBox<String>(GAMEMap2ComboBoxModel);
		set("mapNum2", GAMEMap2ComboBox, null, null, true);
		GAMEMap2Panel.add(GAMEMap2ComboBox);
		
		JPanel GAMECurrentPlayersPanel = new JPanel();
		GAMECurrentPlayersPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMECurrentPlayersPanel);
		
		JLabel lblNewLabel_10 = new JLabel("Current Players:");
		GAMECurrentPlayersPanel.add(lblNewLabel_10);
		
		DefaultComboBoxModel<String> GAMEPlayer1ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer1ComboBox = new JComboBox<String>(GAMEPlayer1ComboBoxModel);
		set("player1", GAMEPlayer1ComboBox, null, null, true);
		GAMECurrentPlayersPanel.add(GAMEPlayer1ComboBox);
		
		DefaultComboBoxModel<String> GAMEPlayer2ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer2ComboBox = new JComboBox<String>(GAMEPlayer2ComboBoxModel);
		set("player2", GAMEPlayer2ComboBox, null, null, true);
		GAMECurrentPlayersPanel.add(GAMEPlayer2ComboBox);
		
		DefaultComboBoxModel<String> GAMEPlayer3ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer3ComboBox = new JComboBox<String>(GAMEPlayer3ComboBoxModel);
		set("player3", GAMEPlayer3ComboBox, null, null, true);
		GAMECurrentPlayersPanel.add(GAMEPlayer3ComboBox);
		
		JPanel GAMEReservePlayerPanel = new JPanel();
		GAMEReservePlayerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMEReservePlayerPanel);
		
		JLabel lblNewLabel_11 = new JLabel("Reserve Players:");
		GAMEReservePlayerPanel.add(lblNewLabel_11);
		
		DefaultComboBoxModel<String> GAMEPlayer4ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer4ComboBox = new JComboBox<String>(GAMEPlayer4ComboBoxModel);
		set("player4", GAMEPlayer4ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer4ComboBox);
		
		DefaultComboBoxModel<String> GAMEPlayer5ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer5ComboBox = new JComboBox<String>(GAMEPlayer5ComboBoxModel);
		set("player5", GAMEPlayer5ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer5ComboBox);
		
		DefaultComboBoxModel<String> GAMEPlayer6ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer6ComboBox = new JComboBox<String>(GAMEPlayer6ComboBoxModel);
		set("player6", GAMEPlayer6ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer6ComboBox);
		
		DefaultComboBoxModel<String> GAMEPlayer7ComboBoxModel = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> GAMEPlayer7ComboBox = new JComboBox<String>(GAMEPlayer7ComboBoxModel);
		set("player7", GAMEPlayer7ComboBox, null, null, true);
		GAMEReservePlayerPanel.add(GAMEPlayer7ComboBox);

		JPanel TIMEPanel = new JPanel();
		tabbedPane.addTab("TIME", null, TIMEPanel, null);
		TIMEPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel TIMEPlayTimePanel = new JPanel();
		TIMEPlayTimePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TIMEPanel.add(TIMEPlayTimePanel);
		
		JLabel lblNewLabel_16 = new JLabel("Play Time:");
		TIMEPlayTimePanel.add(lblNewLabel_16);
		
		TIMEPlayTime = new JTextField();
		TIMEPlayTime.setToolTipText("Every 4096 (0x1000) units is equal to one hour");
		TIMEPlayTimePanel.add(TIMEPlayTime);
		TIMEPlayTime.setColumns(10);
		set("playTime", TIMEPlayTime, uint4, textIntFocusListener, false);
		
		JPanel TIMEDayTimePanel = new JPanel();
		TIMEDayTimePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TIMEPanel.add(TIMEDayTimePanel);
		
		JLabel lblNewLabel_18 = new JLabel("Time of Day:");
		lblNewLabel_18.setVerticalAlignment(SwingConstants.BOTTOM);
		TIMEDayTimePanel.add(lblNewLabel_18);
		
		TIMEDayTime = new JTextField();
		TIMEDayTime.setToolTipText("Normal Range is [0, 43200)");
		TIMEDayTimePanel.add(TIMEDayTime);
		TIMEDayTime.setColumns(10);
		set("dayTime", TIMEDayTime, null, textFloatFocusListener, false);
		
		JPanel TIMEDaysPanel = new JPanel();
		TIMEDaysPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TIMEPanel.add(TIMEDaysPanel);
		
		JLabel lblNewLabel_17 = new JLabel("Day Counter:");
		TIMEDaysPanel.add(lblNewLabel_17);
		
		TIMEDayCounter = new JTextField();
		TIMEDaysPanel.add(TIMEDayCounter);
		TIMEDayCounter.setColumns(10);
		set("numDays", TIMEDayCounter, uint2, textIntFocusListener, false);
		
		JPanel TIMEYearCountPanel = new JPanel();
		TIMEYearCountPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TIMEPanel.add(TIMEYearCountPanel);
		
		JLabel lblNewLabel_19 = new JLabel("Year Counter:");
		TIMEYearCountPanel.add(lblNewLabel_19);
		
		TIMEYearCounter = new JTextField();
		TIMEYearCountPanel.add(TIMEYearCounter);
		TIMEYearCounter.setColumns(10);
		set("numYears", TIMEYearCounter, uint2, textIntFocusListener, false);

		JPanel PCPMPanel = new JPanel();
		tabbedPane.addTab("PCPM", null, PCPMPanel, null);
		PCPMPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		JPanel PCPMPlayer1Panel = new JPanel();
		PCPMPlayer1Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		PCPMPanel.add(PCPMPlayer1Panel);
		
		JLabel lblNewLabel_12 = new JLabel("Player 1:    x:");
		PCPMPlayer1Panel.add(lblNewLabel_12);
		
		PCPMPlayer1x = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1x);
		PCPMPlayer1x.setColumns(10);
		set("p1x", PCPMPlayer1x, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_13 = new JLabel("y:");
		PCPMPlayer1Panel.add(lblNewLabel_13);
		
		PCPMPlayer1y = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1y);
		PCPMPlayer1y.setColumns(10);
		set("p1y", PCPMPlayer1y, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_14 = new JLabel("z:");
		PCPMPlayer1Panel.add(lblNewLabel_14);
		
		PCPMPlayer1z = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1z);
		PCPMPlayer1z.setColumns(10);
		set("p1z", PCPMPlayer1z, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_15 = new JLabel("   Angle:   ");
		PCPMPlayer1Panel.add(lblNewLabel_15);
		
		PCPMPlayer1Angle = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1Angle);
		PCPMPlayer1Angle.setColumns(10);
		set("p1Angle", PCPMPlayer1Angle, null, textFloatFocusListener, false);
		
		JPanel PCPMPlayer2Panel = new JPanel();
		PCPMPlayer2Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		PCPMPanel.add(PCPMPlayer2Panel);
		
		JLabel lblNewLabel_12_1 = new JLabel("Player 2:    x:");
		PCPMPlayer2Panel.add(lblNewLabel_12_1);
		
		PCPMPlayer2x = new JTextField();
		PCPMPlayer2x.setColumns(10);
		PCPMPlayer2Panel.add(PCPMPlayer2x);
		set("p2x", PCPMPlayer2x, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_13_1 = new JLabel("y:");
		PCPMPlayer2Panel.add(lblNewLabel_13_1);
		
		PCPMPlayer2y = new JTextField();
		PCPMPlayer2y.setColumns(10);
		PCPMPlayer2Panel.add(PCPMPlayer2y);
		set("p2y", PCPMPlayer2y, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_14_1 = new JLabel("z:");
		PCPMPlayer2Panel.add(lblNewLabel_14_1);
		
		PCPMPlayer2z = new JTextField();
		PCPMPlayer2z.setColumns(10);
		PCPMPlayer2Panel.add(PCPMPlayer2z);
		set("p2z", PCPMPlayer2z, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_15_1 = new JLabel("   Angle:   ");
		PCPMPlayer2Panel.add(lblNewLabel_15_1);
		
		PCPMPlayer2Angle = new JTextField();
		PCPMPlayer2Panel.add(PCPMPlayer2Angle);
		PCPMPlayer2Angle.setColumns(10);
		set("p2Angle", PCPMPlayer2Angle, null, textFloatFocusListener, false);
		
		JPanel PCPMPlayer3Panel = new JPanel();
		PCPMPlayer3Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		PCPMPanel.add(PCPMPlayer3Panel);
		
		JLabel lblNewLabel_12_1_1 = new JLabel("Player 2:    x:");
		PCPMPlayer3Panel.add(lblNewLabel_12_1_1);
		
		PCPMPlayer3x = new JTextField();
		PCPMPlayer3x.setColumns(10);
		PCPMPlayer3Panel.add(PCPMPlayer3x);
		set("p3x", PCPMPlayer3x, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_13_1_1 = new JLabel("y:");
		PCPMPlayer3Panel.add(lblNewLabel_13_1_1);
		
		PCPMPlayer3y = new JTextField();
		PCPMPlayer3y.setColumns(10);
		PCPMPlayer3Panel.add(PCPMPlayer3y);
		set("p3y", PCPMPlayer3y, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_14_1_1 = new JLabel("z:");
		PCPMPlayer3Panel.add(lblNewLabel_14_1_1);
		
		PCPMPlayer3z = new JTextField();
		PCPMPlayer3z.setColumns(10);
		PCPMPlayer3Panel.add(PCPMPlayer3z);
		set("p3z", PCPMPlayer3z, null, textFloatFocusListener, false);
		
		JLabel lblNewLabel_15_1_1 = new JLabel("   Angle:   ");
		PCPMPlayer3Panel.add(lblNewLabel_15_1_1);
		
		PCPMPlayer3Angle = new JTextField();
		PCPMPlayer3Panel.add(PCPMPlayer3Angle);
		PCPMPlayer3Angle.setColumns(10);
		set("p3Angle", PCPMPlayer3Angle, null, textFloatFocusListener, false);

		JPanel CAMDPanel = new JPanel();
		tabbedPane.addTab("CAMD", null, CAMDPanel, null);
		CAMDPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel cameraVPosPanel = new JPanel();
		cameraVPosPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		CAMDPanel.add(cameraVPosPanel);
		
		JLabel lblNewLabel_20 = new JLabel("Camera Vertical Position:");
		cameraVPosPanel.add(lblNewLabel_20);
		
		CAMDVPos = new JTextField();
		cameraVPosPanel.add(CAMDVPos);
		CAMDVPos.setColumns(10);
		set("cameraPosVertical", CAMDVPos, null, textFloatFocusListener, false);
		
		JPanel cameraHPosPanel = new JPanel();
		cameraHPosPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		CAMDPanel.add(cameraHPosPanel);
		
		JLabel lblNewLabel_21 = new JLabel("Camera Horizontal Position:");
		cameraHPosPanel.add(lblNewLabel_21);
		
		CAMDHPos = new JTextField();
		cameraHPosPanel.add(CAMDHPos);
		CAMDHPos.setColumns(10);
		set("cameraPosHorizontal", CAMDHPos, null, textFloatFocusListener, false);
		
		JPanel CAMDDistancePanel = new JPanel();
		CAMDDistancePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		CAMDPanel.add(CAMDDistancePanel);
		
		JLabel lblNewLabel_22 = new JLabel("Camera Distance:");
		CAMDDistancePanel.add(lblNewLabel_22);
		
		CAMDDistance = new JTextField();
		CAMDDistancePanel.add(CAMDDistance);
		CAMDDistance.setColumns(10);
		set("cameraDistance", CAMDDistance, null, textFloatFocusListener, false);

		JPanel ITEMPanel = new JPanel();
		tabbedPane.addTab("ITEM", null, ITEMPanel, null);
		ITEMPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel ITEMMoneyPanel = new JPanel();
		ITEMMoneyPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ITEMPanel.add(ITEMMoneyPanel);
		
		JLabel lblNewLabel_23 = new JLabel("Money:");
		ITEMMoneyPanel.add(lblNewLabel_23);
		
		ITEMMoney = new JTextField();
		ITEMMoneyPanel.add(ITEMMoney);
		ITEMMoney.setText("");
		ITEMMoney.setColumns(10);
		set("money", ITEMMoney, int4, textIntFocusListener, false);

		JPanel WTHRPanel = new JPanel();
		tabbedPane.addTab("WTHR", null, WTHRPanel, null);
		WTHRPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel SNDSPanel = new JPanel();
		tabbedPane.addTab("SNDS", null, SNDSPanel, null);
		SNDSPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel MINEPanel = new JPanel();
		tabbedPane.addTab("MINE", null, MINEPanel, null);
		MINEPanel.setLayout(new BorderLayout(5, 5));
		
		MINETable = new JTable();
		MINETable.setModel(new DefaultTableModel(
			new Object[] {"Map", "Mine ID", "Number of Harvests", "Cooldown"},
			151 // number of rows
		));
		
		JScrollPane scrollPane = new JScrollPane(MINETable);
		MINEPanel.add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel TBOXPanel = new JPanel();
		tabbedPane.addTab("TBOX", null, TBOXPanel, null);
		TBOXPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel OPTDPanel = new JPanel();
		tabbedPane.addTab("OPTD", null, OPTDPanel, null);
		OPTDPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		
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
	private void set(String name, JComponent jc, DocumentFilter df, FocusListener fl, boolean actionListener) {
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
