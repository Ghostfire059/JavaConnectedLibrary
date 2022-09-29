package ghostfire059.java.connectedLibrary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * API used to gather informations of an ISBN from our local database
 * @author tberasateguy
 *
 */
public class SearchLocal implements Search
{
	private static SearchLocal _me = null;
	private String _data = "data/";
	
	private String _actualTitle;
	private Collection<String> _actualAuthors = new ArrayList<String>();
	private String _actualEditor;
	private String _actualType;
	private Collection<Cover> _actualCovers = new ArrayList<Cover>();
	
	private Long _actualISBN = 0L;
	
	private SearchLocal(){}
	
	/**
	 * Singleton design pattern
	 * Used to instanciate only one instance of the object
	 * @return the only one instance of SearchLocal
	 */
	public static Search getInstance()
	{
		if(_me==null)
		{
			_me = new SearchLocal();
		}
		return _me;
	}
	
	private boolean _search(long isbn, String filename)
	{
		this._actualISBN = isbn;
		File dataFolder = new File(filename);
		for(String path : dataFolder.list())
		{
			String newPath = filename + path;
			Path actualPath = Paths.get(newPath);
			if(Files.isDirectory(actualPath))
			{
				this._actualCovers.clear();
				this._search(isbn, newPath);
			}
			else
			{
				//the file corresponding isbn exists
				String pathString = actualPath.toString();
				if(Files.exists(actualPath) && pathString.contains(String.valueOf(isbn)) && pathString.contains(".json"))
				{
					ImportLibraryEntity jsonImport = ImportLibraryEntityJSON.getInstance();
					try
					{
						//import the book from json
						LibraryEntity book = jsonImport.importation(actualPath.toString());
						
						//read and fill the infos
						this._actualTitle = book.getTitle();
						
						this._actualAuthors.clear();
						book.getAuthors().forEachRemaining(author -> this._actualAuthors.add(author));
						
						this._actualEditor = "";
						book.getEditors().forEachRemaining(editor -> this._actualEditor.concat(editor));
						
						this._actualType = "";
						book.getType().forEachRemaining(type -> this._actualType.concat(type));
						
						
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				else if(Files.exists(actualPath) && pathString.contains(String.valueOf(isbn)) && pathString.contains(".jpg"))
				{
					this._actualCovers.add(new Cover(actualPath));
				}
				else
				{
					return false;
				}	
			}	
		}
		return true;
	}
	
	@Override
	public String searchTitle(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn, this._data);
		}
		return this._actualTitle;
	}

	@Override
	public Iterator<String> searchAuthors(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn, this._data);
		}
		return this._actualAuthors.iterator();
	}

	@Override
	public String searchEditor(long isbn)
	{
		// if(this._actualISBN!=isbn)
		{
			this._search(isbn, this._data);
		}
		return this._actualEditor;
	}

	@Override
	public String searchType(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn, this._data);
		}
		return this._actualType;
	}

	@Override
	public Iterator<Cover> searchCover(long isbn)
	{
		// TODO Auto-generated method stub
		if(this._actualISBN!=isbn)
		{
			this._search(isbn, this._data);
		}
		return this._actualCovers.iterator();
	}

}
