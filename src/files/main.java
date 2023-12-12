package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {
	public static void main(String[] args) throws InterruptedException {

		new main();

	}

	GraphicsConsole gc = new GraphicsConsole(12*100, 12*50, "Example");
	
	Clip getSound (String sound) {
		return gc.loadSound(sound);
	}
	
	Image getImage(String source) {

		return Toolkit.getDefaultToolkit().getImage(gc.getClass().getClassLoader().getResource(source));

	}

	
	main() throws InterruptedException {
		
		Image coin, dot, exit, mask, spike, spiketrap, star, wall1, wall2;
		coin = getImage("sources/coin.png");
		dot = getImage("sources/dot.png");
		exit = getImage("sources/exit.png");
		mask = getImage("sources/mask.png");
		spike = getImage("sources/spike.png");
		spiketrap = getImage("sources/spike trap.png");
		star = getImage("sources/star.png");
		wall1 = getImage("sources/wall1.png");
		wall2 = getImage("sources/wall2.png");
		
		Clip music, sstar1, sstar2, sstar3, scoin, sstar, sdeath, sexit, sjump, sland, sscore_count, sshield, sspikein, sspikeout, sstart;
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
		
		Boolean Start, Finish, Dead;
		int Life;
		int Maskx, Masky;
		Boolean Jump;
		Boolean Maskw, Maska, Masks, Maskd;

		int Dot, Star, Coin;
		int Stage;

		Start = true;
		Finish = false;
		Dead = false;
		
		Life = 1;
		
		Maskx = 12;
		Masky = 28;
		Dot = 0;
		Star = 0;
		Coin = 0;
		
		Jump = false;
		Maskw = false;
		Maska = false;
		Masks = false;
		Maskd = false;
		
		stages maps = new stages();
		
		// Downloading map from stages.java
		int[][] map = maps.map1;
		
		gc.playSound(sstart);
		gc.playSoundLoop(music);
		
		while (true) {
			
		while (Life > 0) {
			
			synchronized (gc) {

				// Clearing Console
				gc.clear();

				// Drawing Objects
				for (int row = 0; row < map.length; row++) {
					for (int col = 0; col < map[0].length; col++) {
						if (map[row][col] == 0) {
							gc.setColor(Color.BLACK);
							gc.fillRect(col * pixel, row * pixel, pixel, pixel);
						}
						if (map[row][col] == 9) {

							gc.drawImage(wall1, col * pixel, row * pixel, pixel, pixel);
						}
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
							gc.playSound(sexit);
						}
						if (map[Masky][Maskx] == 5) {
							Life = 0;
						}

					}
				}

				gc.drawImage(mask, Maskx * pixel, Masky * pixel, pixel, pixel);

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
			
			// Mask Movement
			if (Maskw) {
				if (map[Masky - 1][Maskx] != 9 && map[Masky - 1][Maskx] != 6 && map[Masky - 1][Maskx] != 5) {
					Masky--;
					if (Jump == false) {
						gc.playSound(sjump);
					}
					Jump = true;
				} 
				else if ((map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 666)) {
					Life --;
				}
				else {
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
				} 
				else if ((map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 666)) {
					Life --;
				}
				else {
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
				} 
				else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 666)) {
					Life --;
				}
				else {
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
				} 
				else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 666)) {
					Life --;
				}
				else {
					if (Jump) {
						gc.playSound(sland);
					}
					Jump = false;
				}
			}
			
			// State
			
			if (Finish) {
				break;
			}
			
			if (Life < 1) {
				Finish = true;
				break;
			}
			
		}
		
		while (Finish) {
			if (map == maps.map1) {
				Maskx = 11;
				Masky = 4;
				map = maps.map2;
			}
			else if (map == maps.map2) {
				Maskx = 4;
				Masky = 5;
				map = maps.map3;
			}
			else if (map == maps.map3) {
				Maskx = 3;
				Masky = 43;
				map = maps.map4;
			}
			else if (map == maps.map4) {
				Maskx = 17;
				Masky = 25;
				map = maps.map5;
			}
			else if (map == maps.map5) {
				Maskx = 15;
				Masky = 26;
				map = maps.map6;
			}
			else if (map == maps.map6) {
				Maskx = 12;
				Masky = 28;
				map = maps.map1;
			}
			Finish = false;
			break;
		}
		
		while (Dead) {
			
			
			
		}
	}
		

	}
}
