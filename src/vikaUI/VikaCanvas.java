package vikaUI;

import java.io.InputStream;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import vikaTouch.VikaTouch;
import vikaTouch.base.GifDecoder;

public class VikaCanvas
	extends GameCanvas
{
	public static String debugString;
	public VikaScreen currentScreen;
	public boolean showCaptcha;
	private Image frame;
	private GifDecoder d;

	public VikaCanvas()
	{
		super(false);
		this.setFullScreenMode(true);

		try
		{
			final InputStream in = this.getClass().getResourceAsStream("/loading.gif");
			d = new GifDecoder();
			int err = d.read(in);
	        if (err == 0)
	        {
	           frame = d.getImage();
	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g)
	{
		try
		{
			this.updateScreen(g);
		}
		catch (Exception e)
		{
			VikaTouch.error(e, "Прорисовка: " + DisplayUtils.getCanvasString());
		}
	}
	
	public void updateScreen(Graphics g)
	{
		DisplayUtils.checkdisplay();
		ColorUtils.setcolor(g, ColorUtils.BACKGROUND);
		g.fillRect(0, 0, DisplayUtils.width, DisplayUtils.height);
		
		if(currentScreen != null)
		{
			currentScreen.paint(g);
		}
		
		if(showCaptcha)
		{
			VikaTouch.captchaCanv.paint(g);
		}
		
		if(VikaTouch.loading)
		{
			drawLoading(g);
		}
		if(debugString != null)
		{
			g.setColor(0xffff00);
			g.drawString(debugString, 65, 2, 0);
		}
	}
	
	private void drawLoading(Graphics g)
	{
		ColorUtils.setcolor(g, ColorUtils.TEXT);
		g.drawString("Загрузка", DisplayUtils.width / 2, DisplayUtils.height - 80, Graphics.TOP | Graphics.HCENTER);
		
		if(frame != null)
		{
			g.drawImage(frame, DisplayUtils.width / 2, DisplayUtils.height - 128, Graphics.TOP | Graphics.HCENTER);
		}
	}
	
	public void updategif()
	{
        int n = d.getFrameCount();
        for (int i = 0; i < n; i++)
        {
            frame = d.getFrame(i);
            repaint();
            serviceRepaints();
            try
            {
            	Thread.sleep(25);
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            }
        }
    }

	public void pointerPressed(int x, int y)
	{
		if(showCaptcha)
		{
			VikaTouch.captchaCanv.pointerPressed(x, y);
		}
		else if(currentScreen != null)
		{
			currentScreen.pointerPressed(x, y);
		}
	}
	
	public void pointerReleased(int x, int y)
	{
		if(showCaptcha)
		{
			VikaTouch.captchaCanv.pointerReleased(x, y);
		}
		else if(currentScreen != null)
		{
			currentScreen.pointerReleased(x, y);
		}
	}
	
	public void pointerDragged(int x, int y)
	{
		if(currentScreen != null)
		{
			currentScreen.pointerDragged(x, y);
		}
	}
	
	public void keyPressed(int i)
	{
		if(currentScreen != null)
		{
			currentScreen.keyPressed(i);
		}
	}
	
	public void keyRepeated(int i)
	{
		if(currentScreen != null)
		{
			currentScreen.keyRepeated(i);
		}
	}
	
	public void keyReleased(int i)
	{
		if(currentScreen != null)
		{
			currentScreen.keyReleased(i);
		}
	}

	public void paint()
	{
		if(!VikaTouch.inst.isPaused)
		{
			repaint();
			//serviceRepaints();
		}
	}

}
