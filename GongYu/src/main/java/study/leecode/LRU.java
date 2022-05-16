package study.leecode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class LRU {
    private Map<Integer, ListNode> map;
    //双链表结点类
    private class ListNode {
        private Integer key;
        private Integer value;
        private ListNode pre;
        private ListNode next;
        public ListNode() {
        }
        public ListNode(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
    private int capacity;
    private ListNode dummyHead;
    private ListNode dummyTail;

    public LRU(int capacity) {
        map = new HashMap<>(capacity);
        this.capacity = capacity;
        dummyHead = new ListNode(-1, -1);
        dummyTail = new ListNode(-1, -1);
        // 初始化链表为 head <-> tail
        dummyHead.next = dummyTail;
        dummyTail.pre = dummyHead;
    }

    //  如果存在，把当前结点移动到双向链表的头部
    public int get(int key) {
        if (map.containsKey(key)) {
            ListNode node = map.get(key);
            int val = node.value;
            // 把当前node移动到双向链表的头部
            moveToHead(node);
            return val;
        } else {
            return -1;
        }
    }

    //如果哈希表的容量满了，就要删除一个链表末尾元素，然后在链表头部插入新元素
    public void put(int key, int value) {
        ListNode node = map.get(key);
        if (map.containsKey(key)) {
            // 1、更新value
            map.get(key).value = value;
            // 2、把当前node移动到双向链表的头部
            moveToHead(node);
            return;
        }else{
            // 添加元素
            ListNode newNode = new ListNode(key, value);
            map.put(key, newNode);
            addToHead(newNode);
            if (map.size() > capacity) {
                // 如果满了
                ListNode oldTail = removeTail();
                // 设计key就是为了在这里删除
                map.remove(oldTail.key);
            }
        }
    }

    private void removeNode(ListNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void moveToHead(ListNode node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(ListNode node) {
        node.pre = dummyHead;
        node.next = dummyHead.next;
        dummyHead.next.pre = node;
        dummyHead.next = node;
    }

    // 删除双链表尾部结点
    private ListNode removeTail() {
        ListNode res = dummyTail.pre;
        removeNode(res);
        return res;
    }



    public static void main(String[] args) {
        LRU cache = new LRU(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.map.keySet());

        int res1 = cache.get(1);
        System.out.println(res1);

        cache.put(3, 3);

        int res2 = cache.get(2);
        System.out.println(res2);

        int res3 = cache.get(3);
        System.out.println(res3);

        cache.put(4, 4);
        System.out.println(cache.map.keySet());

        int res4 = cache.get(1);
        System.out.println(res4);

        int res5 = cache.get(3);
        System.out.println(res5);

        int res6 = cache.get(4);
        System.out.println(res6);
    }
}

