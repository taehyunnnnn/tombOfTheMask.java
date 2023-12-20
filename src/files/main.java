package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {
	
	public static void main(String[] args) throws InterruptedException {

		new main();
		

	}

	static GraphicsConsole gc = new GraphicsConsole(26 * 12, 47 * 12, "Tomb of the Mask");

	main() throws InterruptedException {

		gc.setLocationRelativeTo(null);

		int pixel = 12;

		int delay = 15;
		int ticks = 0;
		int seconds = 0;

		Boolean Start = false;
		Boolean Finish = true;
		Boolean Dead = false;
		Boolean Shield = false;
		int Life = 1;

		int Maskx = 0;
		int Masky = 0;

		int Dot = 0;
		int Star = 0;
		int Coin = 0;
		int Balance = 0;

		Boolean Audio = true;

		// 1 = up; 2 = left; 3 = down; 4 = right;
		int MaskD = 0;
		Boolean Move = false;

		Boolean Lava = false;
		int LavaSpeed = 1;
		int Lavay = 650;

		gc.setBackgroundColor(Color.BLACK);
		
		
		stages maps = new stages();
		int[][] map = maps.map3;

		if (map == maps.map1) {
			Maskx = 17;
			Masky = 36;
		} else if (map == maps.map2) {
			Maskx = 14;
			Masky = 16;
		} else if (map == maps.map3) {
			Maskx = 5;
			Masky = 20;
		} else if (map == maps.map4) {
			Maskx = 5;
			Masky = 44;
		} else if (map == maps.map5) {
			Maskx = 19;
			Masky = 34;
		} else if (map == maps.map6) {
			Maskx = 19;
			Masky = 35;
		}
		// gc.playSound(sstart);
		// gc.playSoundLoop(music);

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
								gc.drawImage(assets.dot, col * pixel, row * pixel, pixel, pixel);
								break;
							case 2:
								gc.drawImage(assets.coin, col * pixel, row * pixel, pixel, pixel);
								break;
							case 3:
								gc.drawImage(assets.star, col * pixel, row * pixel, pixel, pixel);
								break;
							case 4:
								gc.drawImage(assets.exit, col * pixel, row * pixel, pixel, pixel);
								break;
							case 5:
								gc.drawImage(assets.spike, col * pixel, row * pixel, pixel, pixel);
								break;
							case 6:
								gc.drawImage(assets.spiketrap, col * pixel, row * pixel, pixel, pixel);
								break;
							// case 7: gc.drawImage(spikeactive, col * pixel, row * pixel, pixel, pixel);
							// break;
							// case 8: gc.drawImage(bat, col * pixel, row * pixel, pixel, pixel); break;
							// break;
							case 9:
								if (map == maps.map6)
									gc.drawImage(assets.wall2, col * pixel, row * pixel, pixel, pixel);
								else
									gc.drawImage(assets.wall1, col * pixel, row * pixel, pixel, pixel);
								break;
							}
						}
					}

					// gc.drawImage(entrance,Maskx * pixel, Masky * pixel);
					gc.drawImage(assets.mask, Maskx * pixel, Masky * pixel, pixel, pixel);

					if (Lava) {
						if (ticks % 20 == 0) {
							gc.setColor(Color.CYAN);
						} else if (ticks % 10 == 0) {
							gc.setColor(Color.MAGENTA);
						}
						gc.fillRect(0, Lavay, 1000, 750);
						if (ticks % 2 == 0)
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

				Thread.sleep(delay);

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
						System.out.println("SHIELD ++ " + Life);
						Balance -= 100;
						Shield = true;
					}
				}
				// ESC to pause
				if (gc.isKeyDown(GraphicsConsole.VK_ESCAPE)) {
					// M to mute
					if (gc.isKeyDown('M')) {
						Audio = false;
					}
				}

				// Updates
				if (Move) {
					switch (MaskD) {
					case 0: 
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

				ticks++;
				if (ticks % (1000 / delay) == 0) {
					seconds++;
					System.out.println("-------------------------" + seconds);
					System.out.println("X: " + Maskx);
					System.out.println("Y: " + Masky);
					System.out.println("$: " + Balance);
					System.out.println("L: " + Life);
				}

				// Collision Detection
				if (map[Masky][Maskx] == 1) {
					map[Masky][Maskx] = 0;
					Balance ++;
					Dot++;
				} else if (map[Masky][Maskx] == 2) {
					map[Masky][Maskx] = 0;
					Balance += 5;
					Coin++;
					if (Audio)
						gc.playSound(assets.scoin);
				} else if (map[Masky][Maskx] == 3) {
					map[Masky][Maskx] = 0;
					Balance += 10;
					Star++;
					if (Audio)
						gc.playSound(assets.sstar);
				} else if (map[Masky][Maskx] == 4) {
					Finish = true;
					map[Masky][Maskx] = 0;
					if (Audio)
						gc.playSound(assets.sexit);
				}

				if (Lavay <= (Masky * pixel)) {
					Life--;
				}

				if (MaskD == 1) {
					if (map[Masky - 1][Maskx] != 9 && map[Masky - 1][Maskx] != 6 && map[Masky - 1][Maskx] != 5) {
						Move = true;
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
					} else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 7)) {
						Life--;
						Shield = false;
						MaskD = 0;
					} else {
						Move = false;
					}
				}
			}

			while (Finish) {
				// Map Transition (for tester)
				if (map == maps.map1) {
					Maskx = 14;
					Masky = 16;
					map = maps.map2;
					System.out.println("Stage2---------------------");
				} else if (map == maps.map2) {
					Maskx = 5;
					Masky = 20;
					map = maps.map3;
					System.out.println("Stage3---------------------");
				} else if (map == maps.map3) {
					Maskx = 5;
					Masky = 44;
					map = maps.map4;
					Lava = true;
					System.out.println("Stage4---------------------");
				} else if (map == maps.map4) {
					Maskx = 19;
					Masky = 34;
					map = maps.map5;
					Lava = false;
					Lavay = 650;
					System.out.println("Stage5---------------------");
				} else if (map == maps.map5) {
					Maskx = 19;
					Masky = 35;
					map = maps.map6;
					System.out.println("Stage6---------------------");
				} else if (map == maps.map6) {
					Maskx = 17;
					Masky = 36;
					map = maps.map1;
					System.out.println("Stage1---------------------");
				}
				Finish = false;
				Start = true;
				// gc.playSound(sstart);
				break;
			}

			while (Dead) {
				// gc.getChar();
				break;
			}
		}

	}

}
