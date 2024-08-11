package algo.linkedlist;

/**
 * 翻转链表
 * eg: 1->2->3->4->5->null to 5->4->3->2->1->null
 *
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
public class ReverseLinkedlist {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 头插法翻转
     *
     * @param node 头结点
     * @return 新的头结点
     */
    public static Node reverse(Node node) {
        Node head = node;
        Node pre = null;
        while (head != null) {
            Node next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    /**
     * 递归翻转
     * @param node 头节点
     * @return 新的头结点
     */
    public static Node reverse2(Node node) {
        if (node == null || node.next == null) return node;

        Node newHead = reverse2(node.next);
        node.next.next = node;
        node.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
//        while (head != null) {
//            System.out.println(head.value);
//            head = head.next;
//        }

        Node node = reverse2(head);
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }
}
