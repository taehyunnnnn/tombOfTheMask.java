package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {
	public static void main(String[] args) throws InterruptedException {

		new main();

	}

	GraphicsConsole gc = new GraphicsConsole(1000, 750, "Example");
	// GraphicsConsole gc = new GraphicsConsole(1500,pixel00, "Example");

	Clip getSound(String sound) {
		return gc.loadSound(sound);
	}

	Image getImage(String source) {

		return Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource(source));

	}

	main() throws InterruptedException {

		Image coin, dot, entrance, exit, mask, spike, spiketrap, star, wall1, wall2;
		coin = getImage("sources/coin.png");
		dot = getImage("sources/dot.png");
		entrance = getImage("sources/entrance.png");
		exit = getImage("sources/exit.png");
		mask = getImage("sources/mask.png");
		spike = getImage("sources/spike.png");
		spiketrap = getImage("sources/spike trap.png");
		star = getImage("sources/star.png");
		wall1 = getImage("sources/wall1.png");
		wall2 = getImage("sources/wall2.png");

		Clip music, sstar1, sstar2, sstar3, scoin, sstar, sdeath, sexit, sjump, sland, sscore_count, sshield, sspikein,
				sspikeout, sstart;
		music = gc.loadSound("sources/main.wav");
		sstar1 = gc.loadSound("sources/1-star.wav");
		sstar2 = gc.loadSound("sources/2-star.wav");
		sstar3 = gc.loadSound("sources/3-star.wav");
		scoin = gc.loadSound("sources/coin.wav");
		sstar = gc.loadSound("sources/star.wav");
		sdeath = gc.loadSound("sources/death.wav");
		sexit = gc.loadSound("sources/win.wav");
		sjump = gc.loadSound("sources/jump.wav");
		sland = gc.loadSound("sources/landing.wav");
		sscore_count = gc.loadSound("sources/score-count.wav");
		sshield = gc.loadSound("sources/shield.wav");
		sspikein = gc.loadSound("sources/spikesinwalls-attack.wav");
		sspikeout = gc.loadSound("sources/spikesinwalls-on-off.wav");
		sstart = gc.loadSound("sources/start.wav");

		gc.setLocationRelativeTo(null);

		int pixel = 12;

		Boolean Start = true;
		Boolean Finish = false;
		Boolean Dead = false;
		int Life = 1;

		int Maskx = 17;
		int Masky = 29;

		int Dot = 100;
		int Star = 0;
		int Coin = 0;
		int Balance = 0;

		Boolean Jump = false;
		// 1 = up; 2 = left; 3 = down; 4 = right;
		int MaskD = 0;
		Boolean Maskw = false;
		Boolean Maska = false;
		Boolean Masks = false;
		Boolean Maskd = false;

		Boolean Lava = false;
		int LavaSpeed = 1;
		int Lavay = 750;

		stages maps = new stages();

		gc.setBackgroundColor(Color.BLACK);

		int[][] map = maps.map1;

		// gc.playSound(sstart);
		// gc.playSoundLoop(music);

		while (true) {
			while (Life > 0) {
				synchronized (gc) {

					// Clearing Console
					gc.clear();

					// Drawing Objects
					for (int row = 0; row < map.length; row++) {
						for (int col = 0; col < map[0].length; col++) {
							if (map[row][col] == 1) {
								gc.drawImage(dot, col * pixel, row * pixel, pixel, pixel);
							}
							if (map[row][col] == 2) {
								gc.drawImage(coin, col * pixel, row * pixel, pixel, pixel);
							}
							if (map[row][col] == 3) {
								gc.drawImage(star, col * pixel, row * pixel, pixel, pixel);
							}
							if (map[row][col] == 4) {
								gc.drawImage(exit, col * pixel, row * pixel, pixel, pixel);
							}
							if (map[row][col] == 5) {
								gc.drawImage(spike, col * pixel, row * pixel, pixel, pixel);
							}
							if (map[row][col] == 6) {
								gc.drawImage(spiketrap, col * pixel, row * pixel, pixel, pixel);
							}
							if (map[row][col] == 7) {
								// spike trap active
							}
							if (map[row][col] == 8) {
								// bat?
							}
							if (map[row][col] == 9) {
								gc.drawImage(wall2, col * pixel, row * pixel, pixel, pixel);
							}
						}
					}
					// gc.drawImage(entrance,x,y);
					gc.drawImage(mask, Maskx * pixel, Masky * pixel, pixel, pixel);

					if (Lava) {
						gc.setColor(Color.RED);
						gc.fillRect(0, Lavay, 1000, 750);
						Lavay -= LavaSpeed;
					}

				}

				Thread.sleep(15);

				// User Input
				if (Jump == false) {
					Maskw = false;
					Maska = false;
					Masks = false;
					Maskd = false;

					if (gc.isKeyDown('W') || gc.isKeyDown(GraphicsConsole.VK_UP)) {
						Maskw = true;
					} else if (gc.isKeyDown('A') || gc.isKeyDown(GraphicsConsole.VK_LEFT)) {
						Maska = true;
					} else if (gc.isKeyDown('S') || gc.isKeyDown(GraphicsConsole.VK_DOWN)) {
						Masks = true;
					} else if (gc.isKeyDown('D') || gc.isKeyDown(GraphicsConsole.VK_RIGHT)) {
						Maskd = true;
					}
				}
					// Enter to buy shield
				if (gc.isKeyDown(GraphicsConsole.VK_ENTER)) {
					if (Life == 1 && ((Dot + (Coin * 5) + (Star * 10)) >= 100)) {
						Life++;
					}
				}

				// Mask Movement
				if (Maskw) {
					if (map[Masky - 1][Maskx] != 9 && map[Masky - 1][Maskx] != 6 && map[Masky - 1][Maskx] != 5) {
						Masky--;
						if (Jump == false) {
							gc.playSound(sjump);
						}
						Jump = true;
					} else if ((map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 666)) {
						Life--;
					} else {
						if (Jump) {
							gc.playSound(sland);
						}
						Jump = false;
					}
				}
				if (Maska) {
					if (map[Masky][Maskx - 1] != 9 && map[Masky][Maskx - 1] != 6 && map[Masky][Maskx - 1] != 5) {
						Maskx--;
						if (Jump == false) {
							gc.playSound(sjump);
						}
						Jump = true;
					} else if ((map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 666)) {
						Life--;
					} else {
						if (Jump) {
							gc.playSound(sland);
						}
						Jump = false;
					}
				}
				if (Masks) {
					if (map[Masky + 1][Maskx] != 9 && map[Masky + 1][Maskx] != 6 && map[Masky + 1][Maskx] != 5) {
						Masky++;
						if (Jump == false) {
							gc.playSound(sjump);
						}
						Jump = true;
					} else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 666)) {
						Life--;
					} else {
						if (Jump) {
							gc.playSound(sland);
						}
						Jump = false;
					}
				}
				if (Maskd) {
					if (map[Masky][Maskx + 1] != 9 && map[Masky][Maskx + 1] != 6 && map[Masky][Maskx + 1] != 5) {
						Maskx++;
						if (Jump == false) {
							gc.playSound(sjump);
						}
						Jump = true;
					} else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 666)) {
						Life--;
					} else {
						if (Jump) {
							gc.playSound(sland);
						}
						Jump = false;
					}
				}

				// Checker

				if (map[Masky][Maskx] == 1) {
					map[Masky][Maskx] = 0;
					Dot++;
				}
				if (map[Masky][Maskx] == 2) {
					map[Masky][Maskx] = 0;
					Coin++;
					gc.playSound(scoin);
				}
				if (map[Masky][Maskx] == 3) {
					map[Masky][Maskx] = 0;
					Star++;
					gc.playSound(sstar);
				}
				if (map[Masky][Maskx] == 4) {
					Finish = true;
					map[Masky][Maskx] = 0;
					Jump = false;
					gc.playSound(sexit);
				}
				if (map[Masky][Maskx] == 5) {
					Life--;
				}
				if (map[Masky][Maskx] == 7) {
					Life--;
				}

				if (Finish) {
					break;
				}

				if (Life < 1) {
					Dead = true;
					break;
				}

			}

			while (Finish) {
				// Map Transition (for tester)
				if (map == maps.map1) {
					Maskx = 14;
					Masky = 5;
					map = maps.map2;
					System.out.println("Stage2");
				} else if (map == maps.map2) {
					Maskx = 5;
					Masky = 6;
					map = maps.map3;
					System.out.println("Stage3");
				} else if (map == maps.map3) {
					Maskx = 5;
					Masky = 44;
					map = maps.map4;
					Lava = true;
					System.out.println("Stage4 ++ LAVA");
				} else if (map == maps.map4) {
					Maskx = 19;
					Masky = 26;
					map = maps.map5;
					Lava = false;
					System.out.println("Stage5");
				} else if (map == maps.map5) {
					Maskx = 18;
					Masky = 27;
					map = maps.map6;
					System.out.println("Stage6");
				} else if (map == maps.map6) {
					Maskx = 17;
					Masky = 29;
					map = maps.map1;
					System.out.println("Stage1");
				}
				Finish = false;
				// gc.playSound(sstart);
				break;
			}

			while (Dead) {
				gc.getChar();
				break;
			}
		}

	}

}
