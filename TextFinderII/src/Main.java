import dataStructures.SinglyLinkedList;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();

        File file = new File("src/hello.txt");

        try {
            libraryManager.addFile(file);
            SinglyLinkedList<Document> documentList = libraryManager.getDocuments();
            Document doc = documentList.get(0);
            System.out.println(doc.getContent());
            System.out.println(doc.getFormattedDate());
            System.out.println(doc.getSize());



        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}