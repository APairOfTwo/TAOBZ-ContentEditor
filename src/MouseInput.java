import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInput implements MouseListener, MouseMotionListener {
	public int blockX, blockY, blockClickX, blockClickY;
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		blockClickX = (e.getX() + GamePanel.map.MapX) / 16;
		blockClickY = (e.getY() + GamePanel.map.MapY) / 16;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		blockX = (e.getX() + GamePanel.map.MapX) / 16;
		blockY = (e.getY() + GamePanel.map.MapY) / 16;
	}

}
