import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	private static final int PWIDTH = 800;
	private static final int PHEIGHT = 608;
	private static final int SCROLL_SPEED = 16;
	private Thread animator;
	private boolean running = false; 
	private BufferedImage dbImage;
	private BufferedImage tileset;
	private Graphics2D dbg;
	private TileMap map;
	private KeyboardInput keyboardInput	= new KeyboardInput();
	private long diffTime, previousTime;
	private int fps, sFps;
	private int fpscount;
	private int mouseX, mouseY;

	public GamePanel() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		setFocusable(true);
		requestFocus();
	
		if (dbImage == null) {
			dbImage = new BufferedImage(PWIDTH, PHEIGHT, BufferedImage.TYPE_INT_ARGB);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				dbg = (Graphics2D)dbImage.getGraphics();
			}
		}
	
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		
			@Override
			public void mouseDragged(MouseEvent e) {}
		});
	
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		
			@Override
			public void mousePressed(MouseEvent arg0) {
				Point p1 = arg0.getPoint();
			}
		
			@Override
			public void mouseExited(MouseEvent arg0) {}
		
			@Override
			public void mouseEntered(MouseEvent arg0) {}
		
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
	
		loadTileset();
		if(tileset == null) System.exit(0);
		
		loadTilemap();
		if(map == null) System.exit(0);
	}
	
	public void loadTileset() {
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new ImageFilter());
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			try {
				tileset = ImageIO.read(f);
			}
			catch(IOException ex) {
				System.out.println("Load Image error: " + ex);
			}
		}
	}
	
	public void loadTilemap() {
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new MapFilter());
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			map = new TileMap(tileset, (PWIDTH>>4)+(((PWIDTH&0x000f)>0)?1:0), (PHEIGHT>>4)+(((PHEIGHT%16)>0)?1:0));
			map.OpenMap(f.getName());
		}
	}
	
	private void scrollMap() {
		if(keyboardInput.LEFT) {
			if(map.MapX > 0) {
				map.MapX -= SCROLL_SPEED;
			}
		}
		if(keyboardInput.RIGHT) {
			if(map.MapX + PWIDTH < map.Largura << 4) {
				map.MapX += SCROLL_SPEED;
			}
		}
		if(keyboardInput.UP) {
			if(map.MapY > 0) {
				map.MapY -= SCROLL_SPEED;
			}
		}
		if(keyboardInput.DOWN) {
			if(map.MapY + PHEIGHT < map.Altura << 4) {
				map.MapY += SCROLL_SPEED;
			}
		}
	}
	
	public void addNotify() {
		super.addNotify();
		startGame();
	}

	private void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void stopGame() {
		running = false;
	}

	public void run() {
		running = true;
	
		int segundo = 0;
		diffTime = 0;
		previousTime = System.currentTimeMillis();
	
		while(running) {
			gameUpdate();
			gameRender();
			paintImmediately(0, 0, PWIDTH, PHEIGHT); // paint with the buffer
			
			try {
				Thread.sleep(2);
			} catch(InterruptedException ex) {}
		
			diffTime = System.currentTimeMillis() - previousTime;
			previousTime = System.currentTimeMillis();
		
			if(segundo!=((int)(previousTime/1000))) {
				fps = sFps;
				sFps = 1;
				segundo = ((int)(previousTime/1000));
			} else {
				sFps++;
			}
		}
		System.exit(0);
	}

	int timerfps = 0;

	private void gameUpdate() {
		scrollMap();
	}
	
	private void gameRender() {
		map.selfDraws(dbg);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}

	public static void main(String args[]) {
		GamePanel ttPanel = new GamePanel();
		ttPanel.addKeyListener(ttPanel.keyboardInput);
		
		JFrame app = new JFrame("TAOBZ-ContentEditor");
		app.getContentPane().add(ttPanel, BorderLayout.CENTER);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.pack();
		app.setResizable(true);  
		app.setVisible(true);
	}

}
