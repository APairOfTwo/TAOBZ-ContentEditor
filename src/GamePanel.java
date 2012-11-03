import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
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
	public static TileMap map;
	private KeyboardInput keyboardInput	= new KeyboardInput();
	private MouseInput mouseInput = new MouseInput();
	private long diffTime, previousTime;
	private int fps, sFps;
	private int fpscount;
	private ArrayList<Item> itens = new ArrayList<Item>();
	private int itemId;
	private boolean showSaveDialog = false;

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
	
	public void saveCsv() {
		int op = JOptionPane.showConfirmDialog(null, "Deseja salvar?");
		
		switch(op) {
		case JOptionPane.YES_OPTION:
			String fileName = JOptionPane.showInputDialog("Digite o nome do arquivo a ser salvo:");
			try {
				FileWriter fstream = new FileWriter(fileName + ".csv");
				BufferedWriter out = new BufferedWriter(fstream); 
				for(Item item: itens) {
					out.write(Integer.toString(item.id)+";"+Integer.toString(item.x)+";"+Integer.toString(item.y)+"\n");
				}
				out.close();
			} catch (Exception e) {
				System.err.println("Erro: " + e.getMessage());
			}
			running = false;
			showSaveDialog = false;
			break;

		case JOptionPane.NO_OPTION:
			running = false;
			showSaveDialog = false;
			break;
			
		default:
			showSaveDialog = false;
			break;
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
	
		while(running || showSaveDialog) {
			update();
			render();
			paintImmediately(0, 0, PWIDTH, PHEIGHT); // paint with the buffer
			
			try {
				Thread.sleep(10);
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
			
			if(showSaveDialog) {
				saveCsv();
			}
		}
		System.exit(0);
	}

	int timerfps = 0;

	private void update() {
		scrollMap();
		
		if(keyboardInput.SPACE) {
			itemId = 1;
		} else {
			if(keyboardInput.ENTER) {
				itemId = 2;
			} else {
				if(keyboardInput.ESCAPE) {
					showSaveDialog = true;
					keyboardInput.ESCAPE = false;
				}
			}
		}
		
		if(mouseInput.clicked) {
			mouseInput.clicked = false;
			if(itemId != 0) {
				itens.add(new Item(itemId, mouseInput.blockClickX, mouseInput.blockClickY));
			}
		}
		//System.out.println(mouseInput.blockX);
		//System.out.println(mouseInput.blockClickX+","+mouseInput.blockClickY);
		//System.out.println(itemId);
		System.out.println(itens.size());
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
	
	private void render() {
		map.selfDraws(dbg);
		dbg.drawRect(mouseInput.blockX*16-map.MapX, mouseInput.blockY*16-map.MapY, 16, 16);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}

	public static void main(String args[]) {
		GamePanel ttPanel = new GamePanel();
		ttPanel.addKeyListener(ttPanel.keyboardInput);
		ttPanel.addMouseListener(ttPanel.mouseInput);
		ttPanel.addMouseMotionListener(ttPanel.mouseInput);
		
		JFrame app = new JFrame("TAOBZ - ContentEditor");
		app.getContentPane().add(ttPanel, BorderLayout.CENTER);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.pack();
		app.setResizable(true);  
		app.setVisible(true);
	}

}

