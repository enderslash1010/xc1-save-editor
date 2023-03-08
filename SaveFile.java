import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

@SuppressWarnings("serial")
public class SaveFile {

	private String fileLocation;
	private byte[] saveFile = new byte[0x28000];
	
	private boolean dirty; // True if saveFile data structure is not same as save file on disk

	final static int[][] sections = 
				{{0x20, 0x9CA0},     // THUM 0
				{0xA030, 0xB244},    // FLAG 1
				{0xB260, 0x11E88},   // GAME 2
				{0x11EB0, 0x11EBC},  // TIME 3
				{0x11EE0, 0x11F14},  // PCPM 4
				{0x11F30, 0x11F40},  // CAMD 5
				{0x11F60, 0x24080},  // ITEM 6
				{0x24090, 0x240A0},  // WTHR 7
				{0x240C0, 0x240D0},  // SNDS 8
				{0x240F0, 0x24474},  // MINE 9
				{0x244A0, 0x246D4},  // TBOX 10
				{0x248B0, 0x248F0}}; // OPTD 11

	final static int[] checksums = {0x1E, 0xA02E, 0xB25E, 0x11EAE, 0x11EDE, 0x11F2E, 0x11F5E, 0x2408E, 0x240BE, 0x240EE, 0x2449E, 0x248AE};
	final static String[] sectionNames = {"THUM", "FLAG", "GAME", "TIME", "PCPM", "CAMD", "ITEM", "WTHR", "SNDS", "MINE", "TBOX", "OPTD"};
	
	final static HashMap<String, Pointer> THUMData = new HashMap<String, Pointer>() {{
		put("level", new Data(0x84, 0x86, DataType.Int));
		put("playTimeHours", new Data(0x2A, 0x2C, DataType.Int));
		put("playTimeMins", new Data(0x2C, 0x2E, DataType.Int));
		put("playTimeSeconds", new Data(0x23, 0x24, DataType.Int));
		put("saveTimeDay", new Data(0x29, 0x2A, DataType.Int));
		put("saveTimeMonth", new Data(0x29, 0x2A, DataType.Int));
		put("saveTimeYear", new Data(0x24, 0x26, DataType.Int));
		put("saveTimeHour", new Data(0x28, 0x29, DataType.Int));
		put("saveTimeMinute", new Data(0x22, 0x23, DataType.Int));
		put("picSlot1", new Data(0x37, 0x38, DataType.Int));
		put("picSlot2", new Data(0x3B, 0x3C, DataType.Int));
		put("picSlot3", new Data(0x3F, 0x40, DataType.Int));
		put("picSlot4", new Data(0x43, 0x44, DataType.Int));
		put("picSlot5", new Data(0x47, 0x48, DataType.Int));
		put("picSlot6", new Data(0x4B, 0x4C, DataType.Int));
		put("picSlot7", new Data(0x4F, 0x50, DataType.Int));
		put("name", new Data(0x64, 0x84, DataType.String));
		put("systemSaveFlag", new Data(0x86, 0x87, DataType.Boolean));
		put("ng+Flag", new Data(0x87, 0x88, DataType.Boolean));
		put("saveImage", new Data(0xE0, 0x9580, DataType.TPL));
	}};
	final static HashMap<String, Pointer> FLAGData = new HashMap<String, Pointer>() {{
		put("scenarioNum", new Data(0xA0B2, 0xA0B4, DataType.Int));
	}};
	final static HashMap<String, Pointer> GAMEData = new HashMap<String, Pointer>() {{
		put("mapNum1", new Data(0xB263, 0xB264, DataType.Int));
		put("mapNum2", new Data(0xB261, 0xB262, DataType.Int));
		put("player1", new Data(0xD1FE, 0xD200, DataType.Int));
		put("player2", new Data(0xD202, 0xD204, DataType.Int));
		put("player3", new Data(0xD206, 0xD208, DataType.Int));
		put("player4", new Data(0xD20A, 0xD20C, DataType.Int));
		put("player5", new Data(0xD20E, 0xD210, DataType.Int));
		put("player6", new Data(0xD212, 0xD214, DataType.Int));
		put("player7", new Data(0xD216, 0xD218, DataType.Int));
		put("shulkLevel", new Data(0xF8D0, 0xF8D4, DataType.Int));
		put("reynLevel", new Data(0xFBD4, 0xFBD8, DataType.Int));
		put("fioraLevel", new Data(0xFEC8, 0xFECC, DataType.Int));
		put("dunbanLevel", new Data(0x101DC, 0x101E0, DataType.Int));
		put("sharlaLevel", new Data(0x104E0, 0x104E4, DataType.Int));
		put("rikiLevel", new Data(0x107E4, 0x107E8, DataType.Int));
		put("meliaLevel", new Data(0x10AE8, 0x10AEC, DataType.Int));
		put("sevenLevel", new Data(0x10DEC, 0x10DF0, DataType.Int));
		put("dicksonLevel", new Data(0x110F0, 0x110F4, DataType.Int));
		put("mumkharLevel", new Data(0x113F4, 0x113F8, DataType.Int));
		put("alvisLevel", new Data(0x116F8, 0x116FC, DataType.Int));
		put("prologueDunbanLevel", new Data(0x119FC, 0x11A00, DataType.Int));
	}};
	final static HashMap<String, Pointer> TIMEData = new HashMap<String, Pointer>() {{
		put("playTime", new Data(0x11EB0, 0x11EB4, DataType.Int));
		put("numDays", new Data(0x11EB8, 0x11EBA, DataType.Int));
	}};
	final static HashMap<String, Pointer> PCPMData = new HashMap<String, Pointer>() {{
		put("p1x", new Data(0x11EE0, 0x11EE4, DataType.Float));
		put("p1y", new Data(0x11EE4, 0x11EE8, DataType.Float));
		put("p1z", new Data(0x11EE8, 0x11EEC, DataType.Float));
		put("p1Angle", new Data(0x11EEC, 0x11EF0, DataType.Float));
		put("p2x", new Data(0x11EF0, 0x11EF4, DataType.Float));
		put("p2y", new Data(0x11EF4, 0x11EF8, DataType.Float));
		put("p2z", new Data(0x11EF8, 0x11EFC, DataType.Float));
		put("p2Angle", new Data(0x11EFC, 0x11F00, DataType.Float));
		put("p3x", new Data(0x11F00, 0x11F04, DataType.Float));
		put("p3y", new Data(0x11F04, 0x11F08, DataType.Float));
		put("p3z", new Data(0x11F08, 0x11F0C, DataType.Float));
		put("p3Angle", new Data(0x11F0C, 0x11F10, DataType.Float));
	}};
	final static HashMap<String, Pointer> CAMDData = new HashMap<String, Pointer>() {{
		put("cameraPosVertical", new Data(0x11F30, 0x11F34, DataType.Float));
		put("cameraPosHorizontal", new Data(0x11F34, 0x11F38, DataType.Float));
		put("cameraDistance", new Data(0x11F3C, 0x11F40, DataType.Float));
	}};
	final static HashMap<String, Pointer> ITEMPointer = new HashMap<String, Pointer>() {{
		put("money", new Data(0x2404A, 0x2404C, DataType.Int));
	}};
	final static HashMap<String, Pointer> WTHRPointer = new HashMap<String, Pointer>() {{

	}};
	final static HashMap<String, Pointer> SNDSPointer = new HashMap<String, Pointer>() {{

	}};
	final static HashMap<String, Pointer> MINEPointer = new HashMap<String, Pointer>() {{
		put("mineArray", new Array(0x240F0, 0x24474, new Element[] {
				new Element("mineCooldown", 2, DataType.Int),
				new Element("numHarvests", 1, DataType.Int),
				new Element("minelistID", 1, DataType.Int),
				new Element("mapID", 2, DataType.Int)
		}));
	}};
	final static HashMap<String, Pointer> TBOXPointer = new HashMap<String, Pointer>() {{
		put("numBoxes", new Data(0x244A3, 0x244A4, DataType.Int));
		put("boxArray", new Array(0x244A4, 0x246D8, new Element[] {
				new Element("blank", 4, DataType.Int),
				new Element("xBox", 4, DataType.Float),
				new Element("yBox", 4, DataType.Float),
				new Element("zBox", 4, DataType.Float),
				new Element("boxAngle", 4, DataType.Float),
				new Element("boxRank", 4, DataType.Int),
				new Element("boxDropTable", 2, DataType.Int),
				new Element("boxMapID", 2, DataType.Int)
		}));
	}};
	final static HashMap<String, Pointer> OPTDData = new HashMap<String, Pointer>() {{
		put("nonInvertedYAxis", new Data(0x248B0, 0x248B1, DataType.Boolean));
		put("nonInvertedXAxis", new Data(0x248B1, 0x248B2, DataType.Boolean));
		put("yAxisSpeed", new Data(0x248B2, 0x248B3, DataType.Int));
		put("xAxisSpeed", new Data(0x248B3, 0x248B4, DataType.Int));
		put("zoomSpeed", new Data(0x248B4, 0x248B5, DataType.Int));
		put("pointOfView", new Data(0x248B5, 0x248B6, DataType.Int));
		put("angleCorrection", new Data(0x248B6, 0x248B7, DataType.Boolean));
		put("battleCamera", new Data(0x248B7, 0x248B8, DataType.Boolean));
		put("gamma", new Data(0x248BF, 0x248C0, DataType.Int));
		put("minimap", new Data(0x248C0, 0x248C1, DataType.Boolean));
		put("rotate", new Data(0x248C1, 0x248C2, DataType.Boolean));
		put("jpVoice", new Data(0x248CC, 0x248CD, DataType.Boolean));
		put("showControls", new Data(0x248D0, 0x248D1, DataType.Boolean));
		put("showArtDescriptions", new Data(0x248D1, 0x248D2, DataType.Boolean));
		put("showBuffDebuffInfoEveryTime", new Data(0x248D2, 0x248D3, DataType.Boolean));
		put("showEnemyIcons", new Data(0x248D3, 0x248D4, DataType.Boolean));
		put("showBuffDebuffIndicator", new Data(0x248D4, 0x248D5, DataType.Boolean));
		put("showDestinationMarker", new Data(0x248D5, 0x248D6, DataType.Boolean));
		put("autoEventScrolling", new Data(0x248E0, 0x248E1, DataType.Boolean));
		put("fastDialogueText", new Data(0x248E1, 0x248E2, DataType.Boolean));
		put("showSubtitles", new Data(0x248E2, 0x248E3, DataType.Boolean));
	}};

	public SaveFile(String fileLocation) throws Exception {
		this.fileLocation = fileLocation;
		readFromFile();
		saveToFile();
	}

	public byte getByteAt(int x) {
		return saveFile[x];
	}

	// returns a byte array containing bytes from start (inclusive) to end (exclusive)
	public byte[] getBytesAt(int start, int end) {
		byte[] result = new byte[end-start];
		for (int i = start; i < end; i++) {
			result[i-start] = saveFile[i];
		}
		return result;
	}

	public byte[] getRawData(Data data) {
		int[] location = data.getLocation();
		return getBytesAt(location[0], location[1]);
	}

	public Object getData(Data data) {
		byte[] rawData = getRawData(data);
		switch (data.getType()) {
			case String:
				return getString(rawData);
			case Int:
				return getInt(rawData);
			case Float:
				return getFloat(rawData);
			case Boolean: // assumes boolean is one byte
				return rawData[0] == 0x0 ? false : true;
			case TPL:
				return rawData;
		}
		return null;
	}
	
	private String getString(byte[] rawData) {
		String result = "";
		for (int i = 0; i < rawData.length; i++) {
			result += ((char) rawData[i]);
		}
		return result;
	}
	
	private int getInt(byte[] rawData) {
		int result = 0;
		for (byte b: rawData) {
			result = (result << 8) + (b & 0xFF);
		}
		return result;
	}
	
	private float getFloat(byte[] rawData) {
		return (float) getInt(rawData);
	}
	
	public void setData(Data data, Object value) throws Exception {	
		// check if type of Object value matches data.getType, throw exception if not
		// transform Object value into a byte array
		byte[] result = new byte[data.size()];
		switch (data.getType()) {
			case String:
				if (!(value instanceof String)) throw new Exception("Expecting String, got " + value.getClass());
				for (int i = 0; i < ((String) value).length() && i < result.length; i++) {
					result[i] = (byte) ((String) value).charAt(i);
				}
				break;
			case Int:
				if (!(Integer.class.isInstance(value))) throw new Exception("Expecting Integer, got " + value.getClass());
				result[0] = (byte)(((int) value >> 24) & 0xFF);
				result[1] = (byte)(((int) value >> 16) & 0xFF);
				result[2] = (byte)(((int) value >> 8) & 0xFF);
				result[3] = (byte)(((int) value >> 0) & 0xFF);
				break;
			case Float:
				if (!(Float.class.isInstance(value))) throw new Exception("Expecting Float, got " + value.getClass());
				int tempInt = Float.floatToRawIntBits((float) value); // floatToRawIntBits() preserves NaN values
				result[0] = (byte)((tempInt >> 24) & 0xFF);
				result[1] = (byte)((tempInt >> 16) & 0xFF);
				result[2] = (byte)((tempInt >> 8) & 0xFF);
				result[3] = (byte)((tempInt >> 0) & 0xFF);
				break;
			case Boolean:
				if (!(Boolean.class.isInstance(value))) throw new Exception("Expecting Boolean, got " + value.getClass());
				if ((boolean) value) {
					result[0] = 1;
				}
				else result[0] = 0;
				break;
			case TPL:
				// TODO: handle TPL Images
				break;
		}

		// put this array of bytes into the saveFile at location specified in data
		setBytesAt(data.start, result);

	}

	public void setByteAt(int x, byte b) {
		if (saveFile[x] != b) dirty = true;
		saveFile[x] = b;
	}

	public void setBytesAt(int x, byte[] b) {
		for (int i = 0; i < b.length; i++) {
			this.setByteAt(x+i, b[i]);
		}
	}

	private void readFromFile() throws Exception {
		FileInputStream fin = new FileInputStream(fileLocation);

		fin.read(saveFile);
		if (saveFile[0] != 'U' || saveFile[1] != 'S' || saveFile[2] != 'R' || saveFile[3] != 'D') { // Check if the file has the correct header, throw an exception if invalid
			fin.close();
			throw new FileNotFoundException("Invalid File");
		}
		fin.close();
		dirty = false;
	}

	public String saveToFile() {
		CRC16.fixChecksums(this); // fix checksums on saving
		try {
			FileOutputStream o = new FileOutputStream(fileLocation);
			o.write(saveFile);
			o.close();
			dirty = false;
		}
		catch (Exception e) {
			System.out.println("Error writing to file: " + e);
			return "Error writing to file: " + e;
		}
		return "";
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

}
