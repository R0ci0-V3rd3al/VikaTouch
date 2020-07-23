package vikaTouch.canvas;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import vikaTouch.newbase.CaptchaObject;
import vikaTouch.newbase.ColorUtils;
import vikaTouch.newbase.DisplayUtils;
import vikaTouch.newbase.TextEditor;

public class CaptchaCanvas
	extends Canvas
{
	
	public Image image;
	private Thread thread;
	public static String input;
	public static boolean finished;
	public CaptchaObject obj;
	private int x;
	private int w;

	protected void paint(Graphics g)
	{
		DisplayUtils.checkdisplay(this);
		if(obj != null && image == null)
		{
			image = obj.getImage();
		}
		w = image.getWidth();
		ColorUtils.setcolor(g, -2);
		g.drawRect(0, 100, 240, 40);
		ColorUtils.setcolor(g, 2);
		if(input != null)
			g.drawString(input, 10, 110, 0);
		x = (DisplayUtils.width - w) / 2;
		ColorUtils.setcolor(g, -5);
		g.drawString("Требуется ввод капчи!", DisplayUtils.width / 2, 0, Graphics.HCENTER);
		g.drawImage(image, x, 24, 0);
		ColorUtils.setcolor(g, 3);
		g.fillRect(x, 150, w, 36);
	}
	
	public final void pointerPressed(int x, int y)
	{
		if(y > 100 && y < 140 && x < 240)
		{
			if(thread != null)
				thread.interrupt();
			thread = new Thread()
			{
				public void run()
				{
					input = TextEditor.inputString("Капча", "", 32, true);
					repaint();
					interrupt();
				}
			};
			thread.start();
		}
	}
	
	public final void pointerReleased(int x, int y)
	{
		if(x > this.x && y > 150 && y < 186 && x < this.x + this.w)
		{
			finished = true;
		}
	}

}
