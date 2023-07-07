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

import Controller.ArrayField;
import Controller.SaveField;

class SaveFileTest {

	Random rand = new Random();
	
	SaveFile saveFile = null;

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
		assertArrayEquals(new byte[] {(byte) 0x0, (byte) 0x3E}, saveFile.getRawData(SaveFile.DataMap.get(SaveField.level)));
		assertArrayEquals(new byte[] {(byte) 0x0}, saveFile.getRawData(SaveFile.DataMap.get(SaveField.systemSaveFlag)));
		assertArrayEquals(new byte[] {(byte) 0xC1, (byte) 0x3E, (byte) 0xCA, (byte) 0x8A}, saveFile.getRawData(SaveFile.DataMap.get(SaveField.p1z)));
	}
	
	@Test
	void getData() {
		initSaveFile();
		assertEquals("Fiora", saveFile.getData((Data) SaveFile.DataMap.get(SaveField.nameString))); // getString
		assertEquals(323, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.scenarioNum))); // getInt	
		assertEquals(8, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.picSlot1)));// getInt with 1 byte int
		assertEquals(-5.74383020401001f, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.p3x))); // getFloat
		assertEquals(true, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.showSubtitles))); // getBoolean
	}
	
	@Test
	void getArrayAt() {
		initSaveFile();
		assertEquals(3.07564878463745f, saveFile.getArrayAt(SaveField.boxArray, 2, ArrayField.boxAngle));
		assertEquals(513, saveFile.getArrayAt(SaveField.boxArray, 0, ArrayField.boxMapID));
		assertEquals(3, saveFile.getArrayAt(SaveField.mineArray, 2, ArrayField.numHarvests));
		
		// ArrayField is not in the array
		assertEquals(null, saveFile.getArrayAt(SaveField.mineArray, 0, ArrayField.blank));
		
		// SaveField is not an Array
		assertEquals(null, saveFile.getArrayAt(SaveField.level, 0, ArrayField.blank));
		
	}
	
	@Test
	void setString() {
		initSaveFile();
		try {
			String set = "abcdefghijklmnopqrstuvwxyz";
			saveFile.setData((Data) SaveFile.DataMap.get(SaveField.nameString), set);
			assertEquals(set, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.nameString)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@Test 
	void setInt() {
		initSaveFile();	
		try {
			int set = 42069;
			int set2 = 2;
			saveFile.setData((Data) SaveFile.DataMap.get(SaveField.shulkLevel), set);
			saveFile.setData((Data) SaveFile.DataMap.get(SaveField.picSlot2), set2);
			assertEquals(set, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.shulkLevel)));
			assertEquals(set2, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.picSlot2)));
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
			saveFile.setData((Data) SaveFile.DataMap.get(SaveField.p3x), set);
			assertEquals(set, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.p3x)));
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
			saveFile.setData((Data) SaveFile.DataMap.get(SaveField.minimap), set);
			assertEquals(set, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.minimap)));
			saveFile.setData((Data) SaveFile.DataMap.get(SaveField.minimap), !set);
			assertEquals(!set, saveFile.getData((Data) SaveFile.DataMap.get(SaveField.minimap)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void setArrayData() throws Exception {
		initSaveFile();
		
		float randFloat = rand.nextFloat();
		saveFile.setArrayData(SaveField.boxArray, 10, ArrayField.yBox, randFloat);
		assertEquals(randFloat, saveFile.getArrayAt(SaveField.boxArray, 10, ArrayField.yBox));
		
		int randInt = rand.nextInt();
		saveFile.setArrayData(SaveField.boxArray, 4, ArrayField.boxRank, randInt);
		assertEquals(randInt, saveFile.getArrayAt(SaveField.boxArray, 4, ArrayField.boxRank));
		
		randInt = rand.nextInt(256);
		saveFile.setArrayData(SaveField.mineArray, 22, ArrayField.minelistID, randInt);
		assertEquals(randInt, saveFile.getArrayAt(SaveField.mineArray, 22, ArrayField.minelistID));
	}
	
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

}
