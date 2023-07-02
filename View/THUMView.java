package View;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class THUMView extends JPanel {
	
	GUI gui;
	
	private JTextField THUMLevel, THUMName, THUMPlayTimeHours, THUMPlayTimeMinutes, THUMSaveTimeDay, THUMSaveTimeMonth, THUMSaveTimeYear, THUMSaveTimeHour, THUMSaveTimeMinute;
	
	public THUMView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[]10[]10[]10[]10[]", "[]10[]10[]"));
		
		JPanel THUMLevelPanel = new JPanel();
		THUMLevelPanel.setBorder(new TitledBorder(null, "Level", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(THUMLevelPanel, "cell 0 0,growx");
		THUMLevelPanel.setLayout(new MigLayout("fillx", "10[]10", "5[]5"));

		THUMLevel = new JTextField();
		THUMLevel.setColumns(4);
		gui.setTextField("level", THUMLevel, gui.uint2);
		THUMLevelPanel.add(THUMLevel, "growx,aligny top");

		JPanel THUMNamePanel = new JPanel();
		THUMNamePanel.setBorder(new TitledBorder(null, "Name Text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(THUMNamePanel, "cell 1 0 4 1,growx");
		THUMNamePanel.setLayout(new MigLayout("fillx", "10[]10", "5[]5"));

		THUMName = new JTextField();
		THUMName.setColumns(32);
		gui.setTextField("name", THUMName, new StringFilter(32));
		THUMNamePanel.add(THUMName, "growx,aligny top");

		JPanel THUMPlayTimePanel = new JPanel();
		THUMPlayTimePanel.setBorder(new TitledBorder(null, "Play Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(THUMPlayTimePanel, "cell 0 1 2 1,growx");
		THUMPlayTimePanel.setLayout(new MigLayout("fillx", "10[50::]10[min!]10[50::]10", "[]"));

		THUMPlayTimeHours = new JTextField();
		THUMPlayTimeHours.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMPlayTimeHours.setColumns(4);
		gui.setTextField("playTimeHours", THUMPlayTimeHours, gui.uint2);
		THUMPlayTimePanel.add(THUMPlayTimeHours, "growx,aligny top");

		JLabel lblNewLabel_3 = new JLabel(":");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		THUMPlayTimePanel.add(lblNewLabel_3, "alignx center,aligny center");

		THUMPlayTimeMinutes = new JTextField();
		THUMPlayTimeMinutes.setColumns(4);
		gui.setTextField("playTimeMins", THUMPlayTimeMinutes, gui.uint2);
		THUMPlayTimePanel.add(THUMPlayTimeMinutes, "growx,aligny top");


		JPanel THUMSaveTimePanel = new JPanel();
		THUMSaveTimePanel.setBorder(new TitledBorder(null, "Save Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(THUMSaveTimePanel, "cell 2 1 2 1,grow");
		THUMSaveTimePanel.setLayout(new MigLayout("fillx", "10[50::]10[min!]10[50::]10[min!]10[50::]10[min!]10[50::]10[min!]10[50::]10", "5[]5"));

		THUMSaveTimeDay = new JTextField();
		THUMSaveTimeDay.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeDay.setColumns(3);
		gui.setTextField("saveTimeDay", THUMSaveTimeDay, gui.uint1);
		THUMSaveTimePanel.add(THUMSaveTimeDay, "growx,aligny top");

		JLabel lblNewLabel_5 = new JLabel(" / ");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		THUMSaveTimePanel.add(lblNewLabel_5, "alignx center,aligny center");

		THUMSaveTimeMonth = new JTextField();
		THUMSaveTimeMonth.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeMonth.setColumns(3);
		gui.setTextField("saveTimeMonth", THUMSaveTimeMonth, gui.uint2);
		THUMSaveTimePanel.add(THUMSaveTimeMonth, "growx,aligny top");

		JLabel lblNewLabel_5_1 = new JLabel(" / ");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		THUMSaveTimePanel.add(lblNewLabel_5_1, "alignx center,aligny center");

		THUMSaveTimeYear = new JTextField();
		THUMSaveTimeYear.setColumns(5);
		gui.setTextField("saveTimeYear", THUMSaveTimeYear, gui.uint2);
		THUMSaveTimePanel.add(THUMSaveTimeYear, "growx,aligny top");

		JLabel lblNewLabel_5_2 = new JLabel("     ");
		THUMSaveTimePanel.add(lblNewLabel_5_2, "alignx left,aligny center");

		THUMSaveTimeHour = new JTextField();
		THUMSaveTimeHour.setHorizontalAlignment(SwingConstants.TRAILING);
		THUMSaveTimeHour.setColumns(3);
		gui.setTextField("saveTimeHour", THUMSaveTimeHour, gui.uint1);
		THUMSaveTimePanel.add(THUMSaveTimeHour, "growx,aligny top");

		JLabel lblNewLabel_5_3 = new JLabel(":");
		lblNewLabel_5_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		THUMSaveTimePanel.add(lblNewLabel_5_3, "alignx center,aligny center");

		THUMSaveTimeMinute = new JTextField();
		THUMSaveTimeMinute.setColumns(3);
		gui.setTextField("saveTimeMinute", THUMSaveTimeMinute, gui.uint1);
		THUMSaveTimePanel.add(THUMSaveTimeMinute, "growx,aligny top");

		JPanel THUMNGPanel = new JPanel();
		THUMNGPanel.setBorder(new TitledBorder(null, "Modifiers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(THUMNGPanel, "cell 4 1,grow");
		THUMNGPanel.setLayout(new MigLayout("fillx", "2[min!][min!]2", "3[]3"));

		JCheckBox THUMNgPlusCheckBox = new JCheckBox("New Game+");
		gui.setCheckBox("ng+Flag", THUMNgPlusCheckBox);
		THUMNGPanel.add(THUMNgPlusCheckBox, "alignx left,aligny top");

		JCheckBox THUMSystemSaveCheckBox = new JCheckBox("System Save");
		gui.setCheckBox("systemSaveFlag", THUMSystemSaveCheckBox);
		THUMNGPanel.add(THUMSystemSaveCheckBox, "alignx left,aligny top");

		JPanel THUMPicSlotPanel = new JPanel();
		THUMPicSlotPanel.setBorder(new TitledBorder(null, "Picture Slots", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(THUMPicSlotPanel, "cell 0 2 5 1,growx");
		THUMPicSlotPanel.setLayout(new MigLayout("fillx", "20[]20[]20[]20[]20", "[]5[]"));

		String[] pclist = new String[] {" ", "Shulk", "Reyn", "Fiora", "Dunban", "Sharla", "Riki", "Melia", "Seven", "Dickson", "Mumkhar", "Alvis", "Prologue Dunban", "Other Dunban"};

		// We need these DefaultComboBoxModels because WindowBuilder doesn't like putting array (pclist) directly in JComboBox initialization for some reason
		DefaultComboBoxModel<String> THUMPicSlot1Model = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> THUMPicSlot2Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot1 = new JComboBox<String>(THUMPicSlot1Model);
		gui.setComboBox("picSlot1", THUMPicSlot1);
		THUMPicSlotPanel.add(THUMPicSlot1, "cell 0 0,growx,aligny top");

		DefaultComboBoxModel<String> THUMPicSlot3Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot2 = new JComboBox<String>(THUMPicSlot2Model);
		gui.setComboBox("picSlot2", THUMPicSlot2);
		THUMPicSlotPanel.add(THUMPicSlot2, "cell 1 0,growx,aligny top");

		DefaultComboBoxModel<String> THUMPicSlot4Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot3 = new JComboBox<String>(THUMPicSlot3Model);
		gui.setComboBox("picSlot3", THUMPicSlot3);
		THUMPicSlotPanel.add(THUMPicSlot3, "cell 2 0,growx,aligny top");

		DefaultComboBoxModel<String> THUMPicSlot5Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot4 = new JComboBox<String>(THUMPicSlot4Model);
		gui.setComboBox("picSlot4", THUMPicSlot4);
		THUMPicSlotPanel.add(THUMPicSlot4, "cell 3 0,growx,aligny top");

		DefaultComboBoxModel<String> THUMPicSlot6Model = new DefaultComboBoxModel<String>(pclist);

		DefaultComboBoxModel<String> THUMPicSlot7Model = new DefaultComboBoxModel<String>(pclist);
		JComboBox<String> THUMPicSlot5 = new JComboBox<String>(THUMPicSlot5Model);
		gui.setComboBox("picSlot5", THUMPicSlot5);
		THUMPicSlotPanel.add(THUMPicSlot5, "cell 0 1,growx,aligny top");
		JComboBox<String> THUMPicSlot6 = new JComboBox<String>(THUMPicSlot6Model);
		gui.setComboBox("picSlot6", THUMPicSlot6);
		THUMPicSlotPanel.add(THUMPicSlot6, "cell 1 1,growx,aligny top");
		JComboBox<String> THUMPicSlot7 = new JComboBox<String>(THUMPicSlot7Model);
		gui.setComboBox("picSlot7", THUMPicSlot7);
		THUMPicSlotPanel.add(THUMPicSlot7, "cell 2 1,growx,aligny top");
	}
	
}
