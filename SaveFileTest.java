import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class SaveFileTest {
	
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
		assertArrayEquals(new byte[] {(byte) 0x0, (byte) 0x3E}, saveFile.getRawData(SaveFile.THUMData.get("level")));
		assertArrayEquals(new byte[] {(byte) 0x0}, saveFile.getRawData(SaveFile.THUMData.get("systemSaveFlag")));
		assertArrayEquals(new byte[] {(byte) 0xC1, (byte) 0x3E, (byte) 0xCA, (byte) 0x8A}, saveFile.getRawData(SaveFile.PCPMData.get("p1z")));
	}
	
	@Test
	void getData() {
		initSaveFile();
		assertEquals("Fiora", saveFile.getData(SaveFile.THUMData.get("name"))); // getString
		assertEquals(323, saveFile.getData(SaveFile.FLAGData.get("scenarioNum"))); // getInt	
		assertEquals(-5.74383020401001f, saveFile.getData(SaveFile.PCPMData.get("p3x"))); // getFloat
		assertEquals(true, saveFile.getData(SaveFile.OPTDData.get("showSubtitles"))); // getBoolean
		// TODO: getArray
	}
	
	@Test
	void setString() {
		initSaveFile();
		try {
			String set = "abcdefghijklmnopqrstuvwxyz";
			saveFile.setData(SaveFile.THUMData.get("name"), set);
			assertEquals(set, saveFile.getData(SaveFile.THUMData.get("name")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test 
	void setInt() {
		initSaveFile();	
		try {
			int set = 42069;
			saveFile.setData(SaveFile.GAMEData.get("shulkLevel"), set);
			assertEquals(set, saveFile.getData(SaveFile.GAMEData.get("shulkLevel")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void setFloat() {
		initSaveFile();
		try {
			float set = 1.1f;			
			saveFile.setData(SaveFile.PCPMData.get("p3x"), set);
			assertEquals(set, saveFile.getData(SaveFile.PCPMData.get("p3x")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void setBoolean() {
		initSaveFile();
		try {
			boolean set = true;
			saveFile.setData(SaveFile.OPTDData.get("minimap"), set);
			assertEquals(set, saveFile.getData(SaveFile.OPTDData.get("minimap")));
			saveFile.setData(SaveFile.OPTDData.get("minimap"), !set);
			assertEquals(!set, saveFile.getData(SaveFile.OPTDData.get("minimap")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void initSaveFile() {
		try {
			saveFile = new SaveFile("C:\\Users\\Ender\\git\\xc1-save-editor\\testSave");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
