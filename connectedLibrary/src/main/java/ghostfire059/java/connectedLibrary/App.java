package ghostfire059.java.connectedLibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
        
        long isbn = 2377173446L;
        
        System.out.println("TEST ImportLibraryEntityJSON");
        ImportLibraryEntity importer = ImportLibraryEntityJSON.getInstance();
        LibraryEntity book = importer.importation(String.valueOf(isbn));
        System.out.println(book.getTitle());
        
        //result different as expected
        System.out.println("\nTEST SearchHTML");
        Search html = SearchHTML.getInstance();
        System.out.println(html.searchTitle(isbn));
        
        
        System.out.println("\nTEST SearchInstance");
        Search instance = SearchInstance.getInstance(book);
        System.out.println(instance.searchTitle(isbn));
        
        System.out.println("\nTEST SearchLocal");
        Search local = SearchLocal.getInstance();
        System.out.println(local.searchTitle(isbn));
        
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }

}