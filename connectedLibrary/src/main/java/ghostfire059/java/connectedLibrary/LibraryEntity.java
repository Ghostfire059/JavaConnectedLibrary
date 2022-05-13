package ghostfire059.java.connectedLibrary;

import java.util.Iterator;

public interface LibraryEntity extends Iterable<LibraryEntity>, Cloneable
{
	public Iterator<Long> getISBN();
	public String getTitle();
	public boolean setTitle(String title);
	public Iterator<String> getAuthors();
	public LibraryEntity clone() throws CloneNotSupportedException;
}
