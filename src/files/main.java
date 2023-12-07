package files;

import hsa2.GraphicsConsole;
import java.awt.*;
import javax.sound.sampled.Clip;

public class main {
	public static void main(String[] args) throws InterruptedException {

		new main();

	}

	GraphicsConsole gc = new GraphicsConsole(24*24, 24*30, "Example");

	main() throws InterruptedException {

		Image coin, dot, exit, mask, spike, spiketrap, star, wall1, wall2;
		coin = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/coin.png"));
		dot = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/dot.png"));
		exit = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/exit.png"));
		mask = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/mask.png"));
		spike = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/spike.png"));
		spiketrap = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/spike trap.png"));
		star = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/star.png"));
		wall1 = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/wall1.png"));
		wall2 = Toolkit.getDefaultToolkit()
				.getImage(gc.getClass().getClassLoader().getResource("sources/wall2.png"));

		gc.setLocationRelativeTo(null);

		Boolean Start, Finish, Dead;
		int Life;
		int Maskx, Masky;
		Boolean Move;
		Boolean Maskw, Maska, Masks, Maskd;

		int Dot, Star, Coin;
		int Stage;

		Start = true;
		Finish = false;
		Dead = false;
		
		Life = 1;
		
		Maskx = 19;
		Masky = 28;
		Dot = 0;
		Star = 0;
		Coin = 0;
		
		Move = true;
		Maskw = false;
		Maska = false;
		Masks = false;
		Maskd = false;

		int[][] map = { {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,4,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,1,9,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,3,1,1,1,9,0,0,0,0},
			    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,0,9,9,9,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,0,9,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,1,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,9,0,0,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,1,9,9,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,1,1,1,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,0,1,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,0,1,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,1,1,3,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,9,1,1,1,1,9,0,0,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,9,1,9,9,1,9,9,9,9,1,9,0,0,0},
		        {0,0,0,0,0,0,0,0,0,0,9,1,9,9,1,1,1,1,1,2,9,0,0,0},
		        {0,0,0,0,0,0,0,9,9,9,9,1,9,9,9,9,9,9,9,9,9,9,9,9},
		        {0,0,0,0,0,0,0,9,1,1,1,1,1,1,1,1,1,9,9,3,1,1,1,9},
		        {0,0,0,0,0,0,0,9,1,0,0,1,9,9,9,9,2,1,1,1,0,0,1,9},
		        {0,0,0,0,0,0,0,9,1,0,0,1,9,0,0,9,9,9,9,9,0,0,1,9},
		        {0,0,0,0,0,0,0,9,1,1,1,1,9,0,0,0,9,0,0,0,0,0,1,9},
		        {0,0,0,0,0,0,0,9,9,9,9,9,9,0,0,0,9,0,0,0,0,0,1,9},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,1,9},
		        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9} };

		while (Life > 0) {
			synchronized (gc) {

				// Clearing Console
				gc.clear();

				// Drawing Objects
				for (int row = 0; row < map.length; row++) {
					for (int col = 0; col < map[0].length; col++) {
						if (map[row][col] == 0) {
							gc.setColor(Color.BLACK);
							gc.fillRect(col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 9) {

							gc.drawImage(wall1, col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 1) {
							gc.drawImage(dot, col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 2) {
							gc.drawImage(coin, col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 3) {
							gc.drawImage(star, col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 4) {
							gc.drawImage(exit, col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 5) {
							gc.drawImage(spike, col * 24, row * 24, 24, 24);
						}
						if (map[row][col] == 6) {
							gc.drawImage(spiketrap, col * 24, row * 24, 24, 24);
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

				gc.drawImage(mask, Maskx * 24, Masky * 24, 24, 24);

			}

			Thread.sleep(5);

			// User Input
			if (Move) {
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
					Move = false;
				} 
				else if ((map[Masky - 1][Maskx] == 5 || map[Masky - 1][Maskx] == 6)) {
					Life --;
				}
				else
					Move = true;
			}
			if (Maska) {
				if (map[Masky][Maskx - 1] != 9 && map[Masky][Maskx - 1] != 6 && map[Masky][Maskx - 1] != 5) {
					Maskx--;
					Move = false;
				} 
				else if ((map[Masky][Maskx - 1] == 5 || map[Masky][Maskx - 1] == 6)) {
					Life --;
				}
				else
					Move = true;
			}
			if (Masks) {
				if (map[Masky + 1][Maskx] != 9 && map[Masky + 1][Maskx] != 6 && map[Masky + 1][Maskx] != 5) {
					Masky++;
					Move = false;
				} 
				else if ((map[Masky + 1][Maskx] == 5 || map[Masky + 1][Maskx] == 6)) {
					Life --;
				}
				else
					Move = true;
			}
			if (Maskd) {
				if (map[Masky][Maskx + 1] != 9 && map[Masky][Maskx + 1] != 6 && map[Masky][Maskx + 1] != 5) {
					Maskx++;
					Move = false;
				} 
				else if ((map[Masky][Maskx + 1] == 5 || map[Masky][Maskx + 1] == 6)) {
					Life --;
				}
				else
					Move = true;
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
			gc.fillRect(Maskx * 24, Masky * 24, 24, 24);
			
			
		}
		
		
		
		
		
		
		
		
		
	}
}



/*
int[][] map = { {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			  {9,1,0,9,0,0,0,0,9,0,0,0,0,6,0,0,0,0,0,0,0,0,0,9},
				        {9,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9},
				        {9,1,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,9,2,0,0,0,0,1,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,9,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9},
				        {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9} };
 */






