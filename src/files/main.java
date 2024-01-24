//Tei and Nihaal
//Tomb of the mask final project
//January 19, 2024
/* This class file includes the main method for the game and has majority of the code. This method draws everything for the game,
 * has all of the user input, carries out said input, animation, movement of the screen, changes maps once completed, plays sound effects, 
 * and resets the map after completion. this file includes all of the game play.*/

package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {

	public static void main(String[] args) throws InterruptedException {

		new main();

	}

	//set the x andd y values of the Graphics Console
	static int mapX = 36 * 12;
	static int mapY = 48 * 12;
	static GraphicsConsole gc = new GraphicsConsole(mapX, mapY, "Tomb of the Mask");

	main() throws InterruptedException {

		//initialize Final integer values
		final int LvlW = 64;
		final int LvlH = 64;
		final int Pixel = 36;
		final int PixelStart = 12;
		final int Margin = 5 * Pixel;
		final int Xblocks = 26;
		final int Yblocks = 46;
		final int Delay = 15;
`		
		//Initialize integer values
		int Ticks = 0;
		int MaskTicks = 1;
		int AnimationTicks = 1;
		int OffsetX = 0;
		int OffsetY = 0;
		int Life = 1;
		int Maskx = 0;
		int Masky = 0;
		int Balance = 0;
		int MouseClick = 0;

		//Initialize Boolean values
		Boolean Start = false;
		Boolean Finish = true;
		Boolean Dead = false;
		Boolean Shield = false;
		Boolean DrawMask = false;

		//Initialize Sound Boolean Values
		Boolean Audio = true;
		Boolean SFX = true;

		// 1 = up; 2 = left; 3 = down; 4 = right;
		int MaskD = 0;
		Boolean Move = false;

		// Lava variables
		Boolean Lava = false;
		int LavaSpeed = 1;
		int Lavay = mapY;

		// Initialize 2d map array
		int[][] map = stages.start;

		// Initialize all gc methods
		gc.setLocationRelativeTo(null);
		gc.enableMouse();
		gc.enableMouseMotion();
		gc.enableMouseWheel();
		gc.setBackgroundColor(Color.BLACK);
		gc.setFont(new Font("Showcard Gothic", Font.BOLD, 25));
		gc.playSoundLoop(assets.smusic);

		// main method while loop
		while (true) {
			while (Start) {
				synchronized (gc) {

					// Clearing Console
					gc.clear();

					// Drawing Objects
					for (int row = 0; row < map.length; row++) {
						for (int col = 0; col < map[0].length; col++) {

							// Set the X and Y values of images for the game play screen to column or row
							int ImageStartingX = col * Pixel - OffsetX;
							int ImageStartingY = row * Pixel - OffsetY;

							// Set the X and Y values of images for the Level Select to column or row
							int LevelStartingX = col * PixelStart;
							int LevelStartingY = row * PixelStart;

							switch (map[row][col]) {

							// Draw Images for game play screen
							case 1:
								gc.drawImage(assets.dot, ImageStartingX, ImageStartingY, Pixel, Pixel);
								break;
							case 2:
								gc.drawImage(assets.coin, ImageStartingX, ImageStartingY, Pixel, Pixel);
								break;
							case 3:
								gc.drawImage(assets.star, ImageStartingX, ImageStartingY, Pixel, Pixel);
								break;
							case 4:
								gc.drawImage(assets.exit, ImageStartingX, ImageStartingY, Pixel, Pixel);
								Lava = false;
								break;
							case 5:
								gc.drawImage(assets.spike, ImageStartingX, ImageStartingY, Pixel, Pixel);
								break;
							case 6:
								gc.drawImage(assets.spiketrap, ImageStartingX, ImageStartingY, Pixel, Pixel);
								break;
							case 9:
								if (map == stages.map6) {
									gc.drawImage(assets.wall2, ImageStartingX, ImageStartingY, Pixel, Pixel);
								} else {
									gc.drawImage(assets.wall1, ImageStartingX, ImageStartingY, Pixel, Pixel);
								}
								break;

							// Draw Images for level select screen
							case 10:
								gc.drawImage(assets.stage1, LevelStartingX, LevelStartingY, LvlW, LvlH);
								break;
							case 20:
								gc.drawImage(assets.stage2, LevelStartingX, LevelStartingY, LvlW, LvlH);
								break;
							case 30:
								gc.drawImage(assets.stage3, LevelStartingX, LevelStartingY, LvlW, LvlH);
								break;
							case 40:
								gc.drawImage(assets.stage4, LevelStartingX, LevelStartingY, LvlW, LvlH);
								break;
							case 50:
								gc.drawImage(assets.stage5, LevelStartingX, LevelStartingY, LvlW, LvlH);
								break;
							case 60:
								gc.drawImage(assets.stage6, LevelStartingX, LevelStartingY, LvlW, LvlH);
								break;
							case 70:
								gc.drawImage(assets.stages, LevelStartingX, LevelStartingY);
								break;
							case 80:
								gc.drawImage(assets.enter, LevelStartingX, LevelStartingY);
								break;
							case 90:
								gc.drawImage(assets.esc, LevelStartingX, LevelStartingY);
								break;
							}
						}
					}
					int centerX = Maskx * Pixel - OffsetX + (Pixel / 2);
					int centerY = Masky * Pixel - OffsetY + (Pixel / 2);

					// Character Rotation
					if (DrawMask) {
						// Rotate up
						if (MaskD == 1) {
							gc.setRotation(180, centerX, centerY);
							// Rotate left
						} else if (MaskD == 2) {
							gc.setRotation(90, centerX, centerY);
							// Rotate down
						} else if (MaskD == 3) {
							gc.setRotation(0, centerX, centerY);
							// Rotate right
						} else if (MaskD == 4) {
							gc.setRotation(270, centerX, centerY);
						}
						// Draw main character
						gc.drawImage(assets.mask, Maskx * Pixel - OffsetX, Masky * Pixel - OffsetY, Pixel, Pixel);
						gc.clearRotation();

						// Draw coin and pause icon as well as the balance
						gc.setColor(Color.BLACK);
						gc.fillRect(0, 0, 3 * Pixel, Pixel);
						gc.fillRect(11 * Pixel, 0, Pixel, Pixel);
						gc.drawImage(assets.pause, 11 * Pixel, 0, Pixel, Pixel);
						gc.drawImage(assets.coinicon, 0, 0, Pixel, Pixel);

						gc.setColor(Color.YELLOW);
						gc.drawString("" + Balance, Pixel + 10, Pixel - 10);
					}

					// Lava
					if (Lava) {
						if (Ticks % 20 == 0) {
							gc.setColor(Color.CYAN);
						} else if (Ticks % 10 == 0) {
							gc.setColor(Color.MAGENTA);
						}
						gc.fillRect(0, Lavay, 1000, 750);
						if (Ticks % 2 == 0)
							Lavay -= LavaSpeed;
					}

					if (Finish) {
						// break out of while(start) loop
						break;
					}
					// update values if character died
					if (Life < 1) {
						Dead = true;
						Start = false;
						break;
					}

				}

				Thread.sleep(Delay);

				if (map != stages.start) {
					// User Input
					if (!Move) {
						if (gc.isKeyDown('W') || gc.isKeyDown(GraphicsConsole.VK_UP)) {
							MaskD = 1;
						} else if (gc.isKeyDown('A') || gc.isKeyDown(GraphicsConsole.VK_LEFT)) {
							MaskD = 2;
						} else if (gc.isKeyDown('S') || gc.isKeyDown(GraphicsConsole.VK_DOWN)) {
							MaskD = 3;
						} else if (gc.isKeyDown('D') || gc.isKeyDown(GraphicsConsole.VK_RIGHT)) {
							MaskD = 4;
						}
					}
					// Enter to buy shield
					if (gc.isKeyDown(GraphicsConsole.VK_ENTER)) {
						if (Life == 1 && (Balance >= 100) && !Shield) {
							Life++;
							Balance -= 100;
							Shield = true;
						}
					}
					// call the pauseChecker method
					Finish = pauseChecker(Pixel);
					if (Finish) {
						break;
					}

					// Update character movement
					if (Move) {
						switch (MaskD) {
						case 0:
							Move = false;
							break;
						case 1:
							Masky--;
							break;
						case 2:
							Maskx--;
							break;
						case 3:
							Masky++;
							break;
						case 4:
							Maskx++;
							break;
						}
					}

					Ticks++;
					if (Ticks % (100 / Delay) == 0) {
						MaskTicks++;
						AnimationTicks++;
						SFX = true;
					}
					// reset animation ticks every 4 count
					if (AnimationTicks > 4) {
						AnimationTicks = 1;
					}
					// update image for every tick
					if (AnimationTicks == 4) {
						assets.star = assets.loadImage("star4");
						assets.coin = assets.loadImage("coin4");
						assets.exit = assets.loadImage("exit3");
					} else if (AnimationTicks == 3) {
						assets.star = assets.loadImage("star3");
						assets.coin = assets.loadImage("coin3");
						assets.exit = assets.loadImage("exit3");
					} else if (AnimationTicks == 2) {
						assets.star = assets.loadImage("star2");
						assets.coin = assets.loadImage("coin2");
						assets.exit = assets.loadImage("exit2");
					} else if (AnimationTicks == 1) {
						assets.star = assets.loadImage("star1");
						assets.coin = assets.loadImage("coin1");
						assets.exit = assets.loadImage("exit1");
					}

					if (!Move) {
						if (MaskTicks > 6)
							MaskTicks = 1;
						// call getNextMaskAnimation method
						assets.mask = getNextMaskAnimation(MaskTicks);
						// add ball animation if character moves
					} else {
						assets.mask = assets.loadImage("ball");
					}

					// call balanceUpdate method
					Integer NewBalance = balanceUpdate(map, Maskx, Masky, Audio, Finish);
					if (NewBalance != null) {
						if (NewBalance == 0) {
							Finish = true;
							break;
						}
						Balance += NewBalance;
					}
					// call updateMovement method
					movementResult Result = updateMovement(map, Masky, Maskx, SFX, MaskD);
					if (Result != null) {

						SFX = Result.SFX;
						Move = Result.Move;
						Shield = Result.Shield;
						MaskD = Result.MaskD;
						//-1 life if collision with damaging block
						Life -= Result.Life;

					}

					// call the calculateOffsetX method to move screen horizontally
					OffsetX = calculateOffsetX(Margin, Pixel, Xblocks, Maskx);
					// call the calculateOffsetY method to move screen vertically
					OffsetY = calculateOffsetY(Margin, Pixel, Yblocks, Masky);

				}

				if (map == stages.start) {
					gc.setCursor(10, 10);

					// call nextLevel method
					int[][] nextLevel = levelSelect(gc.getMouseClick(), PixelStart, LvlW, LvlH);
					// check if mouse is pressed
					if (nextLevel != null) {
						map = nextLevel;
						DrawMask = true;
						gc.playSound(assets.sstart);
						// set character coordinates for each map
						if (map == stages.map1) {
							Maskx = 17;
							Masky = 35;
						}
						if (map == stages.map2) {
							Maskx = 14;
							Masky = 16;
						}
						if (map == stages.map3) {
							Maskx = 5;
							Masky = 20;
						}
						if (map == stages.map4) {
							Lava = true;
							Maskx = 5;
							Masky = 44;
						}
						if (map == stages.map5) {
							Maskx = 19;
							Masky = 34;
						}
						if (map == stages.map6) {
							Maskx = 19;
							Masky = 35;
						}
					}
				}
			}

			while (Finish) {
				// reset character movement and stop drawing mask in the start screen
				MaskD = 0;
				DrawMask = false;
				// Map Transitions and call resetLevel method
				if (map == stages.map1) {
					map = stages.start;
					stages.map1 = resetLevel(assets.ogmap1);

				} else if (map == stages.map2) {
					map = stages.start;
					stages.map2 = resetLevel(assets.ogmap2);

				} else if (map == stages.map3) {
					map = stages.start;
					stages.map3 = resetLevel(assets.ogmap3);

				} else if (map == stages.map4) {
					map = stages.start;
					stages.map4 = resetLevel(assets.ogmap4);

				} else if (map == stages.map5) {
					map = stages.start;
					stages.map5 = resetLevel(assets.ogmap5);

				} else if (map == stages.map6) {
					map = stages.start;
					stages.map6 = resetLevel(assets.ogmap6);

				} else if (map == stages.start) {

				}
				// update finish and start values
				Finish = false;
				Start = true;
				break;
			}

			// dead loop
			while (Dead) {
				synchronized (gc) {
					// death screen
					MouseClick = gc.getMouseClick();
					gc.setColor(Color.YELLOW);
					gc.fillRect(3 * Pixel, 3 * Pixel, 6 * Pixel, 10 * Pixel);
					gc.setColor(Color.RED);
					gc.fillRect(4 * Pixel, 10 * Pixel, 4 * Pixel, Pixel);
					// mouse input for replay
					if ((MouseClick != 0) && (4 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 5 * Pixel + 3 * Pixel)
							&& (10 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 11 * Pixel)) {
						// update values
						Life = 1;
						Finish = true;
						Dead = false;

					}

				}
			}

		}

	}

	// updating the properties of movement given which direction (wasd) and what object is in the way
	static movementResult updateMovement(int[][] map, int Masky, int Maskx, Boolean SFX, int MaskD) {
		movementResult Result = new movementResult();
		// give the object MaskD the existing value so it doesn't default to zero
		Result.MaskD = MaskD;

		// if mask direction is up
		if (MaskD == 1) {
			// check for collision detection with objects in direction
			if (map[Masky - 1][Maskx] != 9 && map[Masky - 1][Maskx] != 6 && map[Masky - 1][Maskx] != 5) {
				Result.Move = true;
				// play sound
				if (SFX) {
					gc.playSound(assets.sjump);
					Result.SFX = false;
				}
				// check for collision detection with objects in direction
			} else if (map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 7) {
				// update values if collision with a damaging object
				Result.Life = 1;
				Result.Shield = false;
				Result.MaskD = 0;
			} else {
				// reset move
				Result.Move = false;
			}
			return Result;
			// if mask direction is left
		} else if (MaskD == 2) {
			// check for collision detection with objects in direction
			if (map[Masky][Maskx - 1] != 9 && map[Masky][Maskx - 1] != 6 && map[Masky][Maskx - 1] != 5) {
				Result.Move = true;
				// play sound
				if (SFX) {
					gc.playSound(assets.sjump);
					Result.SFX = false;
				}
				// check for collision detection with objects in direction
			} else if (map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 7) {
				// update values if collision with a damaging object
				Result.Life = 1;
				Result.Shield = false;
				Result.MaskD = 0;
			} else {
				// reset move
				Result.Move = false;
			}
			return Result;
			// if mask direction is down
		} else if (MaskD == 3) {
			// check for collision detection with objects in direction
			if (map[Masky + 1][Maskx] != 9 && map[Masky + 1][Maskx] != 6 && map[Masky + 1][Maskx] != 5) {
				Result.Move = true;
				// play sound
				if (SFX) {
					gc.playSound(assets.sjump);
					Result.SFX = false;
				}
				// check for collision detection with objects in direction
			} else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 7)) {
				// update values if collision with a damaging object
				Result.Life = 1;
				Result.Shield = false;
				Result.MaskD = 0;
			} else {
				// reset move
				Result.Move = false;
			}
			return Result;
			// if mask direction is right
		} else if (MaskD == 4) {
			// check for collision detection with objects in direction
			if (map[Masky][Maskx + 1] != 9 && map[Masky][Maskx + 1] != 6 && map[Masky][Maskx + 1] != 5) {
				Result.Move = true;
				// play sound
				if (SFX) {
					gc.playSound(assets.sjump);
					Result.SFX = false;
				}
				// check for collision detection with objects in direction
			} else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 7)) {
				// update values if collision with a damaging object
				Result.Life = 1;
				Result.Shield = false;
				Result.MaskD = 0;
			} else {
				// reset move
				Result.Move = false;
			}
			return Result;
		}
		//return null if nothing is pressed
		return null;
	}

	// Set up and check if the user clicks on pause
	static boolean pauseChecker(int Pixel) {
		boolean Finish = false;
		int MouseClick;
		// check for collision with mouse and pause icon
		MouseClick = gc.getMouseClick();
		if ((MouseClick != 0) && (11 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 12 * Pixel)
				&& (0 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 1 * Pixel)) {
			
			while (true) {
				synchronized (gc) {
					//draw pause menu
					MouseClick = gc.getMouseClick();
					gc.setColor(Color.YELLOW);
					gc.fillRect(3 * Pixel, 3 * Pixel, 6 * Pixel, 10 * Pixel);
					gc.setColor(Color.BLUE);
					gc.fillRect(4 * Pixel, 10 * Pixel, Pixel, Pixel);
					gc.fillRect(7 * Pixel, 10 * Pixel, Pixel, Pixel);
					gc.drawImage(assets.wasd, 4 * Pixel, 6 * Pixel, 150, 43);

					//if blue rectangle on left is pressed, return to game play
					if ((MouseClick != 0) && (4 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 5 * Pixel)
							&& (10 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 11 * Pixel)) {
						break;
					}
					//if blue rectangle on left is pressed, return to start screen
					if ((MouseClick != 0) && (7 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 8 * Pixel)
							&& (10 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 11 * Pixel)) {
						Finish = true;
						break;
					}
				}
			}
		}
		return Finish;
	}

	// change the balance and replace the dots,coins, and stars with blank squares
	static Integer balanceUpdate(int[][] map, int Maskx, int Masky, Boolean Audio, Boolean Finish) {
		Integer Balance = null;

		//update balance if character moves over a dot
		if (map[Masky][Maskx] == 1) {
			map[Masky][Maskx] = 0;
			Balance = 1;
			//update balance if character moves over a coin
		} else if (map[Masky][Maskx] == 2) {
			map[Masky][Maskx] = 0;
			Balance = 5;
			//play sound effect
			if (Audio) {
				gc.playSound(assets.scoin);
			}
			//update balance if character moves over a star
		} else if (map[Masky][Maskx] == 3) {
			map[Masky][Maskx] = 0;
			Balance = 10;
			//play sound effect
			if (Audio) {
				gc.playSound(assets.sstar);
			}
		} else if (map[Masky][Maskx] == 4) {
			map[Masky][Maskx] = 0;
			Balance = 0;
			if (Audio) {
				gc.playSound(assets.sexit);
			}
			Finish = true;
		}
		return Balance;
	}

	// Change Mask Image every Masktick (animation tick)
	static Image getNextMaskAnimation(int MaskTicks) {
		//set mask to null
		Image mask = null;

		//change mask image
		if (MaskTicks == 6) {
			mask = assets.loadImage("Mask6");
		} else if (MaskTicks == 5) {
			mask = assets.loadImage("Mask5");
		} else if (MaskTicks == 4) {
			mask = assets.loadImage("Mask4");
		} else if (MaskTicks == 3) {
			mask = assets.loadImage("Mask3");
		} else if (MaskTicks == 2) {
			mask = assets.loadImage("Mask2");
		} else if (MaskTicks == 1) {
			mask = assets.loadImage("Mask1");
		}
		return mask;
	}

	// calculate how much to move the screen by horizontally
	static int calculateOffsetX(int Margin, int Pixel, int Xblocks, int Maskx) {
		int OffsetX = 0;
		int Xposition = Maskx * Pixel;

		if (OffsetX > Xposition - Margin) {
			OffsetX = Xposition - Margin;
			if (OffsetX < 0) {
				OffsetX = 0;
			}
		}
		if (OffsetX < Xposition - mapX + Margin + Pixel) {
			OffsetX = Xposition - mapX + Margin + Pixel;
			if (OffsetX > Xblocks * Pixel - mapX) {
				OffsetX = Xblocks * Pixel - mapX;
			}
		}
		return OffsetX;
	}

	// calculate how much to move the screen by vertically
	static int calculateOffsetY(int Margin, int Pixel, int Yblocks, int Masky) {
		int OffsetY = 0;
		int Yposition = Masky * Pixel;

		if (OffsetY > Yposition - Margin) {
			OffsetY = Yposition - Margin;
			if (OffsetY < 0) {
				OffsetY = 0;
			}
		}
		if (OffsetY < Yposition - mapY + Margin + Pixel) {
			OffsetY = Yposition - mapY + Margin + Pixel;
			if (OffsetY > Yblocks * Pixel - mapY) {
				OffsetY = Yblocks * Pixel - mapY;
			}
		}
		return OffsetY;
	}

	// method for mouse input during level select screen
	static int[][] levelSelect(int mouseclick, int PixelStart, int LvlW, int LvlH) {
		int[][] map = null;
		// return null if mouse is not clicked
		if (mouseclick == 0) {
			return map;
		}
		// replace gc.getMouseX(); and gc.gteMouseY(); with an integer value
		int mouseX = gc.getMouseX();
		int mouseY = gc.getMouseY();
		// mouse collision for selecting level 1
		if ((mouseclick <= 1) && (4 * PixelStart < mouseX) && (mouseX < 5 * PixelStart + LvlW)
				&& (13 * PixelStart < mouseY) && (mouseY < 14 * PixelStart + LvlH)) {
			map = stages.map1;
			// mouse collision for selecting level 2
		} else if ((mouseclick <= 1) && (4 * PixelStart < mouseX) && (mouseX < 5 * PixelStart + LvlW)
				&& (22 * PixelStart < mouseY) && (mouseY < 23 * PixelStart + LvlH)) {
			map = stages.map2;
			// mouse collision for selecting level 3
		} else if ((mouseclick <= 1) && (15 * PixelStart < mouseX) && (mouseX < 16 * PixelStart + LvlW)
				&& (22 * PixelStart < mouseY) && (mouseY < 23 * PixelStart + LvlH)) {
			map = stages.map3;
			// mouse collision for selecting level 4
		} else if ((mouseclick <= 1) && (15 * PixelStart < mouseX) && (mouseX < 16 * PixelStart + LvlW)
				&& (12 * PixelStart < mouseY) && (mouseY < 13 * PixelStart + LvlH)) {
			map = stages.map4;
			// mouse collision for selecting level 5
		} else if ((mouseclick <= 1) && (26 * PixelStart < mouseX) && (mouseX < 27 * PixelStart + LvlW)
				&& (12 * PixelStart < mouseY) && (mouseY < 13 * PixelStart + LvlH)) {
			map = stages.map5;
			// mouse collision for selecting level 6
		} else if ((mouseclick <= 1) && (26 * PixelStart < mouseX) && (mouseX < 27 * PixelStart + LvlW)
				&& (22 * PixelStart < mouseY) && (mouseY < 23 * PixelStart + LvlH)) {
			map = stages.map6;
		}
		return map;
	}

	// method for resetting level upon completion
	static int[][] resetLevel(int[][] mapToCopy) {
		// new array with the same dimensions as mapToCopy
		int[][] result = new int[mapToCopy.length][mapToCopy[0].length];
		// iterating over every position in the 2d array
		for (int i = 0; i < mapToCopy.length; i++) {
			for (int j = 0; j < mapToCopy[i].length; j++) {
				// copy the value at i and j into the result array
				result[i][j] = mapToCopy[i][j];
			}
		}
		return result;
	}
}



