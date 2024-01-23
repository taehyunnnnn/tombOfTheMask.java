package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {

	public static void main(String[] args) throws InterruptedException {

		new main();

	}

	static int mapX = 36 * 12;
	static int mapY = 48 * 12;
	static GraphicsConsole gc = new GraphicsConsole(mapX, mapY, "Tomb of the Mask");

	main() throws InterruptedException {

		gc.setLocationRelativeTo(null);

		final int LvlW = 64;
		final int LvlH = 64;
		final int Pixel = 36;
		final int PixelStart = 12;

		final int Delay = 15;
		int Ticks = 0;
		int MaskTicks = 1;
		int AnimationTicks = 1;

		int MARGIN = 5 * Pixel;
		int XBLOCKS = 26;
		int YBLOCKS = 46;
		int OffsetX = 0;
		int OffsetY = 0;

		Boolean Start = false;
		Boolean Finish = true;
		Boolean Dead = false;
		Boolean Shield = false;
		Boolean DrawMask = false;

		int Life = 1;
		int Maskx = 0;
		int Masky = 0;

		int CenterX = Maskx * Pixel - OffsetX + (Pixel / 2);
		int CenterY = Masky * Pixel - OffsetY + (Pixel / 2);

		int Balance = 100;

		int MouseClick = 0;
		gc.enableMouse();
		gc.enableMouseMotion();
		gc.enableMouseWheel();

		Boolean Audio = true;
		Boolean SFX = true;

		// 1 = up; 2 = left; 3 = down; 4 = right;

		int MaskD = 0;
		Boolean Move = false;

		// Lava variables
		Boolean Lava = false;
		int LavaSpeed = 1;
		int Lavay = mapY;

		gc.setBackgroundColor(Color.BLACK);

		stages maps = new stages();
		int[][] map = maps.start;

		gc.setFont(new Font("Showcard Gothic", Font.BOLD, 25));

		// gc.playSoundLoop(assets.smusic);

		while (true) {
			while (Start) {

				synchronized (gc) {

					// Clearing Console
					gc.clear();

					// Drawing Objects
					for (int row = 0; row < map.length; row++) {
						for (int col = 0; col < map[0].length; col++) {
							switch (map[row][col]) {
							case 1:
								gc.drawImage(assets.dot, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel, Pixel);
								break;
							case 2:
								gc.drawImage(assets.coin, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel, Pixel);
								break;
							case 3:
								gc.drawImage(assets.star, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel, Pixel);
								break;
							case 4:
								gc.drawImage(assets.exit, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel, Pixel);
								Lava = false;
								break;
							case 5:
								gc.drawImage(assets.spike, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel, Pixel);
								break;
							case 6:
								gc.drawImage(assets.spiketrap, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel,
										Pixel);
								break;
							case 7:
								if (assets.spiketrap2 != null) {
									gc.drawImage(assets.spiketrap2, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel,
											Pixel);
								}
								break;

							case 9:
								if (map == maps.map6)
									gc.drawImage(assets.wall2, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel,
											Pixel);

								else
									gc.drawImage(assets.wall1, col * Pixel - OffsetX, row * Pixel - OffsetY, Pixel,
											Pixel);
								break;

							case 10:
								gc.drawImage(assets.stage1, col * PixelStart, row * PixelStart, LvlW, LvlH);
								break;
							case 20:
								gc.drawImage(assets.stage2, col * PixelStart, row * PixelStart, LvlW, LvlH);
								break;
							case 30:
								gc.drawImage(assets.stage3, col * PixelStart, row * PixelStart, LvlW, LvlH);
								break;
							case 40:
								gc.drawImage(assets.stage4, col * PixelStart, row * PixelStart, LvlW, LvlH);
								break;
							case 50:
								gc.drawImage(assets.stage5, col * PixelStart, row * PixelStart, LvlW, LvlH);
								break;
							case 60:
								gc.drawImage(assets.stage6, col * PixelStart, row * PixelStart, LvlW, LvlH);
								break;
							case 70:
								gc.drawImage(assets.stages, col * PixelStart, row * PixelStart);
								break;
							case 80:
								gc.drawImage(assets.enter, col * PixelStart, row * PixelStart);
								break;
							case 90:
								gc.drawImage(assets.esc, col * PixelStart, row * PixelStart);
								break;

							}
						}
					}
					int centerX = Maskx * Pixel - OffsetX + (Pixel / 2);
					int centerY = Masky * Pixel - OffsetY + (Pixel / 2);

					if (DrawMask == true) {
						if (MaskD == 1) {
							gc.setRotation(180, centerX, centerY);
						} else if (MaskD == 2) {
							gc.setRotation(90, centerX, centerY);
						} else if (MaskD == 3) {
							gc.setRotation(0, centerX, centerY);
						} else if (MaskD == 4) {
							gc.setRotation(270, centerX, centerY);
						}
						gc.drawImage(assets.mask, Maskx * Pixel - OffsetX, Masky * Pixel - OffsetY, Pixel, Pixel);
						gc.clearRotation();

						gc.setColor(Color.BLACK);
						gc.fillRect(0, 0, 3 * Pixel, Pixel);
						gc.fillRect(11 * Pixel, 0, Pixel, Pixel);
						gc.setColor(Color.YELLOW);
						gc.drawImage(assets.pause, 11 * Pixel, 0, Pixel, Pixel);
						gc.drawImage(assets.coinicon, 0, 0, Pixel, Pixel);
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
						break;
					}

					if (Life < 1) {
						Dead = true;
						Start = false;
						break;
					}

				}

				Thread.sleep(Delay);

				if (map != maps.start) {
					// User Input
					if (Move == false) {
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
						if (Life == 1 && (Balance >= 100) && Shield == false) {
							Life++;
							// System.out.println("SHIELD ++ " + Life);
							Balance -= 100;
							Shield = true;
						}
					}

					// Pause
					MouseClick = gc.getMouseClick();
					if ((MouseClick != 0) && (11 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 12 * Pixel)
							&& (0 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 1 * Pixel)) {
						System.out.println("pause " + MouseClick);
						MouseClick = 0;

						while (true) {
							synchronized (gc) {
								MouseClick = gc.getMouseClick();
								gc.setColor(Color.YELLOW);
								gc.fillRect(3 * Pixel, 3 * Pixel, 6 * Pixel, 10 * Pixel);
								gc.setColor(Color.BLUE);
								gc.fillRect(4 * Pixel, 10 * Pixel, Pixel, Pixel);
								gc.fillRect(7 * Pixel, 10 * Pixel, Pixel, Pixel);
								gc.drawImage(assets.wasd, 4 * Pixel, 6 * Pixel, 150, 43);

								if ((MouseClick != 0) && (4 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 5 * Pixel)
										&& (10 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 11 * Pixel)) {
									System.out.println("g " + MouseClick);

									break;
								}

								if ((MouseClick != 0) && (7 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 8 * Pixel)
										&& (10 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 11 * Pixel)) {
									Finish = true;
									break;
								}
								if ((MouseClick != 0) && (11 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 12 * Pixel)
										&& (0 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 1 * Pixel)) {
									System.out.println("k " + MouseClick);

									break;
								}

							}
						}
					}
					// Updates
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

					if (AnimationTicks > 4)
						AnimationTicks = 1;
					if (AnimationTicks == 4) {
						assets.star = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/star4.png"));
						assets.coin = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/coin4.png"));
						assets.exit = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/exit3.png"));
					} else if (AnimationTicks == 3) {
						assets.star = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/star3.png"));
						assets.coin = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/coin3.png"));
						assets.exit = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/exit3.png"));
					} else if (AnimationTicks == 2) {
						assets.star = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/star2.png"));
						assets.coin = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/coin2.png"));
						assets.exit = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/exit2.png"));
					} else if (AnimationTicks == 1) {
						assets.star = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/star1.png"));
						assets.coin = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/coin1.png"));
						assets.exit = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/exit1.png"));

					}

					if (Move == false) {
						if (MaskTicks > 6)
							MaskTicks = 1;
						if (MaskTicks == 6) {
							assets.mask = Toolkit.getDefaultToolkit()
									.getImage(main.gc.getClass().getClassLoader().getResource("sources/Mask6.png"));
						} else if (MaskTicks == 5) {
							assets.mask = Toolkit.getDefaultToolkit()
									.getImage(main.gc.getClass().getClassLoader().getResource("sources/Mask5.png"));
						} else if (MaskTicks == 4) {
							assets.mask = Toolkit.getDefaultToolkit()
									.getImage(main.gc.getClass().getClassLoader().getResource("sources/Mask4.png"));
						} else if (MaskTicks == 3) {
							assets.mask = Toolkit.getDefaultToolkit()
									.getImage(main.gc.getClass().getClassLoader().getResource("sources/Mask3.png"));
						} else if (MaskTicks == 2) {
							assets.mask = Toolkit.getDefaultToolkit()
									.getImage(main.gc.getClass().getClassLoader().getResource("sources/Mask2.png"));
						} else if (MaskTicks == 1) {
							assets.mask = Toolkit.getDefaultToolkit()
									.getImage(main.gc.getClass().getClassLoader().getResource("sources/Mask1.png"));
						}
					} else {
						assets.mask = Toolkit.getDefaultToolkit()
								.getImage(main.gc.getClass().getClassLoader().getResource("sources/ball.png"));
					}

					// Collision Detection

					if (map[Masky][Maskx] == 1) {
						map[Masky][Maskx] = 0;
						Balance++;
					} else if (map[Masky][Maskx] == 2) {
						map[Masky][Maskx] = 0;
						Balance += 5;
						if (Audio)
							gc.playSound(assets.scoin);
					} else if (map[Masky][Maskx] == 3) {
						map[Masky][Maskx] = 0;
						Balance += 10;
						if (Audio)
							gc.playSound(assets.sstar);
					} else if (map[Masky][Maskx] == 4) {
						Finish = true;
						map[Masky][Maskx] = 0;
						if (Audio)
							gc.playSound(assets.sexit);
						break;
					}

					if (MaskD == 1) {
						if (map[Masky - 1][Maskx] != 9 && map[Masky - 1][Maskx] != 6 && map[Masky - 1][Maskx] != 5) {
							Move = true;
							if (SFX) {
								gc.playSound(assets.sjump);
								SFX = false;
							}
						} else if (map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 7) {
							Life--;
							Shield = false;
							MaskD = 0;
						} else {
							Move = false;
						}
					} else if (MaskD == 2) {
						if (map[Masky][Maskx - 1] != 9 && map[Masky][Maskx - 1] != 6 && map[Masky][Maskx - 1] != 5) {
							Move = true;
							if (SFX) {
								gc.playSound(assets.sjump);
								SFX = false;
							}
						} else if (map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 7) {
							Life--;
							Shield = false;
							MaskD = 0;
						} else {
							Move = false;
						}
					} else if (MaskD == 3) {
						if (map[Masky + 1][Maskx] != 9 && map[Masky + 1][Maskx] != 6 && map[Masky + 1][Maskx] != 5) {
							Move = true;
							if (SFX) {
								gc.playSound(assets.sjump);
								SFX = false;
							}
						} else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 7)) {
							Life--;
							Shield = false;
							MaskD = 0;
						} else {
							Move = false;
						}
					} else if (MaskD == 4) {
						if (map[Masky][Maskx + 1] != 9 && map[Masky][Maskx + 1] != 6 && map[Masky][Maskx + 1] != 5) {
							Move = true;
							if (SFX) {
								gc.playSound(assets.sjump);
								SFX = false;
							}
						} else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 7)) {
							Life--;
							Shield = false;
							MaskD = 0;
						} else {
							Move = false;
						}
					}

					if ((map[Masky - 1][Maskx] == 6) || (map[Masky + 1][Maskx] == 6) || (map[Masky][Maskx - 1] == 6)
							|| (map[Masky][Maskx + 1] == 6)) {
						System.out.println("SPIKE");

						map[Masky][Maskx] = 7;

					}

					// Scroll the screen up/down/left/right to keep the character on-screen
					int xposition = Maskx * Pixel;
					int yposition = Masky * Pixel;
					if (OffsetX > xposition - MARGIN) {
						OffsetX = xposition - MARGIN;
						if (OffsetX < 0)
							OffsetX = 0;
					}
					if (OffsetX < xposition - mapX + MARGIN + Pixel) {
						OffsetX = xposition - mapX + MARGIN + Pixel;
						if (OffsetX > XBLOCKS * Pixel - mapX)
							OffsetX = XBLOCKS * Pixel - mapX;
					}
					if (OffsetY > yposition - MARGIN) {
						OffsetY = yposition - MARGIN;
						if (OffsetY < 0)
							OffsetY = 0;
					}
					if (OffsetY < yposition - mapY + MARGIN + Pixel) {
						OffsetY = yposition - mapY + MARGIN + Pixel;
						if (OffsetY > YBLOCKS * Pixel - mapY)
							OffsetY = YBLOCKS * Pixel - mapY;
					}
				}

				if (map == maps.start) {
					gc.setCursor(10, 10);

					int[][] nextLevel = levelSelect(gc.getMouseClick(), PixelStart, LvlW, LvlH);
					// check if mouse is pressed
					if (nextLevel != null) {
						map = nextLevel;
						DrawMask = true;
						// gc.playSound(assets.sstart);
						if (map == maps.map1) {
							Maskx = 17;
							Masky = 35;
						}
						if (map == maps.map2) {
							Maskx = 14;
							Masky = 16;
						}
						if (map == maps.map3) {
							Maskx = 5;
							Masky = 20;
						}
						if (map == maps.map4) {
							Lava = true;
							Maskx = 5;
							Masky = 44;
						}
						if (map == maps.map5) {
							Maskx = 19;
							Masky = 34;
						}
						if (map == maps.map6) {
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
				// Map Transitions
				if (map == maps.map1) {
					map = maps.start;
					maps.map1 = resetLevel(assets.ogmap1);

				} else if (map == maps.map2) {
					map = maps.start;
					maps.map2 = resetLevel(assets.ogmap2);

				} else if (map == maps.map3) {
					map = maps.start;
					maps.map3 = resetLevel(assets.ogmap3);

				} else if (map == maps.map4) {
					map = maps.start;
					maps.map4 = resetLevel(assets.ogmap4);

				} else if (map == maps.map5) {
					map = maps.start;
					maps.map5 = resetLevel(assets.ogmap5);

				} else if (map == maps.map6) {
					map = maps.start;
					maps.map6 = resetLevel(assets.ogmap6);

				} else if (map == maps.start) {

				}
				Finish = false;
				Start = true;
				break;
			}

			while (Dead) {
				synchronized (gc) {
					MouseClick = gc.getMouseClick();
					gc.setColor(Color.YELLOW);
					gc.fillRect(3 * Pixel, 3 * Pixel, 6 * Pixel, 10 * Pixel);
					gc.setColor(Color.RED);
					gc.fillRect(4 * Pixel, 10 * Pixel, 4 * Pixel, Pixel);
					if ((MouseClick != 0) && (4 * Pixel < gc.getMouseX()) && (gc.getMouseX() < 5 * Pixel + 3 * Pixel)
							&& (10 * Pixel < gc.getMouseY()) && (gc.getMouseY() < 11 * Pixel)) {
						Life = 1;
						Finish = true;
						Dead = false;

					}

				}
			}

		}

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
			// change map array to level 1
			map = stages.map1;
			// mouse collision for selecting level 2
		} else if ((mouseclick <= 1) && (4 * PixelStart < mouseX) && (mouseX < 5 * PixelStart + LvlW)
				&& (22 * PixelStart < mouseY) && (mouseY < 23 * PixelStart + LvlH)) {
			// change map array to level 2
			map = stages.map2;
			// mouse collision for selecting level 3
		} else if ((mouseclick <= 1) && (15 * PixelStart < mouseX) && (mouseX < 16 * PixelStart + LvlW)
				&& (22 * PixelStart < mouseY) && (mouseY < 23 * PixelStart + LvlH)) {
			// change map array to level 3
			map = stages.map3;
			// mouse collision for selecting level 4
		} else if ((mouseclick <= 1) && (15 * PixelStart < mouseX) && (mouseX < 16 * PixelStart + LvlW)
				&& (12 * PixelStart < mouseY) && (mouseY < 13 * PixelStart + LvlH)) {
			// change map array to level 4
			map = stages.map4;
			// mouse collision for selecting level 5
		} else if ((mouseclick <= 1) && (26 * PixelStart < mouseX) && (mouseX < 27 * PixelStart + LvlW)
				&& (12 * PixelStart < mouseY) && (mouseY < 13 * PixelStart + LvlH)) {
			// change map array to level 5
			map = stages.map5;
			// mouse collision for selecting level 6
		} else if ((mouseclick <= 1) && (26 * PixelStart < mouseX) && (mouseX < 27 * PixelStart + LvlW)
				&& (22 * PixelStart < mouseY) && (mouseY < 23 * PixelStart + LvlH)) {
			// change map array to level 6
			map = stages.map6;
		}
		// return the map value
		return map;
	}

	// method for resting level upon completion
	static int[][] resetLevel(int[][] mapToCopy) {
		int[][] result = new int[mapToCopy.length][mapToCopy[0].length];
		for (int i = 0; i < mapToCopy.length; i++) {
			for (int j = 0; j < mapToCopy[i].length; j++) {
				result[i][j] = mapToCopy[i][j];
			}
		}
		return result;
	}
	// static int maskMovement(int Maskx, int MaskY, )
}
