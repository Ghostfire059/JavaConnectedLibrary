package ghostfire059.java.connectedLibrary;

import java.io.IOException;
import java.util.Iterator;

/**
 * Interface used to store and use datas
 * @author tberasateguy
 *
 */
public interface LibraryEntity extends Iterable<LibraryEntity>, Cloneable
{
	/**
	 * Get the ISBN of the current LibraryEntity through an Iterator
	 * @return the Iterator of ISBN
	 */
	public Iterator<Long> getISBN();
	
	/**
	 * Get the title of the current LibraryEntity
	 * @return the title as a String
	 */
	public String getTitle();
	
	/**
	 * Set the title of the current LibraryEntity
	 * @param title
	 * @return true if done correctly, false else
	 */
	public boolean setTitle(String title);
	
	/**
	 * Get the authors of the current LibraryEntity through an Iterator
	 * @return the Iterator of the authors
	 */
	public Iterator<String> getAuthors();
	
	/**
	 * Get the editors of the current LibraryEntity through an Iterator
	 * @return the Iterator of the editors
	 */
	public Iterator<String> getEditors();
	
	/**
	 * Set the editor of the current LibraryEntity
	 * @param editor
	 * @return true if done correctly, false else
	 */
	public boolean setEditor(String editor);
	
	/**
	 * Get the type of the current LibraryEntity (shonen, shojo, seinen...) through an Iterator.
	 * @return the Iterator of the types
	 */
	public Iterator<String> getType();
	
	/**
	 * Set the editor of the current LibraryEntity
	 * @param type
	 * @return true if done correctly, false else
	 */
	public boolean setType(String type);
	
	/**
	 * Extends Cloneable so need to implement the clone() method, usefull to create new copy of a LibraryEntity with a new identity.
	 * @return a new LibraryEntity that is the same as the current one, except it's another object.
	 * @throws CloneNotSupportedException
	 */
	public LibraryEntity clone() throws CloneNotSupportedException;
	
	/**
	 * Export the current LibraryEntity as JSON file(s)
	 * @param filename
	 * @return true if done correctly, false else
	 * @throws IOException
	 */
	public boolean export(String filename) throws IOException;
	
	/**
	 * Get an Iterator through the Cover of the current LibraryEntity
	 * @return
	 */
	public Iterator<Cover> getCovers();
	
	/**
	 * Set the Cover of the actual LibraryEntity
	 * @param cover
	 * @return
	 */
	public boolean setCover(Cover cover);
}
