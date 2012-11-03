import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {	
	public boolean UP			= false;
	public boolean DOWN			= false;
	public boolean LEFT			= false;
	public boolean RIGHT		= false;
	public boolean SPACE		= false;
	public boolean ENTER		= false;
	public boolean ESCAPE		= false;
	public boolean DEMON		= false;
	public boolean GARGOYLE		= false;
	public boolean VEGETARIAN	= false;
	public boolean BERSERKER	= false;

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		int intKeyCode = e.getKeyCode();
		if (intKeyCode == KeyEvent.VK_UP)		UP			= true;
		if (intKeyCode == KeyEvent.VK_DOWN)		DOWN		= true;
		if (intKeyCode == KeyEvent.VK_LEFT)		LEFT		= true;
		if (intKeyCode == KeyEvent.VK_RIGHT)	RIGHT		= true;
		if (intKeyCode == KeyEvent.VK_SPACE)	SPACE		= true;
		if (intKeyCode == KeyEvent.VK_ENTER)	ENTER		= true;
		if (intKeyCode == KeyEvent.VK_ESCAPE)	ESCAPE		= true;
		if (intKeyCode == KeyEvent.VK_1)		DEMON		= true;
		if (intKeyCode == KeyEvent.VK_2)		GARGOYLE	= true;
		if (intKeyCode == KeyEvent.VK_3)		VEGETARIAN	= true;
		if (intKeyCode == KeyEvent.VK_4)		BERSERKER	= true;
	}

	public void keyReleased(KeyEvent e) {
		int intKeyCode = e.getKeyCode();
		if (intKeyCode == KeyEvent.VK_UP)		UP			= false;
		if (intKeyCode == KeyEvent.VK_DOWN)		DOWN		= false;
		if (intKeyCode == KeyEvent.VK_LEFT)		LEFT		= false;
		if (intKeyCode == KeyEvent.VK_RIGHT)	RIGHT		= false;
		if (intKeyCode == KeyEvent.VK_SPACE)	SPACE		= false;
		if (intKeyCode == KeyEvent.VK_ENTER)	ENTER		= false;
		if (intKeyCode == KeyEvent.VK_ESCAPE)	ESCAPE		= false;
		if (intKeyCode == KeyEvent.VK_1)		DEMON		= false;
		if (intKeyCode == KeyEvent.VK_2)		GARGOYLE	= false;
		if (intKeyCode == KeyEvent.VK_3)		VEGETARIAN	= false;
		if (intKeyCode == KeyEvent.VK_4)		BERSERKER	= false;
	}

	public void resetFlags() {
		UP			= false;
		DOWN		= false;
		LEFT		= false;
		RIGHT		= false;
		SPACE		= false;
		ENTER		= false;
		ESCAPE		= false;
		DEMON		= false;
		GARGOYLE	= false;
		VEGETARIAN	= false;
		BERSERKER	= false;
	}
}
