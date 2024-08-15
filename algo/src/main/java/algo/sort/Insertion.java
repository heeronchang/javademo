package algo.sort;

import java.util.Arrays;

/**
 * insertSort
 * 工作原理与手动整理一副牌的过程非常相似。
 * 具体来说，我们在未排序区间选择一个基准元素，
 * 将该元素与其左侧已排序区间的元素逐一比较大小，并将该元素插入到正确的位置
 * https://www.hello-algo.com/chapter_sorting/insertion_sort/
 */
public class Insertion {
    public static void sort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j-1]) {
                    int tmp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5,2,3,6,7,1,9};
        Insertion.sort(arr);

        Arrays.stream(arr).forEach(System.out::println);
    }
}
