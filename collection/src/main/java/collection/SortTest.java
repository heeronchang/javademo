package collection;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
public class SortTest {
    static class Node {
        int value;
        String name;

        public String getName() {
            return name;
        }

        public Node(int value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node[] nodes = new Node[]{
                new Node(1, "a"),
                new Node(4, "d"),
                new Node(2, "b"),
                new Node(5, "e"),
                new Node(3, "c"),
        };

//        Arrays.sort(nodes, Comparator.comparingInt(o -> o.value));
        Arrays.sort(nodes, Comparator.comparing(Node::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println(Arrays.toString(nodes));

    }
}
