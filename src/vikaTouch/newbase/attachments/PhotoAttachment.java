package vikaTouch.newbase.attachments;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import vikaTouch.base.VikaUtils;

public class PhotoAttachment
	extends ImageAttachment
{
	public int albumid;
	public long ownerid;
	public long userid = 100;
	public int origwidth;
	public int origheight;
	public PhotoSize[] sizes = new PhotoSize[10];

	public PhotoAttachment()
	{
		this.type = "photo";
	}
	
	public void parseJSON()
	{
		sizes = PhotoSize.parseSizes(json.optJSONArray("sizes"));
		origwidth = json.optInt("width");
		origheight = json.optInt("height");
		ownerid = json.optInt("owner_id");
		albumid = json.optInt("album_id");
		userid = json.optInt("user_id");
	}
	
	public Image getImg(int i)
	{
		try
		{
			return VikaUtils.downloadImage(sizes[i].url);
		}
		catch(Exception e)
		{
			try {
				return Image.createImage("/image.png");
			} catch (IOException e1)
			{
				return null;
			}
		}
	}

	public Image getPreviewImage()
	{
		return getImg(0);
	}

	public Image getFullImage()
	{
		return getImg(6);
	}

	public Image getImage(int height)
	{
		return null;
	}
}

