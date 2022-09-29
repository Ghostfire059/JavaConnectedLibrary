package ghostfire059.java.connectedLibrary;

import java.nio.file.Path;

public class Cover
{
	private Path _path = null;
	private String _url = null;
	
	public Cover(Path path)
	{
		this._path = path;
	}
	
	public Cover(String url)
	{
		this._url = url;
	}
	
	public boolean hasPath()
	{
		return this._path!=null;
	}
	
	public boolean hasUrl()
	{
		return this._url!=null;
	}
	
	public Path getPath()
	{
		return this._path;
	}
	
	public String getUrl()
	{
		return this._url;
	}
	
	public String getExtension()
	{
		if(this.hasUrl())
		{
			return this._url.contains(".jpg") ? ".jpg" : this._url.contains(".png") ? ".png" : null;
		}
		else if(this.hasPath())
		{
			String[] splittedPath = this._path.toString().split(".");
			return splittedPath[splittedPath.length-1];
		}
		else
		{
			return ".jpg";
		}
	}
}
