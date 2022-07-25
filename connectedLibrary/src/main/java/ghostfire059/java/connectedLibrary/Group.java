package ghostfire059.java.connectedLibrary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Composite design pattern
 * Uses to store multiple LibraryEntity and is also considered as one
 * @author tberasateguy
 *
 */
public class Group implements LibraryEntity
{
	private Collection<LibraryEntity> _list = new ArrayList<LibraryEntity>();
	private String _title;
	
	/**
	 * To create a Group, you only need a title
	 * @param title
	 */
	public Group(String title)
	{
		this._title = title;
	}
	
	@Override
	public Iterator<LibraryEntity> iterator()
	{
		return this._list.iterator();
	}

	@Override
	public Iterator<Long> getISBN()
	{
		Collection<Long> tmpISBNList = new ArrayList<Long>();
		this._list.forEach(entity ->
		{
			entity.getISBN().forEachRemaining(tmpISBN ->
			{
				tmpISBNList.add(tmpISBN);
			});
		});
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
		Iterator<LibraryEntity> it = this._list.iterator();
		if(it.hasNext())
		{
			return it.next().getAuthors();
		}
		return Collections.emptyIterator();
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
		
		Group tmpObj = (Group)obj;
		if(this._title!=tmpObj._title)
		{
			return false;
		}
		
		Map<LibraryEntity, Integer> libraryEntityMap = new HashMap<LibraryEntity, Integer>();
		this._list.forEach(entity ->
		{
			if(libraryEntityMap.containsKey(entity))
			{
				libraryEntityMap.replace(entity, libraryEntityMap.get(entity)+1);
			}
			else
			{
				libraryEntityMap.put(entity, 1);
			}
		});
		tmpObj._list.forEach(entity ->
		{
			if(libraryEntityMap.containsKey(entity))
			{
				libraryEntityMap.replace(entity, libraryEntityMap.get(entity)+1);
			}
			else
			{
				libraryEntityMap.put(entity, 1);
			}
		});
		for(Integer cpt : libraryEntityMap.values())
		{
			if(cpt%2!=0)
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public LibraryEntity clone() throws CloneNotSupportedException
	{
		Group newObject = (Group)super.clone();
		newObject._title = new String(this._title);
		this._list.forEach(libraryEntity -> {
			try
			{
				newObject._list.add((LibraryEntity)libraryEntity.clone());
			}
			catch(CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
		});
		return newObject;
	}

	@Override
	public Iterator<String> getEditors()
	{
		Collection<String> tmpEditor = new HashSet<String>();
		this._list.forEach(libraryEntity -> 
		{
			Iterator<String> childEditor = libraryEntity.getEditors();
			while(childEditor.hasNext())
			{
				tmpEditor.add(childEditor.next());
			}
		});
		return tmpEditor.iterator();
	}

	@Override
	public boolean setEditor(String editor)
	{
		this._list.forEach(libraryEntity -> libraryEntity.setEditor(editor));
		return true;
	}

	@Override
	public Iterator<String> getType()
	{
		Collection<String> tmpType = new HashSet<String>();
		this._list.forEach(libraryEntity ->
		{
			Iterator<String> childType = libraryEntity.getType();
			while(childType.hasNext())
			{
				tmpType.add(childType.next());
			}
		});
		return tmpType.iterator();
	}

	@Override
	public boolean setType(String type)
	{
		this._list.forEach(libraryEntity -> libraryEntity.setType(type));
		return true;
	}
	
	/**
	 * add a LibraryEntity to the current Group as a child
	 * @param libraryEntity
	 * @return true if done correctly, false else
	 */
	public boolean addChild(LibraryEntity libraryEntity)
	{
		return this._list.add(libraryEntity);
	}
	
	/**
	 * remove a LibraryEntity to the current Group
	 * @param libraryEntity
	 * @return true if done correctly, false else
	 */
	public boolean removeChild(LibraryEntity libraryEntity)
	{
		return this._list.remove(libraryEntity);
	}

	@Override
	public boolean export(String filename) throws IOException
	{
		String newFilename = filename + this._title + "/";
		Path path = Paths.get(newFilename);
		if(!Files.exists(path))
		{			
			Files.createDirectory(path);
		}
		
		for(LibraryEntity libraryEntity : this._list)
		{
			libraryEntity.export(newFilename);
		}
		return true;
	}
}
