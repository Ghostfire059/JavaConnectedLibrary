package ghostfire059.java.connectedLibrary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Book extends AbstractLibraryEntity
{

	public Book(long isbn, String title, Collection<String> authors)
	{
		super(isbn, title, authors);
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
}
