package Controller;

/**
 * Enum for array column names
 * Used to attach a common name to an <code>Element</code> in an <code>Array</code>
 * @author ender
 */
public enum ArrayField {
	// ITEM
	weaponID1, weaponID2, weaponInventorySlot, weaponGem1Value, weaponGem2Value, weaponGem3Value, weaponGem1Index, weaponGem2Index, weaponGem3Index, weaponNumGemSlots, // weapons
	
	// gemID1 -> gemID for name, gemID2 -> gemID for description
	gemID1, gemUnk1, gemInventorySlot, gemUnk2, gemValue, gemRank, gemUnk3, gemID2, // gems
	
	// MINE
	mineCooldown, numHarvests, minelistID, mapID,
	
	// TBOX
	blank, xBox, yBox, zBox, boxAngle, boxRank, boxDropTable, boxMapID,
	
}
