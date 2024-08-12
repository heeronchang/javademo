package algo.sort;

import java.util.Arrays;

/**
 * 每次找到一个最小的元素，放到已排序元素的末尾
 */
public class Insertion {
    public static void sort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int minIdx = i;
            for (int j = i; j < len - 1; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {5,2,3,6,7,1,9};
        Insertion.sort(arr);

        Arrays.stream(arr).forEach(System.out::println);
    }
}
