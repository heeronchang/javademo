package algo.sort;

import java.util.Arrays;

public class Quick {
    public static void sort(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        sort(nums, left, right);
    }

    private static void sort(int[] nums, int left, int right) {
        if (left >= right) { // 子数组长度为1时终止递归
            return;
        }
        // 哨兵划分
        int pivot = partition(nums, left, right);
        sort(nums, left, pivot - 1);
        sort(nums, pivot + 1, right);
    }

    private static int partition(int[] nums, int left, int right) {
        int i = left, j = right; // 以nums[left] 为基准数

        while (i < j) {
            while (i < j && nums[j] >= nums[left]) { // 从右向左找首个小于基准数的元素
                j--;
            }
            while (i < j && nums[i] <= nums[left]) { // 从左向右找首个大于基准数的元素
                i++;
            }
            swap(nums, i, j); // 交换这两个元素
        }
        swap(nums, i, left); // 将基准数交换至两子数组的分界线

        return i; // 返回基准数的索引
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int arr[] = {4, 5, 2, 8, 1, 6, 9, 7, 3};
        Quick.sort(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }
}
