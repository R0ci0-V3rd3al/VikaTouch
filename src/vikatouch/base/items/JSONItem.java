package vikatouch.base.items;

import org.json.me.JSONArray;
import org.json.me.JSONObject;

import vikamobilebase.VikaUtils;
import vikatouch.base.attachments.Attachment;
import vikatouch.base.json.JSONBase;

public class JSONItem
	extends JSONBase
{
	public JSONItem(JSONObject json)
	{
		this.json = json;
	}
	
	public Attachment[] attachments = new Attachment[5];

	public int fromid;
	public String text;
	public long date;
	
	public void parseJSON()
	{
		text = fixJSONString(json.optString("text"));
		fromid = json.optInt("from_id");
		date = json.optLong("date");
	}
	
	protected void parseAttachments()
	{
		try
		{
			if(!json.isNull("attachments"))
			{
				final JSONArray attachments = json.getJSONArray("attachments");
				if(this.attachments.length > attachments.length())
				{
					this.attachments = new Attachment[attachments.length()];
				}
				for(int i = 0; i < attachments.length(); i++)
				{
					if(i >= this.attachments.length)
					{
						break;
					}
					this.attachments[i] = Attachment.parse(attachments.getJSONObject(i));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getTime()
	{
		return VikaUtils.parseTime(date);
	}
}
