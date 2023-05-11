import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TIMEView extends JPanel {

	GUI gui;
	
	private JTextField TIMEPlayTime, TIMEDayCounter, TIMEDayTime, TIMEYearCounter;
	
	public TIMEView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "[]10[]10[]10[]10[]", "[]10[]10[]"));
		
		JPanel TIMEPlaythis = new JPanel();
		TIMEPlaythis.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(TIMEPlaythis, "cell 0 0,growx,aligny top");
		TIMEPlaythis.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_16 = new JLabel("Play Time:");
		TIMEPlaythis.add(lblNewLabel_16, "cell 0 0,alignx left,aligny center");

		TIMEPlayTime = new JTextField();
		TIMEPlayTime.setToolTipText("Every 4096 (0x1000) units is equal to one hour");
		TIMEPlaythis.add(TIMEPlayTime, "cell 1 0,growx,aligny top");
		TIMEPlayTime.setColumns(10);
		gui.set("playTime", TIMEPlayTime, gui.uint4, gui.textIntFocusListener, false);

		JPanel TIMEDaythis = new JPanel();
		TIMEDaythis.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(TIMEDaythis, "cell 1 0,growx,aligny top");
		TIMEDaythis.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_18 = new JLabel("Time of Day:");
		lblNewLabel_18.setVerticalAlignment(SwingConstants.BOTTOM);
		TIMEDaythis.add(lblNewLabel_18, "cell 0 0,alignx left,aligny center");

		TIMEDayTime = new JTextField();
		TIMEDayTime.setToolTipText("Normal Range is [0, 43200)");
		TIMEDaythis.add(TIMEDayTime, "cell 1 0,growx,aligny top");
		TIMEDayTime.setColumns(10);
		gui.set("dayTime", TIMEDayTime, null, gui.textFloatFocusListener, false);

		JPanel TIMEDaysPanel = new JPanel();
		TIMEDaysPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(TIMEDaysPanel, "cell 2 0,growx,aligny top");
		TIMEDaysPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_17 = new JLabel("Day Counter:");
		TIMEDaysPanel.add(lblNewLabel_17, "cell 0 0,alignx left,aligny center");

		TIMEDayCounter = new JTextField();
		TIMEDaysPanel.add(TIMEDayCounter, "cell 1 0,growx,aligny top");
		TIMEDayCounter.setColumns(10);
		gui.set("numDays", TIMEDayCounter, gui.uint2, gui.textIntFocusListener, false);

		JPanel TIMEYearCountPanel = new JPanel();
		TIMEYearCountPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.add(TIMEYearCountPanel, "cell 3 0,growx,aligny top");
		TIMEYearCountPanel.setLayout(new MigLayout("fillx", "10[min!]10[]10", "5[]5"));

		JLabel lblNewLabel_19 = new JLabel("Year Counter:");
		TIMEYearCountPanel.add(lblNewLabel_19, "cell 0 0,alignx left,aligny center");

		TIMEYearCounter = new JTextField();
		TIMEYearCountPanel.add(TIMEYearCounter, "cell 1 0,growx,aligny top");
		TIMEYearCounter.setColumns(10);
		gui.set("numYears", TIMEYearCounter, gui.uint2, gui.textIntFocusListener, false);
	}
	
}
