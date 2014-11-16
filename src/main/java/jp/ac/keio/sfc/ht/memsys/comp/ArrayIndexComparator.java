package jp.ac.keio.sfc.ht.memsys.comp;

import java.util.Comparator;

/**
 * Created by aqram on 6/23/14.
 * 逆順にソートする
 */
public class ArrayIndexComparator implements Comparator<Integer> {
    private final double[] array;

    public ArrayIndexComparator(double[] array) {
        this.array = array;
    }

    public Integer[] createIndexArray() {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2) {
        if (array[index1] > array[index2]) {
            return 1;
        } else if (array[index1] < array[index2]) {
            return -1;
        } else {
            return 0;
        }
    }

}
