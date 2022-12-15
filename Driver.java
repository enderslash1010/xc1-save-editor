

public class Driver {

	public static void main(String[] args) {
		GUI gui = new GUI();
		crc16 c = new crc16(gui);
		gui.addcrc16(c);
	}

}
