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
	private BufferedImage demon;
	private BufferedImage berserker;
	private BufferedImage gargoyle;
	private BufferedImage vegetarian;
	private Graphics2D dbg;
	public static TileMap map;
	private KeyboardInput keyboardInput	= new KeyboardInput();
	private MouseInput mouseInput = new MouseInput();
	private long diffTime, previousTime;
	private ArrayList<Item> itens = new ArrayList<Item>();
	private int itemId = 0;
	private boolean showSaveDialog = false;
	private boolean showSelectedItem = false;
	private String strSelected;
	private int counter = 0;
	public static GamePanel instance;

	public GamePanel() {
		instance = this;
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
		
		demon = loadImage("demon.png");
		gargoyle = loadImage("gargoyle.png");
		vegetarian = loadImage("vegetarian.png");
		berserker = loadImage("berserker.png");
	}
	
	public static BufferedImage loadImage(String source) {
		BufferedImage image = null;
		try {
			BufferedImage tmp = ImageIO.read(GamePanel.instance.getClass().getResource(source));
			image = new BufferedImage(tmp.getWidth(),tmp.getHeight(),BufferedImage.TYPE_INT_ARGB);
			image.getGraphics().drawImage(tmp,0,0,null);
			tmp = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
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
			map.OpenMap(f);
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

	public void run() {
		running = true;
	
		int segundo = 0;
		diffTime = 0;
		previousTime = System.currentTimeMillis();
	
		while(running || showSaveDialog) {
			update();
			render();
			paintImmediately(0, 0, PWIDTH, PHEIGHT);
			
			try {
				Thread.sleep(5);
			} catch(InterruptedException ex) {}
		
			diffTime = System.currentTimeMillis() - previousTime;
			previousTime = System.currentTimeMillis();
		
			if(segundo!=((int)(previousTime/1000))) {
				segundo = ((int)(previousTime/1000));
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
		
		if(keyboardInput.BILLY_SP) {
			itemId = 1;
			strSelected = "Billy Spawn Point";
			showSelectedItem = true;
		}
		if(keyboardInput.ZOMBIE_SP) {
			itemId = 2;
			strSelected = "Zombie Spawn Point";
			showSelectedItem = true;
		}
		if(keyboardInput.CHECKPOINT) {
			itemId = 3;
			strSelected = "Checkpoint";
			showSelectedItem = true;
		}
		if(keyboardInput.DEMON) {
			itemId = 4;
			strSelected = "Demon";
			showSelectedItem = true;
		}
		if(keyboardInput.GARGOYLE) {
			itemId = 5;
			strSelected = "Gargoyle";
			showSelectedItem = true;
		}
		if(keyboardInput.VEGETARIAN) {
			itemId = 6;
			strSelected = "Vegetarian";
			showSelectedItem = true;
		}
		if(keyboardInput.BERSERKER) {
			itemId = 7;
			strSelected = "Berserker";
			showSelectedItem = true;
		}
		
		if(showSelectedItem) {
			counter += diffTime;
			if(counter >= 5000) {
				showSelectedItem = false;
				counter = 0;
			}
		}
		
		if(keyboardInput.ESCAPE) {
			showSaveDialog = true;
			keyboardInput.ESCAPE = false;
		}
		
		if(mouseInput.clicked) {
			mouseInput.clicked = false;
			if(itemId != 0) {
				itens.add(new Item(itemId, mouseInput.blockClickX, mouseInput.blockClickY));
			}
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
	
	private void render() {
		map.selfDraws(dbg);
		
		if(showSelectedItem) {
			dbg.setColor(Color.BLACK);
			dbg.fillRect(10, 10, PWIDTH-20, 20);
			dbg.setColor(Color.WHITE);
			dbg.drawString("Selected:     "+strSelected, 30, 24);
		}
		
		for(int i = 0; i < itens.size(); i++) {
			switch (itens.get(i).id) {
			case 1:
				dbg.setColor(Color.BLUE);
				dbg.fillRect(itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, 70, 85);
				break;
			case 2:
				dbg.setColor(Color.RED);
				dbg.fillRect(itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, 70, 85);
				break;
			case 3:
				dbg.setColor(Color.YELLOW);
				dbg.fillRect(itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, 10, 85);
				break;
			case 4:
				dbg.drawImage(demon, itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, null);
				break;
			case 5:
				dbg.drawImage(gargoyle, itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, null);
				break;
			case 6:
				dbg.drawImage(vegetarian, itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, null);
				break;
			case 7:
				dbg.drawImage(berserker, itens.get(i).x*16-map.MapX, itens.get(i).y*16-map.MapY, null);
				break;
			}
		}
		
		dbg.setColor(Color.WHITE);
		if(itemId == 0) {
			dbg.drawRect(mouseInput.blockX*16-map.MapX, mouseInput.blockY*16-map.MapY, 16, 16);
		} else if(itemId == 3) {
			dbg.drawRect(mouseInput.blockX*16-map.MapX, mouseInput.blockY*16-map.MapY, 10, 85);
		} else {
			dbg.drawRect(mouseInput.blockX*16-map.MapX, mouseInput.blockY*16-map.MapY, 70, 85);
		}
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

