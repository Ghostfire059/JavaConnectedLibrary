package ghostfire059.java.connectedLibrary;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * API used to gather informations of an ISBN from the web
 * @author tberasateguy
 *
 */
public class SearchHTML implements Search
{
	private static SearchHTML _me = null;
	
	private String _url = "http://chasse-aux-livres.fr/prix/";
	private String _actualTitle;
	private Collection<String> _actualAuthors = new ArrayList<String>();
	private String _actualEditor;
	private String _actualType;
	
	private Long _actualISBN = 0L;
	
	private SearchHTML(){}
	
	/**
	 * Singleton design pattern
	 * Used to instanciate only one instance of the object
	 * @return the only one instance of SearchHTML
	 */
	public static SearchHTML getInstance()
	{
		if(_me==null)
		{
			_me = new SearchHTML();
		}
		return _me;
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
		this._actualISBN = isbn;
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try
		{
			String searchUrl = this._url+URLEncoder.encode(String.valueOf(isbn), "UTF-8");
			HtmlPage page = client.getPage(searchUrl);
			
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
			
			HtmlElement editor = page.getFirstByXPath("//a[@class='editor-link']"); 
			this._actualEditor = editor.getTextContent();
			
			HtmlElement type = page.getFirstByXPath("//div[@class='col pr-1']");
			this._actualType = type.getTextContent();
			
		}
		catch(FailingHttpStatusCodeException | IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			client.close();
		}
		return false;
	}

	@Override
	public String searchEditor(long isbn)
	{
		if(isbn!=this._actualISBN)
		{			
			this._search(isbn);
		}
		return this._actualEditor;
	}

	@Override
	public String searchType(long isbn)
	{
		if(isbn!=this._actualISBN)
		{			
			this._search(isbn);
		}
		return this._actualType;
	}

}
