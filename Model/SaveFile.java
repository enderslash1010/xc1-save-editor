package Model;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

import com.google.common.collect.HashBiMap;

import Controller.ArrayField;
import Controller.SaveField;

/**
 *  SaveFile
 *  
 *  The Model for Xenoblade's save file structure
 */
@SuppressWarnings("serial")
public class SaveFile {

	private String fileLocation;
	private byte[] saveFile = new byte[0x28000];

	private boolean dirty; // True if saveFile data structure is not same as save file on disk

	private final static int[][] sections = 
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

	private final static int[] checksumLocations = {0x1E, 0xA02E, 0xB25E, 0x11EAE, 0x11EDE, 0x11F2E, 0x11F5E, 0x2408E, 0x240BE, 0x240EE, 0x2449E, 0x248AE};
	private final static String[] sectionNames = {"THUM", "FLAG", "GAME", "TIME", "PCPM", "CAMD", "ITEM", "WTHR", "SNDS", "MINE", "TBOX", "OPTD"};

	/**
	 *  SaveFile.DataMap maps where information/fields is in the save file
	 */
	public final static HashBiMap<SaveField, Pointer> DataMap = HashBiMap.create(new HashMap<SaveField, Pointer>() {{
		// THUM
		put(SaveField.level, new Data(0x84, 0x86, DataType.Int));
		put(SaveField.playTimeHours, new Data(0x2A, 0x2C, DataType.Int));
		put(SaveField.playTimeMins, new Data(0x2C, 0x2E, DataType.Int));
		put(SaveField.playTimeSeconds, new Data(0x23, 0x24, DataType.Int));
		put(SaveField.saveTimeDay, new Data(0x29, 0x2A, DataType.Int));
		put(SaveField.saveTimeMonth, new Data(0x26, 0x28, DataType.Int));
		put(SaveField.saveTimeYear, new Data(0x24, 0x26, DataType.Int));
		put(SaveField.saveTimeHour, new Data(0x28, 0x29, DataType.Int));
		put(SaveField.saveTimeMinute, new Data(0x22, 0x23, DataType.Int));
		put(SaveField.picSlot1, new Data(0x37, 0x38, DataType.Int));
		put(SaveField.picSlot2, new Data(0x3B, 0x3C, DataType.Int));
		put(SaveField.picSlot3, new Data(0x3F, 0x40, DataType.Int));
		put(SaveField.picSlot4, new Data(0x43, 0x44, DataType.Int));
		put(SaveField.picSlot5, new Data(0x47, 0x48, DataType.Int));
		put(SaveField.picSlot6, new Data(0x4B, 0x4C, DataType.Int));
		put(SaveField.picSlot7, new Data(0x4F, 0x50, DataType.Int));
		put(SaveField.nameString, new Data(0x64, 0x84, DataType.String));
		put(SaveField.systemSaveFlag, new Data(0x86, 0x87, DataType.Boolean));
		put(SaveField.ngPlusFlag, new Data(0x87, 0x88, DataType.Boolean));
		put(SaveField.saveImage, new Data(0xE0, 0x9580, DataType.TPL));

		// FLAG
		put(SaveField.scenarioNum, new Data(0xA0B2, 0xA0B4, DataType.Int));

		// GAME
		put(SaveField.mapNum, new Data(0xB261, 0xB264, DataType.Int));
		put(SaveField.mapNum2, new Data(0xB264, 0xB268, DataType.String));
		put(SaveField.player1, new Data(0xD1FE, 0xD200, DataType.Int));
		put(SaveField.player2, new Data(0xD202, 0xD204, DataType.Int));
		put(SaveField.player3, new Data(0xD206, 0xD208, DataType.Int));
		put(SaveField.player4, new Data(0xD20A, 0xD20C, DataType.Int));
		put(SaveField.player5, new Data(0xD20E, 0xD210, DataType.Int));
		put(SaveField.player6, new Data(0xD212, 0xD214, DataType.Int));
		put(SaveField.player7, new Data(0xD216, 0xD218, DataType.Int));
		put(SaveField.shulkLevel, new Data(0xF8D0, 0xF8D4, DataType.Int));
		put(SaveField.reynLevel, new Data(0xFBD4, 0xFBD8, DataType.Int));
		put(SaveField.fioraLevel, new Data(0xFEC8, 0xFECC, DataType.Int));
		put(SaveField.dunbanLevel, new Data(0x101DC, 0x101E0, DataType.Int));
		put(SaveField.sharlaLevel, new Data(0x104E0, 0x104E4, DataType.Int));
		put(SaveField.rikiLevel, new Data(0x107E4, 0x107E8, DataType.Int));
		put(SaveField.meliaLevel, new Data(0x10AE8, 0x10AEC, DataType.Int));
		put(SaveField.sevenLevel, new Data(0x10DEC, 0x10DF0, DataType.Int));
		put(SaveField.dicksonLevel, new Data(0x110F0, 0x110F4, DataType.Int));
		put(SaveField.mumkharLevel, new Data(0x113F4, 0x113F8, DataType.Int));
		put(SaveField.alvisLevel, new Data(0x116F8, 0x116FC, DataType.Int));
		put(SaveField.prologueDunbanLevel, new Data(0x119FC, 0x11A00, DataType.Int));

		// TIME
		put(SaveField.playTime, new Data(0x11EB0, 0x11EB4, DataType.Int));
		put(SaveField.numDays, new Data(0x11EB8, 0x11EBA, DataType.Int));
		put(SaveField.dayTime, new Data(0x11EB4, 0x11EB8, DataType.Float));
		put(SaveField.numYears, new Data(0x11EBA, 0x11EBC, DataType.Int));

		// PCPM
		put(SaveField.p1x, new Data(0x11EE0, 0x11EE4, DataType.Float));
		put(SaveField.p1y, new Data(0x11EE4, 0x11EE8, DataType.Float));
		put(SaveField.p1z, new Data(0x11EE8, 0x11EEC, DataType.Float));
		put(SaveField.p1Angle, new Data(0x11EEC, 0x11EF0, DataType.Float));
		put(SaveField.p2x, new Data(0x11EF0, 0x11EF4, DataType.Float));
		put(SaveField.p2y, new Data(0x11EF4, 0x11EF8, DataType.Float));
		put(SaveField.p2z, new Data(0x11EF8, 0x11EFC, DataType.Float));
		put(SaveField.p2Angle, new Data(0x11EFC, 0x11F00, DataType.Float));
		put(SaveField.p3x, new Data(0x11F00, 0x11F04, DataType.Float));
		put(SaveField.p3y, new Data(0x11F04, 0x11F08, DataType.Float));
		put(SaveField.p3z, new Data(0x11F08, 0x11F0C, DataType.Float));
		put(SaveField.p3Angle, new Data(0x11F0C, 0x11F10, DataType.Float));

		// CAMD
		put(SaveField.cameraPosVertical, new Data(0x11F30, 0x11F34, DataType.Float));
		put(SaveField.cameraPosHorizontal, new Data(0x11F34, 0x11F38, DataType.Float));
		put(SaveField.cameraDistance, new Data(0x11F3C, 0x11F40, DataType.Float));

		// ITEM
		put(SaveField.money, new Data(0x24048, 0x2404C, DataType.Int));
		put(SaveField.weaponArray, new Array(0x1C4EC, 0x1E364, new Element[] {
				new Element(ArrayField.weaponID1, 12, DataType.Int),
				new StaticElement(4, 2),
				new Element(ArrayField.weaponID2, 11, DataType.Int),
				new StaticElement(5, 0),
				new StaticElement(8, 0),
				new Element(ArrayField.weaponInventorySlot, 8, DataType.Int),
				new StaticElement(8, 1),
				new StaticElement(8, 0),
				new Element(ArrayField.weaponGem1Value, 64, DataType.Int),
				new Element(ArrayField.weaponGem2Value, 64, DataType.Int),
				new Element(ArrayField.weaponGem3Value, 64, DataType.Int),
				new StaticElement(64, 0),
				new Element(ArrayField.weaponGem1Index, 16, DataType.Int),
				new Element(ArrayField.weaponGem2Index, 16, DataType.Int),
				new Element(ArrayField.weaponGem3Index, 16, DataType.Int),
				new StaticElement(16, 0xFFFF),
				new Element(ArrayField.weaponNumGemSlots, 8, DataType.Int),
				new StaticElement(8, 6),
				new StaticElement(16, 0),
		}));
		put(SaveField.gemArray, new Array(0x206D8, 0x21998, new Element[] {
				new StaticElement(16, 0xEA33), // Item ID from ITM_itemlist that doesn't affect gem attributes (just needs to be a gem type item)
				new Element(ArrayField.gemID1, 11, DataType.Int),
				new Element(ArrayField.gemUnk1, 5, DataType.Int),
				new StaticElement(8, 0),
				new Element(ArrayField.gemInventorySlot, 8, DataType.Int),
				new StaticElement(8, 1), // Amount in stack for gems is always 1 (nothing changes for larger stacks)
				new StaticElement(8, 0),
				new Element(ArrayField.gemUnk2, 11, DataType.Int),
				new Element(ArrayField.gemValue, 11, DataType.Int),
				new Element(ArrayField.gemRank, 3, DataType.Int),
				new Element(ArrayField.gemUnk3, 7, DataType.Int),
				new Element(ArrayField.gemID2, 12, DataType.Int),
				new StaticElement(4, 2),
				new StaticElement(16, 0)
		}));

		// WTHR
		put(SaveField.weatherReroll, new Data(0x24090, 0x24094, DataType.Float));
		put(SaveField.weatherMap, new Data(0x24099, 0x2409C, DataType.Int));
		put(SaveField.foregroundWeather, new Data(0x24094, 0x24096, DataType.Int));
		put(SaveField.weatherUnknown1, new Data(0x24096, 0x24098, DataType.Int));
		put(SaveField.backgroundWeather, new Data(0x2409D, 0x2409E, DataType.Int));
		put(SaveField.weatherUnknown2, new Data(0x2409E, 0x240A0, DataType.Int));
		
		// SNDS

		// MINE
		put(SaveField.mineArray, new Array(0x240F0, 0x24474, new Element[] {
				new Element(ArrayField.mineCooldown, 16, DataType.Int),
				new Element(ArrayField.numHarvests, 8, DataType.Int),
				new Element(ArrayField.minelistID, 8, DataType.Int),
				new Element(ArrayField.mapID, 16, DataType.Int)
		}));

		// TBOX
		put(SaveField.numBoxes, new Data(0x244A3, 0x244A4, DataType.Int));
		put(SaveField.boxArray, new Array(0x244A4, 0x246F4, new Element[] {
				new StaticElement(32, 0),
				new Element(ArrayField.xBox, 32, DataType.Float),
				new Element(ArrayField.yBox, 32, DataType.Float),
				new Element(ArrayField.zBox, 32, DataType.Float),
				new Element(ArrayField.boxAngle, 32, DataType.Float),
				new Element(ArrayField.boxRank, 32, DataType.Int),
				new Element(ArrayField.boxDropTable, 16, DataType.Int),
				new Element(ArrayField.boxMapID, 16, DataType.Int)
		}));

		// OPTD
		put(SaveField.nonInvertedYAxis, new Data(0x248B0, 0x248B1, DataType.Boolean));
		put(SaveField.nonInvertedXAxis, new Data(0x248B1, 0x248B2, DataType.Boolean));
		put(SaveField.yAxisSpeed, new Data(0x248B2, 0x248B3, DataType.Int));
		put(SaveField.xAxisSpeed, new Data(0x248B3, 0x248B4, DataType.Int));
		put(SaveField.zoomSpeed, new Data(0x248B4, 0x248B5, DataType.Int));
		put(SaveField.pointOfView, new Data(0x248B5, 0x248B6, DataType.Int));
		put(SaveField.angleCorrection, new Data(0x248B6, 0x248B7, DataType.Boolean));
		put(SaveField.battleCamera, new Data(0x248B7, 0x248B8, DataType.Boolean));
		put(SaveField.gamma, new Data(0x248BF, 0x248C0, DataType.Int));
		put(SaveField.minimap, new Data(0x248C0, 0x248C1, DataType.Boolean));
		put(SaveField.rotate, new Data(0x248C1, 0x248C2, DataType.Boolean));
		put(SaveField.jpVoice, new Data(0x248CC, 0x248CD, DataType.Boolean));
		put(SaveField.showControls, new Data(0x248D0, 0x248D1, DataType.Boolean));
		put(SaveField.showArtDescriptions, new Data(0x248D1, 0x248D2, DataType.Boolean));
		put(SaveField.showBuffDebuffInfoEveryTime, new Data(0x248D2, 0x248D3, DataType.Boolean));
		put(SaveField.showEnemyIcons, new Data(0x248D3, 0x248D4, DataType.Boolean));
		put(SaveField.showBuffDebuffIndicator, new Data(0x248D4, 0x248D5, DataType.Boolean));
		put(SaveField.showDestinationMarker, new Data(0x248D5, 0x248D6, DataType.Boolean));
		put(SaveField.autoEventScrolling, new Data(0x248E0, 0x248E1, DataType.Boolean));
		put(SaveField.fastDialogueText, new Data(0x248E1, 0x248E2, DataType.Boolean));
		put(SaveField.showSubtitles, new Data(0x248E2, 0x248E3, DataType.Boolean));
	}});

	public SaveFile(String fileLocation) throws Exception {
		this.fileLocation = fileLocation;
		readFromFile();
		saveToFile();
	}

	/**
	 * 	Gets a byte of the save file
	 *  @param x - location in saveFile
	 *  @return - byte of the saveFile at x
	 */
	byte getByteAt(int x) {
		return saveFile[x];
	}

	/**
	 * 	Gets bytes from start (inclusive) to end (exclusive) of the save file
	 *  @param start - starting location in saveFile
	 *  @param end - ending location in saveFile
	 *  @return - bytes from start to end in an array
	 */
	byte[] getBytesAt(int start, int end) {
		byte[] result = new byte[end-start];
		for (int i = start; i < end; i++) {
			result[i-start] = getByteAt(i);
		}
		return result;
	}

	/**
	 *  Gets the byte data of a <code>Pointer</code> object
	 *  @param p - the <code>Pointer<code> object
	 *  @return - byte array representing the raw data from p
	 */
	byte[] getRawData(Pointer p) {
		int[] location = p.getLocation();
		byte[] rawData = getBytesAt(location[0], location[1]);
		
		// Truncate to startBit if needed
		for (int i = p.startBit + 1; i < 8; i++) {
			rawData[0] &= ~(1 << i); // Clear bit at i
		}
		
		// Shift right by endBit
		for (int i = 0; i < p.endBit; i++) {
			// Iterate through rawData backwards
			for (int b = rawData.length - 1; b >= 0; b--) {
				rawData[b] >>= 1; // shift right by 1
			    rawData[b] &= ~(0b10000000); // right shifting a 'negative' byte keeps the MSB 1, clear this bit
				if (b != 0) rawData[b] |= (rawData[b-1] & 0b1) << 7; // copy LSB from previous bytes into this byte's MSB (if b != 0)
			}
		}
		
		return rawData;
	}
	
	/**
	 * 	Gets the raw data associated with a <code>Pointer</code> object and transforms it into the correct data type
	 *  @param p - the <code>Pointer<code> object
	 *  @return - data in the data type described in the <code>Pointer</code> object
	 */
	public Object getData(Data p) {
		// get raw data, then transform into the DataType specified in the data object
		byte[] rawData = getRawData(p);

		if (p instanceof Data) {
			switch (((Data) p).getType()) {
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
		}
		return null;
	}
	
	/**
	 *  Helper function for getData to transform raw data into a String
	 *  @param rawData - raw byte data to be transformed
	 *  @return - the transformed String data
	 */
	private String getString(byte[] rawData) {
		String result = "";
		for (int i = 0; i < rawData.length; i++) {
			if (rawData[i] == 0x0) return result; // null char is end of string
			result += ((char) rawData[i]);
		}
		return result;
	}

	/**
	 *  Helper function for getData to transform raw data into an integer
	 *  @param rawData - raw byte data to be transformed
	 *  @return - the transformed integer data
	 */
	private int getInt(byte[] rawData) {
		int result = 0;
		for (byte b: rawData) {
			result = (result << 8) + (b & 0xFF);
		}
		return result;
	}

	/**
	 *  Helper function for getData to transform raw data into a float
	 *  @param rawData - raw byte data to be transformed
	 *  @return - the transformed float data
	 */
	private float getFloat(byte[] rawData) {
		return ByteBuffer.wrap(rawData).getFloat();
	}
	
	/**
	 * 	Gets transformed data from an Array at the specified index and column name
	 *  @param arr - Array to get the data from
	 *  @param index - the index (or row) to get the data from
	 *  @param internalColName - the column name to get the data from
	 *  @return - the transformed data
	 */
	public Object getArrayAt(Array arr, int index, ArrayField internalColName) {
		Data data = arr.get(index, internalColName);
		if (data == null) return null;
		return getData(data);
	}
	public Object getArrayAt(SaveField arr, int index, ArrayField internalColName) {
		if (SaveFile.DataMap.get(arr) instanceof Data) return null;
		return getArrayAt((Array) SaveFile.DataMap.get(arr), index, internalColName);
	}
	
	/**
	 * Checks if the given index of an Array is all 0's, to determine whether to set/clear StaticElement's in the save file
	 * @param arr Array object to check
	 * @param index the index to check
	 * @return true if all Elements of arr are 0, otherwise false
	 */
	public boolean isArrayIndexNull(Array arr, int index) {
		for (ArrayField internalColName : arr.getColNames()) {
			if (!this.getData(arr.get(index, internalColName)).equals(0)) return false;
		}
		return true;
	}
	public boolean isArrayIndexNull(SaveField arr, int index) {
		return isArrayIndexNull((Array) SaveFile.DataMap.get(arr), index);
	}
	
	/**
	 * Checks if the given index of an Array is all 0's, ignoring specified columns
	 * Used to determine whether to clear ArrayField's not exposed to the View
	 * @param arr Array object to check
	 * @param index the index to check
	 * @param exclude array of columns (ArrayFields) to ignore/exclude
	 * @return true if all Elements, excluding the specificed columns, of arr are 0, otherwise false
	 */
	public boolean isArrayIndexNullPartial(Array arr, int index, ArrayField[] exclude) {
		Set<ArrayField> excludeSet = Set.of(exclude);
		
		for (ArrayField internalColName : arr.getColNames()) {
			if (excludeSet.contains(internalColName)) continue;
			if (!this.getData(arr.get(index, internalColName)).equals(0)) return false;
		}
		return true;
	}
	public boolean isArrayIndexNullPartial(SaveField arr, int index, ArrayField[] exclude) {
		return isArrayIndexNullPartial((Array) SaveFile.DataMap.get(arr), index, exclude);
	}

	/**
	 * 	Sets a <code>Data</code> object to the specified value, throwing an exception if the specified value is not the correct type
	 *  @param data - Data object to be set
	 *  @param value - value to set Data object's value to
	 *  @throws IllegalArgumentException if value is not the expected type dictated by data.getType
	 *  
	 *  Note: Arrays go through setArrayData
	 */
	public void setData(Data data, Object value) throws IllegalArgumentException, NullPointerException {
		if (value == null) throw new NullPointerException("value cannot be null");
		
		// check if type of Object value matches data.getType, throw exception if not
		// transform Object value into a byte array
		byte[] result = new byte[data.size()];

		switch (((Data)data).getType()) {
		case String:
			if (!(value instanceof String)) throw new IllegalArgumentException("Expecting String, got " + value.getClass());
			for (int i = 0; i < ((String) value).length() && i < result.length; i++) {
				result[i] = (byte) ((String) value).charAt(i);
			}
			break;
		case Int:
			if (!(Integer.class.isInstance(value))) throw new IllegalArgumentException("Expecting Integer, got " + value.getClass());
			for (int i = 0, shift = result.length*8 - 8; i < result.length && shift >= 0; i++, shift-=8) {
				result[i] = (byte)(((int) value >> shift) & 0xFF);
			}	
			break;
		case Float:
			if (!(Float.class.isInstance(value))) throw new IllegalArgumentException("Expecting Float, got " + value.getClass());
			int bits = Float.floatToIntBits((float) value);
			result[0] = (byte)(bits >> 24);
			result[1] = (byte)(bits >> 16);
			result[2] = (byte)(bits >> 8);
			result[3] = (byte)(bits);
			break;
		case Boolean:
			if (!(Boolean.class.isInstance(value))) throw new IllegalArgumentException("Expecting Boolean, got " + value.getClass());
			if ((boolean) value) {
				result[0] = 1;
			}
			else result[0] = 0;
			break;
		case TPL:
			// TODO: handle TPL Images
			break;
		}

		// Define masks for use with bitfields
		byte startMask = 0;
		byte endMask = 0;
		
		for (int i = data.startBit + 1; i < 8; i++) startMask |= (1 << i); // calculate startMask
		for (int i = data.endBit - 1; i >= 0; i--) endMask |= (1 << i); // calculate endMask
		
		// shift result left by endBit
		for (int i = data.endBit; i > 0; i--) {
			// Iterate through result
			for (int b = 0; b < result.length; b++) {
				result[b] <<= 1; // shift left by 1
				if (b != result.length-1) result[b] |= ((result[b+1] & 0x80) >> 7); // copy MSB from next byte to LSB of this byte, if b != result.length-1
			}
		}
		
		// put this array of bytes into the saveFile at location specified in data	
		setBytesAt(data.start, result, startMask, endMask);
	}
	
	/**
	 * 	Sets a <code>Data</code> object to the specified value, throwing an exception if the specified value is not the correct type
	 *  @param fieldName - field name of the Data object to be set
	 *  @param value - value to set Data object's value to
	 *  @throws IllegalArgumentException
	 *  
	 *  Note: Arrays go through setArrayData
	 */
	public void setData(SaveField fieldName, Object value) throws IllegalArgumentException, NullPointerException {
		Pointer pdata = SaveFile.DataMap.get(fieldName);
		if (pdata instanceof Data) setData((Data) pdata, value);
		else throw new IllegalArgumentException("Cannot use setData() directly with Array, use setArrayData()");
	}
	
	/**
	 * 	Sets a single value in an Array
	 *  @param arr - Array to be changed
	 *  @param index - index of the Array to use to set new array value
	 *  @param internalColName - internal column name to use to set new array value
	 *  @param value - value to set Array to (arr[index][colName] = value)
	 *  @throws Exception
	 */
	public void setArrayData(Array arr, int index, ArrayField internalColName, Object value) throws IllegalArgumentException, NullPointerException {
		// get Data object corresponding to parameters
		Data data = arr.get(index, internalColName);
		
		// pass Data object to setData with value
		setData(data, value);
	}
	
	/**
	 * Sets a single value in an Array
	 * @param fieldName - field name of the Array object
	 * @param index - index of the Array to use to set new array value
	 * @param internalColName - internal column name to use to set new array value
	 * @param value - value to set Array to (arr[index][colName] = value)
	 * @throws Exception
	 */
	public void setArrayData(SaveField fieldName, int index, ArrayField internalColName, Object value) throws IllegalArgumentException, NullPointerException {
		Pointer pdata = SaveFile.DataMap.get(fieldName);
		if (pdata instanceof Array) setArrayData((Array) pdata, index, internalColName, value);
		else throw new IllegalArgumentException("Cannot use setArrayData() for non-array data, use setData()");
	}

	/**
	 * 	Sets a byte of the save file
	 *  @param x location of byte
	 *  @param b new byte to set
	 *  @param mask bitmask of what bits to modify (0) and what bits to conserve their value (1); the bitmask AND'ed to the current value
	 */
	private void setByteAt(int x, byte b, byte mask) {
		byte newValue = (byte) ((saveFile[x] & mask) | (b & ~mask));
		if (saveFile[x] != newValue) {
			dirty = true;
			saveFile[x] = newValue;
		}
	}

	/**
	 * 	Sets multiple bytes of the save file
	 *  @param x - starting location
	 *  @param b - array of bytes to set
	 */
	private void setBytesAt(int x, byte[] b) {
		for (int i = 0; i < b.length; i++) {
			this.setByteAt(x+i, b[i], (byte) 0);
		}
	}
	
	private void setBytesAt(int x, byte[] b, byte startMask, byte endMask) {
		if (b.length == 0) return;
		if (b.length == 1) startMask |= endMask; // If there is only 1 byte, then combine (OR) the start and end mask
		
		this.setByteAt(x, b[0], startMask); // set first byte
		for (int i = 1; i < b.length - 1; i++) { // set middle bytes
			this.setByteAt(x+i, b[i], (byte) 0);
		}
		if (b.length > 1) this.setByteAt(x+b.length-1, b[b.length-1], endMask); // set last byte, if there is more than 1 byte
	}
	
	/**
	 * Copies bytes from origin to destination Data objects
	 * The number of bytes copied is determined by destination size 
	 * @param origin Data object to copy from, may use bytes after this object if the destination Data obj is larger in size
	 * @param dest Data object to copy to, ensured that all bytes in this object are modified
	 */
	public void copyBytes(Data origin, Data dest) {
		byte[] bytesToCopy = getBytesAt(origin.start, origin.start + dest.size());
		setBytesAt(dest.start, bytesToCopy);
	}
	
	/**
	 * 	Reads from the actual file, and saves into saveFile
	 *  Fixes any checksum errors encountered upon load
	 *  @throws Exception - when the opened file doesn't have the correct magic numbers (USRD)
	 */
	private void readFromFile() throws Exception {
		FileInputStream fin = new FileInputStream(fileLocation);

		fin.read(saveFile);
		if (saveFile[0] != 'U' || saveFile[1] != 'S' || saveFile[2] != 'R' || saveFile[3] != 'D') { // Check if the file has the correct header, throw an exception if invalid
			fin.close();
			throw new FileNotFoundException("Invalid File");
		}
		fin.close();
		dirty = false;
		
		// TODO: if checksums are wrong, prompt user to fix them rather than automatically fixing them (i.e. user input before writing to file)
		String result = CRC16.fixChecksums(this);
		if (result.equals("Fixed Checksums")) {
			dirty = true;
		}
	}

	/**
	 *  Saves to actual file
	 *  @return - String message to describe what happened
	 */
	public String saveToFile() {
		if (!this.dirty) return "No changes made, nothing saved"; // if file was not changed, no need to save
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
		return "Changes Saved Sucessfully";
	}
	
	/**
	 *  CRC16
	 *	
	 *	Nested class for SavFile to compute/correct checksums for each save file partition
	 */
	class CRC16 {

		/*	
		 *   Computes the checksum for each section, compares to the stored checksum, and changes the stored checksum if it's not correct
		 */
		public static String fixChecksums(SaveFile saveFile) {
			
			String result = "";

			/// crc16 polynomial: 1 + x^2 + x^15 + x^16 -> 0x8005 (1000 0000 0000 0101) 
			int[] table = {
					0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
					0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
					0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
					0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
					0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
					0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
					0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
					0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
					0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
					0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
					0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
					0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
					0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
					0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
					0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
					0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
					0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
					0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
					0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
					0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
					0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
					0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
					0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
					0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
					0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
					0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
					0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
					0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
					0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
					0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
					0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
					0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
			};

			for (int k = 0; k < SaveFile.sectionNames.length; k++) {

				int crc = 0x0000;
				for (int b = SaveFile.sections[k][0]; b < SaveFile.sections[k][1]; b++) {
					crc = (crc >>> 8) ^ table[(crc ^ saveFile.getByteAt(b)) & 0xff];
				}

//				 String outputText = SaveFile.sectionNames[k] + " Computed CRC16 = " + String.format("%02X", crc) + " | Checksum in File = " + String.format("%02X%02X", saveFile.getByteAt(SaveFile.checksums[k]), saveFile.getByteAt(SaveFile.checksums[k] + 1));
//				 System.out.println(outputText);

				if (saveFile.getByteAt(SaveFile.checksumLocations[k]) != (byte) (crc >> 8) || saveFile.getByteAt(SaveFile.checksumLocations[k] + 1) != (byte) (crc)) { // if checksums are different, change saveFile
					// System.out.println("Bad checksum in " + SaveFile.sectionNames[k] + "\n");
					saveFile.setBytesAt(SaveFile.checksumLocations[k], new byte[] {(byte) (crc >> 8), (byte) (crc)});
					result = "Fixed Checksums";
				}
			}
			
			return result.equals("") ? "Already Correct Checksums" : result;
			
		}
	}

}
