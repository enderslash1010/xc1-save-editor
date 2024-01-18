package Controller;

/**
 * Enum for save file fields
 * Used to attach a common name to a <code>Data</code> object, 
 * and to a <code>JComponent</code> that loads from this <code>Data</code> object
 * Save fields are organized by what save file partition they are in
 * @author ender
 */
public enum SaveField {
	// THUM
	level, 
	playTimeHours, playTimeMins, playTimeSeconds, 
	saveTimeDay, saveTimeMonth, saveTimeYear, saveTimeHour, saveTimeMinute,
	picSlot1, picSlot2, picSlot3, picSlot4, picSlot5, picSlot6, picSlot7,
	nameString,
	systemSaveFlag, ngPlusFlag,
	saveImage,
	
	// FLAG
	scenarioNum,
	
	// GAME
	mapNum, mapNum2,
	player1, player2, player3, player4, player5, player6, player7,
	shulkLevel, reynLevel, fioraLevel, dunbanLevel, sharlaLevel, rikiLevel, meliaLevel, sevenLevel, dicksonLevel, mumkharLevel, alvisLevel, prologueDunbanLevel,
	
	// TIME
	playTime, dayTime,
	numDays, numYears,
	
	// PCPM
	p1x, p1y, p1z, p1Angle,
	p2x, p2y, p2z, p2Angle,
	p3x, p3y, p3z, p3Angle,
	
	// CAMD
	cameraPosVertical, cameraPosHorizontal, cameraDistance,
	
	// ITEM
	money,
	weaponArray,
	gemArray,
	
	// WTHR
	weatherReroll, weatherMap, foregroundWeather, weatherUnknown1, backgroundWeather, weatherUnknown2,
	
	// SNDS
	
	
	// MINE
	mineArray,
	
	// TBOX
	numBoxes, boxArray,
	
	// OPTD
	nonInvertedXAxis, nonInvertedYAxis,
	xAxisSpeed, yAxisSpeed, zoomSpeed,
	pointOfView, angleCorrection, battleCamera,
	gamma, minimap, rotate,
	jpVoice, showSubtitles, fastDialogueText,
	showControls, showArtDescriptions, showBuffDebuffIndicator, showBuffDebuffInfoEveryTime, showDestinationMarker, showEnemyIcons,
	autoEventScrolling,
	
}
