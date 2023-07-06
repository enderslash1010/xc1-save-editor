package Controller;
import java.util.HashMap;

import com.google.common.collect.HashBiMap;

import Model.Array;
import Model.Data;
import Model.DataType;
import Model.Pointer;
import Model.SaveFile;
import View.GUI;
import View.ViewEvent;
import View.ViewListener;

/**
 * This is the "controller" for the SaveFile "model"
 * It stands between the SaveFile and GUI and facilitates communication between them
 * Listens for events sent by the GUI, then communicates with the SaveFile if needed, and then updates the GUI
 * 
 * @author ender
 */

@SuppressWarnings("serial")
public class SaveFileController implements ViewListener {

	private SaveFile saveFile; // model
	private GUI gui; // view

	private boolean loading = false; // flag to silence ViewEvents upon loading, since actionListeners for JComponents would fire from this initial load, when no action from this controller needs to be taken
	private boolean gettingArrayData = false; // don't fire a ViewEvent to set array data while getting array data

	public SaveFileController() {
		this.gui = new GUI(this);
	}

	@Override
	public void viewEventOccurred(ViewEvent e) {
		if (loading) return; // ignore ViewEvents when loading
		if (this.gettingArrayData) return; // if the controller is currently getting array data, then an erroneous ViewEvent is called from an actionListener, which causes inf loop

		System.out.println("View Event: " + e.getType() + " ; " + e.getParam());
		switch (e.getType()) {
		case ViewEvent.OPEN_FILE: // File is opened, need to initialize new saveFile Object, and send data from model to view
			String fileLocation = e.getParam();
			this.loading = true;
			openFile(fileLocation);
			this.loading = false;
			break;
		case ViewEvent.SAVE_FILE: // Model is saved to actual save file, assume that model and view are synced
			saveFile();
			break;
		case ViewEvent.GET_DATA: // sends data (from model) associated with name to view (gui)
			String name = e.getParam();
			loadValue(name);
			break;
		case ViewEvent.GET_ARRAY_DATA:
			String[] args = e.getParam().split(":"); // args.length=3
			this.gettingArrayData = true;
			loadArrayValue(args[0], Integer.parseInt(args[1]), args[2]);
			this.gettingArrayData = false;
			break;
		case ViewEvent.SET_DATA: // sends value of field in gui to save in model
			args = e.getParam().split(":"); // args.length=2
			name = args[0];

			Data data = (Data) SaveFile.DataMap.get(name);

			String value = null;
			try {
				value = args[1];
			}
			catch (ArrayIndexOutOfBoundsException oobe) {
				if (((Data) data).getType() == DataType.String) { // if data type is string, then value = ""
					value = "";
				}
				else { // data is null, reload value
					viewEventOccurred(new ViewEvent(this, ViewEvent.GET_DATA, name));
					return;
				}
			}

			// transform the value if needed
			value = viewToModel(name, value);

			// convert to correct type
			Object convertedValue = convertToType(data, value);
			if (convertedValue == null) viewEventOccurred(new ViewEvent(this, ViewEvent.GET_DATA, name));

			try {
				saveFile.setData(data, convertedValue);
			}
			catch (Exception e1){
				e1.printStackTrace();
			}

			// once the value is saved into the Model, retrieve the value from the model to show in View component
			viewEventOccurred(new ViewEvent(this, ViewEvent.GET_DATA, name));

			break;
		case ViewEvent.SET_ARRAY_DATA:
			args = e.getParam().split(":"); // args.length=4
			String arrName = args[0];
			int index = Integer.parseInt(args[1]);
			String internalColName = args[2];

			value = null;
			try {
				value = args[3];
			}
			catch (ArrayIndexOutOfBoundsException oobe) { // value is empty (i.e. ""), then reload it
				viewEventOccurred(new ViewEvent(this, ViewEvent.GET_ARRAY_DATA, arrName + ":" + index + ":" + internalColName));
				return;
			}

			// transform value if needed
			value = viewToModel(internalColName, value);

			// convert to correct type
			data = ((Array) SaveFile.DataMap.get(arrName)).get(index, internalColName);
			convertedValue = convertToType(data, value);
			if (convertedValue == null) viewEventOccurred(new ViewEvent(this, ViewEvent.GET_ARRAY_DATA, arrName + ":" + index + ":" + internalColName));

			try {
				saveFile.setArrayData(arrName, index, internalColName, convertedValue);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// once the value is saved into the Model, retrieve the value from the model to show in View component
			viewEventOccurred(new ViewEvent(this, ViewEvent.GET_ARRAY_DATA, arrName + ":" + index + ":" + internalColName));

			break;
		}
	}
	
	/**
	 * Instantiates a new SaveFile, enables user input fields, and shows a success message
	 * Shows an error message if something goes wrong upon instantiation
	 * Called when this <code>SaveFileController</code> receives a <code>ViewEvent<code> to open a file
	 * @param fileLocation the location of the file to be opened
	 */
	public void openFile(String fileLocation) {
		try {
			this.saveFile = new SaveFile(fileLocation);
		} catch (Exception e1) {
			gui.showMessage("Error opening file: " + e1.getMessage());
			return;
		}
		loadValues();
		gui.setCurrFile(fileLocation);
		gui.setEnabled(true); // enable fields when a file is opened
		gui.showMessage("Sucessfully Opened File");
	}
	
	/**
	 * Ensures there is a <code>SaveFile</code> before saving, calls saveToFile to save to the actual file, and then shows a success message
	 * Called when this <code>SaveFileController</code> receives a <code>ViewEvent</code> to save a file
	 */
	public void saveFile() {
		if (this.saveFile == null) gui.showMessage("No save file is opened, cannot save");
		else {
			String result = saveFile.saveToFile();
			gui.showMessage(result);
		}
	}
	
	/**
	 * Gets the value of fieldName from saveFile, and tells the GUI to load this value into the component associated with fieldName
	 *  
	 * @param fieldName the name of a <code>Data</code> object from SaveFile.DataMap
	 */
	private void loadValue(String fieldName) {
		// get value of fieldName from saveFile
		Pointer p = SaveFile.DataMap.get(fieldName);
		if (p instanceof Array) return; // cannot load Array values here, use loadArrayValue 
		
		// get value from saveFile
		Object value = saveFile.getData((Data) p);

		// transform data to user-readable value if needed
		value = modelToView(fieldName, value);

		// tell GUI to load value
		gui.setValue(fieldName, value);

	}
	
	/**
	 * Gets all values from the saveFile and tells the GUI to load them
	 */
	private void loadValues() {
		// iterate through SaveFile.DataMap to set all values
		for (String key : SaveFile.DataMap.keySet()) {
			Pointer p = SaveFile.DataMap.get(key);

			if (SaveFile.DataMap.get(key) instanceof Array) {
				Array arr = (Array) p;
				for (String internalColName : arr.getColNames()) {
					for (int index = 0; index < arr.getRowLength(); index++) {
						loadArrayValue(key, index, internalColName);
					}
				}
			}
			else { // instanceof Data
				loadValue(key);
			}
		}
	}
	
	/**
	 * Gets the value of the array at index and internalColName, and tells the GUI to load this value into the associated table cell
	 *  
	 * @param arrName the name of Array from SaveFile.DataMap
	 * @param index the index of Array to get value from
	 * @param internalColName an internal column name of Array to get value from
	 */
	private void loadArrayValue(String arrName, int index, String internalColName) {
		// make sure arrName is a valid array
		Pointer arrP = SaveFile.DataMap.get(arrName);
		if (arrP instanceof Data) return; // don't allow data that's not Array

		// get value of arrName[index][colName]
		Object value = saveFile.getArrayAt((Array) arrP, index, internalColName);

		// transform data if needed
		value = modelToView(internalColName, value);

		// tell GUI to load value
		gui.setArrayValue(arrName, index, internalColName, value);
	}
	
	/**
	 * Converts the value passed in to it's respective data type
	 * 
	 * @param data the <code>Data</code> object used to get value
	 * @param value the value of the <code>Data</code> object
	 * @return the value converted into the correct data type
	 */
	private Object convertToType(Data data, String value) {
		switch (((Data) data).getType()) {
		case Boolean:
			return (value.equals("true") ? true : false);
		case Float:
			try {
				if (value.length() == 10 && value.substring(0, 2).equals("0x")) {
					return Float.intBitsToFloat(Integer.parseInt(value.substring(2), 16));
				}
				else {
					// doesn't fit form 0x????????
					return Float.parseFloat(value);
				}
			}
			catch (NumberFormatException nfe) { // not a real float, load value back
			}
		case Int:
			try {
				return Integer.parseInt(value);
			}
			catch (NumberFormatException nfe) { // not an int (i.e. "" or "-"), load value back
			}
		case String:
			return value;
		case TPL:
			// TODO: handle TPL Images
			break;
		}
		return null;
	}

	/**
	 * Converts a mapID to how its represented in the save file in it's separated form ("Tephra Cave (ma0201)" -> "0201" -> 0x 01 00 02)
	 * 
	 * @param mapID the View value for mapNum
	 * @return the converted Model value for separated mapNum
	 */
	public String viewToModelSeparatedMapID(String mapID) {
		mapID = mapValues.get(mapID);
		int result = 0;
		result += Integer.parseInt(mapID.substring(2));
		result = result << 16;
		result += Integer.parseInt(mapID.substring(0, 2));
		return result + "";
	}

	/**
	 * Converts a separated mapID to how it's shown in the UI (0x010002 -> "0201" -> "Tephra Cave (ma0201)")
	 * 
	 * @param mapID the Model value for separated mapNum
	 * @return the converted View value for mapNum
	 */
	public String modelToViewSeparatedMapID(int mapID) {
		int first = (mapID % 256);
		int second = (mapID >> 16);

		String temp = String.format("%02d%02d", first, second);
		return mapValues.inverse().get(temp);
	}

	/**
	 * Converts a mapID to how it's shown in gui (0x0102 -> "0201" -> "Tephra Cave (ma0201)")
	 * 
	 * @param mapID the Model value for mapNum
	 * @return the converted View value for mapNum
	 */
	public String modelToViewMapID(int mapID) {
		int first = (mapID >> 8);
		int second = (mapID % 256);

		String temp = String.format("%02d%02d", first, second);
		return mapValues.inverse().get(temp);
	}

	/**
	 * Converts a mapID to how its represented in the save file ("Tephra Cave (ma0201)" -> "0201" -> 0x0102)
	 * 
	 * @param mapID the View value for mapNum
	 * @return the converted Model value for separated mapNum
	 */
	public int viewToModelMapID(String mapID) {
		String stringRep = mapValues.get(mapID); // always a string of length 4, i.e. "0102"

		int first = Integer.parseInt(stringRep.substring(0, 2));
		int second = Integer.parseInt(stringRep.substring(2));

		int result = (first << 8) + second;
		return result;
	}

	/**
	 * View-Model Mappings: pairs values that the View (GUI) uses to their internal representation in the save file (Model)
	 * Stored in a HashBiMap, to convert both view->model and model->view 
	 */
	private final HashBiMap<String, String> pclist = HashBiMap.create(new HashMap<String, String>() {{
		put(" ", "0"); // character slot empty
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

	private final HashBiMap<String, String> mapValues = HashBiMap.create(new HashMap<String, String> () {{
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

	final HashBiMap<String, String> boxRankMap = HashBiMap.create(new HashMap<String, String>() {{
		put("Unspecified", "0");
		put("Normal", "1"); 
		put("Rare", "2"); 
		put("Super", "3");
	}});

	/**
	 * Translates model values to view (user-facing) values
	 * 
	 * @param fieldName a field name from SaveFile.DataMap
	 * @param modelVal the Model value to be translated
	 * @return the translated View value if there is a translation, otherwise it's unchanged from parameter modelVal
	 */
	public Object modelToView(String fieldName, Object modelVal) {
		switch (fieldName) {
		case "picSlot1": case "picSlot2": case "picSlot3": case "picSlot4": case "picSlot5": case "picSlot6": case "picSlot7": 
		case "player1": case "player2": case "player3": case "player4": case "player5": case "player6": case "player7":
			modelVal = pclist.inverse().get(modelVal.toString());
			break;
		case "mapNum":
			modelVal = modelToViewSeparatedMapID((int) modelVal);
			break;
		case "mapNum2":
			modelVal = mapValues.inverse().get(modelVal.toString());
			break;
		case "mapID": case "boxMapID": // array column name
			modelVal = modelToViewMapID((int) modelVal);
			break;
		case "boxRank":
			modelVal = boxRankMap.inverse().get(modelVal.toString());
			break;
		case "nonInvertedYAxis": case "nonInvertedXAxis": // need to inverse these fields to have the 'Inverted' checkbox
			modelVal = !((boolean) modelVal);
		}
		return modelVal;
	}

	/**
	 * Translates view values to model values
	 * 
	 * @param fieldName a field name from SaveFile.DataMap
	 * @param viewVal the View value to be translated
	 * @return the translated Model value if there is a translation, otherwise unchanged from parameter viewVal
	 */
	public String viewToModel(String fieldName, String viewVal) {
		switch (fieldName) {
		case "picSlot1": case "picSlot2": case "picSlot3": case "picSlot4": case "picSlot5": case "picSlot6": case "picSlot7":
		case "player1": case "player2": case "player3": case "player4": case "player5": case "player6": case "player7":
			viewVal = pclist.get(viewVal);
			break;
		case "mapNum":
			viewVal = viewToModelSeparatedMapID(viewVal.toString());
			break;
		case "mapNum2":
			viewVal = mapValues.get(viewVal);
			break;
		case "mapID": case "boxMapID": // array column name
			viewVal = viewToModelMapID(viewVal)+"";
			break;
		case "boxRank":
			viewVal = boxRankMap.get(viewVal);
			break;
		case "nonInvertedYAxis": case "nonInvertedXAxis": // need to inverse these fields to have the 'Inverted' checkbox
			viewVal = !((Boolean.parseBoolean(viewVal)))+"";
		}
		return viewVal;
	}

}
