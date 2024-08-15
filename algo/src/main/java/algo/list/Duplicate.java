package algo.list;

import java.util.*;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/14
 */
public class Duplicate {
    public static List<Integer> findDuplicates(int[] nums) {
        Set<Integer> result = new HashSet<>();

        int len = nums.length;
        int i = 0;
        while(i < len) {
            if (nums[i]-1 == i) {
                i++;
                continue;
            }

            if (nums[nums[i]-1] == nums[i]) {
                result.add(nums[i]);
                i ++;
            } else {
                int tmp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i];
                nums[i] = tmp;
            }
        }

        return new ArrayList<>(result);
    }

    public static void main(String[] args) {
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(findDuplicates(nums));
    }
}
