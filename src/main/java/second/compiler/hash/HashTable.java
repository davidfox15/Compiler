package second.compiler.hash;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable<T extends Hashable> {

    private final List<HashElement<T>> table;
    private final int size;


    public HashTable(int size) {
        table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            table.add(null);
        }
        this.size = size;
    }

    private int hash(String str) {
        char[] chars = str.toCharArray();
        int sum = 0;
        for (char aChar : chars) {
            sum += (int)aChar;
        }
        return sum % size;
    }


    public T getElement(String key) {
        int index = hash(key);
        HashElement<T> hashElement = table.get(index);
        if (hashElement != null) {
            do {
                if (hashElement.getElement().getStringElement().equals(key)) {
                    return hashElement.getElement();
                }
                hashElement = hashElement.getNextElement();
            } while (hashElement != null);
        }
        return null;
    }

    public T addElement(T value) {
        int index = hash(value.getStringElement());
        HashElement<T> hashElement = table.get(index);
        if (hashElement == null) {
            table.set(index, new HashElement<>(value));
        } else {
            HashElement<T> next = hashElement;
            do {
                hashElement = next;
                if (hashElement.getElement().getStringElement().equals(value.getStringElement())) {
                    return value;
                }
                next = next.getNextElement();
            } while (next != null);
            try {
                hashElement.setNextElement(new HashElement<>(value));
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

        }
        return value;
    }


    public List<T> toList() {
        List<T> list = new LinkedList<>();
        for (HashElement<T> hashElement : table) {
            if (hashElement != null) {
                do {
                    list.add(hashElement.getElement());
                    hashElement = hashElement.getNextElement();
                } while (hashElement != null);
            }
        }
        return list;
    }
}
