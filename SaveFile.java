import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

@SuppressWarnings("serial")
public class SaveFile {

	private String fileLocation;
	private byte[] saveFile = new byte[0x28000];
	private boolean dirty; // True if saveFile data structure is not same as save file on disk
	private boolean valid; // True if valid saveFile (correct format)

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
	
	final static HashMap<String, Data> THUMData = new HashMap<String, Data>() {{
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
	final static HashMap<String, Data> FLAGData = new HashMap<String, Data>() {{
		put("scenarioNum", new Data(0xA0B2, 0xA0B4, DataType.Int));
	}};
	final static HashMap<String, Data> GAMEData = new HashMap<String, Data>() {{
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
	final static HashMap<String, Data> TIMEData = new HashMap<String, Data>() {{
		put("playTime", new Data(0x11EB0, 0x11EB4, DataType.Int));
		put("numDays", new Data(0x11EB8, 0x11EBA, DataType.Int));
	}};
	final static HashMap<String, Data> PCPMData = new HashMap<String, Data>() {{
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
	final static HashMap<String, Data> CAMDData = new HashMap<String, Data>() {{
		put("cameraPosVertical", new Data(0x11F30, 0x11F34, DataType.Float));
		put("cameraPosHorizontal", new Data(0x11F34, 0x11F38, DataType.Float));
		put("cameraDistance", new Data(0x11F3C, 0x11F40, DataType.Float));
	}};
	final static HashMap<String, Data> ITEMData = new HashMap<String, Data>() {{
		put("money", new Data(0x2404A, 0x2404C, DataType.Int));
	}};
	final static HashMap<String, Data> WTHRData = new HashMap<String, Data>() {{

	}};
	final static HashMap<String, Data> SNDSData = new HashMap<String, Data>() {{

	}};
	final static HashMap<String, Data> MINEData = new HashMap<String, Data>() {{
		put("mineArray", new Data(0x240F0, 0x24474, DataType.Array));
	}};
	final static HashMap<String, Data> TBOXData = new HashMap<String, Data>() {{
		put("numBoxes", new Data(0x244A3, 0x244A4, DataType.Int));
		put("boxArray", new Data(0x244A8, 0x246D8, DataType.Array));
	}};
	final static HashMap<String, Data> OPTDData = new HashMap<String, Data>() {{
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

	public SaveFile() {
		this.fileLocation = null;
		valid = false;
		
		// TODO: read from file here
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

	public void setByteAt(int x, byte b) {
		saveFile[x] = b;
		dirty = true;
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
		valid = true;
		fin.close();
	}

	public String saveToFile() {
		if (!valid) return "Error writing to file: Invalid File";
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

	public String setFileLocation(String f) { 
		this.fileLocation = f;
		try {
			readFromFile();
			dirty = false;
		}
		catch (Exception e) {
			System.out.println("Error reading from file: " + e);
			return "Error reading from file: " + e.getMessage();
		}
		return "";
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

	public boolean isValid() {
		return this.valid;
	}

}
