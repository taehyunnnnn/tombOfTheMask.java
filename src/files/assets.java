package files;

import java.awt.Image;
import java.awt.Toolkit;
import javax.sound.sampled.Clip;

public class assets {
		
	// Audios
		static Clip smusic = main.gc.loadSound("sources/main.wav");
		static Clip sstar1 = main.gc.loadSound("sources/1-star.wav");
		static Clip sstar2 = main.gc.loadSound("sources/2-star.wav");
		static Clip sstar3 = main.gc.loadSound("sources/3-star.wav");
		static Clip scoin = main.gc.loadSound("sources/coin.wav");
		static Clip sstar = main.gc.loadSound("sources/star.wav");
		static Clip sdeath = main.gc.loadSound("sources/death.wav");
		static Clip sexit = main.gc.loadSound("sources/win.wav");
		static Clip sjump = main.gc.loadSound("sources/jump.wav");
		static Clip sland = main.gc.loadSound("sources/landing.wav");
		static Clip sscore_count = main.gc.loadSound("sources/score-count.wav");
		static Clip sshield = main.gc.loadSound("sources/shield.wav");
		static Clip sspikein = main.gc.loadSound("sources/spikesinwalls-attack.wav");
		static Clip sspikeout = main.gc.loadSound("sources/spikesinwalls-on-off.wav");
		static Clip sstart = main.gc.loadSound("sources/start.wav");
		
	// Graphics
		static Image coin = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/coin.png"));
		static Image dot = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/dot.png"));
		static Image entrance = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/entrance.png"));
		static Image exit = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/exit.png"));
		static Image mask = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/mask.png"));
		static Image spike = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/spike.png"));
		static Image spiketrap = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/spike trap.png"));
		static Image star = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/star.png"));
		static Image wall1 = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/wall1.png"));
		static Image wall2 = Toolkit.getDefaultToolkit()
				.getImage(main.gc.getClass().getClassLoader().getResource("sources/wall2.png"));
		
		
		
	
}
