package ru.nnproject.vikaui.popup;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import ru.nnproject.vikaui.menu.items.PressableUIItem;
import ru.nnproject.vikaui.screen.ScrollableCanvas;
import ru.nnproject.vikaui.utils.ColorUtils;
import ru.nnproject.vikaui.utils.DisplayUtils;
import vikatouch.base.VikaTouch;
import vikatouch.base.items.OptionItem;

public class ContextMenu extends VikaNotice {

	public OptionItem[] items;
	public int selected;
	
	public ContextMenu(OptionItem[] list) 
	{
		items = list;
	}
	
	public void draw(Graphics g) {
		int itemsH = 16; // margin = 8
		int width = Math.min(DisplayUtils.width-8, 350);
		int x = DisplayUtils.width/2 - width/2;
		for(int i=0; i < items.length; i++)
		{
			items[i].drawX = x+8;
			items[i].fillW = width-16;
			itemsH = itemsH + items[i].getDrawHeight();
		}
		
		int th = itemsH;
		int y = DisplayUtils.height/2 - th/2;
		
		// BG
		ColorUtils.setcolor(g, ColorUtils.BACKGROUND);
		g.fillRoundRect(x, y, width, th, 16, 16);
		// border
		g.setStrokeStyle(Graphics.SOLID);
		ColorUtils.setcolor(g, ColorUtils.TEXT);
		g.drawRoundRect(x, y, width, th, 16, 16);
		
		int cy = 8;
		for(int i=0; i < items.length; i++)
		{
			items[i].paint(g, cy, 0);
			cy = cy + items[i].getDrawHeight();
		}
	}
	
	public void key(int key)
	{
		ScrollableCanvas.keysMode = true;
		if(key == PressableUIItem.KEY_OK)
		{
			VikaTouch.canvas.currentAlert = null;
			items[selected].keyPressed(PressableUIItem.KEY_OK);
		}
		else if(key == -1)
		{
			selected--; 
			if(selected<0) selected = items.length-1;
		}
		else if(key == -2)
		{
			selected++;
			if(selected>=items.length) selected = 0;
		}
		else if(key == PressableUIItem.KEY_RFUNC)
		{
			VikaTouch.canvas.currentAlert = null;
		}
	}
	
	public void release(int x, int y)
	{
		int margin = 8;
		int itemsH = margin * 2; // margin = 8
		int width = Math.min(DisplayUtils.width-8, 350);
		int rx = DisplayUtils.width/2 - width/2;
		for(int i=0; i < items.length; i++)
		{
			items[i].drawX = x+margin;
			items[i].fillW = width-margin * 2;
			itemsH = itemsH + items[i].getDrawHeight();
		}
		
		int th = itemsH;
		int ry = DisplayUtils.height/2 - th/2;
		
		if(x < rx || x > rx + width || y < ry || y > ry + th)
		{
			VikaTouch.canvas.currentAlert = null;
		}
		
		int tapY = y - ry;
		int currY = margin;
		for(int i=0; i < items.length; i++) 
		{
			int h = items[i].getDrawHeight();
			if(tapY>currY&&tapY<currY+h)
			{
				VikaTouch.canvas.currentAlert = null;
				items[i].tap(x - rx, tapY - currY);
				return;
			}
			currY = currY + h;
		}
	}

}
