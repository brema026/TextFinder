import dataStructures.SinglyLinkedList;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        int element = 2;
        list.add(element);
        try {
            list.remove(56);
        } catch (NoSuchElementException e) {
            logger.log(Level.WARNING, "You can't remove the element " + element + " because is not contained on the list.", e);
        }
    }
}