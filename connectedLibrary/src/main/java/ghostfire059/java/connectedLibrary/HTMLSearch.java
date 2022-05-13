package ghostfire059.java.connectedLibrary;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HTMLSearch implements Search
{
	private String _url = "http://chasse-aux-livres.fr/prix/";
	private String _actualTitle;
	private Collection<String> _actualAuthors = new ArrayList<String>();
	private Long _actualISBN = 0L;
	
	private WebClient _client = new WebClient();
	
	public HTMLSearch()
	{
		this._client.getOptions().setCssEnabled(false);
		this._client.getOptions().setJavaScriptEnabled(false);
	}
	
	@Override
	public String searchTitle(long isbn)
	{
		if(isbn!=this._actualISBN)
		{			
			this._search(isbn);
		}
		return this._actualTitle;
	}

	@Override
	public Iterator<String> searchAuthors(long isbn)
	{
		if(isbn!=this._actualISBN)
		{
			this._search(isbn);
		}
		return this._actualAuthors.iterator();
	}
	
	private boolean _search(long isbn)
	{
		try
		{
			String searchUrl = this._url+URLEncoder.encode(String.valueOf(isbn), "UTF-8");
			HtmlPage page = this._client.getPage(searchUrl);
			
			HtmlElement div = page.getHtmlElementById("book-title-and-details");
			DomNodeList<HtmlElement> h1 = div.getElementsByTagName("h1");
			this._actualTitle = h1.get(0).getTextContent();
			
			HtmlElement authors = page.getHtmlElementById("creators");
			String[] splittedAuthors = authors.getTextContent().split(",");
			this._actualAuthors.clear();
			for(String str: splittedAuthors)
			{
				this._actualAuthors.add(str);
			}
		}
		catch(FailingHttpStatusCodeException | IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
