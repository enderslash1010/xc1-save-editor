package Controller;
import java.util.HashMap;
import com.google.common.collect.HashBiMap;

import Model.Array;
import Model.Data;
import Model.ModelEvent;
import Model.ModelListener;
import Model.Pointer;
import Model.SaveFile;
import View.GUI;
import View.ViewEvent;
import View.ViewListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 *   class SaveFileController
 *   
 *   The "controller" for the SaveFile "model"
 */

@SuppressWarnings("serial")
public class SaveFileController implements ViewListener, ModelListener {
	
	private SaveFile saveFile; // model
	private GUI gui; // view
	
	// Pair structures that pair user-friendly values shown by gui to their corresponding save file values
	private final HashBiMap<String, String> pclist = HashBiMap.create(new HashMap<String, String>() {{
		put(" ", "0");
		put("Shulk", "1");
		put("Reyn", "2");
		put("Fiora", "3");
		put("Dunban", "4");
		put("Sharla", "5");
		put("Riki", "6");
		put("Melia", "7");
		put("Seven", "8");
		put("Dickson", "9");
		put("Mumkhar", "10");
		put("Alvis", "11");
		put("Prologue Dunban", "12");
		put("Other Dunban", "13");
	}});
	
	private final HashBiMap<String, String> GAMEMapValues = HashBiMap.create(new HashMap<String, String> () {{
		put("Title Screen (ma0000)", "0000");
		put("Colony 9 (ma0101)", "0101");
		put("Tephra Cave (ma0201)", "0201");
		put("Bionis' Leg (ma0301)", "0301");
		put("Colony 6 (ma0401)", "0401");
		put("Ether Mine (ma0402)", "0402");
		put("Satorl Marsh (ma0501)", "0501");
		put("Makna Forest (ma0601)", "0601");
		put("Frontier Village (ma0701)", "0701");
		put("Bionis' Shoulder (ma0801)", "0801");
		put("High Entia Tomb (ma0901)", "0901");
		put("Eryth Sea (ma1001)", "1001");
		put("Alcamoth (ma1101)", "1101");
		put("Prison Island (ma1201)", "1201");
		put("Prison Island 2 (ma1202)", "1202");
		put("Valak Mountain (ma1301)", "1301");
		put("Sword Valley (ma1401)", "1401");
		put("Galahad Fortress (ma1501)", "1501");
		put("Fallen Arm (ma1601)", "1601");
		put("Beta Fallen Arm (ma1602)", "1602");
		put("Mechonis Field (ma1701)", "1701");
		put("Agniratha (ma1901)", "1901");
		put("Central Factory (ma2001)", "2001");
		put("Bionis' Interior (ma2101)", "2101");
		put("Memory Space (ma2201)", "2201");
		put("Mechonis Core (ma2301)", "2301");
		put("Junks (ma2401)", "2401");
		put("Post-Game Colony 9 (ma0102)", "0102");
	}});
	
	public SaveFileController() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // set look-and-feel, TODO: make it look better :)
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gui = new GUI(this);
	}
	
	/*
	 * 	loads all values from saveFile upon load
	 */
	private void loadValues() {
		// iterate through SaveFile.DataMap to set all values
		for (String key : SaveFile.DataMap.keySet()) {
			loadValue(key);
		}
	}
	
	private void loadValue(String name) {
		Object data = saveFile.getData(SaveFile.DataMap.get(name));
		
		// transform data to user-readable value if needed
		switch (name) {
		case "picSlot1": case "picSlot2": case "picSlot3": case "picSlot4": case "picSlot5": case "picSlot6": case "picSlot7": 
		case "player1": case "player2": case "player3": case "player4": case "player5": case "player6": case "player7":
			data = pclist.inverse().get(data.toString());
			break;
		case "mapNum":
			data = getFormattedMapNum((int) data);
			break;
		case "mapNum2":
			data = GAMEMapValues.inverse().get(data.toString());
			break;
		}

		gui.setValue(name, data);
	}

	/*
	 *   handles events broadcasted by model (saveFile)
	 */
	@Override
	public void modelEventOccurred(ModelEvent e) {
		switch (e.getType()) {
		case ModelEvent.SET_DATA: // value in model changed, update view
			//System.out.println(e.getParam());
			loadValue(e.getParam());
			break;
		}
	}

	/*
	 *   handles events broadcasted by view (gui)
	 *   
	 *   TODO: when first opening the file, when JComboBoxes are set, it also fires a ViewEvent to SET_DATA too (so it fires actionListener too)
	 */
	@Override
	public void viewEventOccurred(ViewEvent e) {
		switch (e.getType()) {
		case ViewEvent.OPEN_FILE: // File is opened, need to initialize new saveFile Object, and send data from model to view
			String fileLocation = e.getParam();
			try {
				this.saveFile = new SaveFile(fileLocation, this);
			} catch (Exception e1) {
				gui.showMessage("Error loading file");
				e1.printStackTrace();
			}
			loadValues();
			break;
		case ViewEvent.SAVE_FILE: // Model is saved to actual save file, assume that model and view are synced
			if (saveFile == null) gui.showMessage("No save file is opened, cannot save");
			else {
				String result = saveFile.saveToFile();
				gui.showMessage(result);
			}
			break;
		case ViewEvent.GET_DATA: // sends data (from model) associated with name to view (gui)
			String name = e.getParam();
			loadValue(name);
			break;
		case ViewEvent.SET_DATA: // sends value of field in gui to save in model
			String[] temp = e.getParam().split(":");
			name = temp[0];
			String value = temp[1];
			Pointer data = SaveFile.DataMap.get(name);
			
			// see if value needs to be transformed
			switch (name) {
			case "picSlot1": case "picSlot2": case "picSlot3": case "picSlot4": case "picSlot5": case "picSlot6": case "picSlot7":
			case "player1": case "player2": case "player3": case "player4": case "player5": case "player6": case "player7":
				value = pclist.get(value);
				break;
			case "mapNum":
				value = getFileMapNum(value);
				break;
			case "mapNum2":
				value = GAMEMapValues.get(value);
				break;
			}
			
			if (data instanceof Data) { // setData
				switch (((Data) data).getType()) {
				case Boolean:
					try {
						saveFile.setData(data, value.equals("true") ? true : false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case Float:
					try {
						if (value.substring(0, 2).equals("0x") && !value.substring(2).equals("")) { 
							saveFile.setData(data, Float.intBitsToFloat(Integer.parseInt(value.substring(2), 16)));
							return;
						}
					}
					catch (IndexOutOfBoundsException e1) {
						// doesn't fit form 0x????????, don't need to do anything
					} catch (Exception e2) {
						// Shouldn't reach this because user input is checked through the float FocusListener
						e2.printStackTrace();
					}
					
					try {
						saveFile.setData(data, Float.parseFloat(value));
					}
					catch (Exception e3) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e3.printStackTrace();
					}
					break;
				case Int:
					try {
						saveFile.setData(data, Integer.parseInt(value));
					} catch (Exception e1) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e1.printStackTrace();
					}
					break;
				case String:
					try {
						saveFile.setData(data, value);
					} catch (Exception e1) {
						// Shouldn't reach this because user input is checked through DocumentFilters and FocusListeners
						e1.printStackTrace();
					}
					break;
				case TPL:
					// TODO: handle TPL Images
					break;
				}
			}
			else if (data instanceof Array) { // setArray
				// TODO: handle arrays
			}
			break;
		}
	}
	
	// Converts mapNum to how its represented in the save file ("Tephra Cave (ma0201)" -> 0201 -> 0x 01 00 02)
	public String getFileMapNum(String mapNum) {
		mapNum = GAMEMapValues.get(mapNum);
		int result = 0;
		result += Integer.parseInt(mapNum.substring(2));
		result = result << 16;
		result += Integer.parseInt(mapNum.substring(0, 2));
		return result + "";
	}
	
	// Converts mapNum to how it's shown in gui (0x010002 -> 0201 -> "Tephra Cave (ma0201)")
	public String getFormattedMapNum(int mapNum) {
		int first = (mapNum % 256);
		int second = (mapNum >> 16);
		
		String temp = String.format("%02d%02d", first, second);
		
		return GAMEMapValues.inverse().get(temp);
	}

}
