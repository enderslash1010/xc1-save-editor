import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveFile {
	
	private String fileLocation;
	private byte[] saveFile = new byte[0x28000];
	private boolean dirty;
	
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
	
	public SaveFile() {
		this.fileLocation = null;
	}
	
	public SaveFile(String fileLocation) {
		this.fileLocation = fileLocation;
		dirty = false;
	}
	
	public byte getByteAt(int x) {
		return saveFile[x];
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
		fin.close();
	}
	
	public void writeToFile() throws Exception {
		FileOutputStream o = new FileOutputStream(fileLocation);
		o.write(saveFile);
		o.close();
		dirty = false;
	}

	public void setFileLocation(String f) { 
		this.fileLocation = f;
		try {
			readFromFile(); // read file and save into byte array
			dirty = false;
		}
		catch (Exception e) {
			System.out.println("Error: " + e); // if read file method throws error
		}
	}
}
