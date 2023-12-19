package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {
	public static void main(String[] args) throws InterruptedException {

		new main();

	}

	GraphicsConsole gc = new GraphicsConsole(1000, 750, "Tomb of the Mask");

	main() throws InterruptedException {

		Image coin, dot, entrance, exit, mask, spike, spiketrap, star, wall1, wall2;
		coin = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/coin.png"));
		dot = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/dot.png"));
		entrance = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/entrance.png"));
		exit = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/exit.png"));
		mask = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/mask.png"));
		spike = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/spike.png"));
		spiketrap = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/spike trap.png"));
		star = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/star.png"));
		wall1 = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/wall1.png"));
		wall2 = Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource("sources/wall2.png"));

		Clip music, sstar1, sstar2, sstar3, scoin, sstar, sdeath, sexit, sjump, sland, sscore_count, sshield, sspikein,
				sspikeout, sstart, entity;
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

		int delay = 15;
		int ticks = 0;
		int seconds = 0;

		Boolean Start = true;
		Boolean Finish = false;
		Boolean Dead = false;
		int Life = 1;

		int Maskx;
		// Maskx = 17;
		Maskx = 5;
		int Masky;
		// Masky = 29;
		Masky = 44;

		int Dot = 0;
		int Star = 0;
		int Coin = 0;

		// 1 = up; 2 = left; 3 = down; 4 = right;
		int MaskD = 0;
		Boolean Move = false;

		Boolean Lava = true;
		int LavaSpeed = 1;
		int Lavay = 750;

		stages maps = new stages();

		gc.setBackgroundColor(Color.BLACK);

		int[][] map = maps.map4;

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
								gc.drawImage(dot, col * pixel, row * pixel, pixel, pixel);
								break;
							case 2:
								gc.drawImage(coin, col * pixel, row * pixel, pixel, pixel);
								break;
							case 3:
								gc.drawImage(star, col * pixel, row * pixel, pixel, pixel);
								break;
							case 4:
								gc.drawImage(exit, col * pixel, row * pixel, pixel, pixel);
								break;
							case 5:
								gc.drawImage(spike, col * pixel, row * pixel, pixel, pixel);
								break;
							case 6:
								gc.drawImage(spiketrap, col * pixel, row * pixel, pixel, pixel);
								break;
							// case 7: gc.drawImage(spikeactive, col * pixel, row * pixel, pixel, pixel);
							// break;
							// case 8: gc.drawImage(bat, col * pixel, row * pixel, pixel, pixel); break;
							// break;
							case 9:
								gc.drawImage(wall1, col * pixel, row * pixel, pixel, pixel);
								break;
							}
						}
					}

					// gc.drawImage(entrance,Maskx * pixel, Masky * pixel);
					gc.drawImage(mask, Maskx * pixel, Masky * pixel, pixel, pixel);

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
					if (Life == 1 && ((Dot + (Coin * 5) + (Star * 10)) >= 100)) {
						Life++;
					}
				}
				// ESC to pause
				if (gc.isKeyDown(GraphicsConsole.VK_ESCAPE)) {
					// M to mute
					if (gc.isKeyDown('M')) {

					}
				}

				// Updates
				if (Move) {
					switch (MaskD) {
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
					System.out.println(seconds);
				}

				// Collision Detection
				if (map[Masky][Maskx] == 1) {
					map[Masky][Maskx] = 0;
					Dot++;
				} else if (map[Masky][Maskx] == 2) {
					map[Masky][Maskx] = 0;
					Coin++;
					gc.playSound(scoin);
				} else if (map[Masky][Maskx] == 3) {
					map[Masky][Maskx] = 0;
					Star++;
					gc.playSound(sstar);
				} else if (map[Masky][Maskx] == 4) {
					Finish = true;
					map[Masky][Maskx] = 0;
					Move = false;
					gc.playSound(sexit);
				}

				if (Lavay <= (Masky * pixel + pixel)) {
					Life--;
				}

				if (MaskD == 1) {
					if (map[Masky - 1][Maskx] != 9 && map[Masky - 1][Maskx] != 6 && map[Masky - 1][Maskx] != 5) {
						Move = true;
					} else if (map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 7)
						Life--;
					else {
						Move = false;
					}
				} else if (MaskD == 2) {
					if (map[Masky][Maskx - 1] != 9 && map[Masky][Maskx - 1] != 6 && map[Masky][Maskx - 1] != 5) {
						Move = true;
					} else if (map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 7)
						Life--;
					else {
						Move = false;
					}
				} else if (MaskD == 3) {
					if (map[Masky + 1][Maskx] != 9 && map[Masky + 1][Maskx] != 6 && map[Masky + 1][Maskx] != 5) {
						Move = true;
					} else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 7))
						Life--;
					else {
						Move = false;
					}
				} else if (MaskD == 4) {
					if (map[Masky][Maskx + 1] != 9 && map[Masky][Maskx + 1] != 6 && map[Masky][Maskx + 1] != 5) {
						Move = true;
					} else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 7))
						Life--;
					else {
						Move = false;
					}
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
				// gc.getChar();
				break;
			}
		}

	}

}
