import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LongMapImpl<V> implements LongMap<V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private long size = 0;
    private Node<V>[] storage = new Node[INITIAL_CAPACITY];
    private int threshold = (int) (storage.length * LOAD_FACTOR);

    public V put(long key, V value) {
        checkSize();
        int index = getIndex(key);
        Node<V> currentNode = storage[index];
        while (currentNode != null) {
            if (key == currentNode.key || currentNode.key != null && currentNode.key.equals(key)) {
                break;
            }
            currentNode = currentNode.next;
        }
        if (currentNode != null) {
            currentNode.value = value;
        } else {
            Node<V> newNode = new Node<>(key, value, storage[index]);
            storage[index] = newNode;
            size++;
        }
        return value;
    }

    public V get(long key) {
        int index = getIndex(key);
        for (Node<V> node = storage[index]; node != null; node = node.next) {
            if (Objects.equals(key, node.key)) {
                return node.value;
            }
        }
        return null;
    }

    public V remove(long key) {
        int index = getIndex(key);
        Node<V> node = storage[index];
        if (node != null) {
            if (Objects.equals(key, node.key)) {
                storage[index] = node.next;
                size--;
                return node.value;
            }
            for (; node.next != null; node = node.next) {
                if (Objects.equals(key, node.next.key)) {
                    V value = node.next.value;
                    node.next = node.next.next;
                    size--;
                    return value;
                }
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                continue;
            }
            Node<V> node = storage[i];
            if (node != null) {
                if (key == node.key) {
                    return true;
                }
                for (; node.next != null; node = node.next) {
                    if (key == node.next.key) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                continue;
            }
            Node<V> node = storage[i];
            if (node != null) {
                if (value.equals(node.value)) {
                    return true;
                }
                for (; node.next != null; node = node.next) {
                    if (value.equals(node.next.value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public long[] keys() {
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                continue;
            }
            Node<V> node = storage[i];
            if (node != null) {
                result.add(node.key);
            }
            for (; node.next != null; node = node.next) {
                result.add(node.next.key);
            }
        }
        long[] keys = new long[result.size()];
        for (int i = 0; i < result.size(); i++) {
            keys[i] = result.get(i);
        }
        return keys;
    }

    public V[] values() {
        List<V> result = new ArrayList<>();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                continue;
            }
            Node<V> node = storage[i];
            if (node != null) {
                result.add(node.value);
            }
            for (; node.next != null; node = node.next) {
                result.add(node.next.value);
            }
        }
        V[] values = (V[]) new Object[result.size()];
        for (int i = 0; i < result.size(); i++) {
            values[i] = result.get(i);
        }
        return values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        storage = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    private void checkSize() {
        if (size >= threshold) {
            resize();
        }
    }

    private int getIndex(Long key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % storage.length;
    }

    private void resize() {
        Node<V>[] newStorage = new Node[storage.length * 2];
        threshold = (int) (newStorage.length * LOAD_FACTOR);
        size = 0;
        Node<V>[] oldBuckets = storage;
        storage = newStorage;
        for (Node<V> oldNode : oldBuckets) {
            while (oldNode != null) {
                put(oldNode.key, oldNode.value);
                oldNode = oldNode.next;
            }
        }
    }

    private static class Node<V> {
        Long key;
        V value;
        Node<V> next;

        public Node(Long key, V value, Node<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
