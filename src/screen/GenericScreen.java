package screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public interface GenericScreen {

	public void first();

	public void tick();

	public void render(Graphics2D g);

	public void handleKeyPress(KeyEvent keyEvent);

	public void handleKeyRelease(KeyEvent keyEvent);

	public boolean isDoneRendering();
}