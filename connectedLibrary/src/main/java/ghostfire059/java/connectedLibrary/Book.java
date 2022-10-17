package ghostfire059.java.connectedLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Representation of a Book
 * @author tberasateguy
 *
 */
public class Book extends LibraryEntityAbstract
{
	/**
	 * The way to create a Book is to give him all the informations of himself
	 * @param isbn
	 * @param title
	 * @param authors
	 * @param editor
	 * @param type
	 */
	public Book(long isbn, String title, Collection<String> authors, String editor, String type)
	{
		super(isbn, title, authors, editor, type);
	}
	
	@Override
	public LibraryEntity clone() throws CloneNotSupportedException
	{
		return (Book)super.clone();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj)
		{
			return true;
		}
		if(obj==null || this.getClass()!=obj.getClass())
		{
			return false;
		}
		
		Book tmpObj = (Book)obj;
		if(this.getTitle()!=tmpObj.getTitle())
		{
			return false;
		}
		
		Map<String, Integer> authorsMap = new HashMap<String, Integer>();
		this.getAuthors().forEachRemaining(author -> authorsMap.put(author, 1));
		tmpObj.getAuthors().forEachRemaining(author ->
		{
			if(authorsMap.containsKey(author))
			{
				authorsMap.replace(author, authorsMap.get(author)+1);
			}
			else
			{
				authorsMap.put(author, 1);
			}
		});
		for(Integer cpt : authorsMap.values())
		{
			if(cpt!=2)
			{
				return false;
			}
		}
		authorsMap.clear();
		
		Map<Long, Integer> isbnMap = new HashMap<Long, Integer>();
		this.getISBN().forEachRemaining(isbn -> isbnMap.put(isbn, 1));
		tmpObj.getISBN().forEachRemaining(isbn ->
		{
			if(isbnMap.containsKey(isbn))
			{
				isbnMap.replace(isbn, isbnMap.get(isbn)+1);
			}
		});
		for(Integer cpt : authorsMap.values())
		{
			if(cpt!=2)
			{
				return false;
			}
		}
		isbnMap.clear();
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean export(String filename) throws IOException
	{
		String newFilename = "";
		if(!filename.startsWith("data/"))
		{
			newFilename+="data/";
		}
		newFilename+=filename;
		
		JSONArray authors = new JSONArray();
		this.getAuthors().forEachRemaining(author -> authors.add(author));
		
		JSONObject book = new JSONObject();
		long isbn = this.getISBN().next();
		book.put("isbn", isbn);
		book.put("title", this.getTitle());
		book.put("authors", authors);
		book.put("editor", this.getEditors().next());
		book.put("type", this.getType().next());
		
		FileWriter file = new FileWriter(newFilename + isbn + ".json");
		file.write(book.toJSONString());
		file.flush();
		file.close();
		
		SearchHTML downloader = SearchHTML.getInstance();
		downloader.downloadCover(isbn, new File(newFilename + isbn + downloader.searchCover(isbn).next().getExtension()));
		
		return true;
	}
}
