package ghostfire059.java.connectedLibrary;

import java.util.Iterator;

public interface Search
{
	public String searchTitle(long isbn);
	public Iterator<String> searchAuthors(long isbn);
}
