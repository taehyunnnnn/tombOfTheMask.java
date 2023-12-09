package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {
	public static void main(String[] args) throws InterruptedException {

		new main();

	}
	GraphicsConsole gc = new GraphicsConsole(36*17, 36*30, "Example");
	//retreiving images method (the source string is where the file is)
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
		
		Clip music = gc.loadSound("sources/main.wav");
		//gc.playSoundLoop(music);
		
		Clip sstar1 = gc.loadSound("sources/1-star.wav");
		Clip sstar2 = gc.loadSound("sources/2-star.wav");
		Clip sstar3 = gc.loadSound("sources/3-star.wav");
		Clip scoin = gc.loadSound("sources/coin.wav");
		Clip sdeath = gc.loadSound("sources/death.wav");
		Clip sjump = gc.loadSound("sources/jump.wav");
		Clip sland = gc.loadSound("sources/landing.wav");
		Clip sscore_count = gc.loadSound("sources/score-count.wav");
		Clip sshield = gc.loadSound("sources/shield.wav");
		Clip sspikein = gc.loadSound("sources/spikesinwalls-attack.wav");
		Clip sspikeout = gc.loadSound("sources/spikesinwalls-on-off.wav");
		Clip sstart = gc.loadSound("sources/start.wav");
		
		gc.setLocationRelativeTo(null);

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

		
		
		
		
		// 16 x 29
		int[][] map = {
				{0,0,0,0,0,0,0,0,0,0,9,9,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,9,4,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,9,9,9,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,9,3,1,1,1,9,0,0,0,0},
			    {0,0,0,0,0,0,0,9,1,0,9,9,9,0,0,0,0},
		        {0,0,0,0,0,0,0,9,1,0,9,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,9,1,1,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,9,9,9,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,9,9,9,1,9,9,9,0,0,0},
		        {0,0,0,0,0,0,0,9,1,1,1,1,1,9,0,0,0},
		        {0,0,0,0,0,0,0,9,1,0,1,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,9,1,0,1,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,9,1,1,3,9,1,9,0,0,0},
		        {0,0,0,9,9,9,9,9,9,9,9,9,1,9,0,0,0},
		        {0,0,0,9,1,1,1,1,9,0,0,9,1,9,0,0,0},
		        {0,0,0,9,1,9,9,1,9,9,9,9,1,9,0,0,0},
		        {0,0,0,9,1,9,9,1,1,1,1,1,2,9,0,0,0},
		        {9,9,9,9,1,9,9,9,9,9,9,9,9,9,9,9,9},
		        {9,1,1,1,1,1,1,1,1,1,9,9,3,1,1,1,9},
		        {9,1,0,0,1,9,9,9,9,2,1,1,1,0,0,1,9},
		        {9,1,0,0,1,9,0,0,9,9,9,9,9,0,0,1,9},
		        {9,1,1,1,1,9,0,0,0,9,0,0,0,0,0,1,9},
		        {9,9,9,9,9,9,0,0,0,9,0,0,0,0,0,1,9},
		        {0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,1,9},
		        {0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9}
		        };

		gc.playSound(sstart);
		
		
		while (Life > 0) {
			synchronized (gc) {

				// Clearing Console
				gc.clear();

				// Drawing Objects
				for (int row = 0; row < map.length; row++) {
					for (int col = 0; col < map[0].length; col++) {
						if (map[row][col] == 0) {
							gc.setColor(Color.BLACK);
							gc.fillRect(col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 9) {

							gc.drawImage(wall1, col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 1) {
							gc.drawImage(dot, col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 2) {
							gc.drawImage(coin, col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 3) {
							gc.drawImage(star, col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 4) {
							gc.drawImage(exit, col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 5) {
							gc.drawImage(spike, col * 36, row * 36, 36, 36);
						}
						if (map[row][col] == 6) {
							gc.drawImage(spiketrap, col * 36, row * 36, 36, 36);
						}
						if (map[Masky][Maskx] == 1) {
							map[Masky][Maskx] = 0;
							Dot++;
						}
						if (map[Masky][Maskx] == 2) {
							map[Masky][Maskx] = 0;
							Coin++;
						}
						if (map[Masky][Maskx] == 3) {
							map[Masky][Maskx] = 0;
							Star++;
						}
						if (map[Masky][Maskx] == 4) {
							Finish = true;
						}
						if (map[Masky][Maskx] == 5) {
							Life = 0;
						}

					}
				}

				gc.drawImage(mask, Maskx * 36, Masky * 36, 36, 36);

			}

			Thread.sleep(10);

			// User Input
			if (Jump == false) {
				if (gc.isKeyDown('W')) {
					Maskw = true;
					Maska = false;
					Masks = false;
					Maskd = false;
				} else if (gc.isKeyDown('A')) {
					Maskw = false;
					Maska = true;
					Masks = false;
					Maskd = false;
				} else if (gc.isKeyDown('S')) {
					Maskw = false;
					Maska = false;
					Masks = true;
					Maskd = false;
				} else if (gc.isKeyDown('D')) {
					Maskw = false;
					Maska = false;
					Masks = false;
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
				else if ((map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 6)) {
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
				else if ((map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 6)) {
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
				else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 6)) {
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
				else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 6)) {
					Life --;
				}
				else {
					if (Jump) {
						gc.playSound(sland);
					}
					Jump = false;
				}
			}
			
			// Checker
			
			if (Finish) {
				break;
			}
			
			if (Life < 1) {
				Finish = true;
				break;
			}
			
			

		}
		
		while (Finish) {
			
		}
		
		while (Dead) {
			gc.fillRect(Maskx * 36, Masky * 36, 36, 36);
			
			
		}
		
		
		
		
		
		
		
		
		
	}
}
