package second.compiler.hash;

public class HashElement<T extends Hashable> {

    private final T element;
    private HashElement<T> nextElement;

    public HashElement(T element) {
        this.element = element;
        this.nextElement = null;
    }

    public T getElement() {
        return element;
    }

    @Override
    public String toString() {
        return element.getStringElement();
    }

    public HashElement<T> getNextElement() {
        return nextElement;
    }

    public void setNextElement(HashElement<T> nextElement) {
        this.nextElement = nextElement;
    }
}
