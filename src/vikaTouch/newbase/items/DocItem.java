package vikaTouch.newbase.items;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import vikaTouch.VikaTouch;
import vikaTouch.base.VikaUtils;
import vikaTouch.newbase.JSONBase;
import vikaTouch.newbase.attachments.PhotoSize;
import vikaUI.ColorUtils;
import vikaUI.DisplayUtils;
import vikaUI.ScrollableCanvas;

public class DocItem
	extends JSONUIItem
{
	private String name;
	private String url;
	private String iconUrl;
	private String prevImgUrl;
	private int size;
	private PhotoSize[] prevSizes;
	private Image iconImg;
	private int documentType;
	private String ext;
	private int type;
	private String time;
	
	private static Image downloadBI = null;

	public static final int BORDER = 1;

	//типы вложения
	private static final int TYPE_TEXT = 1;
	private static final int TYPE_ARCHIVE = 2;
	private static final int TYPE_GIF = 3;
	private static final int TYPE_PHOTO = 4;
	private static final int TYPE_AUDIO = 5;
	private static final int TYPE_VIDEO = 6;
	private static final int TYPE_EBOOK = 7;
	private static final int TYPE_UNKNOWN = 8;
	private static final int TYPE_UNDEFINED = 0;

	public DocItem(JSONObject json)
	{
		super(json);
		itemDrawHeight = 50;
	}

	public void parseJSON()
	{
		System.out.println(json.toString());

		try
		{
			date = json.optInt("date");
			name = json.optString("title");
			url = fixJSONString(json.optString("url"));
			size = json.optInt("size");
			ext = json.optString("ext");
			type = json.optInt("type");

			if(!json.isNull("preview"))
			{
				prevSizes = PhotoSize.parseSizes(json.getJSONObject("preview").getJSONObject("photo").getJSONArray("sizes"));

				PhotoSize iconPs = null;
				PhotoSize prevPs = null;

				try
				{
					iconPs = PhotoSize.getSize(prevSizes, "s");
				}
				catch (Exception e)
				{
					try
					{
						iconPs = PhotoSize.getSize(prevSizes, "d");
					}
					catch (Exception e3)
					{
						e3.printStackTrace();
					}
				}
				try
				{
					prevPs = PhotoSize.getSize(prevSizes, "x");
				}
				catch (Exception e1)
				{
					try
					{
						prevPs = PhotoSize.getSize(prevSizes, "o");
					}
					catch (Exception e2)
					{
						//не достучались до превьюхи..
					}
				}

				if(iconPs != null)
				{
					iconUrl = fixJSONString(iconPs.url);
				}

				if(prevPs != null)
				{
					prevImgUrl = fixJSONString(prevPs.url);
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			//Предпросмотр не завезли - видимо не картинка. Ну и ладно.
		}
		catch (Exception e)
		{
			e.printStackTrace();
			VikaTouch.error(e, "Обработка объектов: Документ");
		}

		setDrawHeight();

		System.gc();
	}

	private void setDrawHeight()
	{
		itemDrawHeight = 48 + (BORDER * 2);
	}

	public void paint(Graphics g, int y, int scrolled)
	{
		if(ScrollableCanvas.keysMode && selected)
		{
			ColorUtils.setcolor(g, ColorUtils.BUTTONCOLOR);
			g.fillRect(0, y, DisplayUtils.width, itemDrawHeight);
		}
		
		if(iconImg == null)
			iconImg = getPreviewImage();

		if(time == null)
			time = getTime();
		ColorUtils.setcolor(g, 0);
		if(name != null)
			g.drawString(name, 73, y, 0);
		ColorUtils.setcolor(g, ColorUtils.OUTLINE);
		g.drawString(size / 1000 + "кб, " + time, 73, y + 24, 0);
		if(iconImg != null)
		{
			g.drawImage(iconImg, 14, y + BORDER, 0);
		}
		if(!ScrollableCanvas.keysMode)
		{
			try
			{
				if(downloadBI == null)
				{
					downloadBI = Image.createImage("/downloadBtn.png");
				}
				g.drawImage(downloadBI, DisplayUtils.width-downloadBI.getWidth(), y, 0);
			} 
			catch (Exception e)
			{
				
			}
		}
	}

	private Image getPreviewImage()
	{
		Image img = null;
		try
		{
			System.out.println(iconUrl);
			img = DisplayUtils.resizeItemPreview(VikaUtils.downloadImage(iconUrl));
		}
		catch (Exception e)
		{
			try
			{
				switch(type)
				{
					case TYPE_PHOTO:
					case TYPE_GIF:
						return DisplayUtils.resizeItemPreview(VikaTouch.cameraImg);
					case TYPE_AUDIO:
						return DisplayUtils.resizeItemPreview(Image.createImage("/docmus.png"));
					case TYPE_VIDEO:
						return DisplayUtils.resizeItemPreview(Image.createImage("/docvid.png"));
					case TYPE_ARCHIVE:
						return DisplayUtils.resizeItemPreview(Image.createImage("/doczip.png"));
					case TYPE_TEXT:
					case TYPE_EBOOK:
						return DisplayUtils.resizeItemPreview(Image.createImage("/doctxt.png"));
					case TYPE_UNKNOWN:
					case TYPE_UNDEFINED:
					default:
						if(ext.toLowerCase().indexOf("jar") != VikaTouch.INDEX_FALSE || ext.toLowerCase().indexOf("jad") != VikaTouch.INDEX_FALSE)
						{
							return DisplayUtils.resizeItemPreview(Image.createImage("/docjar.png"));
						}
						else if(ext.toLowerCase().indexOf("sis") != VikaTouch.INDEX_FALSE)
						{
							return DisplayUtils.resizeItemPreview(Image.createImage("/docsis.png"));
						}
						else if(ext.toLowerCase().indexOf("rar") != VikaTouch.INDEX_FALSE)
						{
							return DisplayUtils.resizeItemPreview(Image.createImage("/doczip.png"));
						}
						else if(ext.toLowerCase().indexOf("7z") != VikaTouch.INDEX_FALSE)
						{
							return DisplayUtils.resizeItemPreview(Image.createImage("/doczip.png"));
						}
						/*else if(ext.toLowerCase().indexOf("torrent") != VikaTouch.INDEX_FALSE)
						{
							return DisplayUtils.resizeItemPreview(Image.createImage("/doctorr.png"));
						}*/
						else
						{
							return DisplayUtils.resizeItemPreview(Image.createImage("/docfile.png"));
						}
				}
			}
			catch (Exception e2)
			{

			}
		}
		return img;
	}

	public void tap(int x, int y)
	{
		try
		{
			if(x<DisplayUtils.width - 50)
			{
				if(type == TYPE_PHOTO)
				{
					VikaTouch.docsCanv.isPreviewShown = true;
					(new Thread()
					{
						public void run()
						{
							try
							{
								System.out.println("Начато скачивание превью");
								Image img = VikaUtils.downloadImage(prevImgUrl);
								System.out.println("Ресайз превью: исходное "+img.getWidth()+"х"+img.getHeight());
								
								double aspectR = (double)img.getWidth() / (double)img.getHeight();
								int w = 0; int h = 0;
								w = DisplayUtils.width;
								h = (int)(w/aspectR);
								VikaTouch.docsCanv.previewY = (DisplayUtils.height - h)/2;
								VikaTouch.docsCanv.previewImage = VikaUtils.resize(img, w, h);
							}
							catch(Exception e)
							{
								VikaTouch.docsCanv.isPreviewShown = false;
								VikaTouch.error(e, "Скачивание превью");
							}
						}
					}).start();
				}
			}
			else
			{
				VikaTouch.inst.platformRequest(url);
			}
		}
		catch (Exception e)
		{

		}
	}
	
	public void keyPressed(int key)
	{
		if(type == TYPE_PHOTO)
		{
			if(key == KEY_FUNC)
			{
				try
				{
					VikaTouch.inst.platformRequest(url);
				}
				catch (ConnectionNotFoundException e) 
				{
					
				}
			}
			if(key == KEY_OK)
			{
				VikaTouch.warn("Временно не функционирует!");
			}
		}
		else
		{
			if(key == KEY_OK)
			{
				try
				{
					VikaTouch.inst.platformRequest(url);
				}
				catch (ConnectionNotFoundException e) 
				{
					
				}
			}
		}
	}
}
