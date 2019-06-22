import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class AnimationFrame extends JFrame {

	final public static int FRAMES_PER_SECOND = 60;
	final public static int SCREEN_HEIGHT = 800;
	final public static int SCREEN_WIDTH = 1500;

	private int xpCenter = SCREEN_WIDTH / 2;
	private int ypCenter = SCREEN_HEIGHT / 2;

	private double scale = 1;
	//point in universe on which the screen will center
	private double xCenter = 0;		
	private double yCenter = 0;

	private JPanel panel = null;
	private JButton btnPauseRun;
	private JLabel lblTimeLabel;
	private JLabel lblTime;
	private JLabel lblLevelLabel;
	private JLabel lblLevel;
	private JLabel lblscore;

	private static Thread game;
	private static boolean stop = false;

	private long current_time = 0;								//MILLISECONDS
	private long next_refresh_time = 0;							//MILLISECONDS
	private long last_refresh_time = 0;
	private long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	private long actual_delta_time = 0;							//MILLISECONDS
	private long elapsed_time = 0;
	private boolean isPaused = false;

	private KeyboardInput keyboard = new KeyboardInput();
	private Universe universe = null;

	//local (and direct references to various objects in universe ... should reduce lag by avoiding dynamic lookup
	private ActiveSprite player1 = null;
	private ArrayList<ActiveSprite> activeSprites = null;
	private ArrayList<StaticSprite> staticSprites = null;
	private Background background = null;
	boolean centreOnPlayer = false;
	int universeLevel = 1;
	
	static int score = 5;

	public AnimationFrame()
	{
		super("");
		this.setFocusable(true);
		this.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});

		Container cp = getContentPane();
		cp.setBackground(Color.BLACK);
		cp.setLayout(null);

		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getContentPane().add(panel, BorderLayout.CENTER);

		btnPauseRun = new JButton("||");
		btnPauseRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPauseRun_mouseClicked(arg0);
			}
		});

		btnPauseRun.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPauseRun.setBounds(20, 20, 48, 32);
		btnPauseRun.setFocusable(false);
		getContentPane().add(btnPauseRun);
		getContentPane().setComponentZOrder(btnPauseRun, 0);

		lblTimeLabel = new JLabel("Time: ");
		lblTimeLabel.setForeground(Color.YELLOW);
		lblTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTimeLabel.setBounds(80, 22, 96, 30);
		getContentPane().add(lblTimeLabel);
		getContentPane().setComponentZOrder(lblTimeLabel, 0);

		lblTime = new JLabel("000");
		lblTime.setForeground(Color.YELLOW);
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTime.setBounds(192, 22, 320, 30);
		getContentPane().add(lblTime);
		getContentPane().setComponentZOrder(lblTime, 0);

		lblLevelLabel = new JLabel("Score: ");
		lblLevelLabel.setForeground(Color.YELLOW);
		lblLevelLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblLevelLabel.setBounds(1272, 22, 128, 30);
		getContentPane().add(lblLevelLabel);
		getContentPane().setComponentZOrder(lblLevelLabel, 0);

		lblLevel = new JLabel("0");
		lblLevel.setForeground(Color.YELLOW);
		lblLevel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblLevel.setBounds(1380, 22, 100, 30);
		getContentPane().add(lblLevel);
		getContentPane().setComponentZOrder(lblLevel, 0);
		

	}

	public static void main(String[] args)
	{
		AnimationFrame me = new AnimationFrame();
		me.setVisible(true);

		game = new Thread()
		{
			public void run()
			{
				me.animationLoop();
				System.out.println("run() complete");
			}
		};

		game.start();
		System.out.println("main() complete");

	}

	private void animationLoop() {

		universe = Animation.getNextUniverse();
		
		while (stop == false && universe != null) {

			activeSprites = universe.getActiveSprites();
			staticSprites = universe.getStaticSprites();
			player1 = universe.getPlayer1();
			background = universe.getBackground();
			score = universe.getScore();
			universeLevel = Animation.getUniverseCount();
			centreOnPlayer = universe.centerOnPlayer();
			this.scale = universe.getScale();
			this.xCenter = universe.getXCenter();
			this.yCenter = universe.getYCenter();

			// main game loop
			while (stop == false && universe.isComplete() == false) {

				//adapted from http://www.java-gaming.org/index.php?topic=24220.0
				last_refresh_time = System.currentTimeMillis();
				next_refresh_time = current_time + minimum_delta_time;

				//sleep until the next refresh time
				while (current_time < next_refresh_time)
				{
					//allow other threads (i.e. the Swing thread) to do its work
					Thread.yield();

					try {
						Thread.sleep(1);
					}
					catch(Exception e) {    					
					} 

					//track current time
					current_time = System.currentTimeMillis();
				}

				//read input
				keyboard.poll();
				handleKeyboardInput();

				//UPDATE STATE
				updateTime();
				universe.update(keyboard, actual_delta_time);
				updateControls();

				//REFRESH
				this.repaint();
			}

			universe = Animation.getNextUniverse();

		}

		System.out.println("animation complete");
		AudioPlayer.setStopAll(true);
		dispose();	

	}
//	private void setScore(int score) {
//		this.score = score;
//	}

	private void updateControls() {
		this.lblTime.setText(Long.toString(elapsed_time / 1000));
		if (Animation.getUniverseCount() == 1) {
			this.lblLevel.setText(Integer.toString(universe.getScore()));

		}
		else {
			this.lblLevel.setText(Integer.toString(score));

			
		}
		 
	}
	
	public long getTime() {
		return (current_time);
	}

	private void updateTime() {
		if (Animation.getUniverseCount() == 1) {
			current_time = System.currentTimeMillis();
			actual_delta_time = (isPaused ? 0 : current_time - last_refresh_time);
			last_refresh_time = current_time;
			elapsed_time += actual_delta_time;
		}
		
		

	}

	protected void btnPauseRun_mouseClicked(MouseEvent arg0) {
		if (isPaused) {
			isPaused = false;
			this.btnPauseRun.setText("||");
		}
		else {
			isPaused = true;
			this.btnPauseRun.setText(">");
		}
	}

	private void handleKeyboardInput() {
		if (keyboard.keyDown(80) && ! isPaused) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && isPaused ) {
			btnPauseRun_mouseClicked(null);
		}
		if (keyboard.keyDown(112)) {
			scale *= 1.01;
		}
		if (keyboard.keyDown(113)) {
			scale /= 1.01;
		}
		if (keyboard.keyDown(69)) {
			Animation.getNextUniverse();
			System.out.println("  ");
		}
	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g)
		{	
			if (universe == null) {
				return;
			}

			if (player1 != null && centreOnPlayer) {
				xCenter = player1.getCenterX();
				yCenter = player1.getCenterY();     
			}

			paintBackground(g, background);

			for (StaticSprite staticSprite : staticSprites) {
				if (staticSprite.getShowImage()) {
					if (staticSprite.getImage() != null) {
						g.drawImage(staticSprite.getImage(), translateX(staticSprite.getMinX()), translateY(staticSprite.getMinY()), scaleX(staticSprite.getWidth()), scaleY(staticSprite.getHeight()), null);
					}
					else {
						g.setColor(Color.RED);
						g.fillRect(translateX(staticSprite.getMinX()), translateY(staticSprite.getMinY()),scaleX(staticSprite.getWidth()), scaleY(staticSprite.getHeight()));					
					}
				}
			}

			for (ActiveSprite activeSprite : activeSprites) {
				if (activeSprite.getImage() != null) {
					g.drawImage(activeSprite.getImage(), translateX(activeSprite.getMinX()), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()), null);
				}
				else {
					g.setColor(Color.BLUE);
					g.fillRect(translateX(scale * (activeSprite.getMinX())), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()));					
				}
			}

		}
		
		private int translateX(double x) {
			return xpCenter + scaleX(x - xCenter);
		}
		
		private int scaleX(double x) {
			return (int) Math.round(scale * x);
		}
		private int translateY(double y) {
			return ypCenter + scaleY(y - yCenter);
		}		
		private int scaleY(double y) {
			return (int) Math.round(scale * y);
		}

		private void paintBackground(Graphics g, Background background) {

			if ((g == null) || (background == null)) {
				return;
			}
			
			//what tile covers the top-left corner?
			double xTopLeft = ( xCenter - (xpCenter / scale));
			double yTopLeft =  (yCenter - (ypCenter / scale)) ;
			
			int row = background.getRow((int)yTopLeft);
			int col = background.getCol((int)xTopLeft);
			Tile tile = null;

			boolean rowDrawn = false;
			boolean screenDrawn = false;
			while (screenDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					if (tile.getWidth() <= 0 || tile.getHeight() <= 0) {
						//no increase in width; will cause an infinite loop, so consider this screen to be done
						g.setColor(Color.GRAY);
						g.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);					
						rowDrawn = true;
						screenDrawn = true;						
					}
					else {
						Tile nextTile = background.getTile(col+1, row+1);
						int width = translateX(nextTile.getMinX()) - translateX(tile.getMinX());
						int height = translateY(nextTile.getMinY()) - translateY(tile.getMinY());
						g.drawImage(tile.getImage(), translateX(tile.getMinX()), translateY(tile.getMinY()), width, height, null);
					}					
					//does the RHE of this tile extend past the RHE of the visible area?
					if (translateX(tile.getMinX() + tile.getWidth()) > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				if (translateY(tile.getMinY() + tile.getHeight()) > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					screenDrawn = true;
				}
				else {
					//TODO - should be passing in a double, as this represents a universe coordinate
					col = background.getCol((int)xTopLeft);
					row++;
					rowDrawn = false;
				}
			}
		}				
	}
	protected void this_windowClosing(WindowEvent e) {
		System.out.println("windowClosing()");
		stop = true;
		dispose();	
	}
}
