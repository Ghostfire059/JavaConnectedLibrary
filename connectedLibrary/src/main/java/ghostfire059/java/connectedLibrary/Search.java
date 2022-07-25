package ghostfire059.java.connectedLibrary;

import java.util.Iterator;

/**
 * Interface used to group different ways to search datas such as HTML or in our database for example
 * @author tberasateguy
 *
 */
public interface Search
{
	/**
	 * 
	 * @param isbn
	 * @return
	 */
	public String searchTitle(long isbn);
	
	/**
	 * 
	 * @param isbn
	 * @return
	 */
	public Iterator<String> searchAuthors(long isbn);
	
	/**
	 * 
	 * @param isbn
	 * @return
	 */
	public String searchEditor(long isbn);
	
	/**
	 * 
	 * @param isbn
	 * @return
	 */
	public String searchType(long isbn);
}
