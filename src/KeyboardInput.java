import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {	
	public boolean UP			= false;
	public boolean DOWN			= false;
	public boolean LEFT			= false;
	public boolean RIGHT		= false;
	public boolean SPACE		= false;
	public boolean CONTROL		= false;
	public boolean Z			= false;
	public boolean ESCAPE		= false;
	public boolean DEMON		= false;
	public boolean GARGOYLE		= false;
	public boolean VEGETARIAN	= false;
	public boolean BERSERKER	= false;
	public boolean BILLY_SP		= false;
	public boolean ZOMBIE_SP	= false;
	public boolean CHECKPOINT	= false;
	public boolean ENDMAP	= false;
	public boolean TILEWALL	= false;

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		int intKeyCode = e.getKeyCode();
		if (intKeyCode == KeyEvent.VK_W)		UP			= true;
		if (intKeyCode == KeyEvent.VK_S)		DOWN		= true;
		if (intKeyCode == KeyEvent.VK_A)		LEFT		= true;
		if (intKeyCode == KeyEvent.VK_D)		RIGHT		= true;
		if (intKeyCode == KeyEvent.VK_SPACE)	SPACE		= true;
		if (intKeyCode == KeyEvent.VK_CONTROL)	CONTROL		= true;
		if (intKeyCode == KeyEvent.VK_Z)		Z			= true;
		if (intKeyCode == KeyEvent.VK_ESCAPE)	ESCAPE		= true;
		if (intKeyCode == KeyEvent.VK_1)		BILLY_SP	= true;
		if (intKeyCode == KeyEvent.VK_2)		ZOMBIE_SP	= true;
		if (intKeyCode == KeyEvent.VK_3)		CHECKPOINT	= true;
		if (intKeyCode == KeyEvent.VK_4)		DEMON		= true;
		if (intKeyCode == KeyEvent.VK_5)		GARGOYLE	= true;
		if (intKeyCode == KeyEvent.VK_6)		VEGETARIAN	= true;
		if (intKeyCode == KeyEvent.VK_7)		BERSERKER	= true;
		if (intKeyCode == KeyEvent.VK_8)		TILEWALL	= true;
		if (intKeyCode == KeyEvent.VK_9)		ENDMAP	= true;
	}

	public void keyReleased(KeyEvent e) {
		int intKeyCode = e.getKeyCode();
		if (intKeyCode == KeyEvent.VK_W)		UP			= false;
		if (intKeyCode == KeyEvent.VK_S)		DOWN		= false;
		if (intKeyCode == KeyEvent.VK_A)		LEFT		= false;
		if (intKeyCode == KeyEvent.VK_D)		RIGHT		= false;
		if (intKeyCode == KeyEvent.VK_SPACE)	SPACE		= false;
		if (intKeyCode == KeyEvent.VK_CONTROL)	CONTROL		= false;
		if (intKeyCode == KeyEvent.VK_Z)		Z			= false;
		if (intKeyCode == KeyEvent.VK_ESCAPE)	ESCAPE		= false;
		if (intKeyCode == KeyEvent.VK_1)		BILLY_SP	= false;
		if (intKeyCode == KeyEvent.VK_2)		ZOMBIE_SP	= false;
		if (intKeyCode == KeyEvent.VK_3)		CHECKPOINT	= false;
		if (intKeyCode == KeyEvent.VK_4)		DEMON		= false;
		if (intKeyCode == KeyEvent.VK_5)		GARGOYLE	= false;
		if (intKeyCode == KeyEvent.VK_6)		VEGETARIAN	= false;
		if (intKeyCode == KeyEvent.VK_7)		BERSERKER	= false;
		if (intKeyCode == KeyEvent.VK_8)		TILEWALL	= false;
		if (intKeyCode == KeyEvent.VK_9)		ENDMAP	= false;
	}

}
