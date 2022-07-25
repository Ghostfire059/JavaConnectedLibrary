package ghostfire059.java.connectedLibrary;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * JSON implementation of the ImportLibraryEntity
 * @author tberasateguy
 *
 */
public class ImportLibraryEntityJSON implements ImportLibraryEntity
{
	private static ImportLibraryEntityJSON _me = null;
	
	private ImportLibraryEntityJSON(){}
	
	/**
	 * Singleton design pattern
	 * Used to instanciate only one instance of the object
	 * @return the only instance of ImportLibraryEntityJSON
	 */
	public static ImportLibraryEntityJSON getInstance()
	{
		if(_me==null)
		{
			_me = new ImportLibraryEntityJSON();
		}
		return _me;
	}
	
	@Override
	public LibraryEntity importation(String filename) throws IOException
	{
		String newFilename = "";
		if(!filename.startsWith("data/"))
		{
			newFilename+="data/";
		}
		newFilename+=filename;
		Path path = Paths.get(newFilename);
		LibraryEntity libraryEntity;
		if(Files.isDirectory(path))
		{
			libraryEntity = this._groupImport(newFilename);
		}
		else
		{
			libraryEntity = this._bookImport(newFilename);
		}
		return libraryEntity;
	}
	
	private Group _groupImport(String filename) throws IOException
	{
		Group libraryEntity = new Group(filename.replace("data/", ""));
		File file = new File(filename);
		for(String path : file.list())
		{
			String newFilename = filename+path;
			if(Files.isDirectory(Paths.get(path)))
			{
				libraryEntity.addChild(this._groupImport(newFilename));
			}
			else
			{
				libraryEntity.addChild(this._bookImport(newFilename));
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Book _bookImport(String filename) throws IOException
	{
		if(!filename.endsWith(".json"))
		{
			filename = filename.concat(".json");
		}
		System.out.println(filename);
		Long isbn = null;
		String title = "";
		String editor = "";
		String type = "";
		Collection<String> realAuthors = new ArrayList<String>();
		try
		{
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(filename);
			JSONObject jsonBook= (JSONObject) parser.parse(reader);
			isbn = (Long) jsonBook.get("isbn");
			title = (String) jsonBook.get("title");
			editor = (String) jsonBook.get("editor");
			type = (String) jsonBook.get("type");
			JSONArray authors = (JSONArray) jsonBook.get("authors");
			authors.iterator().forEachRemaining(author -> realAuthors.add((String)author));
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return new Book(isbn, title, realAuthors, editor, type);
	}

}
