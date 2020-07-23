package vikaTouch.newbase;

import javax.microedition.lcdui.Graphics;

public class ColorUtils {
	
	public final static int[] color = {80, 118, 167};
	public final static int[] textcolor = {193, 206, 224};
	public final static int[] buttoncolor = {81, 129, 184};
	public final static int[] buttoncolorhover = {102, 144, 192};
	public final static int[] onlinecolor = {74, 178, 78};
	public static boolean isTemnaya = false;
	
	public static void setcolor(final Graphics g, final int i)
	{
		switch(i)
		{
			case 0:
			{
				g.setColor(0);
				break;
			}
			case 1:
			{
				g.setColor(color[0], color[1], color[2]);
				break;
			}
			case 2:
			{
				g.setColor(textcolor[0], textcolor[1], textcolor[2]);
				break;
			}
			case 3:
			{
				if(isTemnaya)
					g.setGrayScale(30);
				else
					g.setColor(buttoncolor[0], buttoncolor[1], buttoncolor[2]);
				break;
			}
			case 4:
			{
				g.setColor(onlinecolor[0], onlinecolor[1], onlinecolor[2]);
				break;
			}
			case 5:
			{
				if(isTemnaya)
					g.setGrayScale(225);
				else
					g.setColor(0);
				break;
			}
			case -1:
			{
				if(isTemnaya)
					g.setColor(0);
				else
					g.setGrayScale(255);
				break;
			}
			case -2:
			{
				if(isTemnaya)
					g.setGrayScale(35);
				else
					g.setGrayScale(238);
				break;
			}
			case -3:
			{
				if(isTemnaya)
					g.setGrayScale(6);
				else
					g.setGrayScale(249);
				break;
			}
			case -4:
			{
				if(isTemnaya)
					g.setGrayScale(9);
				else
					g.setColor(0xCFD6DE);
				break;
			}
			case -5:
			{
				if(isTemnaya)
					g.setGrayScale(3);
				else
					g.setGrayScale(231);
				break;
			}
			case -6:
			{
				if(isTemnaya)
					g.setGrayScale(2);
				else
					g.setGrayScale(243);
				break;
			}
			case -7:
			{
				if(isTemnaya)
					g.setGrayScale(17);
				else
					g.setGrayScale(234);
				break;
			}
			case -8:
			{
				if(isTemnaya)
					g.setGrayScale(2);
				else
					g.setGrayScale(237);
				break;
			}
			case -9:
			{
				if(isTemnaya)
					g.setGrayScale(19);
				else
					g.setGrayScale(247);
				break;
			}
			case -10:
			{
				if(isTemnaya)
					g.setGrayScale(32);
				else
					g.setGrayScale(222);
				break;
			}
			case -11:
			{
				if(isTemnaya)
					g.setGrayScale(25);
				else
					g.setGrayScale(229);
				break;
			}
			case -12:
			{
				if(isTemnaya)
					g.setGrayScale(15);
				else
					g.setGrayScale(241);
				break;
			}
			default:
			{
				//g.setGrayScale(255);
				break;
			}
		}
		return;
	}

}
