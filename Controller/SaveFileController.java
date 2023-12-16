package Controller;
import java.util.HashMap;

import com.google.common.collect.HashBiMap;

import Model.Array;
import Model.Data;
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

		System.out.println("View Event: " + e);
		switch (e.getType()) {
		case OPEN_FILE: // File is opened, need to initialize new saveFile Object, and send data from model to view
			this.loading = true;
			openFile(e.getFileLocation());
			this.loading = false;
			break;
		case SAVE_FILE: // Model is saved to actual save file, assume that model and view are synced
			saveFile();
			break;
		case GET_DATA: // sends data (from model) associated with name to view (gui)
			loadValue(e.getSaveField());
			break;
		case GET_ARRAY_DATA:
			this.gettingArrayData = true;
			loadArrayValue(e.getSaveField(), e.getIndex(), e.getArrayField());
			this.gettingArrayData = false;
			break;
		// TODO: may be able to combine SET_DATA and SET_ARRAY_DATA
		case SET_DATA: // sends value of field in gui to save in model
			SaveField saveField = e.getSaveField();
			Data data = (Data) SaveFile.DataMap.get(saveField);
			String value = e.getValue();

			// transform the value if needed
			value = viewToModel(saveField, value);

			// convert to correct type
			Object convertedValue = convertToType(data, value);
			
			try {
				saveFile.setData(data, convertedValue);
			}
			catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			}
			catch (NullPointerException e2) {
				System.out.print("Reloading saved value: ");
			}

			// once the value is saved into the Model, retrieve the value from the model to show in View component (or if convertedVal is null, reload the saved value)
			viewEventOccurred(new ViewEvent(this, ViewEvent.EventType.GET_DATA, null, saveField, null, null, null));

			break;
		case SET_ARRAY_DATA:
			SaveField arrName = e.getSaveField();
			Array arr = (Array) SaveFile.DataMap.get(arrName);
			int index = e.getIndex();
			ArrayField colName = e.getArrayField();
			value = e.getValue();

			// transform value if needed
			value = viewToModel(colName, value);

			// convert to correct type
			data = arr.get(index, colName);
			convertedValue = convertToType(data, value);

			try {
				saveFile.setData(data, convertedValue);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			}
			catch (NullPointerException e2) {
				System.out.print("Reloading saved value: ");
			}

			// Determine whether to set/clear StaticElements
			boolean isIndexNull = saveFile.isArrayIndexNull(arr, index);
			if (isIndexNull) { // clear StaticElements
				Data[] statics = arr.getStatics(index);
				for (Data d : statics) saveFile.setData(d, 0);
			}
			else { // set StaticElements
				HashMap<Data, Integer> staticVals = arr.getStaticValues(index);
				for (Data d : staticVals.keySet()) saveFile.setData(d, staticVals.get(d));
			}
			
			// once the value is saved into the Model, retrieve the value from the model to show in View component
			viewEventOccurred(new ViewEvent(this, ViewEvent.EventType.GET_ARRAY_DATA, null, arrName, colName, index, null));

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
	private void loadValue(SaveField fieldName) {
		// get value of fieldName from saveFile
		Data p = (Data) SaveFile.DataMap.get(fieldName);

		// get value from saveFile
		Object value = saveFile.getData(p);

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
		for (SaveField sfname : SaveFile.DataMap.keySet()) {
			if (sfname == SaveField.foregroundWeather || sfname == SaveField.backgroundWeather) continue; // foreground/background weather is loaded with the map
			
			Pointer p = SaveFile.DataMap.get(sfname);

			if (SaveFile.DataMap.get(sfname) instanceof Array) {
				Array arr = (Array) p;
				for (ArrayField internalColName : arr.getColNames()) {
					for (int index = 0; index < arr.getRowLength(); index++) {
						loadArrayValue(sfname, index, internalColName);
					}
				}
			}
			else { // instanceof Data
				loadValue(sfname);
				if (sfname == SaveField.mapNum) { // foreground/background weather is loaded with the map
					loadValue(SaveField.foregroundWeather);
					loadValue(SaveField.backgroundWeather);
				}
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
	private void loadArrayValue(SaveField arrName, int index, ArrayField internalColName) {
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
		if (value == null) return null;
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
			catch (NumberFormatException nfe) {
				break; // not a real float, load value back
			}
		case Int:
			try {
				return Integer.parseInt(value);
			}
			catch (NumberFormatException nfe) { // not an int (i.e. "" or "-"), load value back
				break;
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
	public String viewToModelSeparatedMapID(String mapID, boolean reversed) {
		mapID = mapValues.get(mapID);
		int result = 0;
		if (reversed) {
			result += Integer.parseInt(mapID.substring(2));
			result = result << 16;
			result += Integer.parseInt(mapID.substring(0, 2));
		}
		else {
			result += Integer.parseInt(mapID.substring(0, 2));
			result = result << 16;
			result += Integer.parseInt(mapID.substring(2));
		}
		return result + "";
	}

	/**
	 * Converts a separated mapID to how it's shown in the UI (0x010002 -> "0201" -> "Tephra Cave (ma0201)")
	 * 
	 * @param mapID the Model value for separated mapNum
	 * @return the converted View value for mapNum
	 */
	public String modelToViewSeparatedMapID(int mapID, boolean reversed) {
		int first = (mapID >> 16);
		int second = (mapID % 256);
		
		String temp;
		if (reversed) temp = String.format("%02d%02d", second, first);
		else temp = String.format("%02d%02d", first, second);
		
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
		put("Fallen Prison Island (ma1202)", "1202");
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
	
	final HashBiMap<String, String> foregroundWeatherValues = HashBiMap.create(new HashMap<String, String>() {{
		put("Clear", "1");
		put("Rain", "2");
		put("Storm", "3");
		put("Snow", "4");
		put("Blizzard", "5");
		put("Heatwave", "6");
		put("Dense Fog", "9");
		put("Sandstorm", "10");
		put("Shooting Stars", "11");
		put("Slumber", "12");
		put("Rebirth", "14");
	}});
	
	final HashBiMap<String, String> gemNames = HashBiMap.create(new HashMap<String, String>() {{
		put("None", "0");
		put("Strength Up", "1");
		put("Chill Defence", "2");
		put("Sleep Resist", "3");
		put("Slow Resist", "4");
		put("Bind Resist", "5");
		put("Buff Time Plus", "6");
		put("Weapon Power", "7");
		put("Strength Down", "8");
		put("Blaze Plus", "9");
		put("Blaze Attack", "10");
		put("Spike", "11");
		put("Revival HP Up", "12");
		put("Initial Tension", "13");
		put("Aggro Up", "14");
		put("EXP Up", "15");
		put("Weaken", "16"); // Unused
		put("HP Up", "17");
		put("Poison Defence", "18");
		put("Spike Defence", "19");
		put("Paralysis Resist", "20");
		put("Debuff Resist", "21");
		put("Recovery Up", "22");
		put("Aura Heal", "23");
		put("Damage Heal", "24");
		put("Arts Heal", "25");
		put("HP Steal", "26");
		put("Unbeatable", "27");
		put("AP Up", "28");
		put("Aquatic Cloak", "29");
		put("Auto-Heal Up", "30");
		put("Terrain Defence", "31");
		put("HP Weaken", "32"); // Unused
		put("Ether Up", "33");
		put("Double Attack", "34");
		put("Daze Resist", "35");
		put("Pierce Resist", "36");
		put("Daze Plus", "37");
		put("Phys Def Down", "38");
		put("Paralysis", "39");
		put("Lightning Attack", "40");
		put("Electric Plus", "41");
		put("Back Atk Plus", "42");
		put("First Attack Plus", "43");
		put("Daze Up", "44");
		put("Cast Quicken", "45"); // Unused
		put("Tension Swing", "46");
		put("Daze Tension", "47");
		put("Ether Weaken", "48"); // Unused
		put("Ether Def Up", "49");
		put("Blaze Defence", "50");
		put("Lock-On Resist", "51");
		put("Confuse Resist", "52");
		put("Critical Resist", "53"); // Unused
		put("Ether Protect", "54");
		put("Slow", "55");
		put("Bind", "56");
		put("Ether Def Down", "57");
		put("Chill Plus", "58");
		put("Chill Attack", "59");
		put("Auto-Atk Stealth", "60");
		put("Arts Stealth", "61");
		put("Talent Boost", "62");
		put("Heat Sink", "63");
		put("Ether Smash", "64"); // Unused
		put("Agility Up", "65");
		put("Topple Resist", "66");
		put("Good Footing", "67");
		put("Arts Seal Resist", "68");
		put("Accuracy Up", "69"); // Unused
		put("Haste", "70");
		put("Topple Plus", "71");
		put("Bleed Attack", "72");
		put("Bleed Plus", "73");
		put("Topple Up", "74");
		put("Agility Down", "75");
		put("Break", "76");
		put("Quick Step", "77");
		put("Fall Defence", "78");
		put("Aerial Cloak", "79");
		put("Agility Weaken", "80"); // Unused
		put("Muscle Up", "81");
		put("Attack Stability", "82");
		put("Attack Plus", "83");
		put("Critical Up", "84");
		put("Bleed Defence", "85");
		put("Divine Protect", "86");
		put("Physical Protect", "87");
		put("Night Vision", "88");
		put("Debuff Plus", "89");
		put("Armour Power", "90"); // Unused
		put("Ether Down", "91");
		put("Poison Plus", "92");
		put("Poison Attack", "93");
		put("Aggro Down", "94");
		put("Earth Cloak", "95");
		put("Muscle Waste", "96"); // Unused
		put("Unbeatable", "97");
		put("Impurity", "98"); // Unused
	}});

	/**
	 * Translates model values to view (user-facing) values
	 * 
	 * @param fieldName a field name from SaveFile.DataMap
	 * @param modelVal the Model value to be translated
	 * @return the translated View value if there is a translation, otherwise it's unchanged from parameter modelVal
	 */
	@SuppressWarnings("incomplete-switch") // only some SaveField enums need to be translated, so we don't need a complete switch
	public Object modelToView(SaveField fieldName, Object modelVal) {
		Object viewVal = modelVal;
		switch (fieldName) {
		case picSlot1: case picSlot2: case picSlot3: case picSlot4: case picSlot5: case picSlot6: case picSlot7: 
		case player1: case player2: case player3: case player4: case player5: case player6: case player7:
			viewVal = pclist.inverse().get(modelVal.toString());
			break;
		case mapNum:
			viewVal = modelToViewSeparatedMapID((int) modelVal, true);
			break;
		case mapNum2:
			viewVal = mapValues.inverse().get(modelVal.toString());
			break;
		case weatherMap:
			viewVal = modelToViewSeparatedMapID((int) modelVal, false);
			break;
		case nonInvertedYAxis: case nonInvertedXAxis: // need to inverse these fields to have the 'Inverted' checkbox
			viewVal = !((boolean) modelVal);
			break;
		case foregroundWeather:
			viewVal = foregroundWeatherValues.inverse().get(modelVal.toString());
			break;
		case backgroundWeather:
			String map = gui.getValue(SaveField.mapNum);
			int intModelVal = (int) modelVal;
			
			if (intModelVal == 0) { // Clear
				viewVal = "Clear";
			}
			else if (map.equals("Makna Forest (ma0601)")) {
				if (intModelVal == 1) viewVal = "Heatwave";
				else viewVal = "Rain";
			}
			else if (map.equals("Eryth Sea (ma1001)") || map.equals("Alcamoth (ma1101)") || map.equals("Prison Island (ma1201)")) {
				if (intModelVal == 1) viewVal = "Storm";
				else viewVal = "Shooting Stars";
			}
			else if (map.equals("Satorl Marsh (ma0501)")) viewVal = "Dense Fog";
			else if (map.equals("Bionis' Interior (ma2101)")) viewVal = "Slumber";
			else if (map.equals("Valak Mountain (ma1301)")) {
				if (intModelVal == 1) viewVal = "Snow";
				else viewVal = "Blizzard";
			}
			else if (map.equals("Sword Valley (ma1401)")) viewVal = "Sandstorm";
			else if (map.equals("Mechonis Core (ma2301)")) viewVal = "Rebirth";
			else {
				if (intModelVal == 1) viewVal = "Rain";
				else viewVal = "Storm";
			}
			break;
		}
		if (viewVal == null) return modelVal; // if Data is set outside this save editor, then some variables may be set to unmapped values; In that case, return modelVal
		return viewVal;
	}
	@SuppressWarnings("incomplete-switch") // only some Array columns need to be translated, so we don't need a complete switch
	public Object modelToView(ArrayField colName, Object modelVal) {
		Object viewVal = modelVal;
		switch (colName) {
		case mapID: case boxMapID: // array column name
			viewVal = modelToViewMapID((int) modelVal);
			break;
		case boxRank:
			viewVal = boxRankMap.inverse().get(modelVal.toString());
			break;
		case gemID1: case gemID2:
			viewVal = gemNames.inverse().get(modelVal.toString());
			break;
		}
		return viewVal;
	}

	/**
	 * Translates view values to model values
	 * 
	 * @param fieldName a field name from SaveFile.DataMap
	 * @param viewVal the View value to be translated
	 * @return the translated Model value if there is a translation, otherwise unchanged from parameter viewVal
	 */
	@SuppressWarnings("incomplete-switch") // only some SaveField enums need to be translated, so we don't need a complete switch
	public String viewToModel(SaveField fieldName, String viewVal) {
		String modelVal = viewVal;
		switch (fieldName) {
		case picSlot1: case picSlot2: case picSlot3: case picSlot4: case picSlot5: case picSlot6: case picSlot7:
		case player1: case player2: case player3: case player4: case player5: case player6: case player7:
			modelVal = pclist.get(viewVal);
			break;
		case mapNum:
			modelVal = viewToModelSeparatedMapID(viewVal.toString(), true);
			break;
		case mapNum2:
			modelVal = mapValues.get(viewVal);
			break;
		case weatherMap:
			modelVal = viewToModelSeparatedMapID(viewVal.toString(), false);
			break;
		case nonInvertedYAxis: case nonInvertedXAxis: // need to inverse these fields to have the 'Inverted' checkbox
			modelVal = !((Boolean.parseBoolean(viewVal)))+"";
			break;
		case foregroundWeather:
			modelVal = foregroundWeatherValues.get(viewVal);
			break;
		case backgroundWeather:
			switch (viewVal) {
			case "Clear": 
				modelVal = "0"; 
				break;
			case "Slumber":
			case "Heatwave":
			case "Snow":
			case "Rebirth":
			case "Sandstorm":
				modelVal = "1";
				break;
			case "Dense Fog": 
			case "Shooting Stars":
			case "Blizzard":
				modelVal = "2";
				break;
			case "Rain":
				if (gui.getValue(SaveField.mapNum).equals("Makna Forest (ma0601)")) modelVal = "2"; // the only map where rain is 2
				else modelVal = "1";
				break;
			case "Storm":
				String map = gui.getValue(SaveField.mapNum);
				if (map.equals("Eryth Sea (ma1001)") || map.equals("Alcamoth (ma1101)") || map.equals("Prison Island (ma1201)")) modelVal = "1";
				else modelVal = "2";
				break;
			}
			break;
		}
		return modelVal;
	}
	@SuppressWarnings("incomplete-switch") // only some Array columns need to be translated, so we don't need a complete switch
	public String viewToModel(ArrayField colName, String viewVal) {
		String modelVal = viewVal;
		switch (colName) {
		case mapID: case boxMapID: // array column name
			modelVal = viewToModelMapID(viewVal)+"";
			break;
		case boxRank:
			modelVal = boxRankMap.get(viewVal);
			break;
		case gemID1: case gemID2:
			modelVal = gemNames.get(viewVal.toString());
			break;
		}
		return modelVal;
	}

}
