package algo.sort;

/**
 * 通过连续地比较与交换相邻元素实现排序，每轮在剩余对n-i个元素中冒出一个最大的
 */
public class Bubble {
    public static void sort(int[] arr) {
        int len = arr.length;

        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void sort2(int[] arr) {
        int len = arr.length;
        // len -i; 因为每次循环都会找到一个最大的值，所以最后i个元素是从小到大排好序的
        for (int i = 0; i < len - 1; i++) {
            boolean flag = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 6, 5, 8, 1, 9};
        Bubble.sort2(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
