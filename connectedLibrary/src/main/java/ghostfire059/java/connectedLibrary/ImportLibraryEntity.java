package ghostfire059.java.connectedLibrary;

import java.io.IOException;

/**
 * Interface used to group different ways to import datas such as JSON or XML for example
 * @author tberasateguy
 *
 */
public interface ImportLibraryEntity
{
	public LibraryEntity importation(String filename) throws IOException;
}
