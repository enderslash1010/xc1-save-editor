import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar = new JMenuBar();

	private JLabel currFile;
	private JLabel statusMessage;

	private SaveFile saveFile = null;
	private HashMap<JComponent, Pointer> fieldMap = new HashMap<JComponent, Pointer>();

	private JTextField THUMLevel, THUMName, THUMPlayTimeHours, THUMPlayTimeMinutes, THUMSaveTimeDay, THUMSaveTimeMonth, THUMSaveTimeYear, THUMSaveTimeHour, THUMSaveTimeMinute;

	private FocusListener textNumberFocusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) { // do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			// make sure text isn't empty or "-"
			JTextComponent src = (JTextComponent) e.getSource();
			if (src.getText().equals("") || src.getText().equals("-")) {
				// revert text back to what's in the save file
				loadValue(src);
			}
		}

	};
	private JTextField FLAGScenarioNum;
	private JTextField PCPMPlayer1x;
	private JTextField PCPMPlayer1y;
	private JTextField PCPMPlayer1z;

	// TODO: write tool tips for fields that need more explanation
	public GUI() {

		UIntFilter uint1 = new UIntFilter(1);
		UIntFilter uint2 = new UIntFilter(2);
		UIntFilter uint4 = new UIntFilter(4);
		FloatFilter floatFilter = new FloatFilter();

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
				if (fc.showOpenDialog(fc) == 0) {
					try {
						String fileLocation = fc.getSelectedFile().getAbsolutePath();
						saveFile = new SaveFile(fileLocation);
						setCurrFile(fileLocation);
						loadValues();
						setEnabled(true); // enable fields when a file is opened
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
				// TODO: handle null saveFile
				if (saveFile == null) {
					setStatusMessage("No save file is opened, cannot save");
					return;
				}
				saveValues();
				String result = saveFile.saveToFile();
				setStatusMessage(result);
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
		attach(THUMLevel, SaveFile.THUMData.get("level"), uint2, textNumberFocusListener);
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
		attach(THUMName, SaveFile.THUMData.get("name"), new StringFilter(32), null);
		THUMNamePanel.add(THUMName);

		JPanel THUMPlayTimePanel = new JPanel();
		THUMPlayTimePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMPlayTimePanel);

		JLabel lblNewLabel_2 = new JLabel("Play Time:");
		THUMPlayTimePanel.add(lblNewLabel_2);

		THUMPlayTimeHours = new JTextField();
		THUMPlayTimeHours.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMPlayTimeHours.setColumns(4);
		attach(THUMPlayTimeHours, SaveFile.THUMData.get("playTimeHours"), uint2, textNumberFocusListener);
		THUMPlayTimePanel.add(THUMPlayTimeHours);

		JLabel lblNewLabel_3 = new JLabel(":");
		THUMPlayTimePanel.add(lblNewLabel_3);

		THUMPlayTimeMinutes = new JTextField();
		THUMPlayTimeMinutes.setColumns(4);
		attach(THUMPlayTimeMinutes, SaveFile.THUMData.get("playTimeMins"), uint2, textNumberFocusListener);
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
		attach(THUMSaveTimeDay, SaveFile.THUMData.get("saveTimeDay"), uint1, textNumberFocusListener);
		THUMSaveTimePanel.add(THUMSaveTimeDay);

		JLabel lblNewLabel_5 = new JLabel(" / ");
		THUMSaveTimePanel.add(lblNewLabel_5);

		THUMSaveTimeMonth = new JTextField();
		THUMSaveTimeMonth.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeMonth.setColumns(3);
		attach(THUMSaveTimeMonth, SaveFile.THUMData.get("saveTimeMonth"), uint2, textNumberFocusListener);
		THUMSaveTimePanel.add(THUMSaveTimeMonth);

		JLabel lblNewLabel_5_1 = new JLabel(" / ");
		THUMSaveTimePanel.add(lblNewLabel_5_1);

		THUMSaveTimeYear = new JTextField();
		THUMSaveTimeYear.setColumns(5);
		attach(THUMSaveTimeYear, SaveFile.THUMData.get("saveTimeYear"), uint2, textNumberFocusListener);
		THUMSaveTimePanel.add(THUMSaveTimeYear);

		JLabel lblNewLabel_5_2 = new JLabel("     ");
		THUMSaveTimePanel.add(lblNewLabel_5_2);

		THUMSaveTimeHour = new JTextField();
		THUMSaveTimeHour.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeHour.setColumns(3);
		attach(THUMSaveTimeHour, SaveFile.THUMData.get("saveTimeHour"), uint1, textNumberFocusListener);
		THUMSaveTimePanel.add(THUMSaveTimeHour);

		JLabel lblNewLabel_5_3 = new JLabel(":");
		THUMSaveTimePanel.add(lblNewLabel_5_3);

		THUMSaveTimeMinute = new JTextField();
		THUMSaveTimeMinute.setColumns(3);
		attach(THUMSaveTimeMinute, SaveFile.THUMData.get("saveTimeMinute"), uint1, textNumberFocusListener);
		THUMSaveTimePanel.add(THUMSaveTimeMinute);

		JPanel THUMNGPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) THUMNGPanel.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setVgap(3);
		THUMNGPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMNGPanel);

		JCheckBox THUMNgPlusCheckBox = new JCheckBox("New Game+");
		attach(THUMNgPlusCheckBox, SaveFile.THUMData.get("ng+Flag"), null, null);
		THUMNGPanel.add(THUMNgPlusCheckBox);

		JCheckBox THUMSystemSaveCheckBox = new JCheckBox("System Save");
		attach(THUMSystemSaveCheckBox, SaveFile.THUMData.get("systemSaveFlag"), null, null);
		THUMNGPanel.add(THUMSystemSaveCheckBox);
		
		JPanel THUMPicSlotPanel = new JPanel();
		THUMPicSlotPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		THUMPanel.add(THUMPicSlotPanel);
		
		JLabel lblNewLabel_6 = new JLabel("Picture Slots:");
		THUMPicSlotPanel.add(lblNewLabel_6);
		
		IntegerMap[] pclist = {new IntegerMap(" ", 0), 
				new IntegerMap("Shulk", 1), 
				new IntegerMap("Reyn", 2),
				new IntegerMap("Fiora", 3),
				new IntegerMap("Dunban", 4),
				new IntegerMap("Sharla", 5),
				new IntegerMap("Riki", 6),
				new IntegerMap("Melia", 7),
				new IntegerMap("Seven", 8),
				new IntegerMap("Dickson", 9),
				new IntegerMap("Mumkhar", 10),
				new IntegerMap("Alvis", 11),
				new IntegerMap("Prologue Dunban", 12),
				new IntegerMap("Other Dunban", 13)
		};
		
		JComboBox<IntegerMap> THUMPicSlot1 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		ValueMapListCellRenderer renderer = new ValueMapListCellRenderer();
		THUMPicSlot1.setRenderer(renderer);
		attach(THUMPicSlot1, SaveFile.THUMData.get("picSlot1"), null, null);
		THUMPicSlotPanel.add(THUMPicSlot1);
		
		JComboBox<IntegerMap> THUMPicSlot2 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		THUMPicSlot2.setRenderer(renderer);
		attach(THUMPicSlot2, SaveFile.THUMData.get("picSlot2"), null, null);
		THUMPicSlotPanel.add(THUMPicSlot2);
		
		JComboBox<IntegerMap> THUMPicSlot3 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		THUMPicSlot3.setRenderer(renderer);
		attach(THUMPicSlot3, SaveFile.THUMData.get("picSlot3"), null, null);
		THUMPicSlotPanel.add(THUMPicSlot3);
		
		JComboBox<IntegerMap> THUMPicSlot4 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		THUMPicSlot4.setRenderer(renderer);
		attach(THUMPicSlot4, SaveFile.THUMData.get("picSlot4"), null, null);
		THUMPicSlotPanel.add(THUMPicSlot4);
		
		JComboBox<IntegerMap> THUMPicSlot5 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		THUMPicSlot5.setRenderer(renderer);
		attach(THUMPicSlot5, SaveFile.THUMData.get("picSlot5"), null, null);
		THUMPicSlotPanel.add(THUMPicSlot5);
		
		JComboBox<IntegerMap> THUMPicSlot6 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		THUMPicSlot6.setRenderer(renderer);
		attach(THUMPicSlot6, SaveFile.THUMData.get("picSlot6"), null, null);
		THUMPicSlotPanel.add(THUMPicSlot6);
		
		JComboBox<IntegerMap> THUMPicSlot7 = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		THUMPicSlot7.setRenderer(renderer);
		attach(THUMPicSlot7, SaveFile.THUMData.get("picSlot7"), null, null);
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
		attach(FLAGScenarioNum, SaveFile.FLAGData.get("scenarioNum"), uint2, textNumberFocusListener);
		FLAGScenarioPanel.add(FLAGScenarioNum);

		JPanel GAMEPanel = new JPanel();
		tabbedPane.addTab("GAME", null, GAMEPanel, null);
		GAMEPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		JPanel GAMEMapPanel = new JPanel();
		GAMEMapPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMEMapPanel);
		
		JLabel lblNewLabel_8 = new JLabel("Map:");
		GAMEMapPanel.add(lblNewLabel_8);
		
		IntegerMap[] GAMEMapValues = {
				new IntegerMap("Title Screen (ma0000)", SaveFile.getFormattedMapNum("0000")),
				new IntegerMap("Colony 9 (ma0101)", SaveFile.getFormattedMapNum("0101")),
				new IntegerMap("Tephra Cave (ma0201)", SaveFile.getFormattedMapNum("0201")),
				new IntegerMap("Bionis' Leg (ma0301)", SaveFile.getFormattedMapNum("0301")),
				new IntegerMap("Colony 6 (ma0401)", SaveFile.getFormattedMapNum("0401")),
				new IntegerMap("Ether Mine (ma0402)", SaveFile.getFormattedMapNum("0402")),
				new IntegerMap("Satorl Marsh (ma0501)", SaveFile.getFormattedMapNum("0501")),
				new IntegerMap("Makna Forest (ma0601)", SaveFile.getFormattedMapNum("0601")),
				new IntegerMap("Frontier Village (ma0701)", SaveFile.getFormattedMapNum("0701")),
				new IntegerMap("Bionis' Shoulder (ma0801)", SaveFile.getFormattedMapNum("0801")),
				new IntegerMap("High Entia Tomb (ma0901)", SaveFile.getFormattedMapNum("0901")),
				new IntegerMap("Eryth Sea (ma1001)", SaveFile.getFormattedMapNum("1001")),
				new IntegerMap("Alcamoth (ma1101)", SaveFile.getFormattedMapNum("1101")),
				new IntegerMap("Prison Island (ma1201)", SaveFile.getFormattedMapNum("1201")),
				new IntegerMap("Prison Island 2 (ma1202)", SaveFile.getFormattedMapNum("1202")),
				new IntegerMap("Valak Mountain (ma1301)", SaveFile.getFormattedMapNum("1301")),
				new IntegerMap("Sword Valley (ma1401)", SaveFile.getFormattedMapNum("1401")),
				new IntegerMap("Galahad Fortress (ma1501)", SaveFile.getFormattedMapNum("1501")),
				new IntegerMap("Fallen Arm (ma1601)", SaveFile.getFormattedMapNum("1601")),
				new IntegerMap("Beta Fallen Arm (ma1602)", SaveFile.getFormattedMapNum("1602")),
				new IntegerMap("Mechonis Field (ma1701)", SaveFile.getFormattedMapNum("1701")),
				new IntegerMap("Agniratha (ma1901)", SaveFile.getFormattedMapNum("1901")),
				new IntegerMap("Central Factory (ma2001)", SaveFile.getFormattedMapNum("2001")),
				new IntegerMap("Bionis' Interior (ma2101)", SaveFile.getFormattedMapNum("2101")),
				new IntegerMap("Memory Space (ma2201)", SaveFile.getFormattedMapNum("2201")),
				new IntegerMap("Mechonis Core (ma2301)", SaveFile.getFormattedMapNum("2301")),
				new IntegerMap("Junks (ma2401)", SaveFile.getFormattedMapNum("2401")),
				new IntegerMap("Post-Game Colony 9 (ma0102)", SaveFile.getFormattedMapNum("0102"))
		};
		
		JComboBox<IntegerMap> GAMEMapComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(GAMEMapValues));
		GAMEMapComboBox.setRenderer(renderer);
		attach(GAMEMapComboBox, SaveFile.GAMEData.get("mapNum"), null, null);
		GAMEMapPanel.add(GAMEMapComboBox);
		
		JPanel GAMEMap2Panel = new JPanel();
		GAMEMap2Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMEMap2Panel);
		
		JLabel lblNewLabel_9 = new JLabel("Map (for NPCs and music):");
		GAMEMap2Panel.add(lblNewLabel_9);
		
		StringMap[] GAMEMap2Values = {
				new StringMap("Title Screen (ma0000)", "0000"),
				new StringMap("Colony 9 (ma0101)", "0101"),
				new StringMap("Tephra Cave (ma0201)", "0201"),
				new StringMap("Bionis' Leg (ma0301)", "0301"),
				new StringMap("Colony 6 (ma0401)", "0401"),
				new StringMap("Ether Mine (ma0402)", "0402"),
				new StringMap("Satorl Marsh (ma0501)", "0501"),
				new StringMap("Makna Forest (ma0601)", "0601"),
				new StringMap("Frontier Village (ma0701)", "0701"),
				new StringMap("Bionis' Shoulder (ma0801)", "0801"),
				new StringMap("High Entia Tomb (ma0901)", "0901"),
				new StringMap("Eryth Sea (ma1001)", "1001"),
				new StringMap("Alcamoth (ma1101)", "1101"),
				new StringMap("Prison Island (ma1201)", "1201"),
				new StringMap("Prison Island 2 (ma1202)", "1202"),
				new StringMap("Valak Mountain (ma1301)", "1301"),
				new StringMap("Sword Valley (ma1401)", "1401"),
				new StringMap("Galahad Fortress (ma1501)", "1501"),
				new StringMap("Fallen Arm (ma1601)", "1601"),
				new StringMap("Beta Fallen Arm (ma1602)", "1602"),
				new StringMap("Mechonis Field (ma1701)", "1701"),
				new StringMap("Agniratha (ma1901)", "1901"),
				new StringMap("Central Factory (ma2001)", "2001"),
				new StringMap("Bionis' Interior (ma2101)", "2101"),
				new StringMap("Memory Space (ma2201)", "2201"),
				new StringMap("Mechonis Core (ma2301)", "2301"),
				new StringMap("Junks (ma2401)", "2401"),
				new StringMap("Post-Game Colony 9 (ma0102)", "0102")
		};
		
		JComboBox<StringMap> GAMEMap2ComboBox = new JComboBox<StringMap>(new DefaultComboBoxModel<StringMap>(GAMEMap2Values));
		GAMEMap2ComboBox.setRenderer(renderer);
		attach(GAMEMap2ComboBox, SaveFile.GAMEData.get("mapNum2"), null, null);
		GAMEMap2Panel.add(GAMEMap2ComboBox);
		
		JPanel GAMECurrentPlayersPanel = new JPanel();
		GAMECurrentPlayersPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMECurrentPlayersPanel);
		
		JLabel lblNewLabel_10 = new JLabel("Current Players:");
		GAMECurrentPlayersPanel.add(lblNewLabel_10);
		
		JComboBox<IntegerMap> GAMEPlayer1ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer1ComboBox.setRenderer(renderer);
		attach(GAMEPlayer1ComboBox, SaveFile.GAMEData.get("player1"), null, null);
		GAMECurrentPlayersPanel.add(GAMEPlayer1ComboBox);
		
		JComboBox<IntegerMap> GAMEPlayer2ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer2ComboBox.setRenderer(renderer);
		attach(GAMEPlayer2ComboBox, SaveFile.GAMEData.get("player2"), null, null);
		GAMECurrentPlayersPanel.add(GAMEPlayer2ComboBox);
		
		JComboBox<IntegerMap> GAMEPlayer3ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer3ComboBox.setRenderer(renderer);
		attach(GAMEPlayer3ComboBox, SaveFile.GAMEData.get("player3"), null, null);
		GAMECurrentPlayersPanel.add(GAMEPlayer3ComboBox);
		
		JPanel GAMEReservePlayerPanel = new JPanel();
		GAMEReservePlayerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GAMEPanel.add(GAMEReservePlayerPanel);
		
		JLabel lblNewLabel_11 = new JLabel("Reserve Players:");
		GAMEReservePlayerPanel.add(lblNewLabel_11);
		
		JComboBox<IntegerMap> GAMEPlayer4ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer4ComboBox.setRenderer(renderer);
		attach(GAMEPlayer4ComboBox, SaveFile.GAMEData.get("player4"), null, null);
		GAMEReservePlayerPanel.add(GAMEPlayer4ComboBox);
		
		JComboBox<IntegerMap> GAMEPlayer5ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer5ComboBox.setRenderer(renderer);
		attach(GAMEPlayer5ComboBox, SaveFile.GAMEData.get("player5"), null, null);
		GAMEReservePlayerPanel.add(GAMEPlayer5ComboBox);
		
		JComboBox<IntegerMap> GAMEPlayer6ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer6ComboBox.setRenderer(renderer);
		attach(GAMEPlayer6ComboBox, SaveFile.GAMEData.get("player6"), null, null);
		GAMEReservePlayerPanel.add(GAMEPlayer6ComboBox);
		
		JComboBox<IntegerMap> GAMEPlayer7ComboBox = new JComboBox<IntegerMap>(new DefaultComboBoxModel<IntegerMap>(pclist));
		GAMEPlayer7ComboBox.setRenderer(renderer);
		attach(GAMEPlayer7ComboBox, SaveFile.GAMEData.get("player7"), null, null);
		GAMEReservePlayerPanel.add(GAMEPlayer7ComboBox);

		JPanel TIMEPanel = new JPanel();
		tabbedPane.addTab("TIME", null, TIMEPanel, null);
		TIMEPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel PCPMPanel = new JPanel();
		tabbedPane.addTab("PCPM", null, PCPMPanel, null);
		PCPMPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel PCPMPlayer1Panel = new JPanel();
		PCPMPlayer1Panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		PCPMPanel.add(PCPMPlayer1Panel);
		
		JLabel lblNewLabel_12 = new JLabel("Player 1: x:");
		PCPMPlayer1Panel.add(lblNewLabel_12);
		
		PCPMPlayer1x = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1x);
		PCPMPlayer1x.setColumns(10);
		attach(PCPMPlayer1x, SaveFile.PCPMData.get("p1x"), floatFilter, textNumberFocusListener);
		
		JLabel lblNewLabel_13 = new JLabel("y:");
		PCPMPlayer1Panel.add(lblNewLabel_13);
		
		PCPMPlayer1y = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1y);
		PCPMPlayer1y.setColumns(10);
		attach(PCPMPlayer1y, SaveFile.PCPMData.get("p1y"), floatFilter, textNumberFocusListener);
		
		JLabel lblNewLabel_14 = new JLabel("z:");
		PCPMPlayer1Panel.add(lblNewLabel_14);
		
		PCPMPlayer1z = new JTextField();
		PCPMPlayer1Panel.add(PCPMPlayer1z);
		PCPMPlayer1z.setColumns(10);
		attach(PCPMPlayer1z, SaveFile.PCPMData.get("p1z"), floatFilter, textNumberFocusListener);
		
		JLabel lblNewLabel_15 = new JLabel("Angle:");
		PCPMPlayer1Panel.add(lblNewLabel_15);
		
		JSlider PCPMPlayer1Angle = new JSlider();
		PCPMPlayer1Panel.add(PCPMPlayer1Angle);
		// TODO: implement slider function

		JPanel CAMDPanel = new JPanel();
		tabbedPane.addTab("CAMD", null, CAMDPanel, null);
		CAMDPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel ITEMPanel = new JPanel();
		tabbedPane.addTab("ITEM", null, ITEMPanel, null);
		ITEMPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel WTHRPanel = new JPanel();
		tabbedPane.addTab("WTHR", null, WTHRPanel, null);
		WTHRPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel SNDSPanel = new JPanel();
		tabbedPane.addTab("SNDS", null, SNDSPanel, null);
		SNDSPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel MINEPanel = new JPanel();
		tabbedPane.addTab("MINE", null, MINEPanel, null);
		MINEPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel TBOXPanel = new JPanel();
		tabbedPane.addTab("TBOX", null, TBOXPanel, null);
		TBOXPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel OPTDPanel = new JPanel();
		tabbedPane.addTab("OPTD", null, OPTDPanel, null);
		OPTDPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JPanel bottomPanel = new JPanel();
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] {0, 0};
		gbl_bottomPanel.rowHeights = new int[] {0};
		gbl_bottomPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0};
		bottomPanel.setLayout(gbl_bottomPanel);

		currFile = new JLabel("No File Selected");
		currFile.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_currFile = new GridBagConstraints();
		gbc_currFile.anchor = GridBagConstraints.WEST;
		gbc_currFile.insets = new Insets(0, 5, 2, 5);
		gbc_currFile.gridx = 0;
		gbc_currFile.gridy = 0;
		bottomPanel.add(currFile, gbc_currFile);
		
		statusMessage = new JLabel("");
		statusMessage.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_statusMessage = new GridBagConstraints();
		gbc_statusMessage.anchor = GridBagConstraints.EAST;
		gbc_statusMessage.insets = new Insets(0, 0, 2, 5);
		gbc_statusMessage.gridx = 1;
		gbc_statusMessage.gridy = 0;
		bottomPanel.add(statusMessage, gbc_statusMessage);

		// Adding all the panels to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
		frame.getContentPane().add(BorderLayout.CENTER, contentPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		frame.setVisible(true);

		// Starts with null saveFile, so disable user input
		setEnabled(false);
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

	// load one value into it's respective text field/etc.
	private void loadValue(JComponent jc) {
		if (jc instanceof JTextComponent) {
			((JTextComponent) jc).setText(saveFile.getData(fieldMap.get(jc)).toString());
		}
		if (jc instanceof JCheckBox) {
			if ((boolean) saveFile.getData(fieldMap.get(jc))) { // assumes a checkbox item is associated with a boolean var
				((JCheckBox) jc).setSelected(true);
			}
			else {
				((JCheckBox) jc).setSelected(false);
			}

		}
		if (jc instanceof JComboBox) {
			Object saveFileValue = saveFile.getData(fieldMap.get(jc));
			for (int i = 0; i < ((JComboBox<?>) jc).getItemCount(); i++) {
				ValueMap<?> vp = (ValueMap<?>) ((JComboBox<?>) jc).getItemAt(i); // All Combo Boxes are of type JComboBox<ValueMap>
				if (saveFileValue.equals(vp.getSaveFileValue())) {
					((JComboBox<?>) jc).setSelectedItem(vp);
					break;
				}
			}
		}
		// TODO: handle more field types after this; check box, progress bar, etc.
	}

	// load all values into respective text fields/etc.
	private void loadValues() {
		for (JComponent key : fieldMap.keySet()) {
			loadValue(key);
		}
	}

	// TODO: saves one value in actual file, handle more types
	private void saveValue(JComponent jc) {
		Pointer data = fieldMap.get(jc);
		if (data instanceof Data) {
			
			if (jc instanceof JComboBox) { // JComboBoxes handle all different types with ValueMap<?>
				try {
					saveFile.setData(data, ((ValueMap<?>) ((JComboBox<?>) jc).getSelectedItem()).getSaveFileValue()); // Can do this cast because all ComboBox Items are type ValueMap
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			switch (((Data)data).getType()) {
			case Boolean:
				if (jc instanceof JCheckBox) {
					try {
						saveFile.setData(data, ((JCheckBox) jc).isSelected());
					} catch (Exception e) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e.printStackTrace();
					}
				}
				break;
			case Float:
				if (jc instanceof JTextComponent) {
					try {
						saveFile.setData(data, Float.parseFloat(((JTextComponent) jc).getText()));
					}
					catch (Exception e) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e.printStackTrace();
					}
				}
				break;
			case Int:
				if (jc instanceof JTextComponent) {
					try {
						saveFile.setData(data, Integer.parseInt(((JTextComponent) jc).getText()));
					} catch (Exception e) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e.printStackTrace();
					}
				}
				break;
			case String:
				if (jc instanceof JTextComponent) {
					try {
						saveFile.setData(data, ((JTextComponent) jc).getText());
					} catch (Exception e) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e.printStackTrace();
					}
				}
				
				break;
			case TPL:

				break;
			}
		}
		else {
			// TODO: handle arrays here???
		}
	}
	// saves changes made in editor to actual file
	private void saveValues() {		
		for (JComponent key : fieldMap.keySet()) {
			saveValue(key);
		}
	}

	// enables/disables all user input boxes
	public void setEnabled(boolean b) {
		for (JComponent key : fieldMap.keySet()) {
			key.setEnabled(b);
		}
	}

	// associates a GUI object with a Pointer object that describes what the GUI object contains
	public void attach(JComponent jc, Pointer p, DocumentFilter df, FocusListener fl) {
		fieldMap.put(jc, p); // add to fieldMap

		if (df != null && jc instanceof JTextComponent) {
			AbstractDocument doc = (AbstractDocument) ((JTextComponent) jc).getDocument();
			doc.setDocumentFilter(df); // set document filter
		}
		if (fl != null) jc.addFocusListener(fl); // add FocusListener
	}
}
