package algo.sort;

/**
 * SelectionSort
 * https://www.hello-algo.com/chapter_sorting/selection_sort/
 * 每轮从未排序区间选择最小的元素，将其放到已排序区间的末尾
 */
public class Selection {
    public static void sort2(int[] arr) {
        int len = arr.length;
        // i < len - 1; 最后一个元素不需要再排序
        for (int i = 0; i < len - 1; i++) {
            int min = i; // 记录最小值的索引，避免每次找到比arr[i]小的元素都进行一次交换
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            // 最后找到最小的元素，进行一次交换即可
            int tmp = arr[i];
            arr[i] = arr[min];
            arr[min] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 5, 4, 6, 9, 7, 8, 1, 2};
        Selection.sort2(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
