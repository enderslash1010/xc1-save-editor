package Model;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SaveFileTest {
	// TODO: some methods were removed/changed, update tests

	SaveFile saveFile = null;

	/*
	@Test
	void getByteAt() {
		initSaveFile();
		assertEquals((byte) 0x68, saveFile.getByteAt(0x6527));
		assertEquals((byte) 0xFF, saveFile.getByteAt(0xFA72));
		assertEquals((byte) 0x00, saveFile.getByteAt(0x1E72B));
	}

	@Test
	void getBytesAt() {
		initSaveFile();
		assertArrayEquals(new byte[] {(byte) 0x42, (byte) 0x28, (byte) 0x39, (byte) 0xE8}, saveFile.getBytesAt(0x134, 0x138));
		assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x1, (byte) 0x3}, saveFile.getBytesAt(0x16770, 0x1677A));

	}

	@Test
	void getRawData() {
		initSaveFile();
		assertArrayEquals(new byte[] {(byte) 0x0, (byte) 0x3E}, saveFile.getRawData(SaveFile.DataMap.get("level")));
		assertArrayEquals(new byte[] {(byte) 0x0}, saveFile.getRawData(SaveFile.DataMap.get("systemSaveFlag")));
		assertArrayEquals(new byte[] {(byte) 0xC1, (byte) 0x3E, (byte) 0xCA, (byte) 0x8A}, saveFile.getRawData(SaveFile.DataMap.get("p1z")));
	}

	@Test
	void getData() {
		initSaveFile();
		assertEquals("Fiora", saveFile.getData(SaveFile.DataMap.get("name"))); // getString
		assertEquals(323, saveFile.getData(SaveFile.DataMap.get("scenarioNum"))); // getInt	
		assertEquals(8, saveFile.getData(SaveFile.DataMap.get("picSlot1")));// getInt with 1 byte int
		assertEquals(-5.74383020401001f, saveFile.getData(SaveFile.DataMap.get("p3x"))); // getFloat
		assertEquals(true, saveFile.getData(SaveFile.DataMap.get("showSubtitles"))); // getBoolean
	}
	*/

	// Tests for Array class methods
	@Test
	void Array() {
		initSaveFile();
		Array arr = (Array) SaveFile.DataMap.get("mineArray");
		// get()
		Data[] get = arr.get(1);
		Data[] answer1 = new Data[arr.getEntryOutlineLength()];
		answer1[0] = new Data(0x240F6, 0x240F8, DataType.Int);
		answer1[1] = new Data(0x240F8, 0x240F9, DataType.Int);
		answer1[2] = new Data(0x240F9, 0x240FA, DataType.Int);
		answer1[3] = new Data(0x240FA, 0x240FC, DataType.Int);	

		// getPairArray()
		DataPair[] getPairArray = arr.getPairArray(2);
		DataPair[] answer3 = new DataPair[arr.getEntryOutlineLength()];
		answer3[0] = new DataPair("mineCooldown", new Data(0x240FC, 0x240FE, DataType.Int));
		answer3[1] = new DataPair("numHarvests", new Data(0x240FE, 0x240FF, DataType.Int));
		answer3[2] = new DataPair("minelistID", new Data(0x240FF, 0x24100, DataType.Int));
		answer3[3] = new DataPair("mapID", new Data(0x24100, 0x24102, DataType.Int));

		// getPairArrays()
		DataPair[][] getPairArrays = arr.getPairArrays();
		DataPair[][] answer4 = new DataPair[arr.getNumEntries()][arr.getEntryOutlineLength()];

		for (int i = 0, currPos = 0x240F0; i < answer4.length; i++, currPos +=6) {
			answer4[i][0] = new DataPair("mineCooldown", new Data(currPos, currPos+2, DataType.Int));
			answer4[i][1] = new DataPair("numHarvests", new Data(currPos+2, currPos+3, DataType.Int));
			answer4[i][2] = new DataPair("minelistID", new Data(currPos+3, currPos+4, DataType.Int));
			answer4[i][3] = new DataPair("mapID", new Data(currPos+4, currPos+6, DataType.Int));
		}

		assertArrayEquals(get, answer1);
		assertArrayEquals(getPairArray, answer3);
		assertArrayEquals(getPairArrays, answer4);
	}

	@Test
	void getArray() {
		initSaveFile();
		Array arr = (Array) SaveFile.DataMap.get("boxArray");
		ValuePair[][] getArray = saveFile.getArray(arr);

		ValuePair[][] answer = new ValuePair[arr.getNumEntries()][arr.getEntryOutlineLength()];
		for (int i = 0, currPos = 0x244A4; i < answer.length; i++, currPos+=28) {
			answer[i][0] = new ValuePair("blank", saveFile.getData(new Data(currPos, currPos+4, DataType.Int)));
			answer[i][1] = new ValuePair("xBox", saveFile.getData(new Data(currPos+4, currPos+8, DataType.Float)));
			answer[i][2] = new ValuePair("yBox", saveFile.getData(new Data(currPos+8, currPos+12, DataType.Float)));
			answer[i][3] = new ValuePair("zBox", saveFile.getData(new Data(currPos+12, currPos+16, DataType.Float)));
			answer[i][4] = new ValuePair("boxAngle", saveFile.getData(new Data(currPos+16, currPos+20, DataType.Float)));
			answer[i][5] = new ValuePair("boxRank", saveFile.getData(new Data(currPos+20, currPos+24, DataType.Int)));
			answer[i][6] = new ValuePair("boxDropTable", saveFile.getData(new Data(currPos+24, currPos+26, DataType.Int)));
			answer[i][7] = new ValuePair("boxMapID", saveFile.getData(new Data(currPos+26, currPos+28, DataType.Int)));
		}

		assertArrayEquals(getArray, answer);
	}

	/*
	@Test
	void setArray() {
		initSaveFile();
		Random r = new Random();
		Array arr = (Array) SaveFile.DataMap.get("mineArray");
		int index = 2;

		// setArrayEntryAt
		ValuePair[] vps = {new ValuePair("mineCooldown", 69),
				new ValuePair("numHarvests", 255),
				new ValuePair("minelistID", 2),
				new ValuePair("mapID", 4)};
		try {
			saveFile.setArrayEntryAt(arr, index, vps);
			assertArrayEquals(vps, saveFile.getArrayAt(arr, index));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}		

		// setArrayEntryAt with null values
		vps = new ValuePair[]{new ValuePair("mineCooldown", 12345),
				null,
				null,
				new ValuePair("mapID", 333)};
		ValuePair[] answer = {new ValuePair("mineCooldown", 12345),
				new ValuePair("numHarvests", 255),
				new ValuePair("minelistID", 2),
				new ValuePair("mapID", 333)};
		try {
			saveFile.setArrayEntryAt(arr, index, vps);
			assertArrayEquals(answer, saveFile.getArrayAt(arr, index));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		// setArray with random values
		arr = (Array) SaveFile.DataMap.get("boxArray");
		ValuePair[][] values = new ValuePair[arr.getRowLength()][arr.getColLength()];
		for (int i = 0; i < values.length; i++) {
			values[i][0] = new ValuePair("blank", r.nextInt());
			values[i][1] = new ValuePair("xBox", r.nextFloat());
			values[i][2] = new ValuePair("yBox", r.nextFloat());
			values[i][3] = new ValuePair("zBox", r.nextFloat());
			values[i][4] = new ValuePair("boxAngle", r.nextFloat());
			values[i][5] = new ValuePair("boxRank", r.nextInt());
			values[i][6] = new ValuePair("boxDropTable", r.nextInt(256));
			values[i][7] = new ValuePair("boxMapID", r.nextInt(256));
		}
		try {
			saveFile.setArray(arr, values);
			assertArrayEquals(values, saveFile.getArray(arr));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		// setArray with some null values
		ValuePair[][] answer2 = values;
		ValuePair[][] newValues = new ValuePair[arr.getRowLength()][arr.getColLength()];
		String[] names = {"blank", "xBox", "yBox", "zBox", "boxAngle", "boxRank", "boxDropTable", "boxMapID"};
		for (int i = 0; i < newValues.length; i++) {
			for (int j = 0; j < newValues[i].length; j++) {
				ValuePair e = null;
				if (r.nextBoolean()) { // change value
					switch (j) {
					case 0:
					case 5:
						e = new ValuePair(names[j], r.nextInt());
						break;
					case 1:
					case 2:
					case 3:
					case 4:
						e = new ValuePair(names[j], r.nextFloat());
						break;
					case 6:
					case 7:
						e = new ValuePair(names[j], r.nextInt(256));
						break;
					}
					answer2[i][j] = e;
					newValues[i][j] = e;
				}
			}
		}
		try {
			saveFile.setArray(arr, newValues);
			assertArrayEquals(answer2, saveFile.getArray(arr));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		// setArray with all null values
		try {
			saveFile.setArray(arr, new ValuePair[arr.getRowLength()][arr.getColLength()]);
			assertArrayEquals(answer2, saveFile.getArray(arr));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		// setArray with different length ValuePair[][] for error
		try {
			saveFile.setArray(arr, new ValuePair[arr.getRowLength()-1][arr.getColLength()]);
			assertArrayEquals(answer2, saveFile.getArray(arr));
			fail();
		}
		catch (Exception e) {
			if (!e.getMessage().equals("size of Array doesn't match size of ValuePair Array")) {
				fail();
			}
		}
		try {
			saveFile.setArray(arr, new ValuePair[arr.getRowLength()][arr.getColLength()-1]);
			assertArrayEquals(answer2, saveFile.getArray(arr));
			fail();
		}
		catch (Exception e) {
			if (!e.getMessage().equals("size of Array doesn't match size of ValuePair Array")) {
				fail();
			}
		}
	}
	*/

	/*
	// add tests to check if an exception is thrown for mismatched DataTypes for setData()
	@Test
	void setString() {
		initSaveFile();
		try {
			String set = "abcdefghijklmnopqrstuvwxyz";
			saveFile.setData(SaveFile.DataMap.get("name"), set);
			assertEquals(set, saveFile.getData(SaveFile.DataMap.get("name")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	*/

	/*
	@Test 
	void setInt() {
		initSaveFile();	
		try {
			int set = 42069;
			int set2 = 2;
			saveFile.setData(SaveFile.DataMap.get("shulkLevel"), set);
			saveFile.setData(SaveFile.DataMap.get("picSlot2"), set2);
			assertEquals(set, saveFile.getData(SaveFile.DataMap.get("shulkLevel")));
			assertEquals(set2, saveFile.getData(SaveFile.DataMap.get("picSlot2")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	void setFloat() {
		initSaveFile();
		try {
			float set = 1.1f;			
			saveFile.setData(SaveFile.DataMap.get("p3x"), set);
			assertEquals(set, saveFile.getData(SaveFile.DataMap.get("p3x")));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	void setBoolean() {
		initSaveFile();
		try {
			boolean set = true;
			saveFile.setData(SaveFile.DataMap.get("minimap"), set);
			assertEquals(set, saveFile.getData(SaveFile.DataMap.get("minimap")));
			saveFile.setData(SaveFile.DataMap.get("minimap"), !set);
			assertEquals(!set, saveFile.getData(SaveFile.DataMap.get("minimap")));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	*/

	void initSaveFile() {
		try {
			saveFile = new SaveFile("C:\\Users\\Ender\\git\\xc1-save-editor\\testSave");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Restores the actual save file, call at end of test where actual file is modified
	void restore() {
		// copy contents of testSaveCleanCopy to testSave
		FileInputStream in;
		try {
			in = new FileInputStream("testSaveCleanCopy");
			FileOutputStream out = new FileOutputStream("testSave");

			int i;
			while ((i = in.read()) != -1) {
				out.write(i);
			}

			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// helper function to convert integer hex representations (i.e. 0x12345678) to floats
	void hexToFloat(int hex) {
		// TODO: implement
	}

}

/* for easy copy/paste for debugging
		for (int i = 0; i < .length; i++) {
			for (int j = 0; j < [i].length; j++) {
				System.out.println([i][j]);
			}
		}
 */

