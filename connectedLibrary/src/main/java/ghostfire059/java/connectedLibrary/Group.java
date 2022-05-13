package ghostfire059.java.connectedLibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Group implements LibraryEntity
{
	private Collection<LibraryEntity> _list = new ArrayList<LibraryEntity>();
	private String _title;
	
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
}
