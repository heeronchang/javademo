package interview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将列表还原为树
 *
 * @author 叽哒嘎叽
 * @since 2024/8/13
 */
public class List2Tree {
    static class TreeNode {
        int id;
        int val;
        int pId;
        List<TreeNode> children;

        public TreeNode(int id, int val, int pId) {
            this.id = id;
            this.val = val;
            this.pId = pId;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "id=" + id +
                    ", val=" + val +
                    ", pId=" + pId +
                    ", children=" + children +
                    '}';
        }
    }

    public static TreeNode list2Tree(List<TreeNode> list, int pId) {
        TreeNode root = list.stream().filter(node -> node.id == pId).findFirst().orElse(null);
        if (root == null) {
            return null;
        }

        List<TreeNode> children = list.stream()
                .filter(node -> node.pId == pId)
                .peek(node -> list2Tree(list, node.id))
                .collect(Collectors.toList());
        root.children = children;

        return root;
    }

    public static void main(String[] args) {
        List<TreeNode> list = new ArrayList<>();
        int[] ids = {1, 2, 3, 4};
        int[] pIds = {0, 1, 2, 2};
        int[] vals = {1, 2, 3, 4};
        for (int i = 0; i < ids.length; i++) {
            TreeNode node = new TreeNode(ids[i], vals[i], pIds[i]);
            list.add(node);
        }

        TreeNode root = List2Tree.list2Tree(list, 1);
        System.out.println(root);
    }
}
