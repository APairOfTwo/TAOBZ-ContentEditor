
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



//------------------------------------------------------------------------------
/**
 * KeyboardInput reads information from the keyboard. Surprise!
 **/
//------------------------------------------------------------------------------
public class KeyboardInput implements KeyListener {	
	public			boolean 		UP					= false;
	public 			boolean 		DOWN				= false;
	public 			boolean 		LEFT				= false;
	public 			boolean 		RIGHT				= false;
	public 			boolean 		SPACE   			= false;
	public 			boolean 		ENTER   			= false;
	public 			boolean 		ESCAPE				= false;

	public 			boolean 		TEXT_ENTERED		= false;
	private			StringBuffer	text				= new StringBuffer();



	// -------------------------------------------------------------------------
	// KeyListener Interface Methods
	// -------------------------------------------------------------------------

	public void keyTyped(KeyEvent e) {
		char chrKey = e.getKeyChar();
		if (!Character.isISOControl(chrKey)) {
			text.append(chrKey);
		}
	}

	public void keyPressed(KeyEvent e) {
		int intKeyCode = e.getKeyCode();
		if (intKeyCode == KeyEvent.VK_UP)		UP		= true;
		if (intKeyCode == KeyEvent.VK_DOWN)		DOWN	= true;
		if (intKeyCode == KeyEvent.VK_LEFT)		LEFT	= true;
		if (intKeyCode == KeyEvent.VK_RIGHT)	RIGHT	= true;
		if (intKeyCode == KeyEvent.VK_SPACE)	SPACE	= true;
		if (intKeyCode == KeyEvent.VK_ENTER)	ENTER	= true;
		if (intKeyCode == KeyEvent.VK_ESCAPE)	ESCAPE	= true;
	}

	public void keyReleased(KeyEvent e) {
		int intKeyCode = e.getKeyCode();
		if (intKeyCode == KeyEvent.VK_UP)		UP		= false;
		if (intKeyCode == KeyEvent.VK_DOWN)		DOWN	= false;
		if (intKeyCode == KeyEvent.VK_LEFT)		LEFT	= false;
		if (intKeyCode == KeyEvent.VK_RIGHT)	RIGHT	= false;
		if (intKeyCode == KeyEvent.VK_SPACE)	SPACE	= false;
		if (intKeyCode == KeyEvent.VK_ENTER)	ENTER	= false;
		if (intKeyCode == KeyEvent.VK_ESCAPE)	ESCAPE	= false;

		if (intKeyCode == KeyEvent.VK_ENTER)	TEXT_ENTERED = true;
		if (intKeyCode == KeyEvent.VK_BACK_SPACE) {
			text.deleteCharAt(text.length() - 1);
		}
	}

	
	
	// -------------------------------------------------------------------------
	// Public Methods
	// -------------------------------------------------------------------------

	public void resetFlags() {
		UP		= false;
		DOWN	= false;
		LEFT	= false;
		RIGHT	= false;
		SPACE	= false;
		ENTER	= false;
		ESCAPE	= false;
	}

	public void resetText() {
		TEXT_ENTERED = false;
		text = new StringBuffer();
	}

	public String getText() {
		return text.toString();
	}

	public void setText(String strText) {
		text = new StringBuffer(strText);
	}
}
