package View;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TIMEView extends JPanel {

	GUI gui;
	
	private JTextField TIMEPlayTime, TIMEDayCounter, TIMEDayTime, TIMEYearCounter;
	
	public TIMEView(GUI gui) {
		this.gui = gui;
		this.setLayout(new MigLayout("fillx", "10[]10[]10[]10[]10", "10[]10[]10[]"));
		
		JPanel TIMEPlaythis = new JPanel();
		TIMEPlaythis.setBorder(new TitledBorder(null, "Play Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(TIMEPlaythis, "cell 0 0,growx,aligny top");
		TIMEPlaythis.setLayout(new MigLayout("fillx", "10[]10", "[]"));
				
						TIMEPlayTime = new JTextField();
						TIMEPlayTime.setToolTipText("Every 4096 (0x1000) units is equal to one hour");
						TIMEPlaythis.add(TIMEPlayTime, "cell 0 0,growx,aligny top");
						TIMEPlayTime.setColumns(10);
						gui.setTextField("playTime", TIMEPlayTime, gui.uint4);

		JPanel TIMEDaythis = new JPanel();
		TIMEDaythis.setBorder(new TitledBorder(null, "Time of Day", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(TIMEDaythis, "cell 1 0,growx,aligny top");
		TIMEDaythis.setLayout(new MigLayout("fillx", "10[]10", "[]"));
		
				TIMEDayTime = new JTextField();
				TIMEDayTime.setToolTipText("Normal Range is [0, 43200)");
				TIMEDaythis.add(TIMEDayTime, "cell 0 0,growx,aligny top");
				TIMEDayTime.setColumns(10);
				gui.setTextField("dayTime", TIMEDayTime, null);

		JPanel TIMEDaysPanel = new JPanel();
		TIMEDaysPanel.setBorder(new TitledBorder(null, "Day Counter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(TIMEDaysPanel, "cell 2 0,growx,aligny top");
		TIMEDaysPanel.setLayout(new MigLayout("fillx", "10[]10", "[]"));
		
				TIMEDayCounter = new JTextField();
				TIMEDaysPanel.add(TIMEDayCounter, "cell 0 0,growx,aligny top");
				TIMEDayCounter.setColumns(10);
				gui.setTextField("numDays", TIMEDayCounter, gui.uint2);

		JPanel TIMEYearCountPanel = new JPanel();
		TIMEYearCountPanel.setBorder(new TitledBorder(null, "Year Counter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(TIMEYearCountPanel, "cell 3 0,growx,aligny top");
		TIMEYearCountPanel.setLayout(new MigLayout("fillx", "10[]10", "[]"));
		
				TIMEYearCounter = new JTextField();
				TIMEYearCountPanel.add(TIMEYearCounter, "cell 0 0,growx,aligny top");
				TIMEYearCounter.setColumns(10);
				gui.setTextField("numYears", TIMEYearCounter, gui.uint2);
	}
	
}
