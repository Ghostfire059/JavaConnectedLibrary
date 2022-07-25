package ghostfire059.java.connectedLibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Abstract LibraryEntity used to have a good inheritence tree and don't rewrite code
 * @author tberasateguy
 *
 */
public abstract class LibraryEntityAbstract implements LibraryEntity
{
	private long _isbn;
	private String _title;
	private Collection<String> _authors = new ArrayList<String>();
	private String _editor;
	private String _type;
	private String _coverFilename = null;
	private String _imgExt = ".jpg";
	
	public LibraryEntityAbstract(long isbn, String title, Collection<String> authors, String editor, String type)
	{
		this._isbn = isbn;
		this._title = title;
		this._authors = authors;
		this._editor = editor;
		this._type = type;
		this._coverFilename = String.valueOf(this._isbn).concat(this._imgExt);
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
		LibraryEntityAbstract newObject = (LibraryEntityAbstract)super.clone();
		newObject._isbn = Long.valueOf(this._isbn);
		newObject._title = new String(this._title);
		this._authors.forEach(author ->
		{
			newObject._authors.add(author);
		});
		return newObject;
	}

	@Override
	public Iterator<String> getEditors()
	{
		Collection<String> tmpEditors = new ArrayList<String>();
		tmpEditors.add(this._editor);
		return tmpEditors.iterator();
	}

	@Override
	public boolean setEditor(String editor)
	{
		this._editor = editor;
		return true;
	}

	@Override
	public Iterator<String> getType()
	{
		Collection<String> tmpType = new ArrayList<String>();
		tmpType.add(this._type);
		return tmpType.iterator();
	}

	@Override
	public boolean setType(String type)
	{
		this._type = type;
		return true;
	}
	
	@Override
	public Iterator<String> getCoversFilenames()
	{
		Collection<String> tmpCoversPathsList = new ArrayList<String>();
		tmpCoversPathsList.add(this._coverFilename);
		return tmpCoversPathsList.iterator();
	}
}
