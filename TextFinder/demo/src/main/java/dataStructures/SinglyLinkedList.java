package dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for a SinglyLinkedList Node.
 */
class Node<T> {
    T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
        next = null;
    }
}

public class SinglyLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Deletes of content on the list.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Checks if the list is empty.
     *
     * @return If the list ir empty or not.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Gets the amount of elements contained on the list.
     *
     * @return The list size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if a given element is contained on the list.
     *
     * @param element Element to search on the list.
     * @return If the element is on the list or not.
     */
    public boolean contains(T element) {
        if (!isEmpty()) {
            Node<T> current = head;
            while (current.next != null) {
                if (current.value == element) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    /**
     * Adds a given element to the end of the list.
     *
     * @param element Element to add on the list.
     */
    public void add(T element) {
        Node<T> newNode = new Node<T>(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Remove a given element from the list.
     *
     * @param element Element to remove from the list.
     * @throws NoSuchElementException If the element is not contained on the list.
     */
    public void remove(T element) throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (head.value == element) {
            head = head.next;
            size--;
            return;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.value == element) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the stored value in a given index from the list.
     *
     * @param index Index to get the value.
     * @return The stored value in the given index.
     * @throws IndexOutOfBoundsException If the given index it's out of the list range.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> current = head;
        int currentIndex = 0;

        while (currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        return current.value;
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.value = element;
    }

    /**
     * Adds to the end all contained elements from a given SinglyLinkedList
     * @param list List to add to the actual list.
     */
    public void addAll(SinglyLinkedList<T> list) {
        int listSize = list.getSize();
        if (listSize > 0) {
            int currentIndex = 0;
            while (currentIndex < listSize) {
                T newElement = list.get(currentIndex);
                add(newElement);
                currentIndex++;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        Node<T> current = head;
        while (current != null) {
            result.append(current.value);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }


}
