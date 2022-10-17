package ghostfire059.java.connectedLibrary;

import java.util.Collection;

/**
 * API used to export content from the project
 * For a better usage, you may use this API instead of create your own instances of classes
 * @author tberasateguy
 *
 */
public class Api
{
	public LibraryEntity createBook(long isbn, String title, Collection<String> authors, String editor, String type)
	{
		return new Book(isbn, title, authors, editor, type);
	}
	
	public Group createGroup(String title)
	{
		return new Group(title);
	}
	
	public ImportLibraryEntity importFromJSON()
	{
		return ImportLibraryEntityJSON.getInstance();
	}
	
	public SearchHTML searchFromInternet()
	{
		return SearchHTML.getInstance();
	}
	
	public Search searchLoadedContent(LibraryEntity entity)
	{
		return SearchInstance.getInstance(entity);
	}
	
	public Search searchLocal()
	{
		return SearchLocal.getInstance();
	}
}
