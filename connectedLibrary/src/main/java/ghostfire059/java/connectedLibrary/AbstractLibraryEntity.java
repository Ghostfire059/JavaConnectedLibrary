package ghostfire059.java.connectedLibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public abstract class AbstractLibraryEntity implements LibraryEntity
{
	private long _isbn;
	private String _title;
	private Collection<String> _authors = new ArrayList<String>();
	
	public AbstractLibraryEntity(long isbn, String title, Collection<String> authors)
	{
		this._isbn = isbn;
		this._title = title;
		this._authors = authors;
	}

	@Override
	public Iterator<LibraryEntity> iterator()
	{
		return Collections.emptyIterator();
	}

	@Override
	public Iterator<Long> getISBN()
	{
		Collection<Long> tmpISBNList = new ArrayList<Long>();
		tmpISBNList.add(this._isbn);
		return tmpISBNList.iterator();
	}

	@Override
	public String getTitle()
	{
		return this._title;
	}

	@Override
	public boolean setTitle(String title)
	{
		this._title = title;
		return true;
	}

	@Override
	public Iterator<String> getAuthors()
	{
		return this._authors.iterator();
	}
	
	@Override
	public LibraryEntity clone() throws CloneNotSupportedException
	{
		AbstractLibraryEntity newObject = (AbstractLibraryEntity)super.clone();
		newObject._isbn = Long.valueOf(this._isbn);
		newObject._title = new String(this._title);
		this._authors.forEach(author ->
		{
			newObject._authors.add(author);
		});
		return newObject;
	}
}
