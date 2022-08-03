import java.util.ArrayList;
import java.util.Comparator;

public class ArrayIndexComparator implements Comparator<Integer> {
    private final ArrayList<Integer> array;

    public ArrayIndexComparator(ArrayList<Integer> arr) {
        array = arr;
    }

    public ArrayList<Integer> createIndexArray() {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            indices.add(i);
        }

        return indices;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return array.get(o2).compareTo(array.get(o1));
    }
}
