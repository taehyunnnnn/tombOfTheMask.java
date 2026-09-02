//Tei and Nihaal
//Tomb of the mask final project
//January 19, 2024
//This class file contains the variables that are used in the updateMovement method

package files;

//Hold the changes to movement so that it can be provided to the main method
public class movementResult {

	boolean Move;    // true while the character is mid-slide (not yet at the next tile)
	boolean SFX;     // true when a sound effect is allowed to fire this tick
	boolean Shield;  // true if the player currently has a purchased shield (extra life)
	int Life;        // lives to deduct this tick (0 = no damage, 1 = hit a spike)
	int MaskD;       // movement direction: 1=up, 2=left, 3=down, 4=right

}
