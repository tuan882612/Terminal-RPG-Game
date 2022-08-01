import java.util.Comparator;

public class ArrayIndexComparator implements Comparator<Integer> {
    private final Integer[] array;

    public ArrayIndexComparator(Integer[] array) {
        this.array = array;
    }

    public Integer[] createIndexArray() {
        Integer[] indices = new Integer[array.length];

        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        return indices;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return array[o2].compareTo(array[o1]);
    }
}
