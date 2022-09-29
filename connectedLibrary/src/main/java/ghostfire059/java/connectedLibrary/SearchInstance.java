package ghostfire059.java.connectedLibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * API used to gather informations of an ISBN from our instance of database
 * @author tberasateguy
 *
 */
public class SearchInstance implements Search
{
	private static SearchInstance _me = null;
	private static LibraryEntity _entity = null;
	
	private String _actualTitle;
	private Collection<String> _actualAuthors = new ArrayList<String>();
	private String _actualEditor;
	private String _actualType;
	private Collection<Cover> _actualCovers = new ArrayList<Cover>();
	
	private Long _actualISBN = 0L;
	
	private SearchInstance(){}
	
	/**
	 * Singleton design pattern
	 * Used to instanciate only one instance of the object
	 * @return the only one instance of SearchInstance
	 */
	public static Search getInstance(LibraryEntity entity)
	{
		if(_me==null)
		{
			_me = new SearchInstance();
		}
		_entity = entity;
		return _me;
	}
	
	/**
	 * iterate over the entities of the entity
	 * entity.iterator.hasNext()
	 * 	True : entity is a group -> re-_search on it
	 * 	False : entity is a Book -> gather informations from it
	 * @param isbn
	 * @return
	 */
	private boolean _search(long isbn)
	{
		this._actualISBN = isbn;
		LibraryEntity oldEntity = _entity;
		Iterator<LibraryEntity> iterator = _entity.iterator();
		if(iterator.hasNext())
		{
			SearchInstance.getInstance(iterator.next());
			if(this._search(isbn))
			{
				return true;
			}
		}
		else
		{
			if(_entity.getISBN().next()==isbn)
			{
				this._actualTitle = _entity.getTitle();
				
				this._actualAuthors.clear();
				_entity.getAuthors().forEachRemaining(author -> this._actualAuthors.add(author.concat(",")));
				
				this._actualEditor = "";
				_entity.getEditors().forEachRemaining(editor -> this._actualEditor.concat(editor.concat(",")));
				
				this._actualType = "";
				_entity.getType().forEachRemaining(type -> this._actualEditor.concat(type.concat(",")));
				
				this._actualCovers.clear();
				_entity.getCovers().forEachRemaining(cover -> this._actualCovers.add(cover));
				
				_entity = oldEntity;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String searchTitle(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn);			
		}
		return this._actualTitle;
	}

	@Override
	public Iterator<String> searchAuthors(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn);			
		}
		return this._actualAuthors.iterator();
	}

	@Override
	public String searchEditor(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn);			
		}
		return this._actualEditor;
	}

	@Override
	public String searchType(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn);			
		}
		return this._actualType;
	}

	@Override
	public Iterator<Cover> searchCover(long isbn)
	{
		if(this._actualISBN!=isbn)
		{
			this._search(isbn);
		}
		return this._actualCovers.iterator();
	}

}
